package com.yj.intent.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.yj.intent.R;
import com.yj.intent.databinding.ActivityContactListBinding;


public class ContactListActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener {

    private final String TAG = "ContactListActivity";

    private ActivityContactListBinding binding;

    private SimpleCursorAdapter mAdapter;

    private final static String[] FROM_COLUMNS = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER};

    private final static String[] PROJECTION = {ContactsContract.CommonDataKinds.Phone._ID,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.Contacts.LOOKUP_KEY};//

    private final static int[] TO_IDS = {R.id.tv_name, R.id.tv_phone};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contact_list);
        initData();
        initView();
    }

    private void initData() {
        //ignores
    }

    private void initView() {
//        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
//        binding.rvContact.setLayoutManager(manager);

        mAdapter = new SimpleCursorAdapter(getApplicationContext(), R.layout.item_contact_list, null,
                FROM_COLUMNS, TO_IDS, 0);
        binding.rvContact.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();
        binding.rvContact.setOnItemClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LoaderManager.getInstance(this).initLoader(0, null, this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(getApplicationContext(), ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                PROJECTION, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME +" asc");
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Cursor cursor = ((SimpleCursorAdapter) parent.getAdapter()).getCursor();
        // Move to the selected contact
        cursor.moveToPosition(position);
        // Get the _ID value
        long contactId = cursor.getLong(0);
        // Get the selected LOOKUP KEY
        String contactKey = cursor.getString(3);
        // Create the contact's content Uri
        Uri contactUri = ContactsContract.Contacts.getLookupUri(contactId, contactKey);
        Log.e(TAG, contactUri.toString());

        //修改联系人电子邮件
        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setData(contactUri);
        intent.putExtra(ContactsContract.Intents.Insert.EMAIL, "952801974@qq.com");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
