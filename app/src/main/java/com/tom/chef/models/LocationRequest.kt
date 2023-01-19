package com.tom.chef.models

//access_token:{{access_token}}
//building:Saeed tower ,30 th floor
//street:dubai investment park
//landmark:Near Ramla Hypermarket
//latitude:27.54535
//longitude:57.235345
//location:dubai investment park
//nick_name:Chef Pillai
//apartment_no:#310 ,3rd floor
data class LocationRequest(
    val access_token: String,
    val building: String,
    val street: String,
    val landmark: String,
    val latitude: String,
    val longitude: String,
    val location: String,
    val nick_name: String,
    val apartment_no: String,
)
