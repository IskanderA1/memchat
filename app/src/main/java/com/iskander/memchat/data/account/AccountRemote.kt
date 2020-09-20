package com.iskander.memchat.data.account

import com.iskander.memchat.domain.type.Either
import com.iskander.memchat.domain.type.None
import com.iskander.memchat.domain.type.exception.Failure

/**
 * Интерфейс, содержащий функции для взаимодействия с аккаунтом на сервере.
 *
 * Для взаимодействия Data с Remote.
 * При этом Data ничего не знает о Remote и его реализации, так как использует свой интерфейс.
 *
 * Благодаря этому не нарушается Dependency Rule.
 */
interface AccountRemote {
    fun register(
        email: String,
        name: String,
        password: String,
        token: String,
        userData: Long
    ): Either<Failure, None>
}