package com.khvorostov.test.web_service.callbacks;

/**
 * Created by Paul on 08/07/15.
 */
public interface PingMadeCallback {
    void pingMade(String result, String destinationAddress);
}
