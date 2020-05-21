package com.thinker.basethinker.utils.autoupdata;

import com.google.gson.annotations.Expose;

/**
 * Created by thinker on 17/12/23.
 */

public class PyVersionBean {

    /**
     * code : 0
     * message :
     * data : {"buildBuildVersion":"37","downloadURL":"https://www.pgyer.com/app/installUpdate/abaf1e25ceff27f15569a89a6d196ad4?sig=93c8Nme06UNMu8sQscr8ae2XGNBM9kZ5oOsZdH6xdwOjc4vT2P9qX8v6RLVWyE13","buildVersionNo":"22","buildVersion":"1.0.22","buildShortcutUrl":"https://www.pgyer.com/g08R","buildUpdateDescription":""}
     */
    @Expose
    private int code;
    @Expose
    private String message;
    @Expose
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * buildBuildVersion : 37
         * downloadURL : https://www.pgyer.com/app/installUpdate/abaf1e25ceff27f15569a89a6d196ad4?sig=93c8Nme06UNMu8sQscr8ae2XGNBM9kZ5oOsZdH6xdwOjc4vT2P9qX8v6RLVWyE13
         * buildVersionNo : 22
         * buildVersion : 1.0.22
         * buildShortcutUrl : https://www.pgyer.com/g08R
         * buildUpdateDescription :
         */
        @Expose
        private String buildBuildVersion;
        @Expose
        private String downloadURL;
        @Expose
        private String buildVersionNo;
        @Expose
        private String buildVersion;
        @Expose
        private String buildShortcutUrl;
        @Expose
        private String buildUpdateDescription;

        public String getBuildBuildVersion() {
            return buildBuildVersion;
        }

        public void setBuildBuildVersion(String buildBuildVersion) {
            this.buildBuildVersion = buildBuildVersion;
        }

        public String getDownloadURL() {
            return downloadURL;
        }

        public void setDownloadURL(String downloadURL) {
            this.downloadURL = downloadURL;
        }

        public String getBuildVersionNo() {
            return buildVersionNo;
        }

        public void setBuildVersionNo(String buildVersionNo) {
            this.buildVersionNo = buildVersionNo;
        }

        public String getBuildVersion() {
            return buildVersion;
        }

        public void setBuildVersion(String buildVersion) {
            this.buildVersion = buildVersion;
        }

        public String getBuildShortcutUrl() {
            return buildShortcutUrl;
        }

        public void setBuildShortcutUrl(String buildShortcutUrl) {
            this.buildShortcutUrl = buildShortcutUrl;
        }

        public String getBuildUpdateDescription() {
            return buildUpdateDescription;
        }

        public void setBuildUpdateDescription(String buildUpdateDescription) {
            this.buildUpdateDescription = buildUpdateDescription;
        }
    }
}
