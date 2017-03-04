package enrich.enrichacademy.utils;

import com.google.gson.Gson;

import java.util.HashMap;

/**
 * Created by Admin on 02-Mar-17.
 */

public class PostParams extends HashMap<String, String> {
    public static String ANDROID = "0";

    public static PostParams init() {
        return new PostParams();
    }

    public PostParams add(String param, String value) {
        put(param, value);
        return this;
    }

    public PostParams add(String param, String[] values) {
        put(param, new Gson().toJson(values));
        return this;
    }

    public PostParams add(String param, int[] values) {
        put(param, new Gson().toJson(values));
        return this;
    }

    public PostParams add(String param, int value) {
        put(param, value + "");
        return this;
    }

    public PostParams addPlatform() {
        put("Platform", ANDROID);
        return this;
    }

    public PostParams add(String param, double value) {
        put(param, new Gson().toJson(value));
        return this;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
