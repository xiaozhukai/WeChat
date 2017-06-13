package com.wechat.login.controller;


import com.wechat.common.utils.CheckUtil;
import com.wechat.common.utils.MessageUtil;
import org.dom4j.DocumentException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by 30825 on 2017/5/9.
 */
@RestController
//注入配置使用
public class WeChatController {

    @GetMapping(value = "/")
    public String doGet(@RequestParam(value = "signature") String signature, @RequestParam(value = "timestamp") String timestamp, @RequestParam(value = "nonce") String nonce, @RequestParam(value = "echostr") String echostr) throws IOException {
        if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
            return echostr;
        }
        return "";
    }

    @PostMapping(value = "/")
    protected String doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException {
        Map<String, String> map = MessageUtil.xmlToMap(request);
        String toUserName = map.get("ToUserName");
        String fromUserName = map.get("FromUserName");
        String msgType = map.get("MsgType");
        String content = map.get("Content");

        //判断是否是文本消息对象
        String message = null;
        if (MessageUtil.MESSAGE_TEXT.equals(msgType)) {
            //设置了1,2,3,?自动回复(不是必须的)
            if ("1".equals(content)) {
                message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.firstMenu());
            } else if ("2".equals(content)) {
                message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.secondMenu());
            } else if ("3".equals(content)) {
                message = MessageUtil.initNewsMessage(toUserName, fromUserName);
            } else if ("4".equals(content)) {
                message = MessageUtil.initImageMessage(toUserName, fromUserName);
            } else if ("5".equals(content)) {
                message = MessageUtil.initMusicMessage(toUserName, fromUserName);
            }else if ("?".equals(content) || "？".equals(content)) {
                message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
            }
            //事件推送
        } else if (MessageUtil.MESSAGE_EVENT.equals(msgType)) {
            String eventType = map.get("Event");
            //事件推送类型 -> 关注
            if (MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)) {
                message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
            //click类型菜单
            }else if (MessageUtil.MESSAGE_CLICK.equals(eventType)){
                System.out.println(MessageUtil.MESSAGE_CLICK);
                //调用显示一次菜单
                message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
            //view类型菜单
            }else if (MessageUtil.MESSAGE_VIEW.equals(eventType)){
                String url = map.get("EventKey");
                message = MessageUtil.initText(toUserName, fromUserName,url);
            // 事件推送 -> 关注
            }else if (MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)){
                String key = map.get("EventKey");
                message = MessageUtil.initText(toUserName, fromUserName,key);
                //地理位置消息
            // }else if (MessageUtil.MESSAGE_LOCATION.equals(map.get("LOCATION"))) {
            //      String label = map.get("Precision");
            //     message = MessageUtil.initText(toUserName, fromUserName, label);
            }
        }
        //String EventKey = map.get("EventKey");
        //String SendLocationInfo = map.get("SendLocationInfo");
        System.out.println(message);
        return message;
    }
}
