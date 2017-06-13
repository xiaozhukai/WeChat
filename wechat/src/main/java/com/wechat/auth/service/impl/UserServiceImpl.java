package com.wechat.auth.service.impl;


import com.wechat.auth.dao.UserDao;
import com.wechat.auth.pojo.User;
import com.wechat.auth.service.UserService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by 30825 on 2017/5/18.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    @Override
    public User boundUser(JSONObject userInfo) {
        long count = userDao.getCount();
        System.out.println(count);

        //User user = userDao.findDistinctByOpenId(userInfo.getString("openid"));
        String nickName1 = userDao.findnickNameDistinctByOpenId(userInfo.getString("openid"));

        System.out.println(nickName1);
        //为空说明没有绑定使用账号密码条件进行绑定
        if(nickName1 == null){
            String nickName = userInfo.getString("nickname");
            String openId = userInfo.getString("openid");
            userDao.updateUser(nickName,openId);
        }
        return new User();
    }

}
