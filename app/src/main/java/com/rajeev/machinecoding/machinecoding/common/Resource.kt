package com.rajeev.machinecoding.machinecoding.common

sealed class Resource<T>(val data: T ?= null, val message: String ?= null) {
    class Success<T>(data: T): Resource<T>(data)
    class Error<T>(errorMessage: String): Resource<T>(message =  errorMessage)
    class Loading<T>(): Resource<T>()
}
