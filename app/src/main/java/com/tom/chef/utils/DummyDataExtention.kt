package com.tom.chef.utils

import android.app.Activity

fun String.orignalName():String{
    when(this){
        "PENDING"->{
            return "Pending"
        }
        "ACCEPTED"->{
            return "Accepted"
        }
        "REJECTED"->{
            return "Rejected"
        }
        "COMPLETED"->{
            return "Delivered"
        }
        else->{
            return this
        }
    }
}

fun Activity.getAllFilterTypes():List<String>{
    val list=ArrayList<String>()
    list.add("Rating High to Low")
    list.add("Rating Low to High")
    list.add("Price High to Low")
    list.add("Price Low to High")
    list.add("Nearest to Farthest")
    list.add("Farthest to Nearest")
    return list;
}


fun Activity.getProfileMenuList():List<String>{
    return listOf("Edit Profile","Change Password","Menu Setup","Order History","Financial","FAQ","Support","Select Language")
}

fun Activity.getDocumentMenu():List<DocumentItem>{
    val list=ArrayList<DocumentItem>()
    list.add(DocumentItem(hint = "Trade License (pdf/jpg only)", type = 1))
    list.add(DocumentItem(hint = "Emirates ID (pdf/jpg only)", type = 2))
    list.add(DocumentItem(hint = "Passport Copy (pdf/jpg only)", type = 3))
    list.add(DocumentItem(hint = "Residency Visa (pdf/jpg only)", type = 4))
    list.add(DocumentItem(hint = "Bank Account (pdf/jpg only)", type = 5))
    return list;
}

data class DocumentItem(val hint:String,val type:Int)

fun Activity.getAllTimes():List<TimeValues>{
    val list=ArrayList<TimeValues>()
    list.add(TimeValues(0,false,"08:00 AM","08:00:00"))
    list.add(TimeValues(1,false,"08:30 AM","08:30:00"))
    list.add(TimeValues(2,false,"09:00 AM","09:00:00"))
    list.add(TimeValues(3,false,"09:30 AM","09:30:00"))
    list.add(TimeValues(4,false,"10:00 AM","10:00:00"))
    list.add(TimeValues(5,false,"10:30 AM","10:30:00"))
    list.add(TimeValues(6,false,"11:00 AM","11:00:00"))
    list.add(TimeValues(7,false,"11:30 AM","11:30:00"))
    list.add(TimeValues(8,false,"12:00 PM","12:00:00"))
    list.add(TimeValues(9,false,"12:30 PM","12:30:00"))
    list.add(TimeValues(10,false,"1:00 PM","13:00:00"))
    list.add(TimeValues(11,false,"1:30 PM","13:30:00"))
    return list;
}

data class TimeValues(val position:Int,val isSelected:Boolean,val title:String,val in24Hours:String)