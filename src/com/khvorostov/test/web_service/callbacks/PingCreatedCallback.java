package com.khvorostov.test.web_service.callbacks;

import com.khvorostov.test.enums.RequestStatus;
import com.khvorostov.test.model.Ping;

/**
 * Created by Paul on 08/07/15.
 */
public interface PingCreatedCallback {
    void pingRequestSent(RequestStatus requestStatus, Ping ping);
}
