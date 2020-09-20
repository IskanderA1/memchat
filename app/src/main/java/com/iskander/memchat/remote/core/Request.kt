package com.iskander.memchat.remote.core


import com.iskander.memchat.domain.type.Either
import com.iskander.memchat.domain.type.exception.Failure
import retrofit2.Call
import retrofit2.Response

/**
 * Класс, выполняющий сетевые запросы. Содержит:
 * объект для проверки подключения (val networkHandler),
 * функции для выполнения запроса(fun execute(…)) и
 * проверки ответа(extension fun Response.isSucceed()).
 */
class Request (private val netWorkHandler: NetWorkHandler){
    /**
     * вспомогательная функция для проверки сети и вызова fun execute(…).
     */
    fun <T : BaseResponse, R> make(call: Call<T>, transform: (T)->R):Either<Failure, R>{
        return when (netWorkHandler.isConnected){
            true -> execute(call, transform)
            false, null -> Either.Left(Failure.NetworkConnectionError)
        }
    }

    /**
     * выполняет сетевой запрос с помощью переданного в параметрах call (call.execute()).
     * В блоке catch формирует маркеры ошибок для дальнейшей обработки(Either.Left(Failure.ServerError)).
     * Функция имеет параметризированные типы: T(наследуемый от BaseResponse) и R.
     * Принимает Call и функцию высшего порядка для трансформации transform(принимает T, возвращает R).
     * Возвращает Either<Failure, R>.
     */
    private fun <T: BaseResponse, R> execute(call: Call<T>, transform: (T) -> R): Either<Failure, R>{
        return try {
            val response = call.execute()
            when(response.isSucceed()){
                true ->Either.Right(transform((response.body())!!))
                false -> Either.Left(response.parseError())
            }
        }catch (exception: Throwable){
            Either.Left(Failure.ServerError)
        }
    }
}

/**
 * extension ф-ция, которая проверяет ответ от сервера.
 * Ничего не принимает.
 * Возвращает Boolean
 */
fun <T : BaseResponse> Response<T>.isSucceed(): Boolean {
    return isSuccessful && body() != null && (body() as BaseResponse).success == 1
}

/**
 *  extension функция, которая парсит ответ сервера и
 *  проверяет наличие в нем конкретных ошибок.
 *  Если ответсодержит “email already exist”
 *  возвращает EmailAlreadyExist.
 *  В остальных случаях возвращает ServerError.
 *  Ничего не принимает.
 *  Возвращает Failure.
 */
fun <T: BaseResponse> Response<T>.parseError(): Failure{
    val message = (body() as BaseResponse).message
    return when(message){
        "email already exists" -> Failure.EmailAlreadyExistError
        else -> Failure.ServerError
    }
}