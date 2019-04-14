package com.primeton.eos.governor.agent.model;

import java.io.IOException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

public class TraceLogMsgModel
{
    private String app;
    private String time;
    private String level;
    private String PID;
    private String thread;
    private String className;
    private String logMsg;

    public TraceLogMsgModel(String app, String time, String level, String PID, String thread, String className, String logMsg)
    {
        this.app = app;
        this.time = time;
        this.level = level;
        this.PID = PID;
        this.thread = thread;
        this.className = className;
        this.logMsg = logMsg;
    }

    public String getApp()
    {
        return this.app;
    }

    public void setApp(String app)
    {
        this.app = app;
    }

    public String getTime()
    {
        return this.time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    public String getLevel()
    {
        return this.level;
    }

    public void setLevel(String level)
    {
        this.level = level;
    }

    public String getPID()
    {
        return this.PID;
    }

    public void setPID(String PID)
    {
        this.PID = PID;
    }

    public String getThread()
    {
        return this.thread;
    }

    public void setThread(String thread)
    {
        this.thread = thread;
    }

    public String getClassName()
    {
        return this.className;
    }

    public void setClassName(String className)
    {
        this.className = className;
    }

    public String getLogMsg()
    {
        return this.logMsg;
    }

    public void setLogMsg(String logMsg)
    {
        this.logMsg = logMsg;
    }

    public static JSONObject toJson(TraceLogMsgModel traceLogMsgModel)
            throws JSONException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = "";
        try
        {
            jsonStr = objectMapper.writeValueAsString(traceLogMsgModel);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return new JSONObject(jsonStr);
    }
}
