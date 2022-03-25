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
        map.put(4, R.drawable.sub4);
        map.put(5, R.drawable.sub5);
        map.put(6, R.drawable.sub6);
        map.put(7, R.drawable.sub7);
        map.put(8, R.drawable.sub8);
        map.put(9, R.drawable.sub9);
        map.put(10, R.drawable.sub10);
        SUBJECT_MAP = map;
    }
    public static String TOKEN;
    public static Map<Integer,Integer> SUBJECT_MAP;
}
