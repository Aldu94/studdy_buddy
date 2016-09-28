package com.studbud.studbud.domain;

import java.text.DateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/*
 * The scheduleItem class that is accessible from the mainActivity. It will create a list of
 * Dates which can be used as a planner for important dates. This class is taken from
 * the android course exercises no. 5
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
