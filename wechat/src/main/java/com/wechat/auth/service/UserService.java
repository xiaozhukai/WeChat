package com.wechat.auth.service;

import com.wechat.auth.pojo.User;
import net.sf.json.JSONObject;

/**
 * Created by 30825 on 2017/5/18.
 */
public interface UserService {

    User boundUser(JSONObject userInfo);

}
