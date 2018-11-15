package com.zst.ynh.bean;

import java.util.List;

public class Province {
    public String name;
    public List<City> city;

    public static class City {
        public String name;
        public List<String> area;
    }
}
