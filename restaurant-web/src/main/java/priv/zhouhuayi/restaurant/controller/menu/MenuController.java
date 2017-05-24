package priv.zhouhuayi.restaurant.controller.menu;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;

import priv.zhouhuayi.restaurant.api.menu.MenuService;
import priv.zhouhuayi.restaurant.controller.test.TestOssCBController;
import priv.zhouhuayi.restaurant.entity.menu.Menu;

@Controller
@RequestMapping("menu")
public class MenuController {
	
	Logger log = LoggerFactory.getLogger(TestOssCBController.class);
	
	@Resource
	private MenuService menuService;
	
	/**
	 * 添加菜单
	 * 
	 * @author zhy
	 * @param menu 菜单信息
	 * @return
	 */
	@RequestMapping("add")
	@ResponseBody
	public Map<String, Object> addMenu(Menu menu) {
		return menuService.addMenu(menu);
	}
	
	/**
	 * 获取菜单列表
	 * 
	 * @author zhy
	 * @return
	 */
	@RequestMapping("list")
	@ResponseBody
	public Map<String, Object> listMenu() {
		return menuService.menuList();
	}
	
	/**
	 * 修改菜单
	 * 
	 * @author zhy
	 * @param menu 菜单信息
	 * @return
	 */
	@RequestMapping("update")
	@ResponseBody
	public Map<String, Object> updateMenu(Menu menu) {
		return menuService.updateMenu(menu);
	}
}
