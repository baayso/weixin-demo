package cn.xyspace.common.core.utils;

import org.bson.types.ObjectId;
import org.springside.modules.utils.Identities;

/**
 * 主键生成工具类。
 * 
 * @author ChenFangjie(2014/12/26 9:45:48)
 * 
 * @since 1.0.0
 * 
 * @version 1.0.0
 *
 */
public final class IdUtils {

    // 让工具类彻底不可以实例化
    private IdUtils() {
        throw new Error("工具类不可以实例化！");
    }

    /** ObjectId长度 */
    public static final int OBJECT_ID_LENGTH = 24;

    /**
     * 获取主键，使用mongoDB的ObjectId。
     * 
     * @return 主键
     * 
     * @since 1.0.0
     * @version 1.0.0
     */
    public static String getId() {
        return ObjectId.get().toHexString();
    }

    /**
     * 获取uuid，使用jdk自带的uuid，通过Random数字生成，中间无-分割。
     * 
     * @return uuid
     * 
     * @since 1.0.0
     * @version 1.0.0
     */
    public static String getUuid() {
        return Identities.uuid2();
    }

}
