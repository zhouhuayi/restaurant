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
		System.out.println("����Ϊ��"+tableName);
		
		Map<String, Object> tableMap = new HashMap<String, Object>();
		
		// ��ȡʵ���ֶμ���  
        Field[] fields = cls.getDeclaredFields();
        for (Field f : fields) {
        	if(f.getName().equals("serialVersionUID")) {
        		continue;
        	}
            // ͨ�������ȡ�����Զ�Ӧ��ֵ  
            f.setAccessible(true);
            try {
            	// ��ȡ�ֶ�ֵ  
				Object value = f.get(menu);
				FieldAnno fieldanno = f.getAnnotation(FieldAnno.class);
				String filedName = "";
				if(fieldanno == null) {
					filedName = f.getName();
				} else {
					filedName = fieldanno.fieldName();
				}
				System.out.println("���ݿ��Ӧ���ֶΣ�" + filedName);
	        	System.out.println("���ݿ��Ӧ���ֶε�ֵ��" + value);
				tableMap.put(filedName, value);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
        }
	}

}
