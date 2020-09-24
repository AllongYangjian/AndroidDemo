package com.allong.coolweather.bean;

import java.util.List;

/**
 * @author yj on  2018/3/1 16:14
 *         邮箱 yj@allong.net
 * @version 1.0.0
 */

public class WeatherBean {
    private List<HeWeatherBean> HeWeather;

    public List<HeWeatherBean> getHeWeather() {
        return HeWeather;
    }

    public void setHeWeather(List<HeWeatherBean> HeWeather) {
        this.HeWeather = HeWeather;
    }

    public static class HeWeatherBean {
        /**
         * aqi : {"city":{"aqi":"50","qlty":"优","pm25":"22","pm10":"50","no2":"14","so2":"21","co":"0.5","o3":"89"}}
         * basic : {"city":"大兴安岭","cnty":"中国","id":"CN101050701","lat":"52.3352623","lon":"124.71152496","update":{"loc":"2018-03-01 15:49","utc":"2018-03-01 07:49"}}
         * daily_forecast : [{"astro":{"mr":"16:16","ms":"06:13","sr":"06:26","ss":"17:19"},"cond":{"code_d":"407","code_n":"101","txt_d":"阵雪","txt_n":"多云"},"date":"2018-03-01","hum":"84","pcpn":"0.5","pop":"99","pres":"1013","tmp":{"max":"-9","min":"-29"},"uv":"1","vis":"14","wind":{"deg":"286","dir":"西北风","sc":"微风","spd":"4"}},{"astro":{"mr":"17:36","ms":"06:44","sr":"06:24","ss":"17:21"},"cond":{"code_d":"407","code_n":"101","txt_d":"阵雪","txt_n":"多云"},"date":"2018-03-02","hum":"77","pcpn":"0.0","pop":"0","pres":"1011","tmp":{"max":"-9","min":"-29"},"uv":"1","vis":"19","wind":{"deg":"175","dir":"南风","sc":"微风","spd":"6"}},{"astro":{"mr":"18:54","ms":"07:11","sr":"06:22","ss":"17:23"},"cond":{"code_d":"407","code_n":"101","txt_d":"阵雪","txt_n":"多云"},"date":"2018-03-03","hum":"76","pcpn":"0.1","pop":"76","pres":"1016","tmp":{"max":"-10","min":"-28"},"uv":"1","vis":"19","wind":{"deg":"36","dir":"东北风","sc":"3-4","spd":"16"}},{"astro":{"mr":"20:10","ms":"07:35","sr":"06:19","ss":"17:25"},"cond":{"code_d":"101","code_n":"101","txt_d":"多云","txt_n":"多云"},"date":"2018-03-04","hum":"78","pcpn":"0.1","pop":"51","pres":"1019","tmp":{"max":"-8","min":"-24"},"uv":"1","vis":"18","wind":{"deg":"336","dir":"西北风","sc":"3-4","spd":"16"}},{"astro":{"mr":"21:24","ms":"07:58","sr":"06:17","ss":"17:26"},"cond":{"code_d":"101","code_n":"101","txt_d":"多云","txt_n":"多云"},"date":"2018-03-05","hum":"69","pcpn":"0.0","pop":"0","pres":"1025","tmp":{"max":"-11","min":"-26"},"uv":"1","vis":"20","wind":{"deg":"299","dir":"西北风","sc":"3-4","spd":"12"}},{"astro":{"mr":"22:34","ms":"08:22","sr":"06:15","ss":"17:28"},"cond":{"code_d":"101","code_n":"101","txt_d":"多云","txt_n":"多云"},"date":"2018-03-06","hum":"67","pcpn":"0.0","pop":"0","pres":"1034","tmp":{"max":"-8","min":"-28"},"uv":"2","vis":"20","wind":{"deg":"293","dir":"西北风","sc":"4-5","spd":"20"}},{"astro":{"mr":"23:42","ms":"08:47","sr":"06:13","ss":"17:30"},"cond":{"code_d":"101","code_n":"101","txt_d":"多云","txt_n":"多云"},"date":"2018-03-07","hum":"73","pcpn":"0.0","pop":"0","pres":"1035","tmp":{"max":"-7","min":"-23"},"uv":"2","vis":"20","wind":{"deg":"336","dir":"西北风","sc":"3-4","spd":"16"}}]
         * hourly_forecast : [{"cond":{"code":"104","txt":"阴"},"date":"2018-03-01 16:00","hum":"77","pop":"59","pres":"1013","tmp":"-11","wind":{"deg":"12","dir":"东北风","sc":"微风","spd":"6"}},{"cond":{"code":"400","txt":"小雪"},"date":"2018-03-01 19:00","hum":"89","pop":"29","pres":"1013","tmp":"-15","wind":{"deg":"211","dir":"西南风","sc":"微风","spd":"5"}},{"cond":{"code":"104","txt":"阴"},"date":"2018-03-01 22:00","hum":"93","pop":"0","pres":"1014","tmp":"-19","wind":{"deg":"291","dir":"西北风","sc":"微风","spd":"5"}},{"cond":{"code":"103","txt":"晴间多云"},"date":"2018-03-02 01:00","hum":"93","pop":"0","pres":"1013","tmp":"-20","wind":{"deg":"273","dir":"西风","sc":"微风","spd":"5"}},{"cond":{"code":"103","txt":"晴间多云"},"date":"2018-03-02 04:00","hum":"93","pop":"0","pres":"1012","tmp":"-25","wind":{"deg":"265","dir":"西风","sc":"微风","spd":"5"}},{"cond":{"code":"500","txt":"薄雾"},"date":"2018-03-02 07:00","hum":"87","pop":"0","pres":"1012","tmp":"-27","wind":{"deg":"245","dir":"西南风","sc":"微风","spd":"3"}},{"cond":{"code":"104","txt":"阴"},"date":"2018-03-02 10:00","hum":"67","pop":"0","pres":"1010","tmp":"-23","wind":{"deg":"178","dir":"南风","sc":"微风","spd":"4"}},{"cond":{"code":"400","txt":"小雪"},"date":"2018-03-02 13:00","hum":"61","pop":"0","pres":"1008","tmp":"-13","wind":{"deg":"102","dir":"东南风","sc":"微风","spd":"6"}}]
         * now : {"cond":{"code":"104","txt":"阴"},"fl":"-16","hum":"56","pcpn":"0.0","pres":"1012","tmp":"-8","vis":"10","wind":{"deg":"340","dir":"西北风","sc":"微风","spd":"8"}}
         * status : ok
         * suggestion : {"air":{"brf":"良","txt":"气象条件有利于空气污染物稀释、扩散和清除，可在室外正常活动。"},"comf":{"brf":"很不舒适","txt":"天气凉，并会伴有降雪天气，在这种天气条件下，会使您感觉很冷，很不舒适，请注意保暖防寒。"},"cw":{"brf":"不宜","txt":"不宜洗车，未来24小时内有雪，如果在此期间洗车，雪水和路上的泥水可能会再次弄脏您的爱车。"},"drsg":{"brf":"寒冷","txt":"天气寒冷，建议着厚羽绒服、毛皮大衣加厚毛衣等隆冬服装。年老体弱者尤其要注意保暖防冻。"},"flu":{"brf":"极易发","txt":"昼夜温差极大，且空气湿度较大，寒冷潮湿，极易发生感冒，请特别注意增减衣服保暖防寒。"},"sport":{"brf":"较不宜","txt":"有降雪，推荐您在室内进行低强度运动；若坚持户外运动，请选择合适运动并注意保暖。"},"trav":{"brf":"一般","txt":"微风拂面，时有雪花飘落，但气温较低。若您坚持出游，可选择雪上项目，并注意防寒保暖。"},"uv":{"brf":"最弱","txt":"属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。"}}
         */

        private AqiBean aqi;
        private BasicBean basic;
        private NowBean now;
        private String status;
        private SuggestionBean suggestion;
        private List<DailyForecastBean> daily_forecast;
        private List<HourlyForecastBean> hourly_forecast;

        public AqiBean getAqi() {
            return aqi;
        }

        public void setAqi(AqiBean aqi) {
            this.aqi = aqi;
        }

        public BasicBean getBasic() {
            return basic;
        }

        public void setBasic(BasicBean basic) {
            this.basic = basic;
        }

        public NowBean getNow() {
            return now;
        }

        public void setNow(NowBean now) {
            this.now = now;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public SuggestionBean getSuggestion() {
            return suggestion;
        }

        public void setSuggestion(SuggestionBean suggestion) {
            this.suggestion = suggestion;
        }

        public List<DailyForecastBean> getDaily_forecast() {
            return daily_forecast;
        }

        public void setDaily_forecast(List<DailyForecastBean> daily_forecast) {
            this.daily_forecast = daily_forecast;
        }

        public List<HourlyForecastBean> getHourly_forecast() {
            return hourly_forecast;
        }

        public void setHourly_forecast(List<HourlyForecastBean> hourly_forecast) {
            this.hourly_forecast = hourly_forecast;
        }







    }
}
