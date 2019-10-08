package com.dream.city.controller;

import com.dream.city.base.Codes;
import com.dream.city.base.model.entity.Menu;
import com.dream.city.base.model.vo.ZtreeView;
import com.dream.city.base.utils.DataUtils;
import com.dream.city.service.setting.MenuService;
import com.dream.city.base.ResponseResult;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 *
 * @author Wvv
 * @date 2017/11/05
 */
@Controller
@CrossOrigin
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

	/**
	 * 到新建菜单页
	 */
	@RequestMapping(value = "/add" )
	public String toAdd(ModelMap map) {
		map.addAttribute("list", this.getMenuList());
		return "/menu/add";
	}

    /**
     * 新建菜单
     * @param menu
     */
    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseResult createMenu(Menu menu){
    	if (menu.getParentId() != null && menu.getParentId() == 0){
    		menu.setParentId(null);
		}
        menuService.insert(menu);
        return ResponseResult.ok(menu.getId());
    }

	/**
	 * 到资源列表页
	 */
	@RequestMapping(value = "/index" )
	public String toIndex() {
		return "/menu/index";
	}

    /**
     * 菜单列表
     */
    @ResponseBody
    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    public ResponseResult getList(Menu menu,
								  @RequestParam(defaultValue = "1") Integer pageNumber,
								  @RequestParam(defaultValue = "10") Integer pageSize){
        PageInfo<Menu> menuPage = menuService.getPage(menu, pageNumber, pageSize);
        return ResponseResult.ok(menuPage);
    }

    /**
     * 菜单tree列表
     */
    @ResponseBody
    @RequestMapping(value = "/tree/{roleId}", method = RequestMethod.GET)
    public List<ZtreeView> getList(@PathVariable Integer roleId){
        List<ZtreeView> resultTreeNodes = new ArrayList<>();
        resultTreeNodes.add(new ZtreeView(0, null, "系统菜单", true));
        List<Menu> menuList = menuService.getListByRoleId(roleId);
        //用户已分配资源id
		Set<Integer> roleMenuIdSet = new HashSet<>();
		for (Menu roleMenu : menuList){
			roleMenuIdSet.add(roleMenu.getId());
		}
		//所有资源
		List<Menu> allMenu = menuService.getList(new Menu());
		for (Menu menu : allMenu){
			ZtreeView node = new ZtreeView();
			node.setId(menu.getId());
			if (menu.getParentId() == null) {
				node.setpId(0);
			} else {
				node.setpId(menu.getParentId());
			}
			node.setName(menu.getName());
			if (roleMenuIdSet != null && roleMenuIdSet.contains(menu.getId())) {
				node.setChecked(true);
			}
			resultTreeNodes.add(node);
		}
        return resultTreeNodes;
    }

    /**
     * 菜单详情
     */
    @RequestMapping(value = "/getMenu", method = RequestMethod.GET)
    public ResponseResult getMenu(@RequestParam Integer id, String traceID){
        Menu menu = menuService.getMenu(id);
        Map resultMap = DataUtils.getData(menu, Map.class);
        if(null != menu.getParentId()) {
            Menu parentMenu = menuService.getMenu(menu.getParentId());
            resultMap.put("parentName", parentMenu.getName());
        }
        return ResponseResult.ok(resultMap);
    }

	/**
	 * 到新建菜单页
	 */
	@RequestMapping(value = "/edit/{menuId}" )
	public String toEdit(@PathVariable Integer menuId, ModelMap map) {
		Menu menu = this.menuService.getMenu(menuId);
		map.addAttribute("list", this.getMenuList());
		map.addAttribute("menu", menu);
		return "/menu/edit";
	}

    /**
     * 菜单详情
     */
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseResult update(Menu menu){
    	if (menu.getParentId() != null && menu.getParentId() == 0){
    		menu.setParentId(null);
		}
        int count = menuService.update(menu);
        return ResponseResult.ok(count);
    }

    /**
     * 菜单删除
     */
    @ResponseBody
    @RequestMapping(value = "/delete/{menuId}", method = RequestMethod.POST)
    public ResponseResult delete(@PathVariable Integer menuId){
    	int count = this.menuService.getRoleMenuCountByMenuId(menuId);
    	if (count > 0){
			return ResponseResult.error(Codes.ROLE_IS_USED);
		}
    	Menu menu = new Menu();
        menu.setDelFlag("1");
        menuService.delete(menu);
        return ResponseResult.ok(count);
    }

	/**
	 * 构造上级资源列表
	 */
	private List<Menu> getMenuList(){
		List<Menu> list = menuService.getMenuList();
		List<Menu> resultList = new ArrayList<>();
		Menu head = new Menu();
		head.setName("系统菜单");
		head.setId(0);
		head.setType(3);
		resultList.add(head);
		resultList.addAll(list);
		return resultList;
	}
}