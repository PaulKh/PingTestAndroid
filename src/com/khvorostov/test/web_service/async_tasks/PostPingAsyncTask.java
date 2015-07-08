package com.khvorostov.test.web_service.async_tasks;

import android.os.AsyncTask;
import com.khvorostov.test.enums.RequestStatus;
import com.khvorostov.test.model.Ping;
import com.khvorostov.test.web_service.JsonParser;
import com.khvorostov.test.web_service.callbacks.PingCreatedCallback;
import com.khvorostov.test.web_service.callbacks.PingRequestReceivedCallback;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul on 07/07/15.
 */
public class PostPingAsyncTask extends AsyncTask<URL, Void, RequestStatus> {
    private Ping ping;
    private PingCreatedCallback callback;

    public PostPingAsyncTask(PingCreatedCallback pingCreatedCallback, Ping ping) {
        this.callback = pingCreatedCallback;
        this.ping = ping;
    }

    @Override
    protected RequestStatus doInBackground(URL... urls) {
        JSONObject pingJson = JsonParser.pingToJson(ping);
        HttpURLConnection httpConnection = null;
        try {
            httpConnection = (HttpURLConnection) urls[0].openConnection();
            httpConnection.setRequestMethod("POST");
            httpConnection.setDoOutput(true);
            httpConnection.setRequestProperty("Accept-Charset", "utf-8");
            httpConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            httpConnection.connect();
            OutputStream os = httpConnection.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
            osw.write(pingJson.toString());
            osw.flush();
            osw.close();
            int responseCode = httpConnection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                return RequestStatus.SUCCESS;
            }
            else return RequestStatus.ERROR;
        } catch (IOException e) {
            e.printStackTrace();
            return RequestStatus.ERROR;
        }

    }

    @Override
    protected void onPostExecute(RequestStatus requestStatus) {
        if (callback != null) {
            callback.pingRequestSent(requestStatus, ping);
        }
    }

}
