package com.iskander.memchat.domain.account

import android.provider.ContactsContract
import com.iskander.memchat.domain.iterator.UseCase
import com.iskander.memchat.domain.type.Either
import com.iskander.memchat.domain.type.None
import com.iskander.memchat.domain.type.exception.Failure
import javax.inject.Inject

class Register (private val repository: AccountRepository
) : UseCase<None, Register.Params>() {

    override suspend fun run(params: Register.Params): Either<Failure, None> {
        return repository.register(params.email,params.name, params.password)
    }

    data class Params(val email: String, val name:String, val password:String)
}
