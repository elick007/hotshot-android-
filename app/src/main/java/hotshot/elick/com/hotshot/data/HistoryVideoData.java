package hotshot.elick.com.hotshot.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

import hotshot.elick.com.hotshot.entity.VideoBean;
import hotshot.elick.com.hotshot.utils.MyLog;

public class HistoryVideoData extends SQLiteOpenHelper {
    private static final String DATA_NAME = "video.db";
    private static final String TABLE_NAME = "video_history";
    private static int VERSION = 1;
    private static final String ID="id";
    private static final String VIDEO_ID="videoId";
    private static final String CREATED = "created";
    private static final String TYPE = "type";
    private static final String DATE = "date";
    private static final String DURATION = "duration";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String COVER = "cover";
    private static final String PLAY_URL = "playUrl";
    private static final String AUTHOR = "author";
    private static final String EXEC = "create table if not exists " + TABLE_NAME + " (" +
            VIDEO_ID+" INTEGER,"+
            CREATED + " TEXT," +
            TYPE + " TEXT," +
            DATE + " TEXT," +
            DURATION + " TEXT," +
            TITLE + " TEXT," +
            DESCRIPTION + " TEXT," +
            COVER + " TEXT," +
            PLAY_URL + " TEXT UNIQUE," +
            AUTHOR + " TEXT,"+
             ID+" INTEGER PRIMARY KEY AUTOINCREMENT)";

    public HistoryVideoData(Context context) {
        super(context, DATA_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(EXEC);
        db.setMaximumSize(20);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertVideo(VideoBean videoBean) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, PLAY_URL + "=?", new String[]{videoBean.getPlayUrl()}, null, null, null);
        if (cursor.moveToFirst()) {
            if (db.delete(TABLE_NAME,PLAY_URL+"=?",new String[]{videoBean.getPlayUrl()})!=-1){
                MyLog.d("delete video at video_history table success");
            }else {
                MyLog.d("delete video at video_history table fail");
            }
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(VIDEO_ID,videoBean.getId());
        contentValues.put(CREATED, videoBean.getCreated());
        contentValues.put(TYPE, videoBean.getType());
        contentValues.put(DATE, videoBean.getDate());
        contentValues.put(DURATION, videoBean.getDuration());
        contentValues.put(TITLE, videoBean.getTitle());
        contentValues.put(DESCRIPTION, videoBean.getDescription());
        contentValues.put(COVER, videoBean.getCover());
        contentValues.put(PLAY_URL, videoBean.getPlayUrl());
        contentValues.put(AUTHOR, videoBean.getAuthor());
        if (db.insert(TABLE_NAME,null,contentValues)!=-1){
            MyLog.d("insert video at video_history table success");
        }else {
            MyLog.d("insert video at video_history table fail");
        }
        cursor.close();
    }
    public List<VideoBean> getHistory(){
        SQLiteDatabase db=getWritableDatabase();
        Cursor cursor=db.query(TABLE_NAME,null,null,null,null,null,ID+" desc");
        List<VideoBean> list=new ArrayList<>();
        while (cursor.moveToNext()){
            int id=cursor.getInt(cursor.getColumnIndex(VIDEO_ID));
            String created=cursor.getString(cursor.getColumnIndex(CREATED));
            String type=cursor.getString(cursor.getColumnIndex(TYPE));
            String date=cursor.getString(cursor.getColumnIndex(DATE));
            String duration=cursor.getString(cursor.getColumnIndex(DURATION));
            String title=cursor.getString(cursor.getColumnIndex(TITLE));
            String description=cursor.getString(cursor.getColumnIndex(DESCRIPTION));
            String cover=cursor.getString(cursor.getColumnIndex(COVER));
            String playUrl=cursor.getString(cursor.getColumnIndex(PLAY_URL));
            String author=cursor.getString(cursor.getColumnIndex(AUTHOR));
            VideoBean videoBean=new VideoBean();
            videoBean.setId(id);
            videoBean.setCreated(created);
            videoBean.setType(type);
            videoBean.setDate(date);
            videoBean.setDuration(duration);
            videoBean.setTitle(title);
            videoBean.setDescription(description);
            videoBean.setCover(cover);
            videoBean.setPlayUrl(playUrl);
            videoBean.setAuthor(author);
            list.add(videoBean);
        }
        cursor.close();
        return list;
    }
}
