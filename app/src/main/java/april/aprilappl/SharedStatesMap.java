package april.aprilappl;

import java.util.LinkedHashMap;

public class SharedStatesMap {

    private static SharedStatesMap instance;
    private LinkedHashMap<String, Object> map;

    private SharedStatesMap() {
        map = new LinkedHashMap<String, Object>();
    }

    public static SharedStatesMap getInstance() {
        if (instance == null) {
            instance = new SharedStatesMap();
        }
        return instance;
    }


    public void setKey(String key, Object value) {
        map.put(key, value);
    }

    public String getKey(String key) {
        String name = "";
        if (map.get(key) != null) {
            if (!map.isEmpty()) {
                name = map.get(key).toString();
            }
        }
        return name;
    }
}