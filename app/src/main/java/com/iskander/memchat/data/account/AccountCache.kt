package com.iskander.memchat.data.account

import com.iskander.memchat.domain.type.Either
import com.iskander.memchat.domain.type.None
import com.iskander.memchat.domain.type.exception.Failure

/**
 * Интерфейс, содержащий функции для взаимодействия с аккаунтом в локальной базе данных
 *
 * Для взаимодействия Data с Cache.
 * При этом Data ничего не знает о Cache и его реализации, так как использует свой интерфейс.
 * Благодаря этому не нарушается правило Dependency Rule.
 */
interface AccountCache{
    fun getToken(): Either<Failure, String>
    fun saveToken(token:String):Either<Failure, None>
}