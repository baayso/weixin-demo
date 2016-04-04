package cn.xyspace.xyweixin.fun.resource;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

/**
 * 
 *
 * @author ChenFangjie(2015年7月7日 下午5:16:00)
 *
 * @since 1.0.0
 *
 * @version 1.0.0
 *
 */
public class ResourceJoinRecord extends Model<ResourceJoinRecord> {

    private static final long serialVersionUID = -6946902593038596388L;

    /** 全局共享对象， 只能用于查询数据库， 不能用于承载数据对象。承载数据需要使用 new ResourceJoinRecord().set(…)来实现。 */
    public static final ResourceJoinRecord dao = new ResourceJoinRecord();

    public Long selectCount(String telephone, String resourceId) {
        String sql = "SELECT COUNT(*) FROM t_resource_join_record WHERE telephone = ? AND \"resourceId\" = ?";

        return Db.queryLong(sql, telephone, resourceId);
    }

}
