package com.yundingshuyuan.recruit.biz.gatewayservice.filiter;



import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import java.io.IOException;

@Deprecated
//@WebFilter(filterName = "LoginFilter", value = "/*")
@Slf4j
public class LoginFilter implements Filter {
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
       /* HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String URI = request.getRequestURI();
        //定义不再需要处理的请求路径
        String[] urls = new String[]{
                "/v3/**", "/doc.html", "/login", "/actuator/**"
        };
        if (check(urls, URI)) {
            filterChain.doFilter(request, response);
            return;
        }
        log.info("拦截到请求：{}", URI);
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            log.info("用户未登录");
            response.getWriter().write(JSON.toJSONString((BasicResultVO.fail("用户未登录"))));
            return;
        }
        //检查token是否过期
        long tokenTimeout = StpUtil.getTokenTimeout();
        if (tokenTimeout < 0) {
            log.info("token无效");
            response.getWriter().write(JSON.toJSONString((BasicResultVO.fail("token无效"))));
            return;
        }
        filterChain.doFilter(request, response);*/
    }


    public Boolean check(String[] urls, String url) {
        for (String s : urls) {
            boolean match = PATH_MATCHER.match(s, url);
            if (match == true) {
                return true;
            }
        }
        return false;
    }


}
