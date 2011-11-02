package org.monitis.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * utility class for working with time(Calendar, Date and Timestamp)
 * 
 * @author lkhachat
 * @version 0.2, 31/07/06
 */
public final class TimeUtility {

	public static final int DAY_MONTH_YEAR_FORMAT = 0;

	public static final int YEAR_MONTH_DAY_FORMAT = 1;

	public static final int DAYOFWEEK_DAY_MONTH_YEAR_FORMAT = 2;

	private static final String DATE_SEPARATOR = ".";

	private static final String WEEK_DAY_SEPARAOR = " ";

	// name of week days
	private static final String[] dayOfWeeks = new String[] { "Sun", "Mon",
			"Tue", "Wed", "Thu", "Fri", "Sat" };

	// name of months
	private static final String[] months = new String[] { "January",
			"February", "March", "April", "May", "June", "July", "August",
			"September", "October", "November", "December" };

	/**
	 * return name of month
	 * 
	 * @param month-int
	 *            month number (1-January,... 12-December)
	 */
	public static String getMonthName(int month) {
		return months[month];
	}

	/**
	 * return name of day in week
	 * 
	 * @param day-int
	 *            day number (1-Sun, 2-Mon, ...7-Sat)
	 */
	public static String getDayOfWeekName(int day) {
		return dayOfWeeks[day - 1];
	}

	/**
	 * return days in weeks for this month
	 * 
	 * @param date -
	 *            Date date of month
	 * @param week -
	 *            week number in year
	 */
	public static String[] getWeekDaysInMonth(Date date) {
		// set calendar to the given month first day
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, 1);
		// create string array with length equals to the number of weeks in
		// given month
		String[] result = new String[c.getActualMaximum(Calendar.WEEK_OF_MONTH)];
		// current day, at first is first day of month
		int day = c.get(Calendar.DAY_OF_MONTH);
		// index of week
		int week = 0;
		// count of days of given month
		int dayCountInMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		boolean end = false;
		while (!end) {
			int weekday = c.get(Calendar.DAY_OF_WEEK);
			String res = null;
			// if is last day of month
			if (day == dayCountInMonth) {
				res = "" + day;
				end = true;
			} // if week is changed
			else if (weekday == 7) {
				res = "" + day;
				c.add(Calendar.DAY_OF_MONTH, 1);
			}

			else {
				res = c.get(Calendar.DAY_OF_MONTH) + "-";
				// case if isn't first day of week, so to the end of week are
				// left 7 - weekday days
				if (weekday != 1) {
					c.add(Calendar.DAY_OF_MONTH, 7 - weekday);
				} // if week doesn't shared between 2 months and is first day
				// of week so to the end of week are left 6 days
				else if (day + 6 < dayCountInMonth) {
					c.add(Calendar.DAY_OF_MONTH, 6);
				} else {
					// if last week is shared with next month
					c.set(Calendar.DAY_OF_MONTH, dayCountInMonth);
					end = true;
				}

				res += c.get(Calendar.DAY_OF_MONTH);
				c.add(Calendar.DAY_OF_MONTH, 1);
				day = c.get(Calendar.DAY_OF_MONTH);

			}
			result[week++] = res;
		}
		return result;
	}

	/**
	 * 
	 * @return now time by gmt
	 */
	public static Calendar getNowByGMT() {
		Calendar calendar = Calendar.getInstance();
		// if system time zone is 0 then server is on GMT, otherwise now -
		// systemTimeZoneOffset shows right GMT time
		Integer systemTimeZoneOffset = calendar.get(Calendar.ZONE_OFFSET)+
				+ calendar.get(Calendar.DST_OFFSET);

		calendar.add(Calendar.MILLISECOND, -systemTimeZoneOffset);
		return calendar;
	}

	/**
	 * by user time and user timezone offset get the same time by GMT
	 * 
	 * @param userTime
	 * @param usrTimezoneOffset
	 * @return
	 */
	public static Calendar getGMTTime(Date userTime, int usrTimezoneOffset) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(userTime.getTime());
		calendar.add(Calendar.MINUTE, -usrTimezoneOffset);
		return calendar;
	}

	/**
	 * by GMT time and user timezone offset get the same usertime
	 * 
	 * @param nowByGMT
	 * @param timezone
	 * @return
	 */
	public static Calendar getTime(Calendar nowByGMT, int timezone) {
		// nowByGMT + timezone shows now for locale with this timezone
		nowByGMT.add(Calendar.MINUTE, timezone);
		return nowByGMT;
	}

	public static Timestamp getTime(Timestamp nowByGMT, int timezone) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(nowByGMT.getTime());
		return new Timestamp(getTime(calendar, timezone).getTimeInMillis());
	}

	public static Calendar getTime(Date nowByGMT, int timezone) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(nowByGMT.getTime());
		return getTime(calendar, timezone);
	}

	/**
	 * get number of week in year
	 * 
	 * @param date
	 * @return
	 */
	public static int getWeekOfYear(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * get time string in given format
	 * 
	 * @param time
	 * @param format
	 * @return
	 */
	public static String toString(Calendar time, int format) {
		int day = time.get(Calendar.DAY_OF_MONTH);
		int month = time.get(Calendar.MONTH) + 1;
		int year = time.get(Calendar.YEAR);

		switch (format) {
		case DAY_MONTH_YEAR_FORMAT: {
			// day-month-year format
			return day + "-" + month + "-" + year;
		}
		case YEAR_MONTH_DAY_FORMAT: {
			// year-month-day format
			return year + "-" + month + "-" + day;
		}
		case DAYOFWEEK_DAY_MONTH_YEAR_FORMAT: {
			// dayOfWeek day.month.year format
			String dayOfWeek = dayOfWeeks[time.get(Calendar.DAY_OF_WEEK) - 1];

			return dayOfWeek + WEEK_DAY_SEPARAOR + day + DATE_SEPARATOR + month
					+ DATE_SEPARATOR + year;

		}

		}
		// java.util.Date standart format
		return time.getTime().toString();
	}

	/**
	 * get string represented current month string in format yearmonthNumber
	 * 
	 * @return current month represented string
	 */
	public static String getCurrentMonthByGMT() {
		Calendar nowByGMT = getNowByGMT();
		int year = nowByGMT.get(Calendar.YEAR);
		int month = nowByGMT.get(Calendar.MONTH) + 1;
			return year + (month < 10 ? "0" : "") + month;
	}

	/**
	 * get string represented previous month string in format yearmonthNumber
	 * 
	 * @return previous month represented string
	 */
	public static String getPreviousMonthByGMT() {
		Calendar nowByGMT = getNowByGMT();
		int year = nowByGMT.get(Calendar.YEAR);
		int month = nowByGMT.get(Calendar.MONTH);
		return year + (month < 10 ? "0" : "") + month;
	}
	
	/**
	 * 
	 * @param date
	 * @param pattern
	 * @return formated string
	 */
	public static String format(Date date, String pattern) {
		DateFormat myDateFormat = new SimpleDateFormat(pattern);
		try {
			return myDateFormat.format(date);
		} catch (Exception e) {
			return date.toString();
		}
	}

}
