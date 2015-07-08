package com.khvorostov.test.web_service.async_tasks;

import android.os.AsyncTask;
import com.khvorostov.test.model.Ping;
import com.khvorostov.test.web_service.JsonParser;
import com.khvorostov.test.web_service.callbacks.PingRequestReceivedCallback;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul on 06/07/15.
 */
public class RequestAllPingsAsyncTask extends AsyncTask<URL, Void, List<Ping>> {
    private PingRequestReceivedCallback callback;

    public RequestAllPingsAsyncTask(PingRequestReceivedCallback pingRequestReceivedCallback) {
        this.callback = pingRequestReceivedCallback;
    }

    @Override
    protected List<Ping> doInBackground(URL... urls) {
        List<Ping> pings = new ArrayList<>();
        if (urls.length == 1) {
            try {
                InputStream response = urls[0].openStream();
                String jsonString = readInputStream(response);
                pings = JsonParser.parsePings(jsonString);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return pings;
    }

    @Override
    protected void onPostExecute(List<Ping> pings) {
        if (callback != null) {
            callback.pingsReceived(pings);
        }
    }

    public String readInputStream(InputStream inputStream) {
        final char[] buffer = new char[255];
        final StringBuilder out = new StringBuilder();
        try {
            Reader in = new InputStreamReader(inputStream, "UTF-8");
            for (; ; ) {
                int rsz = in.read(buffer, 0, buffer.length);
                if (rsz < 0)
                    break;
                out.append(buffer, 0, rsz);
            }
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return out.toString();
    }
}
