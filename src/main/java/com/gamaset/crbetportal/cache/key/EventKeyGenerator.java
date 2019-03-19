package com.gamaset.crbetportal.cache.key;

import org.springframework.util.Assert;

import com.gamaset.crbetportal.endpoint.PeriodFilter;

/**
 * @since 1.0.0
 * @author Christopher Rozario  (ˇ෴ˇ)
 * CREATE, TEST, COMPILE AND RUN.
 * @date 2018-jan-09
 */
public class EventKeyGenerator extends CacheKeyGenerator {

	private static final String patternIduKey;
	
	static {
		patternIduKey = KeyMarkup.SYSTEM_ENVIRONMENT + KeyConstants.SEPARATOR 
				+ KeyMarkup.EVENT_TYPE_ID + KeyConstants.SEPARATOR + KeyMarkup.PERIOD;
		
	}
	
	public static String makeTheFavoriteEventMojo(String applicationEnvironmentPrefix, Long eventTypeId, PeriodFilter period) {
        Assert.notNull(applicationEnvironmentPrefix, "applicationEnvironmentPrefix must not be null!");
        Assert.hasText(applicationEnvironmentPrefix, "applicationEnvironmentPrefix must not be empty!");
        Assert.notNull(eventTypeId, "productId must not be null!");
        Assert.notNull(period, "period must not be null!");

        String withSystem = mojo(KeyMarkup.SYSTEM_ENVIRONMENT, applicationEnvironmentPrefix, patternIduKey);
        String withEventType = mojo(KeyMarkup.EVENT_TYPE_ID, eventTypeId.toString(), withSystem);
        return mojo(KeyMarkup.PERIOD, period.name(), withEventType);
    }
}
