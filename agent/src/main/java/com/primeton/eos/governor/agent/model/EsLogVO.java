package com.primeton.eos.governor.agent.model;

import com.google.gson.Gson;
import io.swagger.annotations.ApiModelProperty;

public class EsLogVO
{
    @ApiModelProperty("日志记录时间")
    private Long timestamp;
    @ApiModelProperty("日志信息")
    private Object logDetails;
    Gson gson = new Gson();

    public Long getTimestamp()
    {
        return this.timestamp;
    }

    public void setTimestamp(Long timestamp)
    {
        this.timestamp = timestamp;
    }

    public Object getLogDetails()
    {
        return this.logDetails;
    }

    public void setLogDetails(Object logDetails)
    {
        this.logDetails = logDetails;
    }
}
