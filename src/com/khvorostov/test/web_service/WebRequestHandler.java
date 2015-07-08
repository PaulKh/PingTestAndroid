package com.khvorostov.test.web_service;

import com.khvorostov.test.model.Ping;
import com.khvorostov.test.web_service.async_tasks.PostPingAsyncTask;
import com.khvorostov.test.web_service.async_tasks.RequestAllPingsAsyncTask;
import com.khvorostov.test.web_service.callbacks.PingCreatedCallback;
import com.khvorostov.test.web_service.callbacks.PingRequestReceivedCallback;
import org.apache.http.conn.util.InetAddressUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Paul on 06/07/15.
 */
public class WebRequestHandler{
    private String thisDeviceIp;
//    private static final String serverAddress = "http://10.183.230.155:8080/";
    private static final String serverAddress = "http://194.254.174.59:8080/";

    public WebRequestHandler() {
        thisDeviceIp = getIPAddress(true);
    }

    public void requestAllPings(PingRequestReceivedCallback pingsCallback) {
        try {
            RequestAllPingsAsyncTask asyncTask = new RequestAllPingsAsyncTask(pingsCallback);
            asyncTask.execute(new URL(serverAddress + "ping/all"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void postPing(Ping ping, PingCreatedCallback callback){
        try {
            PostPingAsyncTask asyncTask = new PostPingAsyncTask(callback, ping);
            asyncTask.execute(new URL(serverAddress + "ping/new"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getThisDeviceIp() {
        return thisDeviceIp;
    }

    private String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress().toUpperCase();
                        boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 port suffix
                                return delim < 0 ? sAddr : sAddr.substring(0, delim);
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
        } // for now eat exceptions
        return "";
    }
}
