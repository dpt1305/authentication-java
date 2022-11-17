package com.tungjj.user.log;

public enum LoggerType {
    REQUEST("requestLog"),
    APPLICATION("applicationLog"),
    API("apiLog"),
    DATABASE("databaseLog");

    private String loggerName;

    LoggerType(String loggerName) {
        this.loggerName = loggerName;
    }

    public String getLoggerName() {
        return loggerName;
    }
}
