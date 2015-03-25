package core;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;

public class LangFile {
	
	public static String sub(String text,Map<String,String> map){
		for(Entry<String, String> entry:map.entrySet()){
			String subKey=entry.getKey();
			String sub=entry.getValue();
			if(sub!=null){
				String replaced=text.replace("%"+subKey, sub);
				if(replaced!=null){
					text=replaced;
				}
			}
		}
		return text;		
	}
	public static String sub(String text,String name,String value){
		return text.replace("%"+name, value);
	}
	public static String get(Class c,String key){
		try {
			Method method = c.getMethod("getInstance");
			Field f = c.getField(key);
			return (String) f.get(method.invoke(null));
		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}
}
