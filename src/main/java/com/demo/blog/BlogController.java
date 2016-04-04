package com.demo.blog;

import com.jfinal.aop.Before;
import com.jfinal.aop.Enhancer;
import com.jfinal.core.Controller;

/**
 * BlogController 所有 sql 与业务逻辑写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
 */
@Before(BlogInterceptor.class)
public class BlogController extends Controller {

    // private BlogService blogService = new BlogService();

    // private BlogService blogService = Duang.duang(BlogService.class, Tx.class);

    // 使用 enhance方法对业务层进行增强，使用具有AOP能力
    // private BlogService blogService = super.enhance(BlogService.class);
    private BlogService blogService = Enhancer.enhance(BlogService.class);

    public void list() {
        super.setAttr("blogPage", Blog.me.paginate(super.getParaToInt("pageNumber", 1), 10));
        super.render("list.html");
    }

    public void addUI() {
        super.render("add.html");
    }

    @Before(BlogValidator.class)
    public void add() {
        super.getModel(Blog.class).save();
        super.redirect("/blog/list");
    }

    public void editUI() {
        super.setAttr("blog", Blog.me.findById(getParaToInt()));
        super.render("edit.html");
    }

    // @Before(BlogValidator.class)
    // public void edit() {
    // super.getModel(Blog.class).update();
    // super.redirect("/blog/list");
    // }

    @Before(BlogValidator.class)
    public void edit() {
        blogService.edit(super.getModel(Blog.class));
        super.redirect("/blog/list");
    }

    public void delete() {
        Blog.me.deleteById(getParaToInt());
        super.redirect("/blog/list");
    }
}
