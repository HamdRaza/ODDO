package com.tom.chef.models

import okhttp3.MultipartBody
import okhttp3.RequestBody

//access_token:{{access_token}}
//first_name:Kiyara
//last_name:Lakshmi
//address:Mall of the Emirates
//latitude:25.1181
//longitude:55.2006
//about_me:Chef responsibilities include studying recipes, setting up menus and preparing high-quality dishes. You should be able to delegate tasks to kitchen staff to ensure meals are prepared in a timely manner. Also, you should be familiar with sanitation regulations. If you have experience with advanced cooking techniques and non-traditional ingredients, we’d like to meet you.
//brand_name:BinshaMb
//preparation_unit:20
//preparation_time:mins
//cuisines[]:3
//order_limit_per_hour:10
//weekly_mode:1
//allow_ordertype:1
//start_time:16:00
//end_time:20:00
data class ProfileRequest(
    val access_token: RequestBody,
    val first_name: RequestBody,
    val last_name: RequestBody,
    val address: RequestBody,
    val latitude: RequestBody,
    val longitude: RequestBody,
    val about_me: RequestBody,
    val brand_name: RequestBody,
    val image: MultipartBody.Part? = null,
    val cover_image: MultipartBody.Part? = null,
    val preparation_unit: RequestBody,
    val preparation_time: RequestBody,
    val cuisines: ArrayList<String>,
    val order_limit_per_hour: RequestBody,
    val weekly_mode: RequestBody,
    val allow_ordertype: RequestBody,
    val start_time: RequestBody,
    val end_time: RequestBody
)
