package com.tom.chef.network

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
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


}