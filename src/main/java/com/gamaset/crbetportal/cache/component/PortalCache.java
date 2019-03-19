package com.gamaset.crbetportal.cache.component;


import static com.gamaset.crbetportal.infra.log.LogConstants.CACHE_SERIALIZATION;
import static com.gamaset.crbetportal.infra.log.LogConstants.CLASS_METHOD;
import static com.gamaset.crbetportal.infra.log.LogConstants.CLASS_NAME;
import static com.gamaset.crbetportal.infra.log.LogConstants.REDIS_DOWN;
import static com.gamaset.crbetportal.infra.log.LogEvent.create;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamaset.crbetportal.cache.health.CachePacemaker;
import com.gamaset.crbetportal.cache.key.PortalKeyGenerator;
import com.gamaset.crbetportal.endpoint.PeriodFilter;
import com.gamaset.crbetportal.infra.log.LogEvent;
import com.gamaset.crbetportal.schema.SidebarSchema;

/**
 * @since 1.0.0
 * @author Christopher Rozario  (ˇ෴ˇ)
 * CREATE, TEST, COMPILE AND RUN.
 * @date 2018-jan-09
 */
@Component
public class PortalCache {

	private static final Logger LOG_METRIC = LogEvent.logger("METRIC");

    public static final String NAME = "cache.portal";

    private final CachePacemaker cachePacemaker;
    private final Cache cache;
    private final String applicationEnvironmentPrefix;

    private ObjectMapper mapper;
	
    @Autowired
    public PortalCache(CacheManager cacheManager,
                        CachePacemaker cachePacemaker,
                        @Value("${spring.application.name}") String applicationName,
                        @Value("${spring.profiles}") String profile,
                        ObjectMapper mapper) {
        this.cachePacemaker = cachePacemaker;

        this.applicationEnvironmentPrefix = applicationName + ":" + profile;
        this.cache = cacheManager.getCache(this.applicationEnvironmentPrefix + ":" + NAME);

        this.mapper = mapper;
    }

    /**
     * SIDEBAR ACTIONS
     */
    public List<SidebarSchema> getSidebarItems(PeriodFilter period) {
    	String portalCache = "sidebar";
    	List<SidebarSchema> response = null;
    	try {
    		if(cachePacemaker.cacheIsOK()) {
    			String key = PortalKeyGenerator.makeThePortalKeyMojo(applicationEnvironmentPrefix, portalCache, period);
    			String value = cache.get(key, String.class);
    			
    			if (Objects.nonNull(value)) {
    				response = mapper.readValue(value, new TypeReference<List<SidebarSchema>>() {});
    			}
    		}
		} catch (RedisConnectionFailureException e) {
            LOG_METRIC.error(create(REDIS_DOWN)
                    .add(CLASS_NAME, PortalCache.class.getName()).add(CLASS_METHOD, "get")
                    .add("portalCache", portalCache)
                    .add("period", period)
                    .add(e).build());
            cachePacemaker.freezeCache();
		} catch (IOException e) {
			LOG_METRIC.error(create(CACHE_SERIALIZATION)
                    .add(CLASS_NAME, PortalCache.class.getName()).add(CLASS_METHOD, "get")
                    .add("portalCache", portalCache)
                    .add("period", period)
                    .add(e).build());
		}
    	
    	return response;
    }

    public boolean putSidebarItems(PeriodFilter period, List<SidebarSchema> items) {
    	boolean result = false;
    	String portalCache = "sidebar";
    	try {
    		if (cachePacemaker.cacheIsOK()) {
    			String key = PortalKeyGenerator.makeThePortalKeyMojo(applicationEnvironmentPrefix, portalCache, period);
    			String jsonMatch = mapper.writeValueAsString(items);
    			
    			if(Objects.nonNull(jsonMatch)) {
    				cache.put(key, jsonMatch);
    				result = true;
    			}
    		}
    	} catch (JsonProcessingException e) {
    		LOG_METRIC.error(create(CACHE_SERIALIZATION)
    				.add(CLASS_NAME, PortalCache.class.getName()).add(CLASS_METHOD, "put")
    				.add("portalCache", portalCache)
                    .add("period", period)
    				.add(e).build());
    	} catch (RedisConnectionFailureException e) {
    		LOG_METRIC.error(create(REDIS_DOWN)
    				.add(CLASS_NAME, PortalCache.class.getName()).add(CLASS_METHOD, "put")
    				.add("portalCache", portalCache)
                    .add("period", period)
    				.add(e).build());
    		cachePacemaker.freezeCache();
    	}
    	return result;
    }

}
