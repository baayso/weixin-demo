package cn.xyspace.xyweixin.fun.user;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

/**
 * 
 *
 * @author ChenFangjie(2015年7月3日 下午9:57:29)
 *
 * @since 1.0.0
 *
 * @version 1.0.0
 *
 */
public class User extends Model<User> {

    private static final long serialVersionUID = -8985445412107966247L;

    /** 全局共享对象， 只能用于查询数据库， 不能用于承载数据对象。承载数据需要使用 new User().set(…)来实现。 */
    public static final User dao = new User();

    public User selectByTelephone(String telephone) {
        String sql = "SELECT * FROM t_user WHERE telephone = ?";
        return User.dao.findFirst(sql, telephone);
    }

    public boolean updateProfit(Integer reward) {
        String sql = "UPDATE t_user SET profit = profit + ? WHERE id = ?";
        return Db.update(sql, reward, super.get("id")) > 0;
    }

}
