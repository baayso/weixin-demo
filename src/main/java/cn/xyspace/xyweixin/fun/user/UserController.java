package cn.xyspace.xyweixin.fun.user;

import com.jfinal.core.Controller;

/**
 * 用户信息控制器。
 *
 * @author ChenFangjie(2015年7月6日 下午12:51:02)
 *
 * @since 1.0.0
 *
 * @version 1.0.0
 *
 */
public class UserController extends Controller {

    /**
     * 
     * 
     * @since 1.0.0
     * @version 1.0.0
     */
    public void loginUI() {
        super.render("login.html");
    }

    /**
     * 
     * 
     * @since 1.0.0
     * @version 1.0.0
     */
    public void login() {
        String telephone = super.getPara("telephone");

        User user = User.dao.selectByTelephone(telephone);

        if (user == null) {
            super.renderText("手机号或验证码错误");
            return;
        }

        // 记录session

        user.set("profit", user.getInt("profit") / 100.0);

        super.setAttr("user", user);

        super.render("info.html");
    }

    /**
     * 
     * 
     * @since 1.0.0
     * @version 1.0.0
     */
    public void info() {
        String telephone = super.getPara("telephone");

        User user = User.dao.selectByTelephone(telephone);

        super.renderJson(user);
    }

}
