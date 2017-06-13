package com.wechat.main;

import com.wechat.common.pojo.AccessToken;
import com.wechat.common.utils.WeChatUtil;
import net.sf.json.JSONObject;

import java.io.IOException;

/**
 * Created by 30825 on 2017/5/14.
 */
public class MenuCreate {

    public static void main(String[] args) {
        try {
            AccessToken accessToken = WeChatUtil.getAccessToken();
            System.out.println("票据："+ accessToken.getToken());
            System.out.println("有效时间："+ accessToken.getExpiresIn());

            String menu = JSONObject.fromObject(WeChatUtil.initMenu()).toString();
            int result = WeChatUtil.createMenu(accessToken.getToken(),menu);

            if(result == 0){
                System.out.println("创建菜单成功");
            }else{
                System.out.println("错误码:" + result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
