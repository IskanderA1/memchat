package com.iskander.memchat.data.account

import com.iskander.memchat.domain.account.AccountEntity
import com.iskander.memchat.domain.account.AccountRepository
import com.iskander.memchat.domain.type.Either
import com.iskander.memchat.domain.type.None
import com.iskander.memchat.domain.type.exception.Failure
import com.iskander.memchat.domain.type.flatMap
import java.lang.UnsupportedOperationException
import java.util.*

/**
 * Класс, содержащий функции взаимодействия с аккаунтом.
 * При этом решает откуда брать данные: из локальной базы или из сети.
 * Содержит:
 * объекты для работы с бд(val accountCache) и сервером(val accountRemote),
 * функции которые выполняют регистрацию пользователя(funregister(…))
 * и обновление токена(fun updateAcountToken(…)).
 */
class AccountRepositoryImpl
    (private val accountRemote: AccountRemote,
     private val  accountCache: AccountCache): AccountRepository{
    override fun login(email: String, password: String): Either<Failure, AccountEntity> {
        throw UnsupportedOperationException("Login is not supported")
    }

    override fun logout(): Either<Failure, None> {
        throw UnsupportedOperationException("Login is not supported")
    }

    override fun register(email: String, name: String, password: String): Either<Failure, None> {
        return accountCache.getToken().flatMap {
            accountRemote.register(email, name, password, it, Calendar.getInstance().timeInMillis)
        }
    }

    override fun forgetPassword(email: String): Either<Failure, None> {
        throw UnsupportedOperationException("Password recovery is not supported")
    }
    override fun getCurrentAccount(): Either<Failure, AccountEntity> {
        throw UnsupportedOperationException("Get account is not supported")
    }
    override fun updateAccountToken(token: String): Either<Failure, None> {
        return accountCache.saveToken(token)
    }
    override fun updateAccountLastSeen(): Either<Failure, None> {
        throw UnsupportedOperationException("Updating last seen is not supported")
    }
    override fun editAccount(entity: AccountEntity): Either<Failure, AccountEntity> {
        throw UnsupportedOperationException("Editing account is not supported")
    }

}