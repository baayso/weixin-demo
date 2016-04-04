package cn.xyspace.xyweixin.fun.resource;

import java.util.List;

import org.joda.time.DateTime;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

/**
 * 
 *
 * @author ChenFangjie(2015年7月3日 下午5:13:00)
 *
 * @since 1.0.0
 *
 * @version 1.0.0
 *
 */
public class Resource extends Model<Resource> {

    private static final long serialVersionUID = 3028365478269337368L;

    /** 全局共享对象， 只能用于查询数据库， 不能用于承载数据对象。承载数据需要使用 new Resource().set(…)来实现。 */
    public static final Resource dao = new Resource();

    public List<Resource> selectsWithoutAttachment() {
        String sql = "SELECT * FROM t_resource WHERE \"expDate\" >= ? ORDER BY random() LIMIT 10";
        return Resource.dao.find(sql, DateTime.now().getMillis());
    }

    public Resource selectWithAttachment(String id) {
        String sql = "SELECT r.*, ra.\"id\" AS \"raId\", ra.type AS \"raType\", ra.\"path\", ra.\"intro\" AS \"raIntro\" FROM t_resource r INNER JOIN t_resource_attachment ra ON r.\"id\" = ra.\"resourceId\" WHERE r.\"id\" = ? ORDER BY random() LIMIT 1";
        return Resource.dao.findFirst(sql, id);
    }

    public boolean updateJoinCount(String id) {
        String sql = "UPDATE t_resource SET \"joinCount\" = \"joinCount\" + 1 WHERE \"id\" = ?";
        return Db.update(sql, id) > 0;
    }

}
