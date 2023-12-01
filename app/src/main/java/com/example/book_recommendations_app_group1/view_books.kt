package com.example.book_recommendations_app_group1

import DatabaseHelper
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class view_books : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BookAdapter
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_books)

        dbHelper = DatabaseHelper(this)

        recyclerView = findViewById(R.id.recyclerViewBooks)
        recyclerView.layoutManager = LinearLayoutManager(this)


        adapter = BookAdapter(getBooksFromDatabase()) { book, action ->

            when (action) {
                ActionType.DELETE -> deleteBook(book)
                ActionType.UPDATE -> updateBook(book)
            }
        }
        recyclerView.adapter = adapter

        val buttonBack: Button = findViewById(R.id.buttonBack)
        buttonBack.setOnClickListener {
//            startActivity(Intent(this, administrator_module::class.java))
            val intent = Intent(this, administrator_module::class.java)
            intent.putExtra("logInt", 1)
            startActivity(intent)
            finish()
        }
    }




//        if (bookIdToDelete != null) {
//            adapter.removeBook(bookIdToDelete)
//            Toast.makeText(this, "Book Deleted Successfully!", Toast.LENGTH_SHORT).show()
//        } else {
//
//            Toast.makeText(this, "Invalid Book ID", Toast.LENGTH_SHORT).show()
//        }
private fun deleteBook(book: Book) {

    if (adapter.itemCount > 0) {
        adapter.removeBook(book.id.toInt())
        Toast.makeText(this, "Book Deleted Successfully!", Toast.LENGTH_SHORT).show()
    } else {
        Toast.makeText(this, "No books to delete", Toast.LENGTH_SHORT).show()
    }
}



    private fun updateBook(book: Book) {
        managebooks = 2
        val intent = Intent(this, add_book::class.java)
        intent.putExtra("bID", book.id)
        intent.putExtra("managebooks", 4)
        startActivity(intent)
    }

    private fun getBooksFromDatabase(): MutableList<Book> {
        val db = dbHelper.readableDatabase
        val projection = arrayOf(
            DatabaseContract.BookEntry.COLUMN_ID,
            DatabaseContract.BookEntry.COLUMN_NAME,
            DatabaseContract.BookEntry.COLUMN_AUTHOR,
            DatabaseContract.BookEntry.COLUMN_DESCRIPTION,
            DatabaseContract.BookEntry.COLUMN_GENRE,
            DatabaseContract.BookEntry.COLUMN_READING_LEVEL,
            DatabaseContract.BookEntry.COLUMN_IMAGE
        )

        val cursor = db.query(
            DatabaseContract.BookEntry.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            null
        )

        val books = mutableListOf<Book>()

        with(cursor) {
            while (moveToNext()) {
                val bookId = getLong(getColumnIndexOrThrow(DatabaseContract.BookEntry.COLUMN_ID))
                val name = getString(getColumnIndexOrThrow(DatabaseContract.BookEntry.COLUMN_NAME))
                val author = getString(getColumnIndexOrThrow(DatabaseContract.BookEntry.COLUMN_AUTHOR))
                val description = getString(getColumnIndexOrThrow(DatabaseContract.BookEntry.COLUMN_DESCRIPTION))
                val genre = getString(getColumnIndexOrThrow(DatabaseContract.BookEntry.COLUMN_GENRE))
                val image = getString(getColumnIndexOrThrow(DatabaseContract.BookEntry.COLUMN_IMAGE))
                val level = getString(getColumnIndexOrThrow(DatabaseContract.BookEntry.COLUMN_READING_LEVEL))

                val book = Book(bookId, name, author, description, genre, level, image)
                books.add(book)
            }
        }

        cursor.close()
        db.close()

        return books
    }

    override fun onDestroy() {
        super.onDestroy()
        dbHelper.close()
    }


}


