package com.rasyidin.hi_fi.utils

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.rasyidin.hi_fi.R
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

const val NORMAL_DATE_TIME_FORMAT = "dd MMMM yyyy"
const val DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd"
const val DEFAULT_TIME_FORMAT = "HH:mm:ss"

fun hideBotNav(context: Context) {
    val botNav = (context as AppCompatActivity).findViewById<BottomNavigationView>(R.id.bot_nav_view)
    val fabAdd = context.findViewById<FloatingActionButton>(R.id.fab_add)
    val fabBackground = context.findViewById<View>(R.id.fab_bg)
    botNav.visibility = View.GONE
    fabAdd.visibility = View.GONE
    fabBackground.visibility = View.GONE
}

fun showBotNav(context: Context) {
    val botNav = (context as AppCompatActivity).findViewById<BottomNavigationView>(R.id.bot_nav_view)
    val fabAdd = context.findViewById<FloatingActionButton>(R.id.fab_add)
    val fabBackground = context.findViewById<View>(R.id.fab_bg)
    botNav.visibility = View.VISIBLE
    fabAdd.visibility = View.VISIBLE
    fabBackground.visibility = View.VISIBLE
}

fun formatRupiah(number: Double): String {
    return try {
        val formatter: NumberFormat = DecimalFormat("#,###")
        formatter.format(number)
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}

fun String.clearFormatCurrency(): String {
    return try {
        return replace("Rp ", "").replace(",", "")
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}

fun formatRupiah(number: Long): String {
    return try {
        val formatter: NumberFormat = DecimalFormat("#,###")
        formatter.format(number)
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}

@SuppressLint("SimpleDateFormat")
fun String.toDateFormat(toFormat: String = "dd MMM yyy HH:mm"): String {
    return try {
        val idf = SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT, Locale("IND", "ID")).parse(this)
        SimpleDateFormat(toFormat).format(idf!!)
    } catch (e: Exception) {
        e.printStackTrace()
        Log.e("DateFormatter", e.message.toString())
        this
    }
}

fun getCurrentDate(pattern: String = NORMAL_DATE_TIME_FORMAT): String {
    return try {
        val sdf = SimpleDateFormat(pattern, Locale("IND", "ID"))
        sdf.format(Date())
    } catch (e: Exception) {
        "Date time not found"
    }
}

fun getCurrentTime(pattern: String = DEFAULT_TIME_FORMAT): String {
    return try {
        val sdf = SimpleDateFormat(pattern, Locale("IND", "ID"))
        sdf.format(Date().time)
    } catch (e: Exception) {
        "Time not found"
    }
}