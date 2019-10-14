package com.dream.city.controller;

import com.alibaba.fastjson.JSON;
import com.dream.city.base.model.entity.User;
import com.dream.city.base.model.vo.UserVo;
import com.dream.city.base.utils.DataUtils;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("/admin")
public class AdminController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserService userService;
	@Autowired
	private RedisUtils redisUtils;



	@RequestMapping(value = "/login" , method = RequestMethod.GET)
	public String login() {
		return "admin/login";
	}

	/**
	 * 用户登录验证
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestParam("username") String username,
						@RequestParam("password") String password, ModelMap model,HttpServletRequest request) {
		try {
			Subject subject = SecurityUtils.getSubject();
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			subject.login(token);
			Object object = subject.getPrincipal();
			UserVo userVo = new UserVo();
			if (object != null){
				userVo = DataUtils.toJavaObject(object,UserVo.class);
			}
			String sid = request.getRequestedSessionId();
			if (userVo != null && StringUtils.isNotBlank(sid) && !redisUtils.hasKey(RedisKeys.CURRENT_USER + sid)){
				User user = userService.findUserByName(userVo.getLoginName());
				userVo = DataUtils.toJavaObject(user,UserVo.class);
				request.getSession().setAttribute(RedisKeys.CURRENT_USER + sid, userVo);
				redisUtils.setStr(RedisKeys.CURRENT_USER + sid, JSON.toJSONString(userVo));
			}
			return redirect("/admin/index");
		} catch (AuthenticationException e) {
			model.put("message", e.getMessage());
			logger.error("用户登录验证异常",e);
		}
		return "admin/login";
	}



	@RequestMapping(value = "/logout" , method = RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		String sid = request.getRequestedSessionId();
		if (StringUtils.isNotBlank(sid) && redisUtils.hasKey(RedisKeys.CURRENT_USER + sid)){
			redisUtils.del(RedisKeys.CURRENT_USER + sid);
		}
		return redirect("admin/login");
	}

	@RequestMapping(value ={"","/index"}, method = RequestMethod.GET)
	public String index(HttpServletRequest request){
		String sid = request.getRequestedSessionId();
		Object userVoObject = request.getSession().getAttribute(RedisKeys.CURRENT_USER + sid);
		UserVo userVo = new UserVo();
		if (userVoObject == null){
			if (redisUtils.hasKey(RedisKeys.CURRENT_USER + sid)){
				String userVoStr = redisUtils.getStr(RedisKeys.CURRENT_USER + sid);
				userVo = DataUtils.toJavaObject(userVoStr,UserVo.class);
			}
		}else {
			userVo = DataUtils.toJavaObject(userVoObject,UserVo.class);
		}
		request.setAttribute("user",userVo);
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
