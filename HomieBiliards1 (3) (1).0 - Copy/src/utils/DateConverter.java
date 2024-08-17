/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Mtt
 */
public class DateConverter {

    public static SimpleDateFormat getGlobalDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd");
    }

    public static String getDateNow() {
        SimpleDateFormat format = getGlobalDateFormat();

        return format.format(new Date());
    }

    public static String dateToString(Date date) {
        try {
            SimpleDateFormat format = getGlobalDateFormat();

            return format.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date stringToDate(String dateString) {
        try {
            SimpleDateFormat format = getGlobalDateFormat();

            return format.parse(dateString);
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
        System.err.println(DateConverter.getDateNow());
    }
}
