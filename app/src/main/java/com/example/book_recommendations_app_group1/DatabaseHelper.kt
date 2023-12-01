import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.book_recommendations_app_group1.DatabaseContract

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {


     db.execSQL("DROP TABLE IF EXISTS ${DatabaseContract.BookEntry.TABLE_NAME}")

        val SQL_CREATE_BOOKS_TABLE = """
        CREATE TABLE ${DatabaseContract.BookEntry.TABLE_NAME} (
    ${DatabaseContract.BookEntry.COLUMN_ID} INTEGER PRIMARY KEY,
    ${DatabaseContract.BookEntry.COLUMN_NAME} TEXT NOT NULL,
    ${DatabaseContract.BookEntry.COLUMN_IMAGE} TEXT, 
    ${DatabaseContract.BookEntry.COLUMN_DESCRIPTION} TEXT,
    ${DatabaseContract.BookEntry.COLUMN_READING_LEVEL} TEXT,
    ${DatabaseContract.BookEntry.COLUMN_PAGE_COUNT} INTEGER,
    ${DatabaseContract.BookEntry.COLUMN_YEAR} INTEGER,
    ${DatabaseContract.BookEntry.COLUMN_AUTHOR} TEXT,
    ${DatabaseContract.BookEntry.COLUMN_PUBLISHING_COMPANY} TEXT,
    ${DatabaseContract.BookEntry.COLUMN_GENRE} TEXT
      )
      """.trimIndent()

        db.execSQL(SQL_CREATE_BOOKS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }

    companion object {
        const val DATABASE_NAME = "books.db"
        const val DATABASE_VERSION = 1
    }
}
