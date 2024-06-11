package de.phillipw.moebelhawi.kotlin.data.util

import androidx.room.TypeConverter

class UrlListConverter {
    @TypeConverter
    fun toListOfStrings(flatStringList: String): List<String> {
        return flatStringList.split(",")
        //Konvertiert das JSON Format in eine Liste von Listen von Strings
    }
    @TypeConverter
    fun fromListOfStrings(listOfString: List<String>): String {
        return listOfString.joinToString(",")
        //Konvertiert die Liste von Listen von Strings in JSOn-Format
    }
}