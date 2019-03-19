package com.gamaset.crbetportal.cache.key;

import org.springframework.util.Assert;

import com.gamaset.crbetportal.endpoint.PeriodFilter;

/**
 * @since 1.0.0
 * @author Christopher Rozario  (ˇ෴ˇ)
 * CREATE, TEST, COMPILE AND RUN.
 * @date 2018-jan-09
 */
public class PortalKeyGenerator extends CacheKeyGenerator {

	private static final String patternKey;
	
	static {
		patternKey = KeyMarkup.SYSTEM_ENVIRONMENT + KeyConstants.SEPARATOR 
				+ KeyMarkup.PORTAL_PART_CACHE + KeyConstants.SEPARATOR + KeyMarkup.PERIOD;
		
	}
	
	public static String makeThePortalKeyMojo(String applicationEnvironmentPrefix, String portalPartCache, PeriodFilter period) {
        Assert.notNull(applicationEnvironmentPrefix, "applicationEnvironmentPrefix must not be null!");
        Assert.hasText(applicationEnvironmentPrefix, "applicationEnvironmentPrefix must not be empty!");
        Assert.notNull(portalPartCache, "portalPartCache must not be null!");
        Assert.notNull(period, "period must not be null!");

        String withSystem = mojo(KeyMarkup.SYSTEM_ENVIRONMENT, applicationEnvironmentPrefix, patternKey);
        String withEventType = mojo(KeyMarkup.PORTAL_PART_CACHE, portalPartCache, withSystem);
        return mojo(KeyMarkup.PERIOD, period.name(), withEventType);
    }
}
