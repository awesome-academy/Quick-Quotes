package com.truongdc21.quickquotes.database

object ConstanceDb {

    const val DATABASE_NAME = "quotes.db"
    const val DATABASE_VERSION = 1

    const val TABLE_NAME_QUOTES = "Quotes"
    const val TABLE_NAME_AUTHOR = "Author"
    const val TABLE_NAME_TAG = "Tag"
    const val COLUMN_ID = "id"
    const val COLUMN_QUOTES = "quotes"
    const val COLUMN_AUTHOR = "author"
    const val COLUMN_TAG = "tag"

    const val QUERY_CREATE_QUOTES =  "CREATE TABLE " + TABLE_NAME_QUOTES +
            " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
            COLUMN_QUOTES + " TEXT, " +
            COLUMN_AUTHOR + " TEXT, " +
            COLUMN_TAG + " TEXT );"

    val QUERY_CREATE_AUTHOR =  "CREATE TABLE " + TABLE_NAME_AUTHOR +
            " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
            COLUMN_AUTHOR + " TEXT );"

    val QUERY_CREATE_TAG =  "CREATE TABLE " + TABLE_NAME_TAG +
            " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
            COLUMN_TAG + " TEXT );"
}
