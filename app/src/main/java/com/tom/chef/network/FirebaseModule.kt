package com.tom.chef.network

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.tom.chef.data.notifications.NotificationImp
import com.tom.chef.data.notifications.NotificationRepository
import com.tom.chef.utils.SharedPreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object FirebaseModule {


    @Provides
    @Singleton
    fun provideFirestoreInstance():FirebaseFirestore{
        return FirebaseFirestore.getInstance()
    }
    @Provides
    @Singleton
    fun provideRealTimeDataBase():FirebaseDatabase{
        return FirebaseDatabase.getInstance()
    }

    @Provides
    @Singleton
    fun provideNotifications(
        database: FirebaseDatabase,
        sharedPreferenceManager: SharedPreferenceManager
    ): NotificationRepository {
        return NotificationImp(database,sharedPreferenceManager)
    }

}