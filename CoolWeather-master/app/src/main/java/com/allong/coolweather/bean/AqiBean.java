package com.allong.coolweather.bean;

public  class AqiBean {
            /**
             * city : {"aqi":"50","qlty":"优","pm25":"22","pm10":"50","no2":"14","so2":"21","co":"0.5","o3":"89"}
             */

            private CityBean city;

            public CityBean getCity() {
                return city;
            }

            public void setCity(CityBean city) {
                this.city = city;
            }

            public static class CityBean {
                /**
                 * aqi : 50
                 * qlty : 优
                 * pm25 : 22
                 * pm10 : 50
                 * no2 : 14
                 * so2 : 21
                 * co : 0.5
                 * o3 : 89
                 */

                private String aqi;
                private String qlty;
                private String pm25;
                private String pm10;
                private String no2;
                private String so2;
                private String co;
                private String o3;

                public String getAqi() {
                    return aqi;
                }

                public void setAqi(String aqi) {
                    this.aqi = aqi;
                }

                public String getQlty() {
                    return qlty;
                }

                public void setQlty(String qlty) {
                    this.qlty = qlty;
                }

                public String getPm25() {
                    return pm25;
                }

                public void setPm25(String pm25) {
                    this.pm25 = pm25;
                }

                public String getPm10() {
                    return pm10;
                }

                public void setPm10(String pm10) {
                    this.pm10 = pm10;
                }

                public String getNo2() {
                    return no2;
                }

                public void setNo2(String no2) {
                    this.no2 = no2;
                }

                public String getSo2() {
                    return so2;
                }

                public void setSo2(String so2) {
                    this.so2 = so2;
                }

                public String getCo() {
                    return co;
                }

                public void setCo(String co) {
                    this.co = co;
                }

                public String getO3() {
                    return o3;
                }

                public void setO3(String o3) {
                    this.o3 = o3;
                }
            }
        }