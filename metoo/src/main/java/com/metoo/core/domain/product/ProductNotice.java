package com.metoo.core.domain.product;

/**
 * User: Zhang xiaomei
 * Date: 2016/12/11
 */
public class ProductNotice {

    private String title;
    private String content;

    public ProductNotice() {
    }

    public ProductNotice(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
