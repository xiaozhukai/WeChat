package com.wechat.common.utils;

import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 *  网页微信授权工具
 * Created by 30825 on 2017/5/17.
 */
public class AuthUtil {
    //APP的id
    public static final String APPID = "wx06d3924c93f401fd";
    //APP的秘钥
    public static final String APPSECRET = "019d49ee012289ffc65f60814d9ae9dd";


    public static JSONObject doGetJson(String url) throws IOException {
        JSONObject jsonObject = null;
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response = client.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if(entity !=  null){
            String result = EntityUtils.toString(entity,"UTF-8");
            jsonObject = JSONObject.fromObject(result);
        }
        httpGet.releaseConnection();
        return jsonObject;
    }
}
