package com.example.book_recommendations_app_group1

import DatabaseHelper
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.get
import android.Manifest.permission.READ_EXTERNAL_STORAGE

class add_book : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var imageViewBook: ImageView
    private lateinit var spinnerLevel: Spinner
    private lateinit var selectedImageUri: Uri
    private lateinit var textV: TextView
    private lateinit var buttonV: Button
    private val PICK_IMAGE_REQUEST_CODE = 1
    private val REQUEST_STORAGE_PERMISSION = 123

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_book)

        spinnerLevel = findViewById(R.id.editTextReadingLevel)

        val levels = arrayOf(
            "Kindly Select A Reading Level",
            "Board Books (0-3 years)",
            "Picture Books (3-8 years)",
            "Early Readers (6-9 years)",
            "Middle Grade (8-12 years)",
            "Young Adult (YA) (12-18 years)",
            "Adult Fiction and Non-Fiction (18+ years)",
            "All Ages"
        )

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, levels)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerLevel.adapter = adapter

        spinnerLevel.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedLevel = adapter.getItem(position).toString()

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        })

        dbHelper = DatabaseHelper(this)
        imageViewBook = findViewById(R.id.imageViewBook)

        var mbook:Int = intent.getIntExtra("managebooks", 0)
       if(mbook == 3) {
           textV = findViewById(R.id.textView)
           textV.setText("Add A Book")
           buttonV = findViewById(R.id.buttonSave)
           buttonV.setText("Save")

           val buttonPickImage: Button = findViewById(R.id.buttonPickImage)
           buttonPickImage.setOnClickListener {
               openImagePicker()
           }

           buttonV.setOnClickListener {

               if( findViewById<EditText>(R.id.editTextBookName).text.isEmpty() ||
                   findViewById<EditText>(R.id.editTextAuthor).text.isEmpty() ||
                   findViewById<EditText>(R.id.editTextDescription).text.isEmpty() ||
                   findViewById<EditText>(R.id.editTextGenre).text.isEmpty() ||
                   findViewById<EditText>(R.id.editTextPageCount).text.isEmpty() ||
                   findViewById<EditText>(R.id.editTextPublishingCompany).text.isEmpty() ||
                   findViewById<EditText>(R.id.editTextDate).text.isEmpty()){
                   Toast.makeText(this, "Ensure all fields are filled!", Toast.LENGTH_SHORT).show()
               }else{
                   requestPermissions()
                   saveBook()
               }
           }

           val buttonClear: Button = findViewById(R.id.buttonClear)
           buttonClear.setOnClickListener {
               findViewById<EditText>(R.id.editTextBookName).text.clear()
               findViewById<EditText>(R.id.editTextAuthor).text.clear()
               findViewById<EditText>(R.id.editTextDescription).text.clear()
               findViewById<EditText>(R.id.editTextGenre).text.clear()
               findViewById<EditText>(R.id.editTextPageCount).text.clear()
               findViewById<EditText>(R.id.editTextPublishingCompany).text.clear()
               findViewById<EditText>(R.id.editTextDate).text.clear()
               findViewById<Spinner>(R.id.editTextReadingLevel).setSelection(0)
           }

           val buttonBack: Button = findViewById(R.id.buttonSave3)
           buttonBack.setOnClickListener {
               val intent = Intent(this, administrator_module::class.java)
               intent.putExtra("logInt", 1)
               startActivity(intent)
               finish()
           }

       }else if(mbook == 4){
           textV = findViewById(R.id.textView)
           textV.setText("Update A Book")
           buttonV = findViewById(R.id.buttonSave)
           buttonV.setText("Update")

           val buttonPickImage: Button = findViewById(R.id.buttonPickImage)
           buttonPickImage.setOnClickListener {
               openImagePicker()
           }

           //buttonPickImage.visibility = View.INVISIBLE

           val buttonUpdate: Button = findViewById(R.id.buttonSave)
           buttonUpdate.setOnClickListener {
               val intent = Intent(this, view_books::class.java)
               val id: Long = intent.getLongExtra("bID", -1)
               updateBook(id)
           }

           val buttonClear: Button = findViewById(R.id.buttonSave)
           buttonClear.setOnClickListener {
               findViewById<EditText>(R.id.editTextBookName).text.clear()
               findViewById<EditText>(R.id.editTextAuthor).text.clear()
               findViewById<EditText>(R.id.editTextDescription).text.clear()
               findViewById<EditText>(R.id.editTextGenre).text.clear()
               findViewById<EditText>(R.id.editTextPageCount).text.clear()
               findViewById<EditText>(R.id.editTextPublishingCompany).text.clear()
               findViewById<EditText>(R.id.editTextDate).text.clear()
               findViewById<Spinner>(R.id.editTextReadingLevel).setSelection(0)
           }

           val buttonBack: Button = findViewById(R.id.buttonSave3)
           buttonBack.setOnClickListener {
               val intent = Intent(this, administrator_module::class.java)
               intent.putExtra("logInt", 1)
               startActivity(intent)
               finish()
           }

       }
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.data!!
            imageViewBook.setImageURI(selectedImageUri)
        }
    }

    private fun saveBook() {
        val db: SQLiteDatabase = dbHelper.writableDatabase

        val bookName: String = findViewById<EditText>(R.id.editTextBookName).text.toString()
        val description: String = findViewById<EditText>(R.id.editTextDescription).text.toString()
        val selectedGenre: String = findViewById<EditText>(R.id.editTextGenre).text.toString()
        val pageCount: String = findViewById<EditText>(R.id.editTextPageCount).text.toString()
        val year: String = findViewById<EditText>(R.id.editTextDate).text.toString()
        val author: String = findViewById<EditText>(R.id.editTextAuthor).text.toString()
        val publishingCompany: String = findViewById<EditText>(R.id.editTextPublishingCompany).text.toString()

        val selectedLevel: String = findViewById<Spinner>(R.id.editTextReadingLevel).selectedItem.toString()

        val imagePath: String? = selectedImageUri.toString()

        val values = ContentValues().apply {
            put(DatabaseContract.BookEntry.COLUMN_NAME, bookName)
            put(DatabaseContract.BookEntry.COLUMN_IMAGE, imagePath)
            put(DatabaseContract.BookEntry.COLUMN_DESCRIPTION, description)
            put(DatabaseContract.BookEntry.COLUMN_READING_LEVEL, selectedLevel)
            put(DatabaseContract.BookEntry.COLUMN_PAGE_COUNT, pageCount)
            put(DatabaseContract.BookEntry.COLUMN_YEAR, year)
            put(DatabaseContract.BookEntry.COLUMN_AUTHOR, author)
            put(DatabaseContract.BookEntry.COLUMN_PUBLISHING_COMPANY, publishingCompany)
            put(DatabaseContract.BookEntry.COLUMN_GENRE, selectedGenre)
        }

        val newRowId = db.insert(DatabaseContract.BookEntry.TABLE_NAME, null, values)

        dbHelper.close()

        if (newRowId != -1L) {

            Toast.makeText(this, "Book saved successfully!", Toast.LENGTH_SHORT).show()
            finish()
            val intent = Intent(this, administrator_module::class.java)
            intent.putExtra("logInt", 1)
            startActivity(intent)

        } else {

            Toast.makeText(this, "Error saving book. Please try again.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateBook(bookId: Long) {
        val db: SQLiteDatabase = dbHelper.writableDatabase

        val bookName: String = findViewById<EditText>(R.id.editTextBookName).text.toString()
        val description: String = findViewById<EditText>(R.id.editTextDescription).text.toString()
        val selectedGenre: String = findViewById<EditText>(R.id.editTextGenre).text.toString()
        val pageCount: String = findViewById<EditText>(R.id.editTextPageCount).text.toString()
        val year: String = findViewById<EditText>(R.id.editTextDate).text.toString()
        val author: String = findViewById<EditText>(R.id.editTextAuthor).text.toString()
        val publishingCompany: String = findViewById<EditText>(R.id.editTextPublishingCompany).text.toString()
        val selectedLevel: String = findViewById<Spinner>(R.id.editTextReadingLevel).selectedItem.toString()

        val imagePath: String? = selectedImageUri.toString()

        val values = ContentValues().apply {
            put(DatabaseContract.BookEntry.COLUMN_NAME, bookName)
            put(DatabaseContract.BookEntry.COLUMN_IMAGE, imagePath)
            put(DatabaseContract.BookEntry.COLUMN_DESCRIPTION, description)
            put(DatabaseContract.BookEntry.COLUMN_READING_LEVEL, selectedLevel)
            put(DatabaseContract.BookEntry.COLUMN_PAGE_COUNT, pageCount)
            put(DatabaseContract.BookEntry.COLUMN_YEAR, year)
            put(DatabaseContract.BookEntry.COLUMN_AUTHOR, author)
            put(DatabaseContract.BookEntry.COLUMN_PUBLISHING_COMPANY, publishingCompany)
            put(DatabaseContract.BookEntry.COLUMN_GENRE, selectedGenre)
        }

        val selection = "${DatabaseContract.BookEntry.COLUMN_ID} = ?"
        val selectionArgs = arrayOf(bookId.toString())

        val rowsUpdated = db.update(
            DatabaseContract.BookEntry.TABLE_NAME,
            values,
            selection,
            selectionArgs
        )

        dbHelper.close()

        if (rowsUpdated > 0) {

            Toast.makeText(this, "Book updated successfully!", Toast.LENGTH_SHORT).show()
            finish()
            val intent = Intent(this, view_books::class.java)
            startActivity(intent)
        } else {

            Toast.makeText(this, "Error updating book. Please check the ID and try again.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun requestPermissions() {
        val permission = android.Manifest.permission.READ_EXTERNAL_STORAGE
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(permission), REQUEST_STORAGE_PERMISSION)
        } else {

        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_STORAGE_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                }
            }
        }
    }
    
}