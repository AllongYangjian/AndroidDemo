package com.yj.intent.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.AlarmClock;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.yj.intent.R;
import com.yj.intent.databinding.ActivityCommonIntentBinding;
import com.yj.intent.dialog.ContactAddDialog;
import com.yj.intent.utils.Utils;

import java.io.InputStream;
import java.util.Date;
import java.util.Random;



public class CommonIntentActivity extends AppCompatActivity {

    private final String TAG = "CommonIntentActivity";

    private ActivityCommonIntentBinding binding;

    private final int REQUEST_IMAGE_CAPTURE = 1;
    private final int STILL_IMAGE_CAMERA = 2;
    private final int REQUEST_VIDEO_CAPTURE = 3;
    private final int INTENT_CHOOSE_CONTACT_ID = 4;
    private final int INTENT_CHOOSE_CONTACT_PHONE = 5;
    private final int INTENT_CHOOSE_PICTURE = 6;
    private final int INTENT_OPEN_FILE = 7;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_common_intent);
        initView();
    }


    private void initView() {
        binding.btnCreateAlarm.setOnClickListener(onClickListener);
        binding.btnCreateTimer.setOnClickListener(onClickListener);
        binding.btnAddCalendarEvent.setOnClickListener(onClickListener);
        binding.btnTakePhoto.setOnClickListener(onClickListener);
        binding.btnTakePhoto2.setOnClickListener(onClickListener);
        binding.btnTakeVideo.setOnClickListener(onClickListener);

        binding.video.setOnClickListener(onClickListener);

        binding.btnChooseContactId.setOnClickListener(onClickListener);
        binding.btnChooseContact.setOnClickListener(onClickListener);
        binding.btnContactList.setOnClickListener(onClickListener);
        binding.btnAddContact.setOnClickListener(onClickListener);
        binding.btnSendEmail.setOnClickListener(onClickListener);
        binding.btnChoosePicture.setOnClickListener(onClickListener);
        binding.btnOpenFile.setOnClickListener(onClickListener);
        binding.btnCreateNote.setOnClickListener(onClickListener);
        binding.btnCallPhone.setOnClickListener(onClickListener);
        binding.btnSendSms.setOnClickListener(onClickListener);
        binding.btnOpenSettings.setOnClickListener(onClickListener);
    }


    private View.OnClickListener onClickListener = view -> {
        switch (view.getId()) {
            case R.id.btn_create_alarm:
                createAlarm();
                break;
            case R.id.btn_create_timer:
                createTimer();
                break;
            case R.id.btn_add_calendar_event:
                insertCalender();
                break;
            case R.id.btn_take_photo:
                takePhoto();
                break;
            case R.id.btn_take_photo2:
                capturePhoto();
                break;
            case R.id.btn_take_video:
                takeVideo();
                break;
            case R.id.video:
                if (binding.video.isPlaying()) {
                    binding.video.pause();
                } else {
                    binding.video.start();
                }
                break;
            case R.id.btn_choose_contact_id:
                chooseContactId();
                break;
            case R.id.btn_choose_contact:
                chooseContactPhone();
                break;
            case R.id.btn_contact_list:
                selectAllContactList();
                break;
            case R.id.btn_add_contact:
                addContact();
                break;
            case R.id.btn_send_email:
                sendEmail();
                break;
            case R.id.btn_choose_picture:
                choosePicture();
                break;
            case R.id.btn_open_file:
                openFile();
                break;
            case R.id.btn_create_note:
                createNote();
                break;
            case R.id.btn_call_phone:
                callPhone();
                break;
            case R.id.btn_send_sms:
                sendSms();
                break;
            case R.id.btn_open_settings:
                openSettings();
                break;
        }
    };

    /**
     * 打开特定设置
     * ACTION_SETTINGS
     * ACTION_WIRELESS_SETTINGS 网络设置
     * ACTION_AIRPLANE_MODE_SETTINGS 飞行模式
     * ACTION_WIFI_SETTINGS wifi
     * ACTION_APN_SETTINGS
     * ACTION_BLUETOOTH_SETTINGS
     * ACTION_DATE_SETTINGS
     * ACTION_LOCALE_SETTINGS
     * ACTION_INPUT_METHOD_SETTINGS
     * ACTION_DISPLAY_SETTINGS
     * ACTION_SECURITY_SETTINGS
     * ACTION_LOCATION_SOURCE_SETTINGS
     * ACTION_INTERNAL_STORAGE_SETTINGS
     * ACTION_MEMORY_CARD_SETTINGS
     */
    private void openSettings(){
        Intent intent = new Intent(Settings.ACTION_DISPLAY_SETTINGS);
        if(intent.resolveActivity(getPackageManager())!=null){
            startActivity(intent);
        }
    }

    @SuppressLint("IntentReset")
    private void sendSms(){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("text/plain");
        intent.setData(Uri.parse("smsto:18667006178"));
        intent.putExtra("sms_body","sb");
//        intent.putExtra(Intent.EXTRA_STREAM,uri)
        if(intent.resolveActivity(getPackageManager())!=null){
            startActivity(intent);
        }
    }

    /**
     * 打电话
     */
    private void callPhone(){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:18667006178"));
        if(intent.resolveActivity(getPackageManager())!=null){
            startActivity(intent);
        }
    }

    /**
     * 创建笔记
     */
    private void createNote(){
//        Intent intent = new Intent(NoteIntents.ACTION_CREATE_NOTE)
//                .putExtra(NoteIntents.EXTRA_NAME, subject)
//                .putExtra(NoteIntents.EXTRA_TEXT, text);
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivity(intent);
//        }
    }

    private void openFile(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//        intent.setType("image/*");
        intent.setType("audio/*");
//        intent.setType("video/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        if(intent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(intent,INTENT_OPEN_FILE);
        }
    }

//    public void callCar() {
//        Intent intent = new Intent(ReserveIntents.ACTION_RESERVE_TAXI_RESERVATION);
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivity(intent);
//        }
//    }


    private void choosePicture() {
//方式一
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, INTENT_CHOOSE_PICTURE);
        }
        //方式二
//        Intent intent = new Intent(Intent.ACTION_PICK,
//                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(intent, INTENT_CHOOSE_PICTURE);
    }

    private void sendEmail() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"15536894786@163.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "测试邮件");

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * 添加联系人
     */
    private void addContact() {
        ContactAddDialog dialog = new ContactAddDialog();
        dialog.show(getSupportFragmentManager(), dialog.getTag());
    }

    private void selectAllContactList() {
        Intent intent = new Intent(this, ContactListActivity.class);
        startActivity(intent);
    }

    /**
     * 从联系人表中查询数据
     * 一般连说联系人表中的最重要的信息为联系人id
     * 然后根据id从联系人表中获取相关联系人信息
     */
    private void chooseContactId() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
        //等价于
//        Intent intent = new Intent(Intent.ACTION_PICK,ContactsContract.Contacts.CONTENT_URI);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, INTENT_CHOOSE_CONTACT_ID);
        }
    }

    /**
     * 这种方式是直接从view_data视图中获取联系人信息
     */
    private void chooseContactPhone() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, INTENT_CHOOSE_CONTACT_PHONE);
        }
    }

    private Uri videoUri;

    /**
     * 拍摄视频
     */
    private void takeVideo() {
        videoUri = Utils.getOutputVideoUri(getApplicationContext());
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);
        //设置视频质量高
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_VIDEO_CAPTURE);
        }
    }

    /**
     * 静态模式启动相机，拍照后并不会返回
     */
    private void capturePhoto() {
        Intent intent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, STILL_IMAGE_CAMERA);
        }
    }

    private Uri locationPhotos;

    /**
     * 拍照
     * Android 7.0以后需要使用 content://uri 来代替 file://uri
     * 7.0 拍摄照片步骤
     * 1、在manifest文件中添加创建FileProvider
     * 2、创建paths.xml路径配置文件
     * 3、创建uri
     * 4、启动Intent
     */
    private void takePhoto() {
        locationPhotos = Utils.getOutputMediaUri(getApplicationContext());
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, locationPhotos);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

    /**
     * 创建闹钟
     * 需要权限 com.android.alarm.permission.SET_ALARM
     */
    private void createAlarm() {
        Random random = new Random();
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
                .putExtra(AlarmClock.EXTRA_MESSAGE, "设置闹钟") //指定闹钟的自定义消息
                .putExtra(AlarmClock.EXTRA_HOUR, random.nextInt(12))
                .putExtra(AlarmClock.EXTRA_MINUTES, random.nextInt(59));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * 创建定时器
     * 需要 19以上api
     * 需要权限 com.android.alarm.permission.SET_ALARM
     */
    private void createTimer() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            Intent intent = new Intent(AlarmClock.ACTION_SET_TIMER)
                    .putExtra(AlarmClock.EXTRA_LENGTH, 60)
                    .putExtra(AlarmClock.EXTRA_SKIP_UI, true);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }

    /**
     * 插入日历事件
     * 需要设置data
     */
    private void insertCalender() {
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.Events.TITLE, "纪念日")
                .putExtra(CalendarContract.Events.EVENT_LOCATION, "潜江")
//                .putExtra(CalendarContract.Events.ALL_DAY,true)//是否是全天事件
//                .putExtra(CalendarContract.Events.DESCRIPTION,"结婚纪念日")//描述
//                .putExtra(CalendarContract.Events.ACCOUNT_NAME,"")//设置账户名
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, new Date().getTime())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, new Date().getTime());
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap bitmap = getImage(locationPhotos);
            if (bitmap != null)
                binding.imgPicture.setImageBitmap(bitmap);
        } else if (requestCode == STILL_IMAGE_CAMERA && resultCode == RESULT_OK) {

        } else if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            assert data != null;
            if (data.getData() != null) {
                Log.e(TAG, data.getData().toString());
                binding.video.setVideoURI(data.getData());
            }
        } else if (requestCode == INTENT_CHOOSE_CONTACT_PHONE && resultCode == RESULT_OK) {
            showContactPhoneInfo(data.getData());
        } else if (requestCode == INTENT_CHOOSE_CONTACT_ID && resultCode == RESULT_OK) {
            showContactInfoName(data.getData());
        } else if (requestCode == INTENT_CHOOSE_PICTURE && resultCode == RESULT_OK) {
            Uri uri = data.getData();
//            Log.e(TAG,uri.toString());
            Bitmap bitmap = getImage(uri);
            binding.imgChoosePicture.setImageBitmap(bitmap);
        }
    }

    /**
     * 从视图中查询联系人电话信息
     *
     * @param contactUri
     */
    private void showContactPhoneInfo(Uri contactUri) {
//            Log.e(TAG,contactUri.toString());
        String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};
        Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
//                int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
//                int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            String number = cursor.getString(0);
            String name = cursor.getString(1);
            Toast.makeText(getApplicationContext(), name + "的电话号码为:" + number, Toast.LENGTH_LONG).show();
            cursor.close();
        }
    }

    /**
     * 通过联系人ID查询联系人电话信息
     * 这种情况下需要获取联系人权限
     *
     * @param contactUri
     */
    private void showContactInfoName(Uri contactUri) {
        String[] projection = new String[]{ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME};
        Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);

            //通过id查找联系人电话
            Cursor phoneCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null);

            if (phoneCursor != null && phoneCursor.moveToNext()) {
                String phone = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                Toast.makeText(getApplicationContext(), name + "的电话号码为:" + phone, Toast.LENGTH_LONG).show();

                phoneCursor.close();
            }
            cursor.close();
        }
    }

    private Bitmap getImage(Uri uri) {
        try {
            InputStream input = getContentResolver().openInputStream(uri);

            //这一段代码是不加载文件到内存中也得到bitmap的真是宽高，主要是设置inJustDecodeBounds为true
            BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
            onlyBoundsOptions.inJustDecodeBounds = true;//不加载到内存
            onlyBoundsOptions.inDither = true;//optional
            onlyBoundsOptions.inPreferredConfig = Bitmap.Config.RGB_565;//optional
            BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
            input.close();
            int originalWidth = onlyBoundsOptions.outWidth;
            int originalHeight = onlyBoundsOptions.outHeight;
            if ((originalWidth == -1) || (originalHeight == -1))
                return null;

            //图片分辨率以480x800为标准
            float hh = 800f;//这里设置高度为800f
            float ww = 480f;//这里设置宽度为480f
            //缩放比，由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
            int be = 1;//be=1表示不缩放
            if (originalWidth > originalHeight && originalWidth > ww) {//如果宽度大的话根据宽度固定大小缩放
                be = (int) (originalWidth / ww);
            } else if (originalWidth < originalHeight && originalHeight > hh) {//如果高度高的话根据宽度固定大小缩放
                be = (int) (originalHeight / hh);
            }
            if (be <= 0)
                be = 1;
            //比例压缩
            BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
            bitmapOptions.inSampleSize = be;//设置缩放比例
            bitmapOptions.inDither = true;
            bitmapOptions.inPreferredConfig = Bitmap.Config.RGB_565;
            input = getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
            input.close();

            return Utils.compressImage(bitmap);//再进行质量压缩
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
