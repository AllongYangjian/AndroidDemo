package com.yj.app;

import android.content.Context;


import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.yj.app.db.MyRoomDatabase;
import com.yj.app.domain.Library;
import com.yj.app.domain.Playlist;
import com.yj.app.domain.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.List;

@RunWith(RobolectricTestRunner.class)
public class RoomDatabaseTest {

    private MyRoomDatabase database;

    @Before
    public void init(){
        Context context = ApplicationProvider.getApplicationContext();
        database = MyRoomDatabase.getDatabase(context);
    }

    @Test
    public void insert(){
        User user = new User();
        user.firstName = "yang";
        user.lastName = "jian";
        database.getUserDao().insertAll(user);
    }

    @Test
    public void insertLibrary(){
        Library library = new Library();
        library.name = "library1";
        library.sid = 1;
        database.getLibraryDao().insertAll(library);
    }

    @Test
    public void insertPlayList(){

        Playlist[] playlists = new Playlist[5];
        for(int x = 0;x<5;x++){
            Playlist playlist = new Playlist();
            playlist.playName = "playlist"+x;
            playlist.uid = 1;
            playlists[x] = playlist;
        }
        database.getPlayListDao().insertAll(playlists);
    }

    @Test
    public void listAll(){
        insert();
        insertLibrary();
        insertPlayList();
        List<User> all = database.getUserDao().getAll();
        List<Library> libraries = database.getLibraryDao().getAll();
        List<Playlist>  playlists= database.getPlayListDao().getAll();
        System.err.println(all.toString());
        System.err.println(libraries.toString());
        System.err.println(playlists.toString());
        System.err.println(database.getUserDao().getUserLibrary().toString());
        System.err.println(database.getUserDao().getUsersWithPlaylists().toString());
    }


    @After
    public void destroy(){
        database.close();
    }
}
