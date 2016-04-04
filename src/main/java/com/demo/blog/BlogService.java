package com.demo.blog;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;

public class BlogService {

    @Before(Tx.class)
    public void edit(Blog blog) {
        blog.update();

        // int v = 1 / 0;

        blog.set("title", "hahaha-hahaha").update();
    }

}
