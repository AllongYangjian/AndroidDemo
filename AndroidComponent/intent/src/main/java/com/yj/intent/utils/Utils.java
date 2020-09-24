package com.yj.intent.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.core.content.FileProvider;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {

    private static final String TAG = "Utils";

    /**
     * 将指定的路径映射中 content uri
     *
     * @param context
     * @return
     */
    public static Uri getOutputMediaUri(Context context) {
        try {
            String timeTemp = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(new Date());
            String path = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                    .getAbsolutePath() + File.separator + "temp";

            File storagePath = new File(path);
            Log.e(TAG, storagePath.getAbsolutePath());
            storagePath.mkdirs();
            File imageFile = new File(storagePath,"IMG_"+timeTemp+".png");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return FileProvider.getUriForFile(context, context.getPackageName() + ".provider", imageFile);
            } else {
                //直接将地址映射成uri
                return Uri.fromFile(imageFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Uri getOutputVideoUri(Context context) {
        try {
            String timeTemp = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(new Date());

            String path = context.getExternalFilesDir(Environment.DIRECTORY_DCIM).getAbsolutePath() + File.separator + "video";
            File file = new File(path);
            Log.e(TAG, file.getAbsolutePath()+","+timeTemp);
            if (!file.exists()) {
                file.mkdirs();
            }
            File videoPath = new File(file,"VIDEO_"+timeTemp+".mp4");
            videoPath.setWritable(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return FileProvider.getUriForFile(context, context.getPackageName() + ".provider", videoPath);
            } else {
                return Uri.fromFile(videoPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 压缩照片
     *
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            //第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差  ，第三个参数：保存压缩后的数据的流
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
            if (options <= 0)
                break;
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }
}
