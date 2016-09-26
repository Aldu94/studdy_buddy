package com.studbud.studbud.domain;

import java.text.DateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by Aldu on 01.09.16.
 */
public class ScheduleItem {

    private String title;
    private String startTime;
    private GregorianCalendar cal;
    private long id;


    public ScheduleItem(String title, int day, int month, int year) {
            this.title = title;
            cal = new GregorianCalendar(year, month, day);
        }


        public String getTitle() {
            return title;
        }

        public String getFormattedDate() {
            DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT,
                    Locale.GERMANY);
            String dateString = df.format(cal.getTime());

            return dateString;
        }

        public Date getDueDate() {
            return cal.getTime();
        }

        public void setID(long id) {
            this.id = id;
        }

        public long getID() {
            return id;
        }


        public int compareTo(ScheduleItem another) {
            return getDueDate().compareTo(another.getDueDate());

        }

    }
