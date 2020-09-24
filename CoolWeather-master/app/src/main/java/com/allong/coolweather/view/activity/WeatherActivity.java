package com.allong.coolweather.view.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.allong.coolweather.R;
import com.allong.coolweather.bean.DailyForecastBean;
import com.allong.coolweather.bean.WeatherBean;
import com.allong.coolweather.utils.HttpUtil;
import com.allong.coolweather.utils.Utility;
import com.bumptech.glide.Glide;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {

    private static final String TAG = WeatherActivity.class.getSimpleName();

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.nav_button)
    Button navButton;
    @BindView(R.id.weather_layout)
    ScrollView weatherLayout;
    @BindView(R.id.title_city)
    TextView titleCity;
    @BindView(R.id.title_update_time)
    TextView titleUpdateTime;
    @BindView(R.id.degree_text)
    TextView degreeText;
    @BindView(R.id.weather_info_text)
    TextView weatherInfoText;
    @BindView(R.id.forecast_layout)
    LinearLayout forecastLayout;
    @BindView(R.id.aqi_text)
    TextView aqiText;
    @BindView(R.id.pm25_text)
    TextView pm25Text;
    @BindView(R.id.comfort_text)
    TextView comfortText;
    @BindView(R.id.car_wash_text)
    TextView carWashText;
    @BindView(R.id.sport_text)
    TextView sportText;
    @BindView(R.id.bing_pic_img)
    ImageView imgBing;

    private String weatherId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        setStatusBarUpperAPI21();
        ButterKnife.bind(this);

        initView();

        weatherId = getIntent().getStringExtra("weather_id");

        updateShareoreferences(weatherId);

        initData();


    }

    private void setStatusBarUpperAPI21() {
        Window window = getWindow();
        //设置透明状态栏,这样才能让 ContentView 向上
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        ViewGroup mContentView = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 使其不为系统 View 预留空间.
            ViewCompat.setFitsSystemWindows(mChildView, false);
        }
    }

    private void initData() {
        loadBingPic();
        requestWeather(weatherId);
    }

    private void loadBingPic() {
        String requestBindPic = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestBindPic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPic = response.body().string();
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Glide.with(WeatherActivity.this)
                                .load(bingPic).into(imgBing);
                    }
                });
            }
        });
    }

    public void updateShareoreferences(String mWeatherId) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.edit().putString("weather_id", mWeatherId).apply();
        this.weatherId = mWeatherId;
    }

    public DrawerLayout getDrawerLayout() {
        return drawerLayout;
    }

    public SwipeRefreshLayout getSwipeRefresh() {
        return swipeRefresh;
    }

    private void initView() {
        weatherLayout.setVisibility(View.INVISIBLE);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestWeather(weatherId);
            }
        });

        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);//开启侧边栏
            }
        });
    }

    public void requestWeather(String mWeatherId) {
        updateShareoreferences(mWeatherId);
        String weatherUrl = "http://guolin.tech/api/weather?cityid=" + weatherId + "&key=bc0418b57b2d4918819d3974ac1285d9";
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_LONG).show();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                Log.e(TAG, responseText);
                final WeatherBean weatherBean = Utility.handleWeatherResponse(responseText);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        WeatherBean.HeWeatherBean heWeatherBean = weatherBean.getHeWeather().get(0);
                        if (heWeatherBean != null && heWeatherBean.getStatus().equals("ok")) {
                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this);
                            sharedPreferences.edit().putString("weather", responseText).apply();
                            showWeatherInfo(weatherBean);

                        } else {
                            Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_LONG).show();
                        }
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        });
    }

    private void showWeatherInfo(WeatherBean weatherBean) {
        WeatherBean.HeWeatherBean heWeatherBean = weatherBean.getHeWeather().get(0);
        String cityName = heWeatherBean.getBasic().getCity();
        String updateTime = heWeatherBean.getBasic().getUpdate().getLoc();
        String degree = heWeatherBean.getNow().getTmp() + "℃";
        String weatherInfo = heWeatherBean.getNow().getCond().getTxt();

        titleCity.setText(cityName);
        titleUpdateTime.setText(updateTime);
        degreeText.setText(degree);
        weatherInfoText.setText(weatherInfo);

        forecastLayout.removeAllViews();

        for (DailyForecastBean dailyForecastBean : heWeatherBean.getDaily_forecast()) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_forecast, forecastLayout, false);

            TextView dateText = (TextView) view.findViewById(R.id.date_text);
            TextView infoText = (TextView) view.findViewById(R.id.info_text);
            TextView maxText = (TextView) view.findViewById(R.id.max_text);
            TextView minText = (TextView) view.findViewById(R.id.min_text);

            dateText.setText(dailyForecastBean.getDate());
            infoText.setText(dailyForecastBean.getCond().getTxt_n());
            maxText.setText(dailyForecastBean.getTmp().getMax());
            minText.setText(dailyForecastBean.getTmp().getMin());

            forecastLayout.addView(view);
        }

        if (heWeatherBean.getAqi() != null) {
            aqiText.setText(heWeatherBean.getAqi().getCity().getAqi());
            pm25Text.setText(heWeatherBean.getAqi().getCity().getPm25());
        }
        String comfort = "舒适度：" + heWeatherBean.getSuggestion().getComf().getTxt();
        String carWash = "洗车指数：" + heWeatherBean.getSuggestion().getCw().getTxt();
        String sport = "运动建议：" + heWeatherBean.getSuggestion().getSport().getTxt();

        comfortText.setText(comfort);
        carWashText.setText(carWash);
        sportText.setText(sport);

        weatherLayout.setVisibility(View.VISIBLE);
    }
}
