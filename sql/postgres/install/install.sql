
-- 用户表
DROP TABLE IF EXISTS "t_user";
CREATE TABLE "t_user" (
       "id"                   VARCHAR(32)                    PRIMARY KEY             ,           --用户ID，主键
       "name"                 VARCHAR(100)                                           ,           --用户的真实姓名
       "telephone"            VARCHAR(20)                    NOT NULL                ,           --用户电话
       "email"                VARCHAR(100)                                           ,           --用户电子邮件
       "password"             VARCHAR(100)                                           ,           --密码
       "salt"                 VARCHAR(100)                                           ,           --密码盐（密码作料）
       "isLocked"             BOOLEAN                        NOT NULL DEFAULT FALSE  ,           --用户是否已锁定（默认FALSE）
       "firstUseIp"           VARCHAR(64)                                            ,           --首次使用IP地址
       "firstUseTime"         BIGINT                         NOT NULL                ,           --首次使用时间戳
       "lastUseTime"          BIGINT                                                 ,           --最后使用时间戳
       "profit"               INTEGER                        NOT NULL DEFAULT 0      ,           --已获得的收益，单位：分
       --
       "openid"               VARCHAR(100)                                           ,           --用户的唯一标识
       "nickname"             VARCHAR(100)                                           ,           --用户昵称
       "sex"                  SMALLINT                       DEFAULT 0               ,           --用户性别，0:未知、1:男、2:女
       "country"              VARCHAR(20)                                            ,           --国家，如中国为CN
       "province"             VARCHAR(20)                                            ,           --用户个人资料填写的省份
       "city"                 VARCHAR(20)                                            ,           --普通用户个人资料填写的城市
       "headimgurl"           VARCHAR(255)                                           ,           --用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效
       "privilege"            VARCHAR(255)                                           ,           --用户特权信息，json数组
       "unionid"              VARCHAR(100)                                                       --只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段
);
ALTER TABLE "t_user" ADD CONSTRAINT "t_user_telephone_unique" UNIQUE ("telephone");
-- ALTER TABLE "t_user" ADD CONSTRAINT "t_user_email_unique" UNIQUE ("email");
-- ALTER TABLE "t_user" ADD UNIQUE ("telephone");
-- CREATE INDEX "t_user_reg_city_index" ON "t_user"("reg_city");


-- 资源表
DROP TABLE IF EXISTS "t_resource";
CREATE TABLE "t_resource" (
       "id"                   VARCHAR(32)                    PRIMARY KEY             ,           --主键
       "type"                 SMALLINT                       NOT NULL DEFAULT 0      ,           --类型
       "expDate"              BIGINT                         NOT NULL                ,           --有效期
       "joinCount"            INTEGER                        NOT NULL DEFAULT 0      ,           --当前参与次数
       "maxJoinCount"         INTEGER                        NOT NULL DEFAULT 0      ,           --最大参与次数
       "spreadCount"          INTEGER                        NOT NULL DEFAULT 0      ,           --当前传播次数
       "maxSpreadCount"       INTEGER                        NOT NULL DEFAULT 0      ,           --最大传播次数
       "joinReward"           INTEGER                        NOT NULL DEFAULT 0      ,           --参与奖励，单位：分
       "spreadReward"         INTEGER                        NOT NULL DEFAULT 0      ,           --传播奖励，单位：分
       "spreadRange"          INTEGER                        NOT NULL DEFAULT 0      ,           --传播范围，0表示不限制
       "repostCount"          INTEGER                        NOT NULL DEFAULT 0      ,           --分享数
       "likeCount"            INTEGER                        NOT NULL DEFAULT 0      ,           --点赞数
       "browseCount"          INTEGER                        NOT NULL DEFAULT 0      ,           --浏览数
       "intro"                VARCHAR(255)                                                       --简介
);
CREATE INDEX "t_resource_type_index" ON "t_resource"("type");
CREATE INDEX "t_resource_expiry_date_index" ON "t_resource"("expDate");


-- 资源相关附件表
DROP TABLE IF EXISTS "t_resource_attachment";
CREATE TABLE "t_resource_attachment" (
       "id"                   VARCHAR(32)                    PRIMARY KEY             ,           --主键
       "resourceId"           VARCHAR(32)                    NOT NULL                ,           --所属资源的ID（外键）
       "type"                 SMALLINT                       NOT NULL                ,           --附件所属类型
       "path"                 VARCHAR(255)                   NOT NULL                ,           --附件保存路径
       "link"                 VARCHAR(255)                                           ,           --附件链接
       "clickCount"           INTEGER                        NOT NULL DEFAULT 0      ,           --点击次数
       "mime"                 VARCHAR(64)                                            ,           --附件格式
       "size"                 BIGINT                                                 ,           --附件大小
       "extname"              VARCHAR(20)                                            ,           --附件扩展名
       "hash"                 VARCHAR(64)                                            ,           --附件哈希值
       "width"                INTEGER                                                ,           --图片宽度
       "height"               INTEGER                                                ,           --图片高度
       "ctime"                BIGINT                         NOT NULL                ,           --附件上传时间戳
       "ftime"                BIGINT                         NOT NULL                ,           --附件失效时间戳
       "intro"                VARCHAR(255)                                                       --简介
);
CREATE INDEX "t_resource_attachment_resource_id_index" ON "t_resource_attachment"("resourceId");
CREATE INDEX "t_resource_attachment_type_index" ON "t_resource_attachment"("type");


-- 资源参与记录表
DROP TABLE IF EXISTS "t_resource_join_record";
CREATE TABLE "t_resource_join_record" (
       "id"                   VARCHAR(32)                    PRIMARY KEY             ,           --主键
       "telephone"            VARCHAR(20)                    NOT NULL                ,           --用户电话
       "resourceId"           VARCHAR(32)                    NOT NULL                ,           --所属资源的ID（外键）
       "ctime"                BIGINT                         NOT NULL                            --参与时间
);
CREATE INDEX "t_resource_join_record_telephone_index" ON "t_resource_join_record"("telephone");
CREATE INDEX "t_resource_join_record_resource_id_index" ON "t_resource_join_record"("resourceId");

