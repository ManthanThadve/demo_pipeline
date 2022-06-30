package com.pipeline;

import org.apache.beam.runners.dataflow.options.DataflowPipelineOptions;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;

public interface MyOptions extends DataflowPipelineOptions, PipelineOptions {

    @Description("Input Subscription Name")
    void setInputSubscriptionName(String inputSubscriptionName);
    String getInputSubscriptionName();

    @Description("Dead letter Topic name")
    void setDLQTopicName(String DLQTopicName);
    String getDLQTopicName();

}
