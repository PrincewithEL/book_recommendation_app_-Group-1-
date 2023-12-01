package com.example.book_recommendations_app_group1

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

enum class ActionType {
    DELETE,
    UPDATE
}

class BookAdapter(private val books: MutableList<Book>, private val onButtonClick: (Book, ActionType) -> Unit) :
    RecyclerView.Adapter<BookAdapter.BookViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]
        holder.bind(book)

    }

    override fun getItemCount(): Int {
        return books.size
    }

    fun removeBook(position: Int) {
        if (position >= 0 && position < books.size) {
            books.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, itemCount)
        }
    }

    inner class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageViewBook: ImageView = itemView.findViewById(R.id.imageViewBook)
        private val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        private val textViewAuthor: TextView = itemView.findViewById(R.id.textViewAuthor)
        private val textViewDescription: TextView = itemView.findViewById(R.id.textViewDescription)
        private val textViewGenre: TextView = itemView.findViewById(R.id.textViewGenre)
        private val textViewReadingLevel: TextView = itemView.findViewById(R.id.textViewReadingLevel)
        private val buttonDelete: Button = itemView.findViewById(R.id.buttonDelete)
        private val buttonUpdate: Button = itemView.findViewById(R.id.buttonUpdate)

        fun bind(book: Book) {
            //imageViewBook.setImageResource(book.imageResourceId)
            try {
                Glide.with(itemView.context)
                    .load(book.imageResourceId)
                    .placeholder(R.drawable.friends)
                    .into(imageViewBook)
            } catch (e: Exception) {
                Log.e("Glide", "Error loading image", e)
            }
            textViewName.text = book.name
            textViewAuthor.text = "By ${book.genre} & published by ${book.author}"
            textViewDescription.text = book.description
            textViewGenre.text = "Genre: ${book.genre}"
            textViewReadingLevel.text = "Reading Level: ${book.readingLevel}"

            buttonDelete.setOnClickListener {
                onButtonClick(book, ActionType.DELETE)
            }

            buttonUpdate.setOnClickListener {
                onButtonClick(book, ActionType.UPDATE)
            }
//            val img = textViewName.text
//            if(img.toString().equals("How To Win Friends And Influence People")) {
//                try {
//                    Glide.with(itemView.context)
//                        .load(book.imageResourceId)
//                        .placeholder(R.drawable.friends)
//                        .into(imageViewBook)
//                } catch (e: Exception) {
//                    Log.e("Glide", "Error loading image", e)
//                }
//            }else if(textViewName.text.equals("Holy Bible")) {
//                try {
//                    Glide.with(itemView.context)
//                        .load(book.imageResourceId)
//                        .placeholder(R.drawable.bible)
//                        .into(imageViewBook)
//                } catch (e: Exception) {
//                    Log.e("Glide", "Error loading image", e)
//                }
//            }else if(textViewName.text.equals("The Great Gatsby")) {
//                try {
//                    Glide.with(itemView.context)
//                        .load(book.imageResourceId)
//                        .placeholder(R.drawable.gatsby)
//                        .into(imageViewBook)
//                } catch (e: Exception) {
//                    Log.e("Glide", "Error loading image", e)
//                }
//            }

        }
    }
}