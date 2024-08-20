package esze.utils;

import com.google.gson.JsonObject;

import java.lang.reflect.Field;

public class SpellSenderUtils {


    public String objToJson(Object obj) {
        Class<?> objClass = obj.getClass();

        Field[] fields = objClass.getFields();
        JsonObject Jobj = new JsonObject();
        for (Field field : fields) {
            String name = field.getName();
            Object value = "ERROR-objToJsonMethod";
            try {
                value = field.get(obj);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Jobj.addProperty(name, value.toString());
        }
        return Jobj.toString();
    }
}
