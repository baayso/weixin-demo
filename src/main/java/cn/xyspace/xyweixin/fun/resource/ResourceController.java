package cn.xyspace.xyweixin.fun.resource;

import cn.xyspace.common.core.utils.DateTimeUtils;
import cn.xyspace.common.core.utils.IdUtils;
import cn.xyspace.common.core.utils.WebUtils;
import cn.xyspace.xyweixin.fun.user.User;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.tx.Tx;

/**
 * 资源信息控制器。
 *
 * @author ChenFangjie(2015年7月3日 下午6:25:41)
 *
 * @since 1.0.0
 *
 * @version 1.0.0
 *
 */
public class ResourceController extends Controller {

    /**
     * 
     * 
     * @since 1.0.0
     * @version 1.0.0
     */
    public void list() {
        super.setAttr("resourceList", Resource.dao.selectsWithoutAttachment());
        super.render("list.html");
    }

    /**
     * 
     * 
     * @since 1.0.0
     * @version 1.0.0
     */
    public void detail() {
        String id = super.getPara("id");

        Resource resource = Resource.dao.selectWithAttachment(id);

        resource.set("joinReward", resource.getInt("joinReward") / 100.0);

        super.setAttr("resource", resource);

        super.render("detail.html");
    }

    /**
     * 
     * 
     * @since 1.0.0
     * @version 1.0.0
     */
    @Before(Tx.class)
    public void join() {
        String resourceId = super.getPara("resourceId");
        String raId = super.getPara("raId");
        String telephone = super.getPara("telephone");
        String answer = super.getPara("answer");

        Resource resource = Resource.dao.findById(resourceId);

        if (resource == null) {
            // 中止
            super.renderText("未知的资源");
            return;
        }

        if (resource.getInt("joinCount") >= resource.getInt("maxJoinCount")) {
            // 中止
            super.renderText("已达到最大参与次数");
            return;
        }

        // 判断是否已参加
        Long joinCount = ResourceJoinRecord.dao.selectCount(telephone, resourceId);

        if (joinCount != null && joinCount > 0) {
            // 中止
            super.renderText("已参与，不允许重复参与");
            return;
        }

        ResourceAttachment resourceAttachment = ResourceAttachment.dao.findById(raId);

        if (resourceAttachment == null) {
            // 中止
            super.renderText("未知的资源");
            return;
        }

        if (!answer.equals(resourceAttachment.getStr("intro"))) {
            // 中止
            super.renderText("问题答案错了");
            return;
        }

        Resource.dao.updateJoinCount(resourceId);

        Integer joinReward = resource.getInt("joinReward");

        User user = User.dao.selectByTelephone(telephone);

        if (user == null) {
            new User().set("id", IdUtils.getId()) //
                    .set("telephone", telephone) //
                    .set("firstUseIp", WebUtils.getRealIp(super.getRequest())) //
                    .set("firstUseTime", DateTimeUtils.getCurrentTimeInMillis()) //
                    .set("profit", joinReward) //
                    .save();
        }
        else {
            user.updateProfit(joinReward);
        }

        // 添加参与记录
        new ResourceJoinRecord().set("id", IdUtils.getId()) //
                .set("telephone", telephone) //
                .set("resourceId", resourceId) //
                .set("ctime", DateTimeUtils.getCurrentTimeInMillis()) //
                .save();

        super.renderText("参与成功");
    }

}
