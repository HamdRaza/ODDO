package com.tom.chef.data.notifications

import android.util.Log
import com.google.firebase.database.*
import com.tom.chef.utils.Constants
import com.tom.chef.utils.SharedPreferenceManager
import com.tom.chef.utils.UiState


class NotificationImp(val  firestore: FirebaseDatabase,val sharedPreferenceManager: SharedPreferenceManager): NotificationRepository {


    fun getAllImagesBase():DatabaseReference{
        /*
        sharedPreferenceManager.getSavedUser()?.firebaseUserKey?.let {
            return firestore.getReference("${Constants.NotificationNode}/${it}")
        } */
        return firestore.getReference("${Constants.NotificationNode}/hamid")
    }

    override fun getAllNotifications(loadStart: Boolean, result: (UiState<List<NotificationModel>>) -> Unit){

        result.invoke(UiState.Loading)
        getAllImagesBase().addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.let { snapAll->
                    Log.i("notifications",snapAll.toString())
                    val allNotifications=ArrayList<NotificationModel>()
                    snapAll.children.forEach { snapSingle->
                        snapSingle.getValue(NotificationModel::class.java)?.let {
                            it.nodeKey=snapSingle.key.toString()
                            allNotifications.add(it)
                        }
                    }
                    result.invoke(UiState.Success(allNotifications.reversed()))
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }


    override fun deleteANotification(nodeName: String) {
        getAllImagesBase().child(nodeName).setValue(null)
    }

    override fun markAsReadNotification(nodeName: String) {
        getAllImagesBase().child("${nodeName}/seen").setValue("1")
    }

    override fun deleteAll() {
        getAllImagesBase().setValue(null)
    }






}