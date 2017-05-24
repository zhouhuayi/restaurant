import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import priv.zhouhuayi.restaurant.util.redis.RedisDealUtil;

public class RedisTest {
	private static String key = "testKey";
	
	private static String uniqueColumn = "id";
	
	
	public static void main(String[] args) {
		initListAesc();
		appendMapLeft();
		getListByPage(1, 3);
		appendListAesc();
		getListByPage(1, 3);
		
		initListDesc();
		appendMapRight();
		long end = getListLen();
		System.out.println(end);
		getListByRange(end - 3, end);
		appendListDesc();
		getListByRange(end - 3, end + 1);
		
		update();
		find();
		
		delList();
		find();
		end = getListLen();
		System.out.println(end);
		getListByRange(end - 3, end);
	}
	
	private static void initListAesc() {
		RedisDealUtil.delKey(key);
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		
		for (int i = 1; i < 3001; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(uniqueColumn, i);
			map.put("name", "用户"+i);
			listMap.add(map);
		}
		RedisDealUtil.initListAesc(key, listMap, uniqueColumn);
	}
	
	private static void appendListAesc() {
		List<Map<String, Object>> listData = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(uniqueColumn, "3002");
		map.put("name", "追加list");
		listData.add(map);
		RedisDealUtil.appendListAesc(key, listData, uniqueColumn);
	}
	
	private static void appendMapLeft() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(uniqueColumn, 3001);
		map.put("name", "用户3001");
		RedisDealUtil.appendMapLeft(key, map, uniqueColumn);
	}
	
	private static void initListDesc() {
		RedisDealUtil.delKey(key);
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		
		for (int i = 1; i < 3001; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(uniqueColumn, i);
			map.put("name", "用户"+i);
			listMap.add(map);
		}
		
		RedisDealUtil.initListDesc(key, listMap, uniqueColumn);
	}
	
	private static void appendListDesc() {
		List<Map<String, Object>> listData = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(uniqueColumn, "3002");
		map.put("name", "追加list");
		listData.add(map);
		RedisDealUtil.appendListDesc(key, listData, uniqueColumn);
	}
	
	private static void appendMapRight() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(uniqueColumn, 3001);
		map.put("name", "用户3001");
		RedisDealUtil.appendMapRight(key, map, uniqueColumn);
	}
	
	private static void update() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(uniqueColumn, 3001);
		map.put("name", "用户你好");
		RedisDealUtil.updateList(key, "3001", map);
	}
	
	private static void delList() {
		RedisDealUtil.deleteList(key, "3001");
	}
	
	private static void find() {
		System.out.println(RedisDealUtil.getListHash(key, "3001"));
	}
	
	private static void getListByPage(int pageNum, int pageSize) {
		System.out.println(RedisDealUtil.getListByPage(key, pageNum, pageSize));
	}
	
	private static void getListByRange(long start, long end) {
		System.out.println(RedisDealUtil.getListByRange(key, start, end));
	}
	
	private static long getListLen() {
		return RedisDealUtil.getListLen(key);
	}
	
}
