package x40240.shaun.adriano.a2.app.db;

import java.util.ArrayList;
import java.util.List;

import x40240.shaun.adriano.a2.app.model.PersonInfo;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

public final class DBHelper
{
    private static final String LOGTAG = DBHelper.class.getSimpleName();
    
    private static final String DATABASE_NAME    = "namedb.db";
    private static final int    DATABASE_VERSION = 1;
    private static final String TABLE_NAME       = "names";

    // Column Names
    public static final String      KEY_ID            = "_id";
    public static final String      KEY_FIRSTNAME     = "firstname";
    public static final String      KEY_LASTNAME      = "lastname";
    public static final String      KEY_GENDER        = "gender";
    public static final String		KEY_FAVMEAL		  = "favmeal";

    // Column indexes
    public static final int COLUMN_ID         = 0;
    public static final int COLUMN_FIRSTNAME  = 1;
    public static final int COLUMN_LASTNAME   = 2;
    public static final int COLUMN_GENDER     = 3;
    public static final int COLUMN_FAVMEAL    = 4;
    
    private Context             context;
    private SQLiteDatabase      db;
    private SQLiteStatement     insertStmt;

    private static final String INSERT =
        "INSERT INTO " + TABLE_NAME + "(" +
        KEY_FIRSTNAME + ", " +
        KEY_LASTNAME + ", " +
        KEY_GENDER + ", " +
        KEY_FAVMEAL + ") values (?, ?, ?, ?)";

    public DBHelper (Context context)
    {
        this.context = context;
        OpenHelper openHelper = new OpenHelper(this.context);
        db = openHelper.getWritableDatabase();
        insertStmt = db.compileStatement(INSERT);
    }

    public long insert (PersonInfo personInfo)
    {
        insertStmt.bindString(COLUMN_FIRSTNAME, personInfo.getFirstname());
        insertStmt.bindString(COLUMN_LASTNAME, personInfo.getLastname());
        insertStmt.bindLong(COLUMN_GENDER, personInfo.getGender());
        insertStmt.bindString(COLUMN_FAVMEAL, personInfo.getFavmeal());
        
        long value = insertStmt.executeInsert();
        Log.d (LOGTAG, "value="+value);
        return value;
    }

    public void deleteAll()
    {
        db.delete(TABLE_NAME, null, null);
    }

    public List<PersonInfo> selectAll()
    {
        List<PersonInfo> list = new ArrayList<PersonInfo>();
        Cursor cursor = db.query(TABLE_NAME,
            new String[] { KEY_ID, KEY_FIRSTNAME, KEY_LASTNAME, KEY_GENDER, KEY_FAVMEAL },
                null, null, null, null, KEY_LASTNAME);
        if (cursor.moveToFirst())
        {
            do
            {
                PersonInfo personInfo = new PersonInfo();
                personInfo.setFirstname(cursor.getString(COLUMN_FIRSTNAME));
                personInfo.setLastname(cursor.getString(COLUMN_LASTNAME));
                personInfo.setGender((int)cursor.getLong(COLUMN_GENDER));
                personInfo.setFavmeal(cursor.getString(COLUMN_FAVMEAL));
                list.add(personInfo);
            }
            while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed())
        {
            cursor.close();
        }
        return list;
    }

    private static class OpenHelper
        extends SQLiteOpenHelper
    {
        private static final String LOGTAG = OpenHelper.class.getSimpleName();
        
        private static final String CREATE_TABLE =
            "CREATE TABLE " +
            TABLE_NAME +
            " (" + KEY_ID + " integer primary key autoincrement, " +
            KEY_FIRSTNAME + " TEXT, " +
            KEY_LASTNAME  + " TEXT, " +
            KEY_GENDER    + " INTEGER, " +
            KEY_FAVMEAL   + " TEXT);";
        
        OpenHelper (Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate (SQLiteDatabase db)
        {
            Log.d(LOGTAG, "onCreate!");
            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w("Example",
                    "Upgrading database, this will drop tables and recreate.");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
}
