/*******************************************************************************
 *
 * Copyright (c) 2001-2018 Primeton Technologies, Ltd.
 * All rights reserved.
 *
 * Created on 2018-08-8/17/18-10:59 AM
 *******************************************************************************/
package com.primeton.eos.governor.agent.service;

import com.primeton.eos.governor.agent.common.GovernorAgentServiceException;
import com.primeton.eos.governor.agent.common.GovernorAgentServiceExceptionCode;
import com.primeton.eos.governor.agent.model.EsLogResultVO;
import com.primeton.eos.governor.agent.model.EsLogVO;
import com.primeton.eos.governor.agent.model.InstanceId;
import com.primeton.eos.governor.agent.model.TraceLogMsgModel;
import com.primeton.eos.governor.agent.nls.Messages;
import com.primeton.eos.governor.agent.repo.dao.AppNodeRepository;
import com.primeton.eos.governor.agent.repo.model.AppNodePO;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * AppLogService
 *
 * @author flymars (mailto:zhaord@primeton.com)
 */
@Service
public class AppLogService {
    private static final Logger logger = LoggerFactory.getLogger(AppLogService.class);
    private static final String SYSTEM_LOG = "system";
    private static final String TRACE_LOG = "trace";

    @Autowired
    AppNodeRepository appNodeRepository;

    @Autowired
    RestHighLevelClient restHighLevelClient;

    /**
     * ip:serviceid:port[topic]
     */
    public EsLogResultVO getAppLogInfo(String ip, String level, String key,
                                       String type, String startDate,
                                       String endDate, int pageIndex, int pageSize) throws GovernorAgentServiceException {
        List<EsLogVO> esLogVOList = new ArrayList<>();
        AppNodePO appInstance = appNodeRepository.findByCodeAndDelFlag(ip, false);
        if (appInstance == null) {
            throw new GovernorAgentServiceException(GovernorAgentServiceExceptionCode.APPLICATION_NODE_NOT_EXISTED,
                    Messages.getMsg(GovernorAgentServiceExceptionCode.APPLICATION_NODE_NOT_EXISTED));
        }
        InstanceId instanceInfo = getInstanceInfo(appInstance);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date beginTime = new Date();
        Date endTime = new Date();
        try {
            beginTime = format.parse(format.format(Long.valueOf(startDate) * 1000));
            endTime = format.parse(format.format(Long.valueOf(endDate) * 1000));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        EsLogResultVO ret = new EsLogResultVO();
        logger.debug("begintime:{}", beginTime);
        logger.debug("endtime:{}", endTime);
        logger.debug("ins:{}", instanceInfo);
        SearchRequest searchRequest = getEsSearchRequest(level, type, key, pageIndex, pageSize, instanceInfo.toString(), instanceInfo.getAppId(),
                beginTime,
                endTime);

        analyseEshints(esLogVOList, ret, searchRequest);
        ret.setPage(pageIndex);
        ret.setPerPage(pageSize);
        //fix bug

        ret.setEsLogVOList(esLogVOList);
        logger.debug("EsLogResultVO:{}", esLogVOList.size());

        return ret;
    }

    private InstanceId getInstanceInfo(AppNodePO appInstance) {
        String clusterCode = appInstance.getClusterCode();
        String appId = appInstance.getCode().split(":")[1];
        String port = String.valueOf(appInstance.getPort());
        String host = appInstance.getCode().split(":")[0];
        InstanceId instanceId = new InstanceId(appId, host, port, clusterCode);
        return instanceId;
    }

    private SearchRequest getEsSearchRequest(String level, String type, String key, int pageIndex, int pageSize,
                                             String instanceId, String appId,
                                             Date beginTime, Date endTime) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        PaginationUtils.Page page = PaginationUtils.INSTANCE.exchange(pageIndex, pageSize);
        searchSourceBuilder.from(page.getFrom());
        searchSourceBuilder.size(page.getLimit());
        searchSourceBuilder.sort("@timestamp", SortOrder.DESC);
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        if (!StringUtils.isEmpty(appId)) {
            TermQueryBuilder keyMatchQuery = QueryBuilders.termQuery("app", appId);
//            keyMatchQuery.operator(Operator.AND);
            boolQueryBuilder.must(keyMatchQuery);
        }

        //eos log type;
        if (!StringUtils.isEmpty(type)) {
            MatchQueryBuilder typeMatchQuery = QueryBuilders.matchQuery("logtype", type);
            typeMatchQuery.operator(Operator.AND);
            boolQueryBuilder.must(typeMatchQuery);
        }

        if (!StringUtils.isEmpty(level)) {
            //
            MatchQueryBuilder levelMatchQuery = QueryBuilders.matchQuery("level", level);
            levelMatchQuery.operator(Operator.AND);
            boolQueryBuilder.must(levelMatchQuery);
        }

        if (SYSTEM_LOG.equalsIgnoreCase(type) && !StringUtils.isEmpty(instanceId)) {

            MatchQueryBuilder instanceMatchQuery = QueryBuilders.matchQuery("instanceId", instanceId);
            instanceMatchQuery.operator(Operator.AND);
            boolQueryBuilder.must(instanceMatchQuery);
            //build query
            //add search value [log value]
        }

        if ( !StringUtils.isEmpty(key)) {
            MatchQueryBuilder keyMatchQuery ;
            if (SYSTEM_LOG.equalsIgnoreCase(type)){
                keyMatchQuery = QueryBuilders.matchQuery("syslog", key);
            }else {
                keyMatchQuery = QueryBuilders.matchQuery("logMsg", key);
            }
            keyMatchQuery.operator(Operator.AND);
            boolQueryBuilder.must(keyMatchQuery);
        }
        RangeQueryBuilder timeRangeQuery = QueryBuilders.rangeQuery("@timestamp");
        timeRangeQuery.gte(beginTime);
        timeRangeQuery.lte(endTime);
        boolQueryBuilder.must(timeRangeQuery);

        searchSourceBuilder.query(boolQueryBuilder);

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.source(searchSourceBuilder);

        return searchRequest;
    }

    private void analyseEshints(List<EsLogVO> esLogVOList, EsLogResultVO ret, SearchRequest searchRequest)
            throws GovernorAgentServiceException {
        SearchResponse searchResponse;
        SearchHits hits;
        try {
            searchResponse = restHighLevelClient.search(searchRequest);
        } catch (Exception e) {
            logger.error("Could not get response from elasticsearch server!", e);
            throw new GovernorAgentServiceException(
                    GovernorAgentServiceExceptionCode.MONITOR_ES_API_STATUS_RESPONSE_ERROR,
                    Messages.getMsg(GovernorAgentServiceExceptionCode.MONITOR_ES_API_STATUS_RESPONSE_ERROR), e);
        }
        hits = searchResponse.getHits();
        try {
            long totalHits = hits.getTotalHits();
            logger.debug("totalHits:{}", totalHits);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
            for (SearchHit t : hits) {
                TraceLogMsgModel traceLogMsgModel = null;
                Object message = t.getSource().get("logMsg");
                String date = (String) t.getSource().get("@timestamp");
                date = date.replace("Z", " UTC");
                String logtype = (String) t.getSource().get("logtype");
                String level = (String) t.getSource().get("level");
                String app = (String) t.getSource().get("app");
                if (SYSTEM_LOG.equalsIgnoreCase(logtype)) {
                    message = t.getSource().get("syslog");
                }
                if (TRACE_LOG.equalsIgnoreCase(logtype)) {
                    String pid = (String) t.getSource().get("PID");
                    String className = (String) t.getSource().get("className");
                    String thread = (String) t.getSource().get("thread");
                    logger.debug("pid:className:thread:{},{},{}", pid, className,thread,level);
//                    if (null == pid || null == className || null == thread || null == level){
//                        totalHits = totalHits - 1;
//                        continue;
//                    }
                    traceLogMsgModel = parseTraceLogMsg(message, date, logtype, level, app, pid, className, thread);
                    logger.debug("traceLogMsg:{}", traceLogMsgModel.getLogMsg());
                }

                EsLogVO esLogVO = new EsLogVO();
                if (traceLogMsgModel != null) {
                    esLogVO.setLogDetails(traceLogMsgModel);
                } else {
                    esLogVO.setLogDetails(message);
                }

                logger.debug("EsLogVO:{}", esLogVO.getLogDetails());
                esLogVO.setTimestamp(formatter.parse(date).getTime());
                esLogVOList.add(esLogVO);
            }

            ret.setTotalNum(totalHits);
        } catch (ParseException e) {
            e.printStackTrace();
            logger.error("Error parse date", e);
            throw new GovernorAgentServiceException(
                    GovernorAgentServiceExceptionCode.MONITOR_ES_API_STATUS_TRANSFORM_ERROR,
                    Messages.getMsg(GovernorAgentServiceExceptionCode.MONITOR_ES_API_STATUS_TRANSFORM_ERROR), e);
        }
    }

    private TraceLogMsgModel parseTraceLogMsg(Object message, String date, String logtype, String level, String app, String pid, String className, String thread) {
        return new TraceLogMsgModel(app, date, level, pid, thread, className, message == null ? "": message.toString());
    }

    public enum PaginationUtils {
        INSTANCE;

        public Page exchange(Integer pageIndex, Integer pageSize) {
            int limit = pageSize;
            int from = pageSize * pageIndex;

            return new Page(from, limit);
        }

        public class Page {
            private int from;
            private int limit;

            Page(int from, int limit) {
                this.from = from;
                this.limit = limit;
            }

            public int getFrom() {
                return from;
            }

            public int getLimit() {
                return limit;
            }
        }
    }


}
