package com.tom.chef.utils

import com.tom.chef.app.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

fun <T> performGetOperation(networkCall: suspend () -> Resource<T>): Flow<Resource<T>> = flow {
    val responseStatus = networkCall.invoke()
    try {
        if (responseStatus.status == Resource.Status.SUCCESS) {
            emit(responseStatus)
        } else if (responseStatus.status == Resource.Status.ERROR) {
            emit(Resource.error(responseStatus.message.toString(),data = null, code = responseStatus.code))
        }
    } catch (e: Exception) {
        e.printStackTrace()
        emit(Resource.error(e.message.toString(),data = null,responseStatus.code))
    }

}.flowOn(Dispatchers.IO)


fun <T> getOnlySuccess(data:Resource<T>, tokenValidation: SingleLiveEvent<String>, success:(T)->Unit) {
    when(data.status){
        Resource.Status.SUCCESS->{
            data.data?.let {
                success.invoke(it)
            }
        }
        Resource.Status.ERROR->{
            data.code.let {
                if (it==0){
                    tokenValidation.value=data.message.toString()
                    return
                }
                tokenValidation.value=it.toString()
            }
        }
        else->{

        }
    }
}

