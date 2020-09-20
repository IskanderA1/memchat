package com.iskander.memchat.remote.account

import com.iskander.memchat.data.account.AccountRemote
import com.iskander.memchat.domain.type.Either
import com.iskander.memchat.domain.type.None
import com.iskander.memchat.domain.type.exception.Failure
import com.iskander.memchat.remote.core.Request
import com.iskander.memchat.remote.service.ApiService

/**
 * Класс, содержащий функции взаимодействия с аккаунтом в сети.
 * Содержит: объект для создания сетевых запросов(val request),
 * API сервис(valservice),
 * функции для выполнения регистрации(fun register(…))
 * и map’а параметров запроса(fun createRegisterMap(…)).
 */
class AccountRemoteImpl
    (private val request: Request,
     private val service: ApiService
) : AccountRemote {


    override fun register(
        email: String,
        name: String,
        password: String,
        token: String,
        userData: Long):
            Either<Failure, None> {
        return request.make(service.register(createRegisterMap(email,name,password,token,userData))){None()}
    }

    private fun createRegisterMap(
        email: String,
        name: String,
        password: String,
        token: String,
        userData: Long)
            : Map<String, String>{
        val map = HashMap<String, String>()
        map.put(ApiService.PARAM_EMAIL, email)
        map.put(ApiService.PARAM_NAME, name)
        map.put(ApiService.PARAM_PASSWORD, password)
        map.put(ApiService.PARAM_TOKEN, token)
        map.put(ApiService.PARAM_USER_DATE, userData.toString())
        return map

    }
}