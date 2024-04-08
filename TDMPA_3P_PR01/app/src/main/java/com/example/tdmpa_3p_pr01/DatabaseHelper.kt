package com.example.tdmpa_3p_pr01

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.io.Serializable

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){
    companion object{
        const val DATABASE_NAME = "LoginDB"
        const val DATABASE_VERSION = 1
        const val  TABLE_NAME ="LoginTable"
        const val KEY_ID = "_id"
        const val KEY_NAME = "name"
        const val KEY_LASTNAME = "lastname"
        const val KEY_CAREER = "career"
        const val KEY_CREDITSPORTS = "creditsports"
        const val KEY_CREDITCULTURAL = "creditcultural"
        const val KEY_PASSWORD = "password"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE $TABLE_NAME ($KEY_ID INTEGER PRIMARY KEY, $KEY_NAME TEXT, $KEY_LASTNAME TEXT, $KEY_CAREER TEXT, $KEY_CREDITSPORTS INTEGER, $KEY_CREDITCULTURAL INTEGER,$KEY_PASSWORD TEXT);")
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addLogin(loginModel: LoginModel){
        val db = this.writableDatabase
        val values =  ContentValues()
        values.put(KEY_NAME, loginModel.name)
        values.put(KEY_LASTNAME, loginModel.lastName)
        values.put(KEY_CAREER, loginModel.career)
        values.put(KEY_CREDITSPORTS, loginModel.creditSports)
        values.put(KEY_CREDITCULTURAL, loginModel.creditCultural)
        values.put(KEY_PASSWORD, loginModel.password)
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    @SuppressLint("Range")
    fun getLoginByUsername(name: String): LoginModel? {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_NAME,
            arrayOf(KEY_ID, KEY_NAME, KEY_LASTNAME, KEY_CAREER, KEY_CREDITSPORTS, KEY_CREDITCULTURAL, KEY_PASSWORD),
            "$KEY_NAME=?",
            arrayOf(name),
            null,
            null,
            null
        )
        val loginModel: LoginModel? = try {
            if (cursor.moveToFirst()) {
                val id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                val lastName = cursor.getString(cursor.getColumnIndex(KEY_LASTNAME))
                val career = cursor.getString(cursor.getColumnIndex(KEY_CAREER))
                val creditSports = cursor.getInt(cursor.getColumnIndex(KEY_CREDITSPORTS))
                val creditCultural = cursor.getInt(cursor.getColumnIndex(KEY_CREDITCULTURAL))
                val password = cursor.getString(cursor.getColumnIndex(KEY_PASSWORD))
                LoginModel(id, name, lastName, career, creditSports, creditCultural, password)
            } else {
                null
            }
        } catch (e: Exception) {

            null
        } finally {
            cursor?.close()
            db.close()
        }
        return loginModel
    }


    fun updateLogin(loginModel: LoginModel){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_NAME, loginModel.name)
        values.put(KEY_LASTNAME, loginModel.lastName)
        values.put(KEY_CAREER, loginModel.career)
        values.put(KEY_CREDITSPORTS, loginModel.creditSports)
        values.put(KEY_CREDITCULTURAL, loginModel.creditCultural)
        values.put(KEY_PASSWORD, loginModel.password)
        db.update(TABLE_NAME, values, "$KEY_ID=?", arrayOf(loginModel.id.toString()))
    }
}

data class LoginModel(val id:Int, val name: String, val lastName: String, val career:String, val creditSports:Int, val creditCultural:Int,val password: String): Serializable