package com.demo.blog;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

/**
 * BlogInterceptor 此拦截器仅做为示例展示，在本 demo 中并不需要
 */
public class BlogInterceptor implements Interceptor {

    @Override
    public void intercept(Invocation ai) {
        System.out.println("Before invoking " + ai.getActionKey());
        ai.invoke();
        System.out.println("After invoking " + ai.getActionKey());
    }
}
