package com.tom.chef.utils

data class Resource<out T>(val status: Status, val data: T?, val message: String?, val code:Int) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T,code: Int): Resource<T> {
            return Resource(Status.SUCCESS, data, null, code = code)
        }

        fun <T> error(message: String, data: T? = null,code: Int): Resource<T> {
            return Resource(Status.ERROR, data, message, code = code)
        }

        fun <T> loading(data: T? = null,code: Int): Resource<T> {
            return Resource(Status.LOADING, data, null, code = code)
        }
    }
}