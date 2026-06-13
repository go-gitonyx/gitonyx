package eu.gitonyx.gitonyx.model;

import java.lang.reflect.Field;

import org.json.JSONObject;

public class ModelHandler {

    public static <T> JSONObject parseObject(T instance) {
        JSONObject jsonObject = new JSONObject();

        for (Field field : instance.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            try {
                jsonObject.put(field.getName(), field.get(instance));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        return jsonObject;
    }
}
