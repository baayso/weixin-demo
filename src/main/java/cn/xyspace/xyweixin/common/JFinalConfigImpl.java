package cn.xyspace.xyweixin.common;

import org.apache.commons.lang3.StringUtils;
import org.beetl.ext.jfinal.BeetlRenderFactory;

import cn.xyspace.common.core.utils.security.AESCoder;
import cn.xyspace.xyweixin.fun.index.IndexController;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.ext.plugin.sqlinxml.SqlInXmlPlugin;
import com.jfinal.ext.plugin.tablebind.AutoTableBindPlugin;
import com.jfinal.ext.plugin.tablebind.ParamNameStyles;
import com.jfinal.ext.route.AutoBindRoutes;
import com.jfinal.plugin.activerecord.dialect.PostgreSqlDialect;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.druid.DruidStatViewHandler;
import com.jfinal.plugin.redis.RedisPlugin;
import com.jfinal.render.ViewType;

/**
 * API引导式配置。
 * 
 * @author ChenFangjie(2015年7月2日 上午10:10:10)
 * 
 * @since 1.0.0
 * 
 * @version 1.0.0
 *
 */
public class JFinalConfigImpl extends JFinalConfig {

    // private static final Logger log = LoggerFactory.getLogger(ServerConfig.class);

    /** 配置常量 */
    @Override
    public void configConstant(Constants me) {
        // 加载数据库配置，随后可用getProperty(...)获取值
        super.loadPropertyFile("config.properties");

        // 配置开发模式
        me.setDevMode(super.getPropertyToBoolean("xyweixin.devMode", false));

        // 设置视图类型为JSP，默认为FreeMarker
        me.setViewType(ViewType.JSP);
        // me.setEncoding("UTF-8");

        me.setMainRenderFactory(new BeetlRenderFactory());
        // 获取GroupTemplate ,可以设置共享变量等操作
        // GroupTemplate groupTemplate = BeetlRenderFactory.groupTemplate;

        // 配置文件上传的保存基路径
        me.setUploadedFileSaveDirectory(super.getProperty("xyweixin.fileUploadDir", "upload"));

        // me.setErrorView(401, "/au/login.html");
        // me.setErrorView(403, "/au/login.html");
        // me.setError404View("/404.html");
        // me.setError500View("/500.html");
    }

    /** 配置路由 */
    @Override
    public void configRoute(Routes me) {
        // 由于使用了beetl模板引擎，第三个参数（视图存放路径）以 beetl.properties中的 RESOURCE.root= /WEB-INF/views/ 配置为 根路径
        me.add("/", IndexController.class, "/"); // 第三个参数为该Controller的视图存放路径
        // me.add("/blog", BlogController.class, "/WEB-INF/jsp/blog/");

        // 自动注册Route
        me.add(new AutoBindRoutes());
    }

    /** 配置插件 */
    @Override
    public void configPlugin(Plugins me) {

        // String jdbcUsername = super.getProperty("jdbc.username");
        // String jdbcPassword = super.getProperty("jdbc.password");

        String jdbcUsername = AESCoder.decryptStr(super.getProperty("jdbc.username"));
        String jdbcPassword = AESCoder.decryptStr(super.getProperty("jdbc.password"));

        // 配置Druid数据库连接池插件
        DruidPlugin druidPlugin = new DruidPlugin(super.getProperty("jdbc.url"), jdbcUsername, jdbcPassword, super.getProperty("jdbc.driverClassName"));
        // 初始化大小
        druidPlugin.setInitialSize(super.getPropertyToInt("druid.initialSize", 10));
        // 最小连接
        druidPlugin.setMinIdle(super.getPropertyToInt("druid.minIdle", 10));
        // 最大连接
        druidPlugin.setMaxActive(super.getPropertyToInt("druid.maxActive", 100));
        // 获取连接等待超时的时间
        druidPlugin.setMaxWait(Long.parseLong(super.getProperty("druid.maxWait", "60000")));
        // 间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        druidPlugin.setTimeBetweenEvictionRunsMillis(Long.parseLong(super.getProperty("druid.timeBetweenEvictionRunsMillis", "60000")));
        // 一个连接在池中最小生存的时间，单位是毫秒
        druidPlugin.setMinEvictableIdleTimeMillis(Long.parseLong(super.getProperty("druid.minEvictableIdleTimeMillis", "300000")));
        druidPlugin.setValidationQuery(super.getProperty("druid.validationQuery", "SELECT 'x'"));
        druidPlugin.setTestWhileIdle(super.getPropertyToBoolean("druid.testWhileIdle", true));
        druidPlugin.setTestOnBorrow(super.getPropertyToBoolean("druid.testOnBorrow", false));
        druidPlugin.setTestOnReturn(super.getPropertyToBoolean("druid.testOnReturn", false));
        // 只要maxPoolPreparedStatementPerConnectionSize>0, poolPreparedStatements就会被自动设定为true，使用oracle时可以设定此值
        druidPlugin.setMaxPoolPreparedStatementPerConnectionSize(super.getPropertyToInt("druid.maxPoolPreparedStatementPerConnectionSize", 20));
        // 监控统计拦截的filters
        druidPlugin.setFilters(super.getProperty("druid.filters", "stat,wall"));
        // 是否打开连接泄露自动检测
        druidPlugin.setRemoveAbandoned(super.getPropertyToBoolean("druid.removeAbandoned", false));
        // 连接长时间没有使用，被认为发生泄露时长
        druidPlugin.setRemoveAbandonedTimeoutMillis(Long.parseLong(super.getProperty("druid.removeAbandonedTimeoutMillis", "300000")));
        // 发生泄露时是否需要输出 log，建议在开启连接泄露检测时开启，方便排错
        druidPlugin.setLogAbandoned(super.getPropertyToBoolean("druid.logAbandoned", false));
        // add
        me.add(druidPlugin);

        // ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring.xml");
        // 加载Spring插件
        // me.add(new SpringPlugin(ctx));
        // DataSource dataSource = (DataSource) ctx.getBean("dataSource");
        // DbKit.addConfig(new Config("xyserver", dataSource));

        // 配置自动注册表插件
        AutoTableBindPlugin atbp = new AutoTableBindPlugin(druidPlugin, ParamNameStyles.lowerUnderlineModule("t"));
        // AutoTableBindPlugin atbp = new AutoTableBindPlugin(dataSource, SimpleNameStyles.LOWER_UNDERLINE);
        atbp.setDialect(new PostgreSqlDialect());
        atbp.setDevMode(super.getPropertyToBoolean("xyweixin.devMode", false));
        atbp.setShowSql(super.getPropertyToBoolean("xyweixin.showSql", false));
        // atbp.addExcludeClasses(Blog.class);
        me.add(atbp);

        // 配置类似mybatis在xml中管理sql的插件。
        // 插件会扫描classpath根目录下以sql结尾的xml文件，如user-sql.xml
        SqlInXmlPlugin sixp = new SqlInXmlPlugin();
        me.add(sixp);

        // 映射表到模型
        // arp.addMapping("blog", Blog.class); // 映射blog表到Blog模型

        // redis
        String redisIp = super.getProperty("redis.ip", "127.0.0.1");
        Integer redisPort = super.getPropertyToInt("redis.port", 6379);
        String redisPassword = super.getProperty("redis.password");

        Integer redisPoolMaxTotal = super.getPropertyToInt("redis.pool.maxTotal", 1024);
        Integer redisPoolMaxIdle = super.getPropertyToInt("redis.pool.maxIdle", 200);
        Long redisPoolMaxWaitMillis = super.getPropertyToLong("redis.pool.maxWaitMillis", 2000L);
        Boolean redisPoolTestOnBorrow = super.getPropertyToBoolean("redis.pool.testOnBorrow", true);

        RedisPlugin defaultRedis = null;

        if (StringUtils.isBlank(redisPassword)) {
            defaultRedis = new RedisPlugin("default", redisIp, redisPort);
        }
        else {
            redisPassword = AESCoder.decryptStr(redisPassword);
            defaultRedis = new RedisPlugin("default", redisIp, redisPort, redisPassword);
        }

        defaultRedis.getJedisPoolConfig().setMaxTotal(redisPoolMaxTotal);
        defaultRedis.getJedisPoolConfig().setMaxIdle(redisPoolMaxIdle);
        defaultRedis.getJedisPoolConfig().setMaxWaitMillis(redisPoolMaxWaitMillis);
        defaultRedis.getJedisPoolConfig().setTestOnBorrow(redisPoolTestOnBorrow);

        me.add(defaultRedis);
    }

    /** 配置全局拦截器 */
    @Override
    public void configInterceptor(Interceptors me) {
        // me.add(new PermissionInterceptor()); // 权限过滤器
    }

    /** 配置处理器 */
    @Override
    public void configHandler(Handlers me) {
        DruidStatViewHandler dvh = new DruidStatViewHandler("/druid");
        me.add(dvh);

        me.add(new ContextPathHandler("ctx_path"));
    }

    @Override
    public void afterJFinalStart() {
        super.afterJFinalStart();
    }

    /** 运行此 main方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此 */
    public static void main(String[] args) {
        JFinal.start("src/main/webapp", 80, "/", 5);
    }

}
