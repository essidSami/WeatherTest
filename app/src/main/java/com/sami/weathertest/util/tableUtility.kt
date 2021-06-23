package com.sami.weathertest.util

import android.app.Activity
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import com.sami.weathertest.R
import java.util.*

fun createTable(table: TableLayout, activity: Activity, random: Random): TableLayout{

    for (i in 0 until 5) {
        val row = TableRow(activity)
        for (j in 0 until 3) {
            if (i == 0 ){
                when(j){
                    0 -> {addRow(activity, activity.getString(R.string.txt_city_name))}
                    1 -> {addRow(activity, activity.getString(R.string.txt_temperature))}
                    2 -> {addRow(activity, activity.getString(R.string.txt_cloud))}
                }
            }else{
                addRow(activity, (random.nextInt(100) + 1).toString())
            }
        }

        table.addView(row)
    }
    return table
}

fun addRow(activity: Activity, value: String){
    val row = TableRow(activity)
    val tv = TextView(activity)
    tv.text = value.toString()
    row.addView(tv)
}