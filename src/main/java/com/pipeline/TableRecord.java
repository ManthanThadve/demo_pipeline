package com.pipeline;

import org.apache.beam.sdk.schemas.JavaFieldSchema;
import org.apache.beam.sdk.schemas.annotations.DefaultSchema;
import org.apache.beam.sdk.schemas.annotations.SchemaCreate;

@DefaultSchema(JavaFieldSchema.class)
public class TableRecord {

    public String CallingIMSI;
    public String CalledIMSI;
    public String SwitchNum;
    public String CallingNum;
    public String CalledNum;
    public int CalledPeriod;
    public String DateTime;

    @SchemaCreate
    public TableRecord(String callingIMSI, String calledIMSI, String switchNum, String callingNum,
                       String calledNum, int calledPeriod, String dateTime) {
        CallingIMSI = callingIMSI;
        CalledIMSI = calledIMSI;
        SwitchNum = switchNum;
        CallingNum = callingNum;
        CalledNum = calledNum;
        CalledPeriod = calledPeriod;
        DateTime = dateTime;
    }

    public String getCallingIMSI() {
        return CallingIMSI;
    }

    public void setCallingIMSI(String callingIMSI) {
        CallingIMSI = callingIMSI;
    }

    public String getCalledIMSI() {
        return CalledIMSI;
    }

    public void setCalledIMSI(String calledIMSI) {
        CalledIMSI = calledIMSI;
    }

    public String getSwitchNum() {
        return SwitchNum;
    }

    public void setSwitchNum(String switchNum) {
        SwitchNum = switchNum;
    }

    public String getCallingNum() {
        return CallingNum;
    }

    public void setCallingNum(String callingNum) {
        CallingNum = callingNum;
    }

    public String getCalledNum() {
        return CalledNum;
    }

    public void setCalledNum(String calledNum) {
        CalledNum = calledNum;
    }

    public int getCalledPeriod() {
        return CalledPeriod;
    }

    public void setCalledPeriod(int calledPeriod) {
        CalledPeriod = calledPeriod;
    }

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }
}
