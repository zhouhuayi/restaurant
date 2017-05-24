
import java.util.HashMap;
import java.util.Map;

import priv.zhouhuayi.restaurant.util.thread.ThreadUtil;


public class ThreadTest {
	private static int num;
	
	public synchronized static void callBack(String message, HashMap<String, Object> message2) {
		System.out.println(message);
		System.out.println(message2);
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		num ++;
		System.out.println(num);
	}
	
	public static void main(String[] args) {
		String aa = "99";
		System.out.println(aa.contains("99"));
		
		Object[] str = new Object[2];
		Map<String, Object> message2 = new HashMap<String, Object>();
		message2.put("message", "回调测试2");
		
		str[0] = "回调同步方法测试";
		str[1] = message2;
		
		for (int i = 0; i < 20; i++) {
			ThreadUtil threadUtil = new ThreadUtil(ThreadTest.class, "callBack", str);
			Thread thread = new Thread(threadUtil);
			thread.start();
		}
		
	}
}
