package com.gamaset.crbetportal.infra.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import com.gamaset.crbetportal.endpoint.PeriodFilter;
import com.gamaset.crbetportal.integration.betfair.aping.entities.TimeRange;

public class TimeRangeUtils {
	
	private static final ZoneId zoneId = ZoneId.of("America/Sao_Paulo");
	
	public static TimeRange getByPeriod(PeriodFilter period) {
		TimeRange timeRange = null;
		if (period.equals(PeriodFilter.TOMORROW)) {
			timeRange = TimeRangeUtils.getTomorrow();
		}else if(period.equals(PeriodFilter.NEXT_3_DAYS)) {
			timeRange = TimeRangeUtils.getTimeRangeDefault();
		}else {
			timeRange = TimeRangeUtils.getToday();
		}
		return timeRange;
	}
	
	public static TimeRange getTimeRangeDefault() {
		TimeRange timeRange = new TimeRange();
		timeRange.setFrom(Date.from(Instant.now().atZone(zoneId).toInstant()));
		timeRange.setTo(
				Date.from(LocalDate.now().plusDays(3).atStartOfDay(zoneId).toInstant().atZone(zoneId).toInstant()));
		return timeRange;
	}
	
	public static TimeRange getToday() {
		Instant from = Instant.now().atZone(zoneId).toInstant();
		Instant now20h = getLimitHourToday();
		
		TimeRange timeRange = new TimeRange();
		timeRange.setFrom(Date.from(from));
		
		if(from.isAfter(now20h)) {
			timeRange.setTo(Date.from(getFinalTomorrowInstant()));
		}else {
			timeRange.setTo(Date.from(getFinalTodayInstant()));
		}
		
		return timeRange;
	}

	public static TimeRange getTomorrow() {
		Instant from = Instant.now().atZone(zoneId).toInstant();
		
		TimeRange timeRange = new TimeRange();
		timeRange.setFrom(Date.from(from));
		timeRange.setTo(Date.from(getFinalTomorrowInstant()));
		
		return timeRange;
	}
	
	
	private static Instant getLimitHourToday() {
		return LocalDate.now().atTime(20, 00).atZone(zoneId).toInstant();
	}

	private static Instant getFinalTodayInstant() {
		return LocalDate.now().atTime(23, 59).atZone(zoneId).toInstant();
	}
	
	private static Instant getFinalTomorrowInstant() {
		return LocalDate.now().plusDays(1).atTime(20, 00).atZone(zoneId).toInstant();
	}

}
