package com.thinker.basethinker.utils;

import android.app.Activity;


import com.thinker.basethinker.bean.PersonalBean;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by Farley on 2018/3/28.
 */

public class LogUpToKiBaNA extends Thread {
    private LogBean logBean;
    public LogUpToKiBaNA(Activity mainActivity, String logMessage, String level, int lines) {
        logBean = MyUtils.getAppMsgForBean(mainActivity);
        PersonalBean personalBean = MyUtils.getPersonData();
        if (personalBean != null){
            logMessage = personalBean.getNickname()+":"+logMessage;
        }
        logBean.setLogMessage(logMessage);
        logBean.setLogLevel(level);
        logBean.setLogLines(lines);
        logBean.setClassName(mainActivity.getLocalClassName());
    }

    @Override
    public void run() {
        super.run();
        send(logBean.toString());
    }
    public static void send(String message) {
        message = (message == null ? "Hello IdeasAndroid!" : message);
        int server_port = 7000;
        DatagramSocket s = null;
        try {
            s = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        InetAddress local = null;
        try {
            // 换成服务器端IP
            local = InetAddress.getByName("120.25.75.93");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        int msg_length = message.length();
        byte[] messageByte = message.getBytes();
        DatagramPacket p = new DatagramPacket(messageByte, msg_length, local,
                server_port);
        try {
            s.send(p);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
