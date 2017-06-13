package com.wechat.news.pojo;

import com.wechat.common.pojo.BaseMessage;

import java.util.List;

/**
 * Created by 30825 on 2017/5/13.
 */
public class NewsMessage extends BaseMessage {
    private int ArticleCount;
    private List<News> Articles;

    public int getArticleCount() {
        return ArticleCount;
    }

    public void setArticleCount(int articleCount) {
        ArticleCount = articleCount;
    }

    public List<News> getArticles() {
        return Articles;
    }

    public void setArticles(List<News> articles) {
        Articles = articles;
    }
}
