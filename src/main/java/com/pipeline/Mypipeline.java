package com.pipeline;

import com.google.api.services.bigquery.model.*;
import com.google.gson.Gson;
import org.apache.beam.runners.dataflow.DataflowRunner;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO;
import org.apache.beam.sdk.io.gcp.pubsub.PubsubIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.*;
import org.apache.beam.sdk.transforms.windowing.FixedWindows;
import org.apache.beam.sdk.transforms.windowing.Window;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PCollectionTuple;
import org.apache.beam.sdk.values.Row;
import org.apache.beam.sdk.values.TupleTag;
import org.apache.beam.sdk.values.TupleTagList;
import org.joda.time.Duration;
import org.apache.beam.sdk.schemas.Schema;
import java.io.Serializable;


public class Mypipeline {

    static final TupleTag<RecordSchema> VALID_Messages = new TupleTag<RecordSchema>() {};
    static final TupleTag<String> INVALID_Messages = new TupleTag<String>() {};

    public static void main(String[] args) throws Exception {

        MyOptions options = PipelineOptionsFactory.fromArgs(args).withValidation().as(MyOptions.class);

        options.setStreaming(true);
        options.setRunner(DataflowRunner.class);
        options.setProject("di-gcp-351221");

        TableReference tableSpec = new TableReference()
                .setProjectId("di-gcp-351221")
                .setDatasetId("DI_POC_Team1")
                .setTableId("fraud_call_records");


        runPipeline(options, tableSpec);
    }

    public static class CommonLog extends DoFn<String, RecordSchema> implements Serializable {

        public static PCollectionTuple convert(PCollection<String> input) throws Exception {
            return input.apply("JsonToCommonLog", ParDo.of(new DoFn<String, RecordSchema>() {

                @ProcessElement
                public void processElement(@Element String record, ProcessContext context) {
                    String[] rec = record.split(",");
                    if (rec[rec.length - 1].equals(" 'DateTime': 'F F'")) {
                        try {
                            Gson gson = new Gson();
                            RecordSchema commonLog;
                            commonLog = gson.fromJson(record, RecordSchema.class);
                            context.output(VALID_Messages, commonLog);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        context.output(INVALID_Messages, record);
                    }
                }
            }).withOutputTags(VALID_Messages, TupleTagList.of(INVALID_Messages)));
        }
    }

    public static final Schema BQSchema_1 = Schema
            .builder()
            .addStringField("CallingIMSI")
            .addStringField("CalledIMSI")
            .addStringField("SwitchNum")
            .addStringField("CalledNum")
            .addStringField("CallingNum")
            .addStringField("DataTime")
            .addInt64Field("CallPeriod").build();


    static public void runPipeline(MyOptions options, TableReference tableSpec) throws Exception {

        Pipeline p = Pipeline.create(options);

        PCollection<String> pubSubMessages = p.apply("ReadMessages", PubsubIO.readStrings()
                                            .fromSubscription(options.getInputSubscriptionName())).

                apply("WindowOfOneMin", Window.into(FixedWindows.of(Duration.standardSeconds(60L))));

        PCollectionTuple TaggedMessages = CommonLog.convert(pubSubMessages);

        PCollection<String> TableRec = TaggedMessages.get(VALID_Messages).
                apply("BigQueryTableSchema", ParDo.of(new DoFn<RecordSchema, TableRecord>() {
                    @ProcessElement
                    public void processElement(RecordSchema log, OutputReceiver<TableRecord> o) {
                        TableRecord tableRecord = new TableRecord(log.CallingIMSI,log.CalledIMSI, log.SwitchNum,log.CallingNum,log.CalledNum,log.CallPeriod,log.DateTime);
                        o.output(tableRecord);
                    }
                }))
                
                .apply("CommonLogToJson", ParDo.of(new DoFn<TableRecord, String>() {
                    @ProcessElement
                    public void processElement(@Element TableRecord record, OutputReceiver<String> o) {
                        Gson gObj = new Gson();
                        String jsonString = gObj.toJson(record);
                        o.output(jsonString);
                    }
                }));


        PCollection<Row> Rows1 = TableRec.apply("JsonToRow", JsonToRow.withSchema(BQSchema_1));


        Rows1.apply("WriteToBigQuery", BigQueryIO.<Row>write().to(tableSpec)
                .useBeamSchema()
                .withCreateDisposition(BigQueryIO.Write.CreateDisposition.CREATE_NEVER)
                .withWriteDisposition(BigQueryIO.Write.WriteDisposition.WRITE_APPEND));


        TaggedMessages.get(INVALID_Messages)
//                .apply("WriteToDeadLetterTopic", PubsubIO.writeStrings().to(options.getDLQTopicName()));
                .apply("WriteToCloudStorage", TextIO.write().to(options.getDLQTopicName()+"/rec")
                        .withSuffix(".txt")
                        .withWindowedWrites()
                        .withNumShards(1));

        p.run().waitUntilFinish();

    }

}
