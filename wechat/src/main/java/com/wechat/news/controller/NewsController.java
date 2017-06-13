package com.wechat.news.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 30825 on 2017/5/13.
 */
@RestController
public class NewsController {
    @GetMapping(value = "/getImage")
    public String getImage(){
        return "/image/weixin.png";
    }
}
