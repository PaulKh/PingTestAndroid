package com.khvorostov.test.web_service;

import com.khvorostov.test.model.Ping;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul on 07/07/15.
 */
public class JsonParser {
    private static final String pingResultKEY = "pingResult";
    private static final String sourceAddressKEY = "sourceAddress";
    private static final String destinationAddressKEY = "destinationAddress";

    public static List<Ping> parsePings(String jsonString) {
        List<Ping> pings = new ArrayList<>();
        try {
            JSONArray json = new JSONArray(jsonString);
            for (int i = 0; i < json.length(); i++){
                JSONObject pingJson = json.getJSONObject(i);
                String pingTime = pingJson.getString(pingResultKEY);
                pings.add(new Ping(pingTime, pingJson.getString(sourceAddressKEY), pingJson.getString(destinationAddressKEY)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return pings;
    }
    public static JSONObject pingToJson(Ping ping){
        JSONObject pingJson = new JSONObject();
        try {
            pingJson.put(pingResultKEY, ping.getPingResult());
            pingJson.put(sourceAddressKEY, ping.getSourceAddress());
            pingJson.put(destinationAddressKEY, ping.getDestinationAddress());
            return pingJson;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
}
