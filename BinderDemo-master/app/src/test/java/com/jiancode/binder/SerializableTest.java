package com.jiancode.binder;

import android.util.Log;

import com.jiancode.binder.bean.User;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SerializableTest {

    public static final String TAG = Serializable.class.getSimpleName();

    @Test
    public void testSerializable(){
        User user = new User("1","test",23);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("cache.txt"));
            oos.writeObject(user);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUnSerializable(){
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("cache.txt"));
            User user = (User) ois.readObject();
            System.err.print(user.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testEquals(){
        User user = new User("1","test",23);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("cache.txt"));
            oos.writeObject(user);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        User user2 = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("cache.txt"));
             user2= (User) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.err.println(user);
        System.err.println(user2);
    }
}
