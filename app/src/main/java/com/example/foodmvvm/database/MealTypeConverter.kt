package com.example.foodmvvm.database

import androidx.room.TypeConverter
import androidx.room.TypeConverters

@TypeConverters
class MealTypeConverter {


//    Room will use this fun if it wants to insert something
    @TypeConverter
    fun fromAnyToString(attributes: Any?) : String{
        if (attributes == null)
            return ""
        return attributes as String
    }

//    Room will use this function is it wants to retrieve something from database
    @TypeConverter
    fun fromStringToAny(attributes: String?) : Any{
        if (attributes == null)
            return ""
        return attributes
    }
}