package com.dream.city.shiro;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Import(ShiroManager.class)
public class ShiroConfig {

	@Value("${spring.redis.host:127.0.0.1}")
	private String host;
	@Value("${spring.redis.port:6379}")
	private int port;
	@Value("${spring.redis.password:123456}")
	private String password;
	@Value("${spring.redis.database:0}")
	private int database;
	@Value("${spring.redis.jedis.pool.max-active:300}")
	private int maxActive;
	@Value("${spring.redis.jedis.pool.max-wait:-1}")
	private int maxWait;
	@Value("${spring.redis.jedis.pool.max-idle:300}")
	private int maxIdle;
	@Value("${spring.redis.jedis.pool.min-idle:1}")
	private int minIdle;
	@Value("${spring.redis.jedis.timeout:0}")
	private int timeout;

	@Value("${dreamcity.register.password.salt:0123456789}")
	private String salt;



	/*@Bean
	@ConditionalOnMissingBean(name = "poolConfig")
	public JedisPoolConfig poolConfig() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setBlockWhenExhausted(true);
		jedisPoolConfig.setTestOnBorrow(true);
		jedisPoolConfig.setMaxTotal(maxActive);
		jedisPoolConfig.setMaxIdle(maxIdle);
		jedisPoolConfig.setMinIdle(minIdle);
		jedisPoolConfig.setMaxWaitMillis(maxWait);// 100000

		jedisPoolConfig.setTestOnBorrow(true);
		jedisPoolConfig.setTestOnReturn(true);
		//Idle时进行连接扫描
		jedisPoolConfig.setTestWhileIdle(true);
		//表示idle object evitor两次扫描之间要sleep的毫秒数
		jedisPoolConfig.setTimeBetweenEvictionRunsMillis(30000);
		//表示idle object evitor每次扫描的最多的对象数
		jedisPoolConfig.setNumTestsPerEvictionRun(10);
		//表示一个对象至少停留在idle状态的最短时间，然后才能被idle object evitor扫描并驱逐；这一项只有在timeBetweenEvictionRunsMillis大于0时才有意义
		jedisPoolConfig.setMinEvictableIdleTimeMillis(60000);
		return jedisPoolConfig;
	}
	@Bean
	@ConditionalOnMissingBean(name = "jedisPool")
	public JedisPool jedisPool(JedisPoolConfig poolConfig){
		return new JedisPool(poolConfig, host, port);
	}*/
	/*@Bean
	@ConditionalOnMissingBean(name = "jedis")
	public Jedis jedis(){
		return new Jedis(host,port);
	}*/

	/*@Bean(name = "realm")
	@DependsOn("lifecycleBeanPostProcessor")
	@ConditionalOnMissingBean
	public Realm realm() {
		return new MyRealm();
	}
    @Bean(name = "shiroCacheManager")
    @ConditionalOnMissingBean
    public CacheManager cacheManager() {
        return new MemoryConstrainedCacheManager();
    }
    @Bean(name = "securityManager")
    @ConditionalOnMissingBean
    public DefaultSecurityManager securityManager() {
        DefaultSecurityManager sm = new DefaultWebSecurityManager();
        sm.setCacheManager(cacheManager());
        return sm;
    }
	@Bean(name = "shiroFilter")
	@DependsOn("securityManager")
	@ConditionalOnMissingBean
	public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultSecurityManager securityManager, @Qualifier("realm") Realm realm) {
		securityManager.setRealm(realm);

		ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
		shiroFilter.setSecurityManager(securityManager);
		shiroFilter.setLoginUrl("/admin/login");
		shiroFilter.setSuccessUrl("/admin/index");
		shiroFilter.setUnauthorizedUrl("/previlige/no");
		Map<String, String> filterChainDefinitionMap = new HashMap<String, String>();

		filterChainDefinitionMap.put("/admin/login", "anon");

		filterChainDefinitionMap.put("/user/index", "perms[system:user:index]");
		filterChainDefinitionMap.put("/user/add", "perms[system:user:add]");
		filterChainDefinitionMap.put("/user/edit", "perms[system:user:edit]");
		filterChainDefinitionMap.put("/user/delete", "perms[system:user:delete]");
		filterChainDefinitionMap.put("/user/grant/**", "perms[system:user:grant]");

		filterChainDefinitionMap.put("/role/index", "perms[system:role:index]");
		filterChainDefinitionMap.put("/role/add", "perms[system:role:add]");
		filterChainDefinitionMap.put("/role/edit*", "perms[system:role:edit]");
		filterChainDefinitionMap.put("/role/delete", "perms[system:role:deleteBatch]");
		filterChainDefinitionMap.put("/role/grant/**", "perms[system:role:grant]");

		filterChainDefinitionMap.put("/menu/index", "perms[system:menu:index]");
		filterChainDefinitionMap.put("/menu/add", "perms[system:menu:add]");
		filterChainDefinitionMap.put("/menu/edit*", "perms[system:menu:edit]");
		filterChainDefinitionMap.put("/menu/delete", "perms[system:menu:delete]");

		filterChainDefinitionMap.put("/admin/**", "authc");
		filterChainDefinitionMap.put("/user/**", "authc");
		filterChainDefinitionMap.put("/role/**", "authc");
		filterChainDefinitionMap.put("/menu/**", "authc");
		shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilter;
	}*/

	/**
	 * 生命周期处理器
	 * @return
	 */
	 /*@Bean
	 public HashedCredentialsMatcher hashedCredentialsMatcher() {
		 HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
		 //加密方式
		 credentialsMatcher.setHashAlgorithmName("MD5");
		 //散列次数
		 credentialsMatcher.setHashIterations(2);
		 return credentialsMatcher;
	 }*/
	@Bean(name = "realm")
	@DependsOn("lifecycleBeanPostProcessor")
	 public Realm realm() {
		 MyRealm customRealm = new MyRealm();
		 //customRealm.setCredentialsMatcher(hashedCredentialsMatcher());
		 customRealm.setAuthenticationCachingEnabled(true);
		 customRealm.setAuthorizationCachingEnabled(true);
		 return customRealm;
	 }
	private RedisManager redisManager() {
		 RedisManager redisManager = new RedisManager();
		 redisManager.setHost(host);
		 redisManager.setPort(port);
		 redisManager.setTimeout(timeout);
		 if (StringUtils.isNotBlank(password)) {
			 redisManager.setPassword(password);
		 }
		 return redisManager;
	 }
	 @Bean(name = "shiroCacheManager")
	 @ConditionalOnMissingBean
	 public CacheManager shiroCacheManager(){
		 RedisCacheManager redisCacheManager = new RedisCacheManager();
		 redisCacheManager.setRedisManager(redisManager());
		 return redisCacheManager;
	 }
	@Bean(name = "defaultSecurityManager")
	public DefaultSecurityManager defaultSecurityManager() {
		DefaultWebSecurityManager defaultSecurityManager = new DefaultWebSecurityManager();
		defaultSecurityManager.setRealm(realm());
		defaultSecurityManager.setCacheManager(shiroCacheManager());
		defaultSecurityManager.setSessionManager(sessionManager());
		defaultSecurityManager.setRememberMeManager(rememberMeManager());
		return defaultSecurityManager;
	}
	@Bean(name = "securityManager")
	public SecurityManager securityManager() {
		return defaultSecurityManager() ;
	}
	private SimpleCookie rememberMeCookie() {
		// 设置 cookie 名称，对应 login.html 页面的 <input type="checkbox" name="rememberMe"/>
		SimpleCookie cookie = new SimpleCookie("rememberMe");
		// 设置 cookie 的过期时间，单位为秒 一个月
		cookie.setMaxAge(3600 * 30);
		return cookie;
	}
	private CookieRememberMeManager rememberMeManager() {
		CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
		cookieRememberMeManager.setCookie(rememberMeCookie());
		// rememberMe cookie 加密的密钥
		//cookieRememberMeManager.setCipherKey(Base64.decode(salt));
		return cookieRememberMeManager;
	}

	/**
	 * RedisSessionDao 插件
	 * @return
	 */
	@Bean
	public RedisSessionDAO redisSessionDAO() {
		RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
		redisSessionDAO.setRedisManager(redisManager());
		return redisSessionDAO;
	}
	@Bean
	public DefaultWebSessionManager sessionManager() {
		DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
		defaultWebSessionManager.setSessionDAO(redisSessionDAO());
		//单位毫秒
		defaultWebSessionManager.setGlobalSessionTimeout(3600 * 1000);
		//defaultWebSessionManager.setSessionIdUrlRewritingEnabled(false);
		//defaultWebSessionManager.setSessionIdCookieEnabled(false);
		return defaultWebSessionManager;
	}

	/**
	 * 开启 shiro aop 注解模式  注意需要注解依赖
	 * @param securityManager
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}




	@Bean(name = "shiroFilter")
	@DependsOn("securityManager")
	@ConditionalOnMissingBean
	public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
		shiroFilter.setSecurityManager(securityManager);
		//shiroFilter.setUnauthorizedUrl("*/*");
		shiroFilter.setLoginUrl("/admin/login");
		shiroFilter.setSuccessUrl("/admin/index");
		shiroFilter.setUnauthorizedUrl("/previlige/no");
		Map<String, String> filterChainDefinitionMap = new HashMap<String, String>();

		filterChainDefinitionMap.put("/admin/login", "anon");
		filterChainDefinitionMap.put("/user/index", "perms[system:user:index]");
		filterChainDefinitionMap.put("/user/add", "perms[system:user:add]");
		filterChainDefinitionMap.put("/user/edit", "perms[system:user:edit]");
		filterChainDefinitionMap.put("/user/delete", "perms[system:user:delete]");
		filterChainDefinitionMap.put("/user/grant/**", "perms[system:user:grant]");

		filterChainDefinitionMap.put("/role/index", "perms[system:role:index]");
		filterChainDefinitionMap.put("/role/add", "perms[system:role:add]");
		filterChainDefinitionMap.put("/role/edit*", "perms[system:role:edit]");
		filterChainDefinitionMap.put("/role/delete", "perms[system:role:deleteBatch]");
		filterChainDefinitionMap.put("/role/grant/**", "perms[system:role:grant]");

		filterChainDefinitionMap.put("/menu/index", "perms[system:menu:index]");
		filterChainDefinitionMap.put("/menu/add", "perms[system:menu:add]");
		filterChainDefinitionMap.put("/menu/edit*", "perms[system:menu:edit]");
		filterChainDefinitionMap.put("/menu/delete", "perms[system:menu:delete]");

		filterChainDefinitionMap.put("/admin/**", "authc");
		filterChainDefinitionMap.put("/user/**", "authc");
		filterChainDefinitionMap.put("/role/**", "authc");
		filterChainDefinitionMap.put("/menu/**", "authc");
		shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilter;
	}


}
