package hotshot.elick.com.hotshot.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import hotshot.elick.com.hotshot.entity.UserBean;
import hotshot.elick.com.hotshot.utils.MyLog;

public class UserInfoData extends SQLiteOpenHelper {
    private static String DATA_NAME = "user";
    private static String TABLE_NAME = "userInfo";
    private static int VERSION = 1;
    private static String USERNAME = "username";
    private static String AVATAR = "avatar";
    private static String UID = "uid";
    private static String PHONE = "phone";

    public UserInfoData(Context context) {
        super(context, DATA_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " " +
                "(username TEXT PRIMARY KEY NOT NULL,avatar TEXT,uid TEXT,phone TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addUser(UserBean userBean) {
        if (getWritableDatabase().delete(TABLE_NAME, null, null) != -1) {
            MyLog.d("delete user table success, start insert user");
            ContentValues contentValues = new ContentValues();
            contentValues.put(USERNAME, userBean.getUsername());
            contentValues.put(AVATAR, userBean.getAvatar());
            contentValues.put(UID, userBean.getUid());
            contentValues.put(PHONE, userBean.getPhone());
            if (getWritableDatabase().insert(TABLE_NAME, null, contentValues) != -1) {
                MyLog.d("insert user success");
            } else {
                MyLog.d("insert user fail");
            }
        }
    }

    public UserBean getUser() {
        Cursor cursor = getReadableDatabase().query(TABLE_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            UserBean userBean = new UserBean();
            userBean.setUsername(cursor.getString(cursor.getColumnIndex(USERNAME)));
            userBean.setAvatar(cursor.getString(cursor.getColumnIndex(AVATAR)));
            userBean.setUid(cursor.getString(cursor.getColumnIndex(UID)));
            userBean.setPhone(cursor.getColumnName(cursor.getColumnIndex(PHONE)));
            cursor.close();
            return userBean;
        }
        cursor.close();
        return null;
    }

    public void deleteUser() {
        int result = getWritableDatabase().delete(TABLE_NAME, null, null);
        if (result != -1) {
            MyLog.d("delete user table success");
        } else {
            MyLog.d("delete user table fail");
        }
    }
}
