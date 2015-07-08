package com.khvorostov.test.web_service.async_tasks;

import android.os.AsyncTask;
import com.khvorostov.test.web_service.callbacks.PingMadeCallback;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;

/**
 * Created by Paul on 08/07/15.
 */
public class PingAddressAsyncTask extends AsyncTask<Void, Void, String> {
    private int numberOfPings;
    private String destinationURL;
    private PingMadeCallback callback;

    public PingAddressAsyncTask(PingMadeCallback pingMadeCallback, int numberOfPings, String destinationURL) {
        this.callback = pingMadeCallback;
        this.numberOfPings = numberOfPings;
        this.destinationURL = destinationURL;
    }

    @Override
    protected String doInBackground(Void... voids) {
        String str = "";
        try {
            InetAddress address = InetAddress.getByName(destinationURL);
            Process process = Runtime.getRuntime().exec(
                    "/system/bin/ping -c " + numberOfPings + " " + address.getHostAddress());
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            int i;
            char[] buffer = new char[4096];
            StringBuffer output = new StringBuffer();
            while ((i = reader.read(buffer)) > 0)
                output.append(buffer, 0, i);
            reader.close();
            str = output.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    @Override
    protected void onPostExecute(String result) {
        if (callback != null) {
            callback.pingMade(result, destinationURL);
        }
    }
}
