package com.lylynx.hideout.jade.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: kuba
 * Date: 06.07.14
 * Time: 01:31
 */
public class FlashModelVariable extends AbstractMapAdapter{

    @Autowired
    private HttpServletRequest request;

    @Override
    public Object get(final Object key) {
        final Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
        if(null == inputFlashMap){
            return null;
        }

        return inputFlashMap.get(key);
    }
}
