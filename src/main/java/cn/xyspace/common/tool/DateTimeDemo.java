package cn.xyspace.common.tool;

import org.joda.time.DateTime;

public class DateTimeDemo {

    public static void main(String[] args) {

        System.out.println("====================================================");

        DateTime nowTime = DateTime.now();
        // System.out.println(nowTime.getYear());
        // System.out.println(nowTime.getMonthOfYear());
        // System.out.println(nowTime.getDayOfMonth());
        // System.out.println(nowTime.getYearOfCentury());
        // System.out.println(nowTime.getYearOfEra());
        //
        // System.out.println(nowTime.getDayOfWeek());
        // System.out.println(nowTime.getDayOfYear());
        // System.out.println(nowTime.getEra());
        // System.out.println(nowTime.getCenturyOfEra());
        // System.out.println(nowTime.getWeekOfWeekyear());
        // System.out.println(nowTime.getWeekyear());

        System.out.println("====================================================");

        // System.out.println(nowTime.getMillisOfDay());
        // System.out.println(nowTime.getMillis());
        //
        // DateTime todayStartTime = new DateTime(nowTime.getYear(), nowTime.getMonthOfYear(), nowTime.getDayOfMonth(), 0, 0);
        // System.out.println(todayStartTime.getMillis());
        // System.out.println(todayStartTime.plusDays(1).getMillis());

        System.out.println("====================================================");

        DateTime todayStartTime = new DateTime(nowTime.getYear(), nowTime.getMonthOfYear(), 1, 0, 0);
        System.out.println(todayStartTime.getMillis());
        System.out.println(new DateTime(todayStartTime.getMillis()).toString("yyyy-MM-dd HH:mm:ss"));
        System.out.println();

        for (int i = 1; i <= 12; i++) {
            DateTime d = todayStartTime.plusMonths(i);
            System.out.println(d.getMillis());
            // millis.add(d.getMillis());
            System.out.println(new DateTime(d.getMillis()).toString("yyyy-MM-dd HH:mm:ss"));
            System.out.println();
        }

        System.out.println("====================================================");

        /*
         * 1427817600000 1430409600000
         */

        System.out.println("====================================================");

        DateTime tStartTime = new DateTime(nowTime.getYear(), 4, 30, 10, 0);
        System.out.println(tStartTime.getMillis());
        System.out.println(new DateTime(tStartTime.getMillis()).toString("yyyy-MM-dd HH:mm:ss"));
        System.out.println();

        DateTime d = tStartTime.plusDays(1);
        System.out.println(d.getMillis());
        System.out.println(new DateTime(d.getMillis()).toString("yyyy-MM-dd HH:mm:ss"));
        System.out.println();

        DateTime d2 = d.plusDays(1);
        System.out.println(d2.getMillis());
        System.out.println(new DateTime(d2.getMillis()).toString("yyyy-MM-dd HH:mm:ss"));
        System.out.println();

        System.out.println("====================================================");

        DateTime testD = new DateTime(1428912070534L);
        System.out.println(testD.toString("yyyy-MM-dd HH:mm:ss:SSS"));

        DateTime testD2 = new DateTime(1428912070000L + 999L);
        System.out.println(testD2.toString("yyyy-MM-dd HH:mm:ss:SSS"));

        DateTime testD3 = new DateTime(2015, 4, 13, 16, 1, 10, 999);
        System.out.println(testD3.getMillis());

        System.out.println("====================================================");

        System.out.println(DateTime.now().getMillis());

        System.out.println("====================================================");

        DateTime testD4 = new DateTime(2015, 7, 13, 19, 0, 0, 999);
        System.out.println(testD4.getMillis());

        System.out.println("====================================================");

    }

}
