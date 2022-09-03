package com.nepplus.gooduck.utils

import android.content.Context
import android.content.SharedPreferences
import org.json.JSONArray
import org.json.JSONException


class ContextUtil {

    companion object{
        private val prefName = "GooDuck"
        private val LOGIN_TOKEN = "LOGIN_TOKEN"
        private val AUTO_LOGIN = "AUTO_LOGIN"
        private val RECENT_SEARCH = "RECENT_SEARCH"

        fun setLoginToken (context : Context, token : String){
            val pref =  context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            pref.edit().putString(LOGIN_TOKEN, token).apply()
        }

        fun getLoginToken (context: Context) : String {
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            return pref.getString(LOGIN_TOKEN, "")!!
        }

        fun setAutoLogin(context: Context, autoLogin : Boolean){
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            pref.edit().putBoolean(AUTO_LOGIN, autoLogin).apply()
        }

        fun getAutoLogin(context: Context) : Boolean{
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            return pref.getBoolean(AUTO_LOGIN, false)
        }



        fun clear(context: Context){
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            pref.edit().clear().apply()
        }

        fun clearTag(context: Context, tag : String){
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            val editor = pref.edit()
            editor.remove(tag)
            editor.apply()
        }


    }
}