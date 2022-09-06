package kr.or.ddit.object.reflect;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import kr.or.ddit.reflect.ReflectionTest;

/**
 * 리플렉션 ?
 *  : 객체로부터 타입, property, method 등 해당 객체의 구조를 추측해 가는 과정.
 *		java.lang.reflcet패키지의 API 툴로 지원.
 *		Class, Field, Method, Parameter . . .
 */
public class ReflectionDesc {
	public static void main(String[] args) {
		Object retValue = ReflectionTest.getObject();
		System.out.println(retValue);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mem_hp", "010-7188-8518");
		
		Class<? extends Object> clz = retValue.getClass();
		Field[] fields = clz.getDeclaredFields();
		for (Field fld : fields) {
			String fldName = fld.getName();
			fld.setAccessible(true);
			try {
				fld.set(retValue, map.get(fldName));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		retValue = ReflectionTest.getObject();
		
		for(Entry<String, Object> entry : map.entrySet()) {
			System.out.println(entry.getKey());
			String key = entry.getKey();
			Object value = entry.getValue();
			
			try {
//				Field fld = clz.getDeclaredField(key);
//				String setterName = "set" + key.substring(0,1).toUpperCase() + key.substring(1);
//				Method setter = clz.getDeclaredMethod(setterName, value.getClass());
//				setter.invoke(retValue,value);
				
				PropertyDescriptor pd = new PropertyDescriptor("mem_hp", clz);
				Method setter = pd.getWriteMethod();
				Method getter = pd.getReadMethod();
				Object fldValue = getter.invoke(retValue);
				System.out.println(fldValue);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println(retValue);
	}
}
