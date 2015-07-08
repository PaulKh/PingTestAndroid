package com.khvorostov.test.web_service.callbacks;

import com.khvorostov.test.model.Ping;

import java.util.List;

/**
 * Created by Paul on 07/07/15.
 */
public interface PingRequestReceivedCallback {
    void pingsReceived(List<Ping> pings);
}
