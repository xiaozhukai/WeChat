package com.wechat.auth.controller;

import com.wechat.common.utils.AuthUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by 30825 on 2017/5/17.
 */
@RestController
public class AuthController {

    /**
     * 微信url授权地址：https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect
     */
    @GetMapping(value = "/authLogin")
    public void authLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuffer url = new StringBuffer();
        String backUrl = "https://xiaokai.tunnel.2bdata.com/callBack";
        url.append("https://open.weixin.qq.com/connect/oauth2/authorize?appid=");
        url.append(AuthUtil.APPID);
        url.append("&redirect_uri=");
        url.append(URLEncoder.encode(backUrl));
        //SCOPE类型： 1、snsapi_base 不弹出授权页面，默认授权 2、snsapi_userinfo 弹出授权页面，用户确认授权
        url.append("&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");
        response.sendRedirect(url.toString());
    }

    @GetMapping(value = "/index")
    public void index(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String contextPath = request.getRealPath("/");
        response.sendRedirect("authLogin");
    }
}
