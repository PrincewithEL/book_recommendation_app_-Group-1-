package com.example.book_recommendations_app_group1

import DatabaseHelper
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private lateinit var recyclerView: RecyclerView
private lateinit var adapter: BookAdapter
private lateinit var dbHelper: DatabaseHelper

class view_recommendations : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_recommendations)


        val imageView = findViewById<ImageView>(R.id.adImg)

        imageView.visibility = View.INVISIBLE

        Handler().postDelayed({
            imageView.visibility = View.VISIBLE
        }, 1000)

        imageView.setOnClickListener {
            val adMobUri = Uri.parse("https://admob.google.com")
            val intent = Intent(Intent.ACTION_VIEW, adMobUri)
            startActivity(intent)
        }

        dbHelper = DatabaseHelper(this)

        recyclerView = findViewById(R.id.recyclerViewBooks)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val intent = intent

        var genre:String? = intent.getStringExtra("myGenre")
        var rl:String? = intent.getStringExtra("myRL")

        adapter = BookAdapter(getBooksFromDatabase(filterGenre = genre, filterReadingLevel = rl)) { book, action ->

        }
        recyclerView.adapter = adapter

        val buttonBack: Button = findViewById(R.id.buttonBack)
        buttonBack.setOnClickListener {
            val intent = Intent(this, user_module::class.java)
            intent.putExtra("logInt", 2)
            startActivity(intent)
            finish()
        }
    }

    private fun getBooksFromDatabase(filterGenre: String? = null, filterReadingLevel: String? = null): MutableList<Book>  {
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

        val selectionList = mutableListOf<String>()
        val selectionArgsList = mutableListOf<String>()

        if (filterGenre != null) {
            selectionList.add("${DatabaseContract.BookEntry.COLUMN_GENRE} = ?")
            selectionArgsList.add(filterGenre)
        }

        if (filterReadingLevel != null) {
            selectionList.add("${DatabaseContract.BookEntry.COLUMN_READING_LEVEL} = ?")
            selectionArgsList.add(filterReadingLevel)
        }

        val selection: String?
        val selectionArgs: Array<String>?

        if (selectionList.isNotEmpty()) {
            selection = selectionList.joinToString(" AND ")
            selectionArgs = selectionArgsList.toTypedArray()
            } else {
            selection = null
            selectionArgs = null
            }

        val cursor = db.query(
            DatabaseContract.BookEntry.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            "${DatabaseContract.BookEntry.COLUMN_ID} DESC", // Order by ID in descending order
            "1" // Limit the result to 1 row
        )

        val books = mutableListOf<Book>()

        with(cursor) {
            if (moveToFirst()) {
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
