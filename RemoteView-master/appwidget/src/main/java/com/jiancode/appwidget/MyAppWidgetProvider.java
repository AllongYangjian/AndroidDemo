package com.jiancode.appwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;

public class MyAppWidgetProvider extends AppWidgetProvider {

    private static final String TAG = MyAppWidgetProvider.class.getSimpleName();

    public static final String CLICK_ACTION = "com.allong.yj";

    public MyAppWidgetProvider() {
        super();
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.e(TAG, "onReceive:"+intent.getAction());
        if (intent.getAction().equals(CLICK_ACTION)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Bitmap sccBitMap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_remind_hj);
                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                    for (int x = 0; x < 37; x++) {
                        float degree = (x * 10) % 360;
                        Log.e(TAG,"degree:"+degree);
                        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.view_widget);
                        views.setImageViewBitmap(R.id.img_label, rotateBitmap(context, sccBitMap, degree));
                        Intent intenti = new Intent();
                        intenti.setAction(CLICK_ACTION);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intenti, 0);
                        views.setOnClickPendingIntent(R.id.img_label,pendingIntent);
                        appWidgetManager.updateAppWidget(new ComponentName(context, MyAppWidgetProvider.class), views);
                        SystemClock.sleep(30);
                    }
                }
            }).start();
        }
    }

    private Bitmap rotateBitmap(Context context, Bitmap sccBitMap, float degree) {
        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.setRotate(degree);
        Bitmap tmpBitmap = Bitmap.createBitmap(sccBitMap, 0, 0, sccBitMap.getWidth(), sccBitMap.getHeight(), matrix, true);
        return tmpBitmap;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.e(TAG, "onUpdate");
        for (int id : appWidgetIds) {
            updateWidget(context, appWidgetManager, id);
        }
    }

    private void updateWidget(Context context, AppWidgetManager appWidgetManager, int id) {
        Log.e(TAG,"appwidgetId:"+id);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.view_widget);
        Intent intent = new Intent();
        intent.setAction(CLICK_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.img_label, pendingIntent);
        appWidgetManager.updateAppWidget(id, views);
    }


}
