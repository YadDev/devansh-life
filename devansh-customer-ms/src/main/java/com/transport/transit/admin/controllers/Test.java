package com.transport.transit.admin.controllers;

import com.transport.transit.admin.util.Util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;

public class Test {
    public  static  void main(String[] args){
        String currentDate = String.valueOf(LocalDate.now().withDayOfMonth(1));
        YearMonth lastMonth    = YearMonth.now().minusMonths(1);
        System.out.print("Current Date"+ currentDate);
        DateTimeFormatter monthYearFormatter = DateTimeFormatter.ofPattern("MMM-yy");
        String billFor = lastMonth.format(monthYearFormatter);
        System.out.printf("Last Month: %s\n",billFor);
        Double d = 555.49;
        Integer integer = Math.toIntExact(Math.round(d));
        System.out.println(integer);
    }
}
