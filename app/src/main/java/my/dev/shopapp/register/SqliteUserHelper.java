package my.dev.shopapp.register;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

/*Through the methods of this class, we define the interaction with the database.*/
public class SqliteUserHelper extends SQLiteOpenHelper {

    public SqliteUserHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }
    public void queryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }
    public void insertData(String login, String password ){
        SQLiteDatabase database = getWritableDatabase();
        String sqltousers = "INSERT INTO USERS VALUES (NULL, ?, ?)";
        SQLiteStatement statement = database.compileStatement(sqltousers);
        statement.clearBindings();
        statement.bindString(1, login);
        statement.bindString(2, password);
        statement.executeInsert();
    }
    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
