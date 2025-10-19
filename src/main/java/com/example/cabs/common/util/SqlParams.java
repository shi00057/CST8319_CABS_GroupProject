package com.example.cabs.common.util;

import java.util.HashMap;
import java.util.Map;

public class SqlParams extends HashMap<String, Object> {
    public static SqlParams of(String k, Object v) {
        SqlParams m = new SqlParams();
        m.put(k, v);
        return m;
    }
    public SqlParams add(String k, Object v) {
        this.put(k, v);
        return this;
    }
    public static SqlParams from(Map<String,Object> src) {
        SqlParams m = new SqlParams();
        if (src != null) m.putAll(src);
        return m;
    }
}
