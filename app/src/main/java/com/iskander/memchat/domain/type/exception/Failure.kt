package com.iskander.memchat.domain.type.exception

sealed class Failure {
    object NetworkConnectionError : Failure()
    object ServerError : Failure()

    object EmailAlreadyExistError : Failure()
}