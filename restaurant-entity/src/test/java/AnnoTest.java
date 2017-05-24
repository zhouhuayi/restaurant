import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import priv.zhouhuayi.restaurant.entity.menu.Menu;
import priv.zhouhuayi.restaurant.util.anno.TableAnno;
import priv.zhouhuayi.restaurant.util.anno.FieldAnno;

public class AnnoTest {

	public static void main(String[] args) {
		Menu menu = new Menu();
		Class<?> cls = menu.getClass();
		String tableName = cls.getAnnotation(TableAnno.class).tableName();
		System.out.println("表名为："+tableName);
		
		Map<String, Object> tableMap = new HashMap<String, Object>();
		
		// 获取实体字段集合  
        Field[] fields = cls.getDeclaredFields();
        for (Field f : fields) {
        	if(f.getName().equals("serialVersionUID")) {
        		continue;
        	}
            // 通过反射获取该属性对应的值  
            f.setAccessible(true);
            try {
            	// 获取字段值  
				Object value = f.get(menu);
				FieldAnno fieldanno = f.getAnnotation(FieldAnno.class);
				String filedName = "";
				if(fieldanno == null) {
					filedName = f.getName();
				} else {
					filedName = fieldanno.fieldName();
				}
				System.out.println("数据库对应的字段：" + filedName);
	        	System.out.println("数据库对应的字段的值：" + value);
				tableMap.put(filedName, value);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
        }
	}

}
