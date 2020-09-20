package com.iskander.memchat.cache

import android.content.SharedPreferences
import com.iskander.memchat.domain.type.Either
import com.iskander.memchat.domain.type.None
import com.iskander.memchat.domain.type.exception.Failure

class SharedPrefsManager (private val prefs: SharedPreferences){
    companion object{
        const val ACCOUNT_TOKEN ="account_token"
    }

    fun saveToken(token: String): Either<Failure, None>{
        prefs.edit().apply{
           putString(ACCOUNT_TOKEN, token)
        }.apply()
        return Either.Right(None())
    }

    fun getToken(): Either<Failure, String> {
        return Either.Right(prefs.getString(ACCOUNT_TOKEN, "").toString())
    }
}