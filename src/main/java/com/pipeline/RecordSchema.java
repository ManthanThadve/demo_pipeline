package com.pipeline;

//import org.apache.beam.sdk.schemas.JavaFieldSchema;
//import org.apache.beam.sdk.schemas.annotations.DefaultSchema;
//import org.apache.beam.sdk.schemas.annotations.SchemaCreate;
import java.io.Serializable;

public class RecordSchema implements Serializable{
    public String systemIdentity;
    public int RecordType;
    public int TimeType;
    public int ServiceType;
    public int EndType;
    public int OutgoingTrunk;
    public int Transfer;
    public String CallingIMSI;
    public String CalledIMSI;
    public String MSRN;
    public int FileNum;
    public String SwitchNum;
    public String Date;
    public String Time;
    public String DateTime;
    public String CalledNum;
    public String CallingNum;
    public int CallPeriod;


    public RecordSchema(String systemIdentity, int recordType, int timeType, int serviceType, int endType,
                        int outgoingTrunk, int transfer, String callingIMSI, String calledIMSI, String MSRN,
                        int fileNum, String switchNum, String date, String time, String dateTime, String calledNum,
                        String callingNum, int callPeriod) {
        this.systemIdentity = systemIdentity;
        RecordType = recordType;
        TimeType = timeType;
        ServiceType = serviceType;
        EndType = endType;
        OutgoingTrunk = outgoingTrunk;
        Transfer = transfer;
        CallingIMSI = callingIMSI;
        CalledIMSI = calledIMSI;
        this.MSRN = MSRN;
        FileNum = fileNum;
        SwitchNum = switchNum;
        Date = date;
        Time = time;
        DateTime = dateTime;
        CalledNum = calledNum;
        CallingNum = callingNum;
        CallPeriod = callPeriod;
    }

    public String getSystemIdentity() {
        return systemIdentity;
    }

    public void setSystemIdentity(String systemIdentity) {
        this.systemIdentity = systemIdentity;
    }

    public int getRecordType() {
        return RecordType;
    }

    public void setRecordType(int recordType) {
        RecordType = recordType;
    }

    public int getTimeType() {
        return TimeType;
    }

    public void setTimeType(int timeType) {
        TimeType = timeType;
    }

    public int getServiceType() {
        return ServiceType;
    }

    public void setServiceType(int serviceType) {
        ServiceType = serviceType;
    }

    public int getEndType() {
        return EndType;
    }

    public void setEndType(int endType) {
        EndType = endType;
    }

    public int getOutgoingTrunk() {
        return OutgoingTrunk;
    }

    public void setOutgoingTrunk(int outgoingTrunk) {
        OutgoingTrunk = outgoingTrunk;
    }

    public int getTransfer() {
        return Transfer;
    }

    public void setTransfer(int transfer) {
        Transfer = transfer;
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

    public String getMSRN() {
        return MSRN;
    }

    public void setMSRN(String MSRN) {
        this.MSRN = MSRN;
    }

    public int getFileNum() {
        return FileNum;
    }

    public void setFileNum(int fileNum) {
        FileNum = fileNum;
    }

    public String getSwitchNum() {
        return SwitchNum;
    }

    public void setSwitchNum(String switchNum) {
        SwitchNum = switchNum;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }

    public String getCalledNum() {
        return CalledNum;
    }

    public void setCalledNum(String calledNum) {
        CalledNum = calledNum;
    }

    public String getCallingNum() {
        return CallingNum;
    }

    public void setCallingNum(String callingNum) {
        CallingNum = callingNum;
    }

    public int getCallPeriod() {
        return CallPeriod;
    }

    public void setCallPeriod(int callPeriod) {
        CallPeriod = callPeriod;
    }
}
