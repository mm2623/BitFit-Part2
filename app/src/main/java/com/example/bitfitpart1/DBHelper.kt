package com.example.bitfitpart1

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + ID + " INTEGER PRIMARY KEY, " +
                FOOD + " TEXT," +
                CALORIE + " INTEGER,"
                +")")

        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        // this method is to check if table already exists
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun populateDB(){
        addFood("Yellow Rice", "220")
        addFood("BBQ Chicken", "350")
        addFood("Beans & Rice", "400")
        addFood("Yogurt", "95")
        addFood("Oatmeal", "110")
        addFood("Chicken", "250")
        addFood("Grapes", "65")
        addFood("Cheesecake", "350")
        addFood("Lemonade", "155")
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun addFood(foodName : String, calorieCount : String){
        val values = ContentValues()
        val db = this.writableDatabase

        values.put(FOOD, foodName)
        values.put(CALORIE, calorieCount)
        Log.i("DB_VAL", values.toString())


        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getFood(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)

    }

    companion object{
        private val DATABASE_NAME = "Fit-db"
        private val DATABASE_VERSION = 1

        val TABLE_NAME = "fit_Table"

        val ID = "id"
        val FOOD = "food_name"
        val CALORIE = "food_calories"
    }
}