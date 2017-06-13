package com.wechat.common.utils;


import com.wechat.common.pojo.image.Image;
import com.wechat.common.pojo.image.ImageMessage;
import com.wechat.common.pojo.music.Music;
import com.wechat.common.pojo.music.MusicMessage;
import com.wechat.login.pojo.TextMessage;
import com.wechat.news.pojo.News;
import com.wechat.news.pojo.NewsMessage;
import com.thoughtworks.xstream.XStream;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by 30825 on 2017/5/13.
 */
public class MessageUtil {
    /**
     * 回复类型
     */
    //文本
    public static final String MESSAGE_TEXT = "text";
    //图片
    public static final String MESSAGE_IMAGE = "image";
    //语言
    public static final String MESSAGE_VOICE = "voice";
    //音乐
    public static final String MESSAGE_MUSIC = "music";
    //视频
    public static final String MESSAGE_VIDEO = "video";
    //链接消息
    public static final String MESSAGE_LINK = "link";
    //地理位置消息
    public static final String MESSAGE_LOCATION = "LOCATION";
    //事件推送
    public static final String MESSAGE_EVENT = "event";
    //事件推送 -> 关注
    public static final String MESSAGE_SUBSCRIBE = "subscribe";
    //事件推送 -> 取消关注
    public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe";
    //图文消息
    public static final String MESSAGE_NEWS = "news";
    //click菜单
    public static final String MESSAGE_CLICK = "CLICK";
    //VIEW菜单
    public static final String MESSAGE_VIEW = "VIEW";

    public static final String MESSAGE_SCANCODE= "scancode_push";

    /**
     * xml文本 转换成 map集合
     *
     * @param request
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    public static Map<String, String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException {
        Map<String, String> map = new HashMap<String, String>();
        SAXReader reader = new SAXReader();

        //从request中获取输入流
        InputStream inputStream = request.getInputStream();
        Document doc = reader.read(inputStream);

        //获取xml的root权限
        Element root = doc.getRootElement();
        List<Element> list = root.elements();

        //循环便利存放到map集合中
        for (Element e : list) {
            map.put(e.getName(), e.getText());
        }
        inputStream.close();
        return map;
    }

    /**
     * 文本 转换成 xml
     */
    public static String textMessageToXml(TextMessage textMessage) {
        XStream xStream = new XStream();
        //微信文档接收根节点为<xml>,不为类的全路径
        xStream.alias("xml", textMessage.getClass());
        return xStream.toXML(textMessage);
    }

    /**
     * 主菜单
     */
    public static String menuText() {
        StringBuffer sb = new StringBuffer();
        sb.append("欢迎您的关注，请按照菜单提示进行操作 : \n\n");
        sb.append("1、自我介绍 \n");
        sb.append("2、北京介绍 \n");
        sb.append("3、微信介绍 \n");
        sb.append("4、魔兽图片 \n");
        sb.append("5、速度与激情7片曲 \n");
        sb.append("回复 ? 调出此菜单。");
        return sb.toString();
    }

    /**
     * 自我介绍回复
     */
    public static String firstMenu() {
        StringBuffer sb = new StringBuffer();
        sb.append("我叫小开,来自江西");
        return sb.toString();
    }

    /**
     * 自我介绍回复
     */
    public static String secondMenu() {
        StringBuffer sb = new StringBuffer();
        sb.append("北京俗称帝都,国家首都");
        return sb.toString();
    }

    /**
     * 拼接文本消息
     */
    public static String initText(String toUserName, String fromUserName, String content) {
        TextMessage text = new TextMessage();
        //返回正好相反
        text.setFromUserName(toUserName);
        text.setToUserName(fromUserName);
        text.setMsgType(MESSAGE_TEXT);
        text.setCreateTime(new Date().getTime());
        text.setContent(content);
        return textMessageToXml(text);
    }

    /**
     * 图文消息 转换成 xml
     */
    public static String newsMessageToXml(NewsMessage newsMessage) {
        XStream xStream = new XStream();
        //微信文档接收根节点为<xml>,不为类的全路径
        xStream.alias("xml", newsMessage.getClass());
        xStream.alias("item", new News().getClass());
        return xStream.toXML(newsMessage);
    }

    /**
     * 拼接图文消息
     */
    public static String initNewsMessage(String toUserName, String fromUserName) {
        List<News> newsList = new ArrayList<News>();
        NewsMessage newsMessage = new NewsMessage();
        News news = new News();
        news.setTitle("微信介绍");
        news.setDescription("微信是腾讯旗下的一款软件");
        news.setPicUrl("127.0.0.1:8080/image/weixin.png");
        news.setUrl("https://wx.qq.com/");
        newsList.add(news);
        newsMessage.setArticles(newsList);
        newsMessage.setArticleCount(newsList.size());
        //返回正好相反
        newsMessage.setFromUserName(toUserName);
        newsMessage.setToUserName(fromUserName);
        newsMessage.setCreateTime(new Date().getTime());
        newsMessage.setMsgType(MESSAGE_NEWS);
        return newsMessageToXml(newsMessage);
    }

    /**
     * 图片消息 转换成 xml
     */
    public static String imageMessageToXml(ImageMessage imageMessage) {
        XStream xStream = new XStream();
        //微信文档接收根节点为<xml>,不为类的全路径
        xStream.alias("xml", imageMessage.getClass());
        return xStream.toXML(imageMessage);
    }

    /**
     * 图片消息拼接
     */
    public static String initImageMessage(String toUserName, String fromUserName){
        Image image = new Image();
        //获取main方法上传的medaId
        //egtsWJ8xWeDdqd3CpJG3BEWRLHRob4nyAhnqUM_-NSLjK4iIjH_3ieFrJ-cFhFlj
        image.setMediaId("g8s5JDGaGoPm-jshzT5gf0q-q-dv3W7_gfgbrqsR8wLHyItfgIYwWB8sSwU9i82C");
        ImageMessage imageMessage = new ImageMessage();
        imageMessage.setFromUserName(toUserName);
        imageMessage.setToUserName(fromUserName);
        imageMessage.setMsgType(MESSAGE_IMAGE);
        imageMessage.setCreateTime(new Date().getTime());
        imageMessage.setImage(image);
        String message = imageMessageToXml(imageMessage);
        return message;
    }

    /**
     * 图片消息 转换成 xml
     */
    public static String musicMessageToXml(MusicMessage musicMessage) {
        XStream xStream = new XStream();
        //微信文档接收根节点为<xml>,不为类的全路径
        xStream.alias("xml", musicMessage.getClass());
        return xStream.toXML(musicMessage);
    }

    /**
     * 图片消息拼接
     */
    public static String initMusicMessage(String toUserName, String fromUserName){
        Music music = new Music();
        music.setTitle("see you again");
        music.setDescription("速七片曲");

        music.setMusicUrl("http://xiaokai.tunnel.2bdata.com/music/See_you_again.mp3");
        //设置高级路径
        music.setHQMusicUrl("http://xiaokai.tunnel.2bdata.com/music/See_you_again.mp3");

        MusicMessage musicMessage = new MusicMessage();
        musicMessage.setFromUserName(toUserName);
        musicMessage.setToUserName(fromUserName);
        musicMessage.setMsgType(MESSAGE_MUSIC);
        musicMessage.setCreateTime(new Date().getTime());
        musicMessage.setMusic(music);
        String message = musicMessageToXml(musicMessage);
        return message;
    }
}
