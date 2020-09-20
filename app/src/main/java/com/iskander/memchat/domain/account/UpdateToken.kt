package com.iskander.memchat.domain.account

import com.iskander.memchat.domain.iterator.UseCase
import com.iskander.memchat.domain.type.Either
import com.iskander.memchat.domain.type.None
import com.iskander.memchat.domain.type.exception.Failure


class UpdateToken
     (private val accountRepository: AccountRepository):
     UseCase<None, UpdateToken.Params>() {

    override suspend fun run(params: Params) =
        accountRepository.updateAccountToken(params.token)

    data class Params(val token: String)
}