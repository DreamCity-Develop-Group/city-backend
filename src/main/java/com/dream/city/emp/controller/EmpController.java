package com.dream.city.emp.controller;

import com.dream.city.base.Codes;
import com.dream.city.base.ResponseResult;
import com.dream.city.emp.dto.EmpDto;
import com.dream.city.emp.service.EmpService;
import com.dream.city.setting.entity.Role;
import com.dream.city.emp.entity.Emp;
import com.dream.city.setting.service.MenuService;
import com.dream.city.setting.dto.UserRoleDto;
import com.dream.city.setting.service.RoleService;
import com.dream.city.setting.service.UserRoleService;
import com.dream.city.util.DataUtils;
import com.dream.city.util.EncryptUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Wvv
 * @date 2017/8/28
 */
@Controller
@RequestMapping("/user")
@CrossOrigin
public class EmpController {

    private static final Logger logger = LoggerFactory.getLogger(EmpController.class);

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private EmpService empService;

    @Autowired
    private UserRoleService userRoleService;

	/**
	 * 跳转添加用户页面
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap map) {
		return "/user/add";
	}

    /**
     * 生成用户
     * @param empDto
     */
    @ResponseBody
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ResponseResult insertOrUpdate(EmpDto empDto){

		if(StringUtils.isNotBlank(empDto.getPassword())){
			empDto.setPassword(EncryptUtils.encryptMD5(empDto.getPassword()));
		}else {
			return ResponseResult.error(Codes.PARAM_ERROR);
		}
		this.empService.insert(empDto);
		Integer userId = empDto.getId();
        return ResponseResult.ok(userId);
    }

    /**
     * 获取用户详情
     */
    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    public ResponseResult getUser(@RequestParam Integer id){
        Emp emp = this.empService.getUserDetail(id);
        List<Role> roleList = this.roleService.getList(new HashMap());
        List<UserRoleDto> userRoleList = DataUtils.getDataArray(roleList, UserRoleDto.class);
        EmpDto empDto = DataUtils.getData(emp, EmpDto.class);
        empDto.setRoleList(userRoleList);

        //获取角色信息
        List<Integer> userIdList = new ArrayList<>();
        userIdList.add(id);
        List<UserRoleDto> userRoleDtoList = this.userRoleService.getUserRoleList(userIdList);

        //用户对应角色列表是否被选中
        for (UserRoleDto userRoleDto : empDto.getRoleList()){
            for (UserRoleDto selectRole : userRoleDtoList){
                if (userRoleDto.getId().equals(selectRole.getRoleId())){
                    userRoleDto.setIsSelect(1);
                }
            }
        }

        return ResponseResult.ok(empDto);
    }

	/**
	 * 跳转到修改用户页面
	 * @param id
	 */
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String toEdit(@PathVariable Integer id, ModelMap map){
		Emp emp = empService.getUserDetail(id);
		map.addAttribute("userDto", emp);
		return "/user/edit";
	}

    /**
     * 更新用户信息
     * @param empDto
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
    public ResponseResult update(EmpDto empDto){

        if(empDto.getId() == null){
            return ResponseResult.error(Codes.PARAM_ERROR);
        }
        int count = empService.updateUser(empDto);
        return ResponseResult.ok(count);
    }

    /**
     * 用户管理初始化页面
     * @return
     */
    @RequestMapping(value = "/index" )
    public String index() {
        return "/user/index";
    }

    /**
     * 获取用户列表
     * @param empDtoParam
     */
	@ResponseBody
    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    public ResponseResult getList(EmpDto empDtoParam,
                                  @RequestParam(defaultValue = "1") Integer pageNumber,
                                  @RequestParam(defaultValue = "10") Integer pageSize){
        Map param = new HashMap();
        param.put("pageSize", pageSize);
        param.put("startRow", (pageNumber - 1) * pageSize);
        if (StringUtils.isNotBlank(empDtoParam.getLoginName())){
            param.put("loginName", empDtoParam.getLoginName());
        }
        List<Map> userMapList = this.empService.getList(param);
        //没有用户数据直接返回
        if(userMapList == null || userMapList.size() == 0){
            Map<String, Object> responseResult = new HashMap<>();
            responseResult.put("userList", new ArrayList<>());
            responseResult.put("count", 0);
            return ResponseResult.ok(responseResult);
        }

        List<EmpDto> userList = DataUtils.getDataArray(userMapList, EmpDto.class);

        List<Integer> userIdList = new ArrayList<>();
        for(EmpDto empDto : userList){
            userIdList.add(empDto.getId());
        }
        //获取用户角色信息
        List<UserRoleDto> userRoleVOList = this.userRoleService.getUserRoleList(userIdList);
        for(EmpDto empDto : userList){
            //添加角色list
            for(UserRoleDto userRoleDto : userRoleVOList){
                if(empDto.getId().equals(userRoleDto.getUserId())){
                    if(empDto.getRoleList() == null){
                        empDto.setRoleList(new ArrayList<>());
                        empDto.getRoleList().add(userRoleDto);
                    }else{
                        empDto.getRoleList().add(userRoleDto);
                    }
                }
            }
        }

        Integer count = this.empService.getCount(param);
        Map<String, Object> responseResult = new HashMap<>();
        responseResult.put("userList",userList);
        responseResult.put("count", count);
        return ResponseResult.ok(responseResult);
    }

    /**
     * 删除用户
     * @param userId
     */
    @ResponseBody
    @RequestMapping(value = "/delete/{userId}", method = RequestMethod.POST)
    public ResponseResult delete(@PathVariable Integer userId){

        if(userId == null){
            return ResponseResult.error(Codes.PARAM_ERROR);
        }
        Emp emp = new Emp();
        emp.setId(userId);
        emp.setDelFlag(1);
        empService.deleteUser(emp);
        return ResponseResult.ok(emp.getId());
    }

    @RequestMapping(value = "/grant/{id}", method = RequestMethod.GET)
    public String grant(@PathVariable Integer id, ModelMap map) {
        Emp emp = empService.getUserDetail(id);
        map.put("user", emp);

        List<Role> roleList = roleService.getListByUserId(id);
        List<Integer> roleIds = new ArrayList<Integer>();
        for (Role role : roleList) {
            roleIds.add(role.getId());
        }
        map.put("roleIds", roleIds);

        List<Role> roles = roleService.getList(new HashMap());
        map.put("roles", roles);
        return "/user/grant";
    }

//    @RequestMapping(value = "/get", method = RequestMethod.GET)
//    public ResponseResult get(User user){
//
//        return ResponseResult.ok(userService.findUserByName("test2"));
//    }
}
