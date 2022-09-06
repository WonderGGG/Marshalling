package kr.or.ddit.object;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;

public class BeanUtilsTest {

   public static void main(String[] args) {
      Map<String, Object> map = new HashMap<>();
      map.put("prop1", "VALUE1");
      map.put("prop2", "VALUE2");

      TestVO vo = new TestVO();

      Class<? extends TestVO> clz = vo.getClass();
      Field[] fields = clz.getDeclaredFields();
      for (Field fld : fields) {
         String fldName = fld.getName();
         System.out.println(fld);
         fld.setAccessible(true);
         try {
            fld.set(vo, map.get(fldName));
         } catch (IllegalArgumentException | IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      }
      System.out.println(vo);
      vo = new TestVO();

      for (Entry<String, Object> entry : map.entrySet()) {
         System.out.println(entry.getKey());
         String key = entry.getKey();
         Object value = entry.getValue();

         try {
            Field fld = clz.getDeclaredField(key);
            String setterName = "set" + key.substring(0, 1).toUpperCase() + key.substring(1);
            Method setter = clz.getDeclaredMethod(setterName, value.getClass());
            setter.invoke(vo, value);
         } catch (NoSuchFieldException | SecurityException | NoSuchMethodException | IllegalAccessException
               | IllegalArgumentException | InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      }
      System.out.println(vo);
      
      vo = new TestVO();
      
      try {
         BeanUtils.populate(vo, map);
      } catch (IllegalAccessException | InvocationTargetException e) {
         e.printStackTrace();
      }
      
      System.out.println(vo);
   }

   
}