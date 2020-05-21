package com.thinker.basethinker.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Farley on 2018/3/28.
 *  * 1,项目包名：myPackage
 2,移动端类别：phoneType
 3,app版本号:appVersion
 4,设备厂商及型号:Manufacturer
 5.设备系统版本：mfVersion
 6,日志级别:logLevel
 7,时间:logDate
 8,日志内容:logMessage
 */

public class LogBean {
    public LogBean(String myPackage, String phoneType, String appVersion, String manufacturer, String mfVersion) {
        this.myPackage = myPackage;
        this.phoneType = phoneType;
        this.appVersion = appVersion;
        Manufacturer = manufacturer;
        this.mfVersion = mfVersion;
    }

    private String userId;
    private String myPackage;
    private String phoneType;
    private String appVersion;
    private String Manufacturer;
    private String mfVersion;
    private String logLevel;
    private String logMessage;
    private int logLines;
    private String className;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMyPackage() {
        return myPackage;
    }

    public void setMyPackage(String myPackage) {
        this.myPackage = myPackage;
    }

    public String getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getManufacturer() {
        return Manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        Manufacturer = manufacturer;
    }

    public String getMfVersion() {
        return mfVersion;
    }

    public void setMfVersion(String mfVersion) {
        this.mfVersion = mfVersion;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public String getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(String logMessage) {
        this.logMessage = logMessage;
    }

    public int getLogLines() {
        return logLines;
    }

    public void setLogLines(int logLines) {
        this.logLines = logLines;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String myDate=format.format(new Date());
        StringBuilder sb = new StringBuilder();
        sb.append(this.toIndentedString(this.myPackage)).append(" - ");
        sb.append(this.toIndentedString(this.phoneType));
        sb.append("[");
        sb.append(this.toIndentedString(this.Manufacturer)).append("\\");
        sb.append(this.toIndentedString(this.mfVersion)).append("\\");
        sb.append(this.toIndentedString(this.appVersion)).append("] ");
        sb.append("[");
        sb.append(this.toIndentedString(myDate)).append("] ");
        sb.append("[");
        sb.append(this.toIndentedString(this.className)).append(":");
        sb.append(this.toIndentedString(this.logLines)).append("] ");
        sb.append("[");
        sb.append(this.toIndentedString(this.logLevel)).append("] :");
        sb.append(this.toIndentedString(this.logMessage));

        return sb.toString();
    }
    private String toIndentedString(Object o) {
        return o == null?"null":o.toString().replace("\n", "\n    ");
    }
}
