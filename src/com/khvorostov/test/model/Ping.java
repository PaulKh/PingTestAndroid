package com.khvorostov.test.model;

import java.io.Serializable;

/**
 * Created by Paul on 06/07/15.
 */
public class Ping implements Serializable {
    private String pingResult;
    private String sourceAddress;
    private String destinationAddress;


    public Ping(String pingResult, String sourceAddress, String destinationAddress) {
        this.pingResult = pingResult;
        this.sourceAddress = sourceAddress;
        this.destinationAddress = destinationAddress;
    }

    public String getPingResult() {
        return pingResult;
    }

    public String getSourceAddress() {
        return sourceAddress;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }
}