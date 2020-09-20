package com.iskander.memchat.cache

import com.iskander.memchat.data.account.AccountCache
import com.iskander.memchat.domain.type.Either
import com.iskander.memchat.domain.type.None
import com.iskander.memchat.domain.type.exception.Failure

/**
 * Класс, содержащий функции взаимодействия с аккаунтом в бд.
 * Содержит: объект для работы с SharedPrefsManager(val prefsManager),
 * функции которые выполняют сохранение(fun saveToken(…)) и
 * восстановление(fun getToken()) токена.
 */
class AccountCacheImpl(private val prefsManager: SharedPrefsManager)
    : AccountCache {
    override fun saveToken(token: String): Either<Failure, None> {
        return prefsManager.saveToken(token)
    }
    override fun getToken(): Either<Failure, String> {
        return prefsManager.getToken()
    }
}