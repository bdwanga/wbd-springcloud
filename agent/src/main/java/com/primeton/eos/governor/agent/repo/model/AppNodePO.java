package com.primeton.eos.governor.agent.repo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="gov_app_node")
public class AppNodePO
{
    @Column(name="context_path")
    private String contextPath;
    @Column(name="cluster_id")
    private String clusterId;
    @Column(name="cluster_code")
    private String clusterCode;
    @Column(name="app_id")
    private String appId;
    @Column(name="app_code")
    private String appCode;
    @Column(name="app_sys_id")
    private String appSysId;
    @Column(name="url")
    private String url;
    @Column(name="ip")
    private String ip;
    @Column(name="port")
    private long port;
    @Column(name="node_host")
    private String nodeHost;
    @Column(name="type")
    private String type;
    @Column(name="config_cent_id")
    private String configCentId;
    @Column(name="reg_cent_id")
    private String regCentId;
    @Column(name="is_secure")
    private boolean isSecure = false;

    private String code;

    public boolean isSecure()
    {
        return this.isSecure;
    }

    public void setSecure(boolean secure)
    {
        this.isSecure = secure;
    }

    public String getClusterCode()
    {
        return this.clusterCode;
    }

    public void setClusterCode(String clusterCode)
    {
        this.clusterCode = clusterCode;
    }

    public String getAppCode()
    {
        return this.appCode;
    }

    public void setAppCode(String appCode)
    {
        this.appCode = appCode;
    }

    public String getClusterId()
    {
        return this.clusterId;
    }

    public void setClusterId(String clusterId)
    {
        this.clusterId = clusterId;
    }

    public String getAppId()
    {
        return this.appId;
    }

    public void setAppId(String appId)
    {
        this.appId = appId;
    }

    public String getAppSysId()
    {
        return this.appSysId;
    }

    public void setAppSysId(String appSysId)
    {
        this.appSysId = appSysId;
    }

    public String getUrl()
    {
        return this.url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getIp()
    {
        return this.ip;
    }

    public void setIp(String ip)
    {
        this.ip = ip;
    }

    public long getPort()
    {
        return this.port;
    }

    public void setPort(long port)
    {
        this.port = port;
    }

    public String getNodeHost()
    {
        return this.nodeHost;
    }

    public void setNodeHost(String nodeHost)
    {
        this.nodeHost = nodeHost;
    }

    public String getType()
    {
        return this.type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getConfigCentId()
    {
        return this.configCentId;
    }

    public void setConfigCentId(String configCentId)
    {
        this.configCentId = configCentId;
    }

    public String getRegCentId()
    {
        return this.regCentId;
    }

    public void setRegCentId(String regCentId)
    {
        this.regCentId = regCentId;
    }

    public String getContextPath()
    {
        return this.contextPath;
    }

    public void setContextPath(String contextPath)
    {
        this.contextPath = contextPath;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }
}
