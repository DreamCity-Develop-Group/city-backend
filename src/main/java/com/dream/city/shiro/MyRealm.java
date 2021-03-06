package com.dream.city.shiro;

import com.dream.city.base.model.entity.Menu;
import com.dream.city.base.model.entity.Role;
import com.dream.city.base.model.entity.User;
import com.dream.city.base.utils.EncryptUtils;
import com.dream.city.service.user.UserService;
import com.dream.city.service.setting.MenuService;
import com.dream.city.service.setting.RoleService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.security.provider.MD5;

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
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private MenuService menuService;



	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		User user = (User) principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		User dbUser = userService.findUserByName(user.getLoginName());
		List<Role> roleList = roleService.getListByUserId(user.getId());
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
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String loginName = (String) token.getPrincipal();

		User user = userService.findUserByName(loginName);

		String password = new String((char[]) token.getCredentials());

		// 账号不存在
		if (user == null) {
			throw new UnknownAccountException("账号或密码不正确");
		}
		// 密码错误
        String pwd = new Md5Hash(password,loginName).toHex();
		String pwd2 = EncryptUtils.encryptMD5(password);
		if (!pwd2.equals(user.getPassword())) {
			throw new IncorrectCredentialsException("账号或密码不正确");
		}
		// 账号锁定
		if (user.getState() == 0) {
			throw new LockedAccountException("账号已被锁定,请联系管理员");
		}

		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, pwd2, getName());
		return info;
	}

	/**
	 * 清除当前用户的权限认证缓存
	 *
	 * @param principals 权限信息 身份集合
	 */
	@Override
	public void clearCache(PrincipalCollection principals) {
		super.clearCache(principals);
	}


}
