package com.example.foodmvvm.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.foodmvvm.pojo.Meal

@Database(entities = [Meal::class], version = 2)
@TypeConverters(MealTypeConverter::class)

abstract class MealDatabase : RoomDatabase (){

    abstract fun mealdao() : MealDao

    companion object{
        @Volatile
        var INSTANCE : MealDatabase? = null

        @Synchronized
        fun getInstance(context: Context) : MealDatabase{
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    MealDatabase::class.java,
                    "meal.db"
                ).fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as MealDatabase
        }
    }


}