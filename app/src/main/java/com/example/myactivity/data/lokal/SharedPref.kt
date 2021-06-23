package com.example.myactivity.data.lokal

import android.content.Context

class SharedPref(context: Context) {
    private val pref = context.getSharedPreferences("myData", Context.MODE_PRIVATE)
    private val ISLOGIN = "KEY_ISLOGIN"
    private val ID = "KEY_ID"
    private val EMAIL = "KEY_EMAIL"
    private val USERNAME = "KEY_USERNAME"
    private val PASSWORD = "KEY_PASSWORD"
    private val TOKEN = "KEY_TOKEN"

    var isLogin: Boolean?
        get() = pref?.getBoolean(ISLOGIN, false)
        set(value) {
            value?.let {
                pref?.edit()
                    ?.putBoolean(ISLOGIN, it)
                    ?.apply()
            }
        }

    var token: String?
        get() = pref?.getString(TOKEN, "")
        set(value) {
            value?.let {
                pref?.edit()
                    ?.putString(TOKEN, it)
                    ?.apply()
            }
        }

    var id: Int?
        get() = pref?.getInt(ID, 0)
        set(value) {
            value?.let {
                pref?.edit()
                    ?.putInt(ID, it)
                    ?.apply()
            }
        }

    var username: String?
        get() = pref?.getString(USERNAME, "")
        set(value) {
            value.let {
                pref?.edit()
                    ?.putString(USERNAME, it)
                    ?.apply()
            }
        }
    var email: String?
        get() = pref?.getString(EMAIL, "")
        set(value) {
            value.let {
                pref?.edit()
                    ?.putString(EMAIL, it)
                    ?.apply()
            }
        }

    var password: String?
        get() = pref?.getString(PASSWORD, "")
        set(value) {
            value.let {
                pref?.edit()
                    ?.putString(PASSWORD, it)
                    ?.apply()
            }
        }
}