package com.primeton.eos.governor.agent.model;

public class InstanceId
{
    private String appId;
    private String hostName;
    private String port;
    private String cluster;

    public InstanceId() {}

    public InstanceId(String appId, String hostName, String port, String cluster)
    {
        this.appId = appId;
        this.hostName = hostName;
        this.port = port;
        this.cluster = cluster;
    }

    public String getAppId()
    {
        return this.appId;
    }

    public void setAppId(String appId)
    {
        this.appId = appId;
    }

    public String getHostName()
    {
        return this.hostName;
    }

    public void setHostName(String hostName)
    {
        this.hostName = hostName;
    }

    public String getPort()
    {
        return this.port;
    }

    public void setPort(String port)
    {
        this.port = port;
    }

    public String getCluster()
    {
        return this.cluster;
    }

    public void setCluster(String cluster)
    {
        this.cluster = cluster;
    }

    public String toString()
    {
        return this.hostName + ":" + this.port + "@" + this.appId + "." + this.cluster;
    }
}
