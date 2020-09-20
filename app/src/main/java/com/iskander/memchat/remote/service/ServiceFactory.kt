package com.iskander.memchat.remote.service

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Синглтон. Содержит: функции для создания сервиса(fun makeService(…)),
 * http клиента(fun makeOkHttpClient(…)) и
 * логгера(funmakeLoggingInterCeptor(…)).
 */

object ServiceFactory {
    const val BASE_URL = "https://memchat.xyz/rest_api/"

    fun makeService(isDebug: Boolean): ApiService {
        val okHttpClient = makeOkHttpClient(
            makeLoggingInterceptor((isDebug))
        )
        return makeService(okHttpClient, Gson())
    }

    /**
     * создает сервис Retrofit.
     * Функции в билдере: добавление ссылки(baseUrl(…)),
     * добавление http клиента(client(…)),
     * добавление конвертера для парса ответа сервера(addConverterFactory(…)).
     * Принимает: http клиент(OkHttpClient) и конвертер(Gson). В
     * озвращает ApiService.
     */
    private fun makeService(okHttpClient: OkHttpClient, gson: Gson): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(ApiService::class.java)
    }

    /**
     * создает http клиент.
     * Функции в билдере: добавление логгера(addInterceptor(…)),
     * установка таймаута на подключение(connectTimeot(…)),
     * установка таймаута на чтение(readTimeout(…)).
     * Принимает логгер(HttpLoggingInterceptor).
     * ВозвращаетOkHttpClien
     */
    private fun makeOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    private fun makeLoggingInterceptor(isDebug: Boolean): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (isDebug) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return logging
    }

}