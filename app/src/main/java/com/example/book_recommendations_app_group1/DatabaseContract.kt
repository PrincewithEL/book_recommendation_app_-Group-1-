package com.example.book_recommendations_app_group1

import android.provider.BaseColumns

object DatabaseContract {

    object BookEntry : BaseColumns {
        const val TABLE_NAME = "books"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_IMAGE = "image"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_READING_LEVEL = "reading_level"
        const val COLUMN_PAGE_COUNT = "page_count"
        const val COLUMN_GENRE = "genre"
        const val COLUMN_YEAR = "year"
        const val COLUMN_AUTHOR = "author"
        const val COLUMN_PUBLISHING_COMPANY = "publishing_company"
    }
}
