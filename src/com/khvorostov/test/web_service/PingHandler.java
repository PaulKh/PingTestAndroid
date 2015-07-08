package com.khvorostov.test.web_service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Paul on 07/07/15.
 */
public class PingHandler {
//    public static boolean executeCommand(String ipAddress){
//        System.out.println("executeCommand");
//        Runtime runtime = Runtime.getRuntime();
//        try
//        {
//            Process  mIpAddrProcess = runtime.exec("/system/bin/ping -c 1 " + ipAddress);
//            int mExitValue = mIpAddrProcess.waitFor();
//            System.out.println(" mExitValue "+mExitValue);
//            if(mExitValue==0){
//                return true;
//            }else{
//                return false;
//            }
//        }
//        catch (InterruptedException ignore)
//        {
//            ignore.printStackTrace();
//            System.out.println(" Exception:"+ignore);
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//            System.out.println(" Exception:"+e);
//        }
//        return false;
//    }
//    public static String ping(String url, int numberOfPings) {
//        String str = "";
//        try {
//            InetAddress address = InetAddress.getByName(url);
//            Process process = Runtime.getRuntime().exec(
//                    "/system/bin/ping -c " + numberOfPings + " -w 1 " + address);
//            BufferedReader reader = new BufferedReader(new InputStreamReader(
//                    process.getInputStream()));
//            int i;
//            char[] buffer = new char[4096];
//            StringBuffer output = new StringBuffer();
//            while ((i = reader.read(buffer)) > 0)
//                output.append(buffer, 0, i);
//            reader.close();
//            str = output.toString();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return str;
//    }
}
