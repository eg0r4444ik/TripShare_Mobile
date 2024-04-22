package ru.vsu.tripshare_mobile.utils

import java.util.Date

class DataUtils {

    public fun dateToString(date: Date): String{
        var res = date.date.toString() + " "
        if(date.month == 0){
            res += "января"
        }
        if(date.month == 1){
            res += "февраля"
        }
        if(date.month == 2){
            res += "марта"
        }
        if(date.month == 3){
            res += "апреля"
        }
        if(date.month == 4){
            res += "мая"
        }
        if(date.month == 5){
            res += "июня"
        }
        if(date.month == 6){
            res += "июля"
        }
        if(date.month == 7){
            res += "августа"
        }
        if(date.month == 8){
            res += "сентября"
        }
        if(date.month == 9){
            res += "октября"
        }
        if(date.month == 10){
            res += "ноября"
        }
        if(date.month == 11){
            res += "декабря"
        }
        return res
    }

}