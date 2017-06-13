package com.wechat.common.pojo;

/**
 * Created by 30825 on 2017/5/13.
 */
public class BaseMessage {
    private String ToUserName;              //接收方帐号（收到的OpenID）
    private String FromUserName;            //开发者微信号
    private Long CreateTime;                //消息创建时间 （整型）
    private String MsgType;                 //消息类型

    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }

    public Long getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(Long createTime) {
        CreateTime = createTime;
    }

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }

}
