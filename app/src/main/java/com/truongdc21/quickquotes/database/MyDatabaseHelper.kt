package com.truongdc21.quickquotes.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDatabaseHelper(
    context: Context,
    dbName : String,
    dbVersion : Int,
    ): SQLiteOpenHelper(context , dbName , null , dbVersion ) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(ConstanceDb.QUERY_CREATE_QUOTES)
        db?.execSQL(ConstanceDb.QUERY_CREATE_AUTHOR)
        db?.execSQL(ConstanceDb.QUERY_CREATE_TAG)
        db?.execSQL(ConstanceDb.QUERY_CREATE_SEARCH)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL(" DROP TABLE IF EXISTS " + ConstanceDb.TABLE_NAME_QUOTES)
        db?.execSQL(" DROP TABLE IF EXISTS " + ConstanceDb.TABLE_NAME_AUTHOR)
        db?.execSQL(" DROP TABLE IF EXISTS " + ConstanceDb.TABLE_NAME_TAG)
        db?.execSQL(" DROP TABLE IF EXISTS " + ConstanceDb.TABLE_NAME_SEARCH)
        onCreate(db)
    }

    companion object{
        private var instance : MyDatabaseHelper? = null
        fun getInstance (context: Context) = synchronized(this){
            instance?:MyDatabaseHelper(context , ConstanceDb.DATABASE_NAME , ConstanceDb.DATABASE_VERSION).also { instance = it }
        }
    }
}
