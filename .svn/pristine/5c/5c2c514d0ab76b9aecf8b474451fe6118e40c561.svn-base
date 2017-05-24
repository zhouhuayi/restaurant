package priv.zhouhuayi.restaurant.dao.common.test;

import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import priv.zhouhuayi.restaurant.dao.common.CommonDao;
import priv.zhouhuayi.restaurant.entity.menu.Menu;
import priv.zhouhuayi.restaurant.util.convert.ConvertUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:Mybatis.xml"})
public class CommonDaoTest {
	
	@Autowired
	private CommonDao<Menu> commonDao;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
	}

	@Test
	public void test() {
		System.out.println(commonDao);
		List<Map<String, Object>> listMap = commonDao.getListByBean(Menu.class);
		Menu menu = new Menu();
		menu.setMenuImg("aaaaaaa");
		menu.setMenuIntro("vvvvvv");
		menu.setMenuName("混沌");
		menu.setMenuPrice(20.5);
		menu.setMenuSource("河南");
		
		Map<String, Object> addData = ConvertUtil.convertMapByDb(menu);
		System.out.println(commonDao.addClass(Menu.class, addData));
		System.out.println(listMap);
	}

}
