package com.primeton.eos.governor.agent.common;

import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class GovernorAgentServiceException
        extends Exception
{
    private static final long serialVersionUID = -9085646291636250231L;
    public Object[] params;
    private String errorCode;
    private List<String> errorMessages;
    private int status;
    private Object additional;

    public GovernorAgentServiceException() {}

    public GovernorAgentServiceException(Exception ex)
    {
        super(ex);
    }

    public GovernorAgentServiceException(String errorCode, String errorMessage)
    {
        this(errorCode, errorMessage, -1);
    }

    public GovernorAgentServiceException(String errorCode, List<String> errorMessages)
    {
        this(errorCode, errorMessages, -1, null, null);
    }

    public GovernorAgentServiceException(String errorCode, String errorMessage, Object[] params)
    {
        this(errorCode, errorMessage, -1, params);
    }

    public GovernorAgentServiceException(String errorCode, String errorMessage, int status)
    {
        this(errorCode, Arrays.asList(new String[] { errorMessage }), -1, null, null);
    }

    public GovernorAgentServiceException(String errorCode, String errorMessage, int status, Object[] params)
    {
        this(errorCode, Arrays.asList(new String[] { String.format(errorMessage, params) }), -1, params, null);
    }

    public GovernorAgentServiceException(String errorCode, String errorMessage, Object[] params, Object additional)
    {
        this(errorCode, Arrays.asList(new String[] { String.format(errorMessage, params) }), -1, params, additional);
    }

    public GovernorAgentServiceException(String errorCode, String errorMessage, Object additional)
    {
        this(errorCode, Arrays.asList(new String[] { errorMessage }), -1, null, additional);
    }

    public GovernorAgentServiceException(String errorCode, List<String> errorMessages, int status, Object[] params, Object additional)
    {
        this.errorCode = errorCode;
        this.errorMessages = errorMessages;
        this.status = status;
        this.params = params;
        this.additional = additional;
        if (StringUtils.isBlank(this.errorCode)) {
            this.errorCode = getClass().getSimpleName();
        }
    }

    public String getMessage()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("ErrorCode: " + this.errorCode);
        sb.append(", ErrorMessages: " + this.errorMessages);
        sb.append(", Status: " + this.status);
        return sb.toString();
    }

    public Object[] getParams()
    {
        return this.params;
    }

    public void setParams(Object[] params)
    {
        this.params = params;
    }

    public String getErrorCode()
    {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode)
    {
        this.errorCode = errorCode;
    }

    public List<String> getErrorMessages()
    {
        return this.errorMessages;
    }

    public void setErrorMessages(List<String> errorMessages)
    {
        this.errorMessages = errorMessages;
    }

    public int getStatus()
    {
        return this.status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public Object getAdditional()
    {
        return this.additional;
    }

    public void setAdditional(Object additional)
    {
        this.additional = additional;
    }
}
