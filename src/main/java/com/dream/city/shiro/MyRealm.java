package com.dream.city.shiro;

import com.dream.city.emp.entity.Emp;
import com.dream.city.emp.service.EmpService;
import com.dream.city.setting.entity.Menu;
import com.dream.city.setting.entity.Role;
import com.dream.city.setting.service.MenuService;
import com.dream.city.setting.service.RoleService;
import com.dream.city.util.EncryptUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Wvv
 */
@Component
public class MyRealm extends AuthorizingRealm {

	public MyRealm(){
		super(new AllowAllCredentialsMatcher());
        setAuthenticationTokenClass(UsernamePasswordToken.class);

        //FIXME: 暂时禁用Cache
        setCachingEnabled(false);
	}

	@Autowired
	private EmpService empService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private MenuService menuService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		Emp emp = (Emp) principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		Emp dbEmp = empService.findUserByName(emp.getLoginName());
		List<Role> roleList = roleService.getListByUserId(emp.getId());
		Set<String> shiroPermissions = new HashSet<>();
		Set<String> roleSet = new HashSet<String>();
		for (Role role : roleList) {
			List<Menu> menus = menuService.getListByRoleId(role.getId());
			for (Menu menu : menus) {
				shiroPermissions.add(menu.getPermission());

			}
			roleSet.add(role.getEnname());
		}
		authorizationInfo.setRoles(roleSet);
		authorizationInfo.setStringPermissions(shiroPermissions);
		return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		String loginName = (String) token.getPrincipal();

		Emp emp = empService.findUserByName(loginName);

		String password = new String((char[]) token.getCredentials());

		// 账号不存在
		if (emp == null) {
			throw new UnknownAccountException("账号或密码不正确");
		}
		// 密码错误
		if (!EncryptUtils.encryptMD5(password).equals(emp.getPassword())) {
			throw new IncorrectCredentialsException("账号或密码不正确");
		}
		// 账号锁定
		if (emp.getState() == 0) {
			throw new LockedAccountException("账号已被锁定,请联系管理员");
		}

		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(emp, password, getName());

		return info;
	}

}
