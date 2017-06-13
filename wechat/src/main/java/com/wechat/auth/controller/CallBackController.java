package com.wechat.auth.controller;

import com.wechat.auth.pojo.User;
import com.wechat.auth.service.UserService;
import com.wechat.common.utils.AuthUtil;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  通过code获取access_token
 * Created by 30825 on 2017/5/17.
 */
@RestController
public class CallBackController {
    @Resource
    public UserService userService;


    //获取code后，请求以下链接获取 access_token：  https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
    @GetMapping(value = "/callBack")
    public void callBack(@RequestParam(value = "code") String code, HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuffer access_tokenUrl = new StringBuffer();
        access_tokenUrl.append("https://api.weixin.qq.com/sns/oauth2/access_token?appid=");
        access_tokenUrl.append(AuthUtil.APPID);
        access_tokenUrl.append("&secret=");
        access_tokenUrl.append(AuthUtil.APPSECRET);
        access_tokenUrl.append("&code=");
        access_tokenUrl.append(code);
        access_tokenUrl.append("&grant_type=authorization_code");

        //请求获得票据
        JSONObject jsonObject = AuthUtil.doGetJson(access_tokenUrl.toString());
        String openid = jsonObject.getString("openid");
        String access_token = jsonObject.getString("access_token");


        //获取用户信息 https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
        StringBuffer userInfoUrl = new StringBuffer();
        userInfoUrl.append("https://api.weixin.qq.com/sns/userinfo?access_token=");
        userInfoUrl.append(access_token);
        userInfoUrl.append("&openid=");
        userInfoUrl.append(openid);
        userInfoUrl.append("&lang=zh_CN");
        //请求获得用户信息
        JSONObject userInfo = AuthUtil.doGetJson(userInfoUrl.toString());
        System.out.println(userInfo);

        //1、使用微信用户信息直接登录，无需注册和绑定
        request.setAttribute("info",userInfo);


        //2、进行微信账号的openId查询并进行绑定
        User user = userService.boundUser(userInfo);

    }

    //2、将微信与当前系统的账号进行绑定

}
