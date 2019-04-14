package com.primeton.eos.governor.agent.nls;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.springframework.context.i18n.LocaleContextHolder;

public class Messages
{
    private static final String BUNDLE_NAME = "com.primeton.eos.governor.agent.nls.messages";

    public static String getMsg(String key)
    {
        try
        {
            return ResourceBundle.getBundle("com.primeton.eos.governor.agent.nls.messages", LocaleContextHolder.getLocale()).getString(key);
        }
        catch (MissingResourceException e) {}
        return '!' + key + '!';
    }
}
