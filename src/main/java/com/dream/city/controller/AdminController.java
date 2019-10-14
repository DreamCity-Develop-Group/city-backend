package com.dream.city.controller;

import com.alibaba.fastjson.JSON;
import com.dream.city.base.model.entity.User;
import com.dream.city.base.utils.RedisKeys;
import com.dream.city.base.utils.RedisUtils;
import com.dream.city.service.user.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserService userService;
	@Autowired
	private RedisUtils redisUtils;



	@RequestMapping(value = "/login" , method = RequestMethod.GET)
	public String login(ModelMap model) {
		model.put("user",new User());
		return "admin/login";
	}

	/**
	 * 用户登录验证
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestParam("username") String username,
						@RequestParam("password") String password, ModelMap model) {
		try {
			Subject subject = SecurityUtils.getSubject();
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			subject.login(token);
			return redirect("/admin/index?user=" + username);
		} catch (AuthenticationException e) {
			model.put("message", e.getMessage());
			logger.error("用户登录验证异常",e);
		}
		return "admin/login";
	}



	@RequestMapping(value = "/logout" , method = RequestMethod.GET)
	public String logout(ModelMap model) {
		Subject subject = SecurityUtils.getSubject();
		Object object = subject.getPrincipal();
		String loginName = null;
		if (object != null){
			Object json = JSON.toJSON(object);
			Map map = JSON.parseObject(JSON.toJSONString(json));
			if (map != null && map.containsKey("loginName")){
				loginName = String.valueOf(map.get("loginName"));
			}
		}
		if (StringUtils.isNotBlank(loginName) && redisUtils.hasKey(RedisKeys.CURRENT_USER + loginName)){
			redisUtils.del(RedisKeys.CURRENT_USER + loginName);
		}
		subject.logout();
		model.put("user",new User());
		return redirect("admin/login");
	}

	@RequestMapping(value ={"","/index"}, method = RequestMethod.GET)
	public String index(String user,ModelMap model){
		User data = new User();
		if (StringUtils.isNotBlank(user)) {
			data = userService.findUserByName(user);
			if (data != null && StringUtils.isNotBlank(data.getLoginName()) && !redisUtils.hasKey(RedisKeys.CURRENT_USER + data.getLoginName())){
				redisUtils.setStr(RedisKeys.CURRENT_USER + data.getLoginName(), JSON.toJSONString(data));
			}
		}
		model.put("user",data);
		return "admin/index";
	}

	/**
	 * 带参重定向
	 *
	 * @param path
	 * @return
	 */
	private String redirect(String path) {
		return "redirect:" + path;
	}
}
