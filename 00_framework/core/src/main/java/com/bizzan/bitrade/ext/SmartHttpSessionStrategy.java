package com.bizzan.bitrade.ext;

import org.springframework.session.web.http.CookieHttpSessionIdResolver;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

public class SmartHttpSessionStrategy implements HttpSessionIdResolver {
    private final CookieHttpSessionIdResolver browser;
    private final HeaderHttpSessionIdResolver api;
    private final String tokenName = "x-auth-token";

    public SmartHttpSessionStrategy() {
        this.browser = new CookieHttpSessionIdResolver();
        this.api = HeaderHttpSessionIdResolver.xAuthToken(); // 使用 Spring 提供的默认 x-auth-token 解析器
    }

    @Override
    public List<String> resolveSessionIds(HttpServletRequest request) {
        String paramToken = request.getParameter(tokenName);
        if (paramToken != null && !paramToken.isEmpty()) {
            return Arrays.asList(paramToken); // 返回 token 参数作为 session ID
        }
        return getStrategy(request).resolveSessionIds(request);
    }

    @Override
    public void setSessionId(HttpServletRequest request, HttpServletResponse response, String sessionId) {
        getStrategy(request).setSessionId(request, response, sessionId);
    }

    @Override
    public void expireSession(HttpServletRequest request, HttpServletResponse response) {
        getStrategy(request).expireSession(request, response);
    }

    private HttpSessionIdResolver getStrategy(HttpServletRequest request) {
        String authType = request.getHeader("x-auth-token");
        if (authType == null) {
            return this.browser;
        } else {
            return this.api;
        }
    }
}