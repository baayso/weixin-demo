package com.demo.blog;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * <pre>
 * Blog model.
 * 
 * 将表结构放在此，消除记忆负担
 * 
 * psql -U postgres -d xyweixin
 * 
 * xyweixin=# \d t_blog
 * 
 * +---------+--------------+------+-----+---------+----------------+
 * | Field   | Type         | Null | Key | Default | Extra          |
 * +---------+--------------+------+-----+---------+----------------+
 * | id      | bigserial    | NO   | PRI | NULL    |                |
 * | title   | varchar(200) | NO   |     | NULL    |                |
 * | content | varchar      | NO   |     | NULL    |                |
 * +---------+--------------+------+-----+---------+----------------+
 * 
 * 数据库字段名建议使用驼峰命名规则，便于与 java 代码保持一致，如字段名： userId
 * 
 * </pre>
 */
public class Blog extends Model<Blog> {

    private static final long serialVersionUID = 3392168147269417695L;

    /** 全局共享对象， 只能用于查询数据库， 不能用于承载数据对象。承载数据需要使用 new Blog().set(…)来实现。 */
    public static final Blog me = new Blog();

    /**
     * 所有 sql 与业务逻辑写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
     */
    public Page<Blog> paginate(int pageNumber, int pageSize) {
        return paginate(pageNumber, pageSize, "select *", "from t_blog order by id asc");
    }
}
