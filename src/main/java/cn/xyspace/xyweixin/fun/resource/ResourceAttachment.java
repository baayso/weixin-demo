package cn.xyspace.xyweixin.fun.resource;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;

/**
 * 
 *
 * @author ChenFangjie(2015年7月3日 下午5:16:00)
 *
 * @since 1.0.0
 *
 * @version 1.0.0
 *
 */
public class ResourceAttachment extends Model<ResourceAttachment> {

    private static final long serialVersionUID = 8548364567528134066L;

    /** 全局共享对象， 只能用于查询数据库， 不能用于承载数据对象。承载数据需要使用 new ResourceAttachment().set(…)来实现。 */
    public static final ResourceAttachment dao = new ResourceAttachment();

    public List<ResourceAttachment> selectsByResourceId(String resourceId) {
        String sql = "SELECT id, path, intro FROM t_resource_attachment WHERE \"resourceId\" = ? ORDER BY random() LIMIT 1";
        return ResourceAttachment.dao.find(sql, resourceId);
    }

}
