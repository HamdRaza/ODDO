package com.tom.chef.data.notifications


import com.google.firebase.firestore.Exclude
import com.google.gson.annotations.SerializedName
import com.tom.chef.utils.handLocalString

class NotificationModel(){
    @SerializedName("time")
    val time: Any?=null
    @SerializedName("createdAt")
    val createdAt: String=""
    @SerializedName("createdDate")
    val createdDate: String=""
    @SerializedName("description")
    val description: String=""
    @SerializedName("imageURL")
    val imageURL: String=""
    @SerializedName("notificationType")
    val notificationType: String=""
    @SerializedName("type")
    val type: String=""
    @SerializedName("orderId")
    val orderId: Any?=null
    @SerializedName("order_Id")
    val order_Id: Any?=null
    @SerializedName("read")
    val read: String=""
    @SerializedName("seen")
    val seen: String=""
    @SerializedName("title")
    val title: String=""
    @Exclude
    var nodeKey:String=""

    fun localType():String{
        if (notificationType.isNotEmpty()){
            return notificationType
        }
        return type
    }
    fun localOrderId():String{
        if (order_Id.handLocalString().isNotEmpty()){
            return order_Id.handLocalString()
        }
        return orderId.handLocalString()
    }
}
