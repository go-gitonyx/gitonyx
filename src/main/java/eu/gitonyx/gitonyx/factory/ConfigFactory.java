package eu.gitonyx.gitonyx.factory;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;

import org.json.JSONArray;
import org.json.JSONObject;

import eu.gitonyx.gitonyx.config.annotation.Config;
import eu.gitonyx.gitonyx.config.annotation.DefaultValue;
import eu.gitonyx.gitonyx.config.annotation.Key;

public class ConfigFactory {

    public <T> T loadConfig(Class<T> clazz) throws Exception {
        if (!clazz.isAnnotationPresent(Config.class)) {
            throw new IllegalAccessException("Class does not have @Config annotation.");
        }

        Config config = clazz.getAnnotation(Config.class);

        File file = Path.of(config.path(), config.value() + ".json").toFile();
        T instance = clazz.getConstructor().newInstance();

        if (!file.exists()) {
            return instance;
        }

        JSONObject jsonObject = new JSONObject(Files.readString(file.toPath()));

        for (Field field : instance.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            if (!field.isAnnotationPresent(Key.class)) {
                continue;
            }

            Key key = field.getAnnotation(Key.class);
            Object value = jsonObject.get(key.value());

            field.set(instance, value);
        }

        return instance;
    }

    public <T> void saveConfig(T instance) throws Exception {
        Class<?> clazz = instance.getClass();
        if (!clazz.isAnnotationPresent(Config.class)) {
            throw new IllegalAccessException("Class does not have @Config annotation.");
        }

        Config config = clazz.getAnnotation(Config.class);

        File file = Path.of(config.path(), config.value() + ".json").toFile();

        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        if (!file.exists()) {
            file.createNewFile();
        }

        JSONObject jsonObject = new JSONObject();

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);

            if (!field.isAnnotationPresent(Key.class)) {
                continue;
            }

            Key key = field.getAnnotation(Key.class);
            if (field.get(instance) == null) {
                if (!field.isAnnotationPresent(DefaultValue.class)) {
                    continue;
                }

                DefaultValue defaultValue = field.getAnnotation(DefaultValue.class);
                jsonObject.put(key.value(), parseString(defaultValue.value()));
                continue;
            }

            jsonObject.put(key.value(), field.get(instance));
        }

        Files.write(file.toPath(), jsonObject.toString().getBytes());
    }

    public Object parseString(String value) {
        if (value == null || value.equalsIgnoreCase("null")) {
            return null;
        }

        if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
            return Boolean.parseBoolean(value);
        }

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ignored) {
        }
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException ignored) {
        }
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException ignored) {
        }

        if (value.startsWith("[") && value.endsWith("]")) {
            return new org.json.JSONArray(value);
        }

        if (value.startsWith("{") && value.endsWith("}")) {
            return new JSONObject(value);
        }

        return value;
    }

    public String parseObject(Object value) {
        if (value == null) {
            return "null";
        }

        if (value instanceof JSONArray) {
            return ((org.json.JSONArray) value).toString();
        }

        if (value instanceof JSONObject) {
            return ((JSONObject) value).toString();
        }

        return value.toString();
    }
}
