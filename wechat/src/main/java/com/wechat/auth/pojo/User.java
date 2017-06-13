package com.wechat.auth.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by 30825 on 2017/5/21.
 */
@Entity
public class User {

    @Id
    @GeneratedValue
    private Integer id;         //主键

    @Column(length = 20,nullable = false)
    private String account;     //账号
    @Column(length = 50,nullable = false)
    private String password;    //密码
    @Column(length = 50)
    private String nickName;    //昵称
    @Column(length = 50)
    private String openId;      //微信id


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
