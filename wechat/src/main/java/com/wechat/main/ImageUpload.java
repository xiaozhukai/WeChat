package com.wechat.main;


import com.wechat.common.pojo.AccessToken;
import com.wechat.common.utils.MessageUtil;
import com.wechat.common.utils.WeChatUtil;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

/**
 * Created by 30825 on 2017/5/14.
 */
public class ImageUpload {
    public static void main(String[] args) {
        try {
            AccessToken accessToken = WeChatUtil.getAccessToken();
            System.out.println("票据："+ accessToken.getToken());
            System.out.println("有效时间："+ accessToken.getExpiresIn());
            //图片上传
            String imageUrl = "D:/wechat-resource/image/11082909231a78d1f71b84f628.jpg";
            String mediaId = WeChatUtil.upload(imageUrl,accessToken.getToken(), MessageUtil.MESSAGE_IMAGE);
            System.out.println(mediaId);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
    }

}
