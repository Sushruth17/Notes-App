package seventeen.my.notes.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import seventeen.my.notes.model.Notes
import java.lang.String.valueOf


class DatabaseHandler(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_NOTES_TABLE = ("CREATE TABLE " + TABLE_NAME + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + ", "
                + KEY_TITLE + " TEXT NOT NULL" + ", "
                + KEY_SUB_TITLE + " TEXT NOT NULL" + ", "
                + KEY_DATE_TIME + " TEXT NOT NULL" + ", "
                + KEY_NOTE + " TEXT" + ", "
                + KEY_IMG_PATH + " TEXT" + ", "
                + KEY_WEB_URL + " TEXT" + ", "
                + KEY_COLOR + " TEXT" + ", "
                + KEY_EMAIL + " TEXT"
                + ")")
        Log.d(TAG, CREATE_NOTES_TABLE)
        db.execSQL(CREATE_NOTES_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
        Log.d(TAG, DROP_TABLE)
        db.execSQL(DROP_TABLE)
        onCreate(db)
    }

    //CRUD OPERATIONS
    fun addNote(note: Notes, email: String?) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_TITLE, note.title)
        values.put(KEY_SUB_TITLE, note.subTitle)
        values.put(KEY_DATE_TIME, note.dateTime)
        values.put(KEY_NOTE, note.noteText)
        values.put(KEY_IMG_PATH, note.imgPath)
        values.put(KEY_WEB_URL, note.webLink)
        values.put(KEY_COLOR, note.color)
        values.put(KEY_EMAIL, email)
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun updateNote(note: Notes): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_TITLE, note.title)
        values.put(KEY_SUB_TITLE, note.subTitle)
        values.put(KEY_DATE_TIME, note.dateTime)
        values.put(KEY_NOTE, note.noteText)
        values.put(KEY_IMG_PATH, note.imgPath)
        values.put(KEY_WEB_URL, note.webLink)
        values.put(KEY_COLOR, note.color)

        return db.update(TABLE_NAME, values, "$KEY_ID = ?", arrayOf(valueOf(note.id)))
    }

    fun deleteNote(noteId: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$KEY_ID = ?", arrayOf(valueOf(noteId)))
        db.close()
    }

    fun getNote(id: Int): Notes {
        val db = this.readableDatabase
        val c: Cursor? = db.query(
            TABLE_NAME,
            COLS_ID_TITLE_NOTE,
            "$KEY_ID=?",
            arrayOf(id.toString()),
            null,
            null,
            null,
            null )
        c?.moveToFirst()
        db.close()
        Log.d(
            TAG,
            "Get Note Result " + c?.getString(0).toString()
                    + "," + c?.getString(1).toString()
                    + "," + c?.getString(2)
        )
        return Notes(
            c?.getString(0)?.toInt(),
            c?.getString(1),
            c?.getString(2),
            c?.getString(3),
            c?.getString(4),
            c?.getString(5),
            c?.getString(6),
            c?.getString(7))
    }

    fun allNotes(email: String?): ArrayList<Notes>{
            val db = this.readableDatabase
            val noteList: ArrayList<Notes> = ArrayList()
            val cursor: Cursor? =
                db.query(TABLE_NAME, COLS_ID_TITLE_NOTE, "$KEY_EMAIL=?", arrayOf(email), null, null, null)
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    val note = Notes()
                    note.id = (cursor.getString(0).toInt())
                    note.title = (cursor.getString(1))
                    note.subTitle = (cursor.getString(2))
                    note.dateTime = (cursor.getString(3))
                    note.noteText = (cursor.getString(4))
                    note.imgPath = (cursor.getString(5))
                    note.webLink = (cursor.getString(6))
                    note.color = (cursor.getString(7))
                    noteList.add(note)
                } while (cursor.moveToNext())
            }
//            cursor.close()
            db.close()
            return noteList
        }

    companion object {
        private const val TAG = "DatabaseHandler"
        private const val DATABASE_VERSION = 4
        private const val DATABASE_NAME = "notes_manager"
        private const val TABLE_NAME = "notes"

        // Coloumn Names
        private const val KEY_ID = "id"
        private const val KEY_TITLE = "title"
        private const val KEY_SUB_TITLE = "subTitle"
        private const val KEY_DATE_TIME = "dateTime"
        private const val KEY_NOTE = "note"
        private const val KEY_IMG_PATH = "imgPath"
        private const val KEY_WEB_URL = "webUrl"
        private const val KEY_COLOR = "color"
        private const val KEY_EMAIL = "email"


        // Coloumn Combinations
        private val COLS_ID_TITLE_NOTE = arrayOf(KEY_ID, KEY_TITLE, KEY_SUB_TITLE,
                                                KEY_DATE_TIME, KEY_NOTE, KEY_IMG_PATH,
                                                KEY_WEB_URL, KEY_COLOR, KEY_EMAIL)
    }
}