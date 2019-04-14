package com.primeton.eos.governor.agent.model;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

public class EsLogResultVO
{
    @ApiModelProperty("结果内容")
    @SerializedName("Content")
    private List<EsLogVO> esLogVOList;
    @ApiModelProperty("总数")
    @SerializedName("TotalNum")
    private Long totalNum;
    @ApiModelProperty("页索引")
    @SerializedName("Page")
    private Integer page;
    @ApiModelProperty("分页大小")
    @SerializedName("PerPage")
    private Integer perPage;

    public List<EsLogVO> getEsLogVOList()
    {
        return this.esLogVOList;
    }

    public void setEsLogVOList(List<EsLogVO> esLogVOList)
    {
        this.esLogVOList = esLogVOList;
    }

    public Long getTotalNum()
    {
        return this.totalNum;
    }

    public void setTotalNum(long totalNum)
    {
        this.totalNum = Long.valueOf(totalNum);
    }

    public int getPage()
    {
        return this.page.intValue();
    }

    public void setPage(int page)
    {
        this.page = Integer.valueOf(page);
    }

    public int getPerPage()
    {
        return this.perPage.intValue();
    }

    public void setPerPage(int perPage)
    {
        this.perPage = Integer.valueOf(perPage);
    }
}
