package com.rasyidin.hi_fi.data.source.local.db.converter

import androidx.room.TypeConverter
import java.sql.Date

class DateConverters {

    @TypeConverter
    fun toDate(value: Long): Date {
        return Date(value)
    }

    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.time
    }
}