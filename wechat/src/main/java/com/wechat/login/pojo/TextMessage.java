package com.wechat.login.pojo;

import com.wechat.common.pojo.BaseMessage;

/**
 * Created by 30825 on 2017/5/13.
 */
public class TextMessage extends BaseMessage {
    private String Content;
    private String MsgId;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getMsgId() {
        return MsgId;
    }

    public void setMsgId(String msgId) {
        MsgId = msgId;
    }
}
