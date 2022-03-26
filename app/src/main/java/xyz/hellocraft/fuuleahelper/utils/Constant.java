package xyz.hellocraft.fuuleahelper.utils;

import java.util.HashMap;
import java.util.Map;

import xyz.hellocraft.fuuleahelper.R;

public class Constant {
    static {
        Map<Integer,Integer> map = new HashMap<>();
        map.put(1, R.drawable.sub1);
        map.put(2, R.drawable.sub2);
        map.put(3, R.drawable.sub3);
        map.put(5, R.drawable.sub5);
        map.put(6, R.drawable.sub6);
        map.put(7, R.drawable.sub7);
        map.put(8, R.drawable.sub8);
        map.put(9, R.drawable.sub9);
        map.put(10, R.drawable.sub10);
        map.put(21, R.drawable.sub21);
        map.put(22, R.drawable.sub21);
        map.put(24, R.drawable.sub21);
        map.put(27, R.drawable.sub21);
        map.put(29, R.drawable.sub21);
        SUBJECT_MAP = map;
    }
    public static Boolean HAS_LOGIN = false;
    public static String TOKEN;
    public static Map<String, String> AUTH_MAP;
    public static Map<Integer, Integer> SUBJECT_MAP;
}
