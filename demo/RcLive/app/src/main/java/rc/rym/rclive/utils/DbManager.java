package rc.rym.rclive.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DbManager extends SQLiteOpenHelper {

    private static final String DB_NAME = "rclive.db";
    private static final int DB_VER = 2;

    public class TableUser implements BaseColumns {
        static final String TABLE_NAME = "user";
        static final String COLUMN_NAME_USER_ID = "user_id";
        static final String COLUMN_NAME_NICKNAME = "nickname";
        static final String COLUMN_NAME_PORTRAIT = "portrait";
    }
    private static final String SQL_CREATE_USER_TABLE = "CREATE TABLE " + TableUser.TABLE_NAME +
            " (" +
            TableUser.COLUMN_NAME_USER_ID + " TEXT PRIMARY KEY, " +
            TableUser.COLUMN_NAME_NICKNAME + " TEXT, " +
            TableUser.COLUMN_NAME_PORTRAIT + " TEXT" +
            " )";
    private static final String SQL_DELETE_USER_TABLE = "DROP TABLE IF EXISTS " + TableUser.TABLE_NAME;


    public DbManager(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_USER_TABLE);
        onCreate(db);
    }

    public void replaceUser(User user) {
        ContentValues cv = new ContentValues();
        cv.put(TableUser.COLUMN_NAME_USER_ID, user.userId);
        cv.put(TableUser.COLUMN_NAME_NICKNAME, user.nickname);
        cv.put(TableUser.COLUMN_NAME_PORTRAIT, user.portrait);
        getWritableDatabase().replace(TableUser.TABLE_NAME, null, cv);
    }

    public User getUser(String userId) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TableUser.TABLE_NAME + " WHERE " +
                TableUser.COLUMN_NAME_USER_ID + " = ?", new String[] {userId});
        User user = null;
        if(cursor.moveToFirst()) {
            String nickname = cursor.getString(cursor.getColumnIndex(TableUser.COLUMN_NAME_NICKNAME));
            String portrait = cursor.getString(cursor.getColumnIndex(TableUser.COLUMN_NAME_PORTRAIT));
            user = new User(userId, nickname, portrait);
        }
        cursor.close();
        return user;
    }
}
