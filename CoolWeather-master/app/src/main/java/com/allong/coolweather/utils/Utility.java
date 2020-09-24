package com.allong.coolweather.utils;

import android.text.TextUtils;

import com.allong.coolweather.bean.WeatherBean;
import com.allong.coolweather.db.City;
import com.allong.coolweather.db.County;
import com.allong.coolweather.db.Province;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author yj on  2018/3/1 14:22
 *         邮箱 yj@allong.net
 * @version 1.0.0
 */

public class Utility {
    /**
     * 处理省份json数据
     *
     * @param response <p>[{"id":1,"name":"北京"},{"id":2,"name":"上海"},{"id":3,"name":"天津"},{"id":4,"name":"重庆"},{"id":5,"name":"香港"},{"id":6,"name":"澳门"},{"id":7,"name":"台湾"},{"id":8,"name":"黑龙江"},{"id":9,"name":"吉林"},{"id":10,"name":"辽宁"},{"id":11,"name":"内蒙古"},{"id":12,"name":"河北"},{"id":13,"name":"河南"},{"id":14,"name":"山西"},{"id":15,"name":"山东"},{"id":16,"name":"江苏"},{"id":17,"name":"浙江"},{"id":18,"name":"福建"},{"id":19,"name":"江西"},{"id":20,"name":"安徽"},{"id":21,"name":"湖北"},{"id":22,"name":"湖南"},{"id":23,"name":"广东"},{"id":24,"name":"广西"},{"id":25,"name":"海南"},{"id":26,"name":"贵州"},{"id":27,"name":"云南"},{"id":28,"name":"四川"},{"id":29,"name":"西藏"},{"id":30,"name":"陕西"},{"id":31,"name":"宁夏"},{"id":32,"name":"甘肃"},{"id":33,"name":"青海"},{"id":34,"name":"新疆"}]</p>
     */
    public static boolean handleProvinceResponse(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int x = 0; x < jsonArray.length(); x++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(x);

                    Province province = new Province();
                    province.setProvinceName(jsonObject.getString("name"));
                    province.setProvinceCode(jsonObject.getInt("id"));

                    province.save();//保存数据到数据库
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }
        return false;
    }

    /**
     * 处理城市json数据
     *
     * @param response   json
     * @param provinceId 城市id
     * @return true false
     */
    public static boolean handleCityResponse(String response, int provinceId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray jsonArray = new JSONArray(response);

                if (jsonArray.length() > 0) {
                    for (int x = 0; x < jsonArray.length(); x++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(x);

                        City city = new City();
                        city.setCityName(jsonObject.getString("name"));
                        city.setCityCode(jsonObject.getInt("id"));
                        city.setProvinceId(provinceId);

                        city.save();//保存城市数据
                    }
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }
        return false;
    }

    public static boolean handleCountyResponse(String response, int cityId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray jsonArray = new JSONArray(response);
                if (jsonArray.length() > 0) {
                    for (int x = 0; x < jsonArray.length(); x++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(x);
                        County county = new County();
                        county.setCountyName(jsonObject.getString("name"));
                        county.setWeatherId(jsonObject.getString("weather_id"));
                        county.setCityId(cityId);

                        county.save();
                    }
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }
        return false;
    }

    public static WeatherBean handleWeatherResponse(String response) {
        Gson gson = new Gson();
        return gson.fromJson(response, WeatherBean.class);
    }
}
