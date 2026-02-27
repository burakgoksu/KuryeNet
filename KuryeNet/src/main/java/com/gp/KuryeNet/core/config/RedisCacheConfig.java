package com.gp.KuryeNet.core.config;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
@ConditionalOnProperty(name = "spring.cache.type", havingValue = "redis")
public class RedisCacheConfig {

	@Bean
	public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
		RedisCacheConfiguration defaults = RedisCacheConfiguration.defaultCacheConfig()
				.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
				.disableCachingNullValues()
				.entryTtl(Duration.ofMinutes(5));

		Map<String, RedisCacheConfiguration> cacheConfigs = new HashMap<>();
		cacheConfigs.put("ordersById", defaults.entryTtl(Duration.ofMinutes(2)));
		cacheConfigs.put("ordersByNumber", defaults.entryTtl(Duration.ofMinutes(2)));
		cacheConfigs.put("ordersByStatus", defaults.entryTtl(Duration.ofMinutes(5)));
		cacheConfigs.put("ordersByType", defaults.entryTtl(Duration.ofMinutes(5)));
		cacheConfigs.put("ordersByDate", defaults.entryTtl(Duration.ofMinutes(5)));
		cacheConfigs.put("ordersByDateAndStatus", defaults.entryTtl(Duration.ofMinutes(5)));
		cacheConfigs.put("ordersByHour", defaults.entryTtl(Duration.ofMinutes(5)));
		cacheConfigs.put("ordersByHourAndStatus", defaults.entryTtl(Duration.ofMinutes(5)));
		cacheConfigs.put("ordersByAddressId", defaults.entryTtl(Duration.ofMinutes(5)));
		cacheConfigs.put("ordersWithCourier", defaults.entryTtl(Duration.ofMinutes(5)));
		cacheConfigs.put("ordersWithAddress", defaults.entryTtl(Duration.ofMinutes(5)));

		cacheConfigs.put("couriersAll", defaults.entryTtl(Duration.ofMinutes(5)));
		cacheConfigs.put("couriersSortedByName", defaults.entryTtl(Duration.ofMinutes(5)));
		cacheConfigs.put("couriersById", defaults.entryTtl(Duration.ofMinutes(5)));
		cacheConfigs.put("couriersByEmail", defaults.entryTtl(Duration.ofMinutes(5)));
		cacheConfigs.put("couriersByIdentity", defaults.entryTtl(Duration.ofMinutes(10)));
		cacheConfigs.put("couriersByCity", defaults.entryTtl(Duration.ofMinutes(10)));
		cacheConfigs.put("courierWithVehicle", defaults.entryTtl(Duration.ofMinutes(5)));
		cacheConfigs.put("courierWithOrder", defaults.entryTtl(Duration.ofMinutes(2)));

		cacheConfigs.put("customersAll", defaults.entryTtl(Duration.ofMinutes(10)));
		cacheConfigs.put("customersSortedByName", defaults.entryTtl(Duration.ofMinutes(10)));
		cacheConfigs.put("customersById", defaults.entryTtl(Duration.ofMinutes(10)));
		cacheConfigs.put("customersByEmail", defaults.entryTtl(Duration.ofMinutes(10)));
		cacheConfigs.put("customersByNameSurname", defaults.entryTtl(Duration.ofMinutes(10)));
		cacheConfigs.put("customersByAddressId", defaults.entryTtl(Duration.ofMinutes(10)));
		cacheConfigs.put("customersByCity", defaults.entryTtl(Duration.ofMinutes(10)));

		cacheConfigs.put("addressesById", defaults.entryTtl(Duration.ofMinutes(10)));
		cacheConfigs.put("addressesByTitle", defaults.entryTtl(Duration.ofMinutes(10)));
		cacheConfigs.put("addressesByPhone", defaults.entryTtl(Duration.ofMinutes(10)));
		cacheConfigs.put("addressesByCity", defaults.entryTtl(Duration.ofMinutes(10)));
		cacheConfigs.put("addressesSortedByCity", defaults.entryTtl(Duration.ofMinutes(10)));

		cacheConfigs.put("providersAll", defaults.entryTtl(Duration.ofMinutes(10)));
		cacheConfigs.put("providersSortedByName", defaults.entryTtl(Duration.ofMinutes(10)));
		cacheConfigs.put("providersByName", defaults.entryTtl(Duration.ofMinutes(10)));
		cacheConfigs.put("providersByType", defaults.entryTtl(Duration.ofMinutes(10)));
		cacheConfigs.put("providersByAddressId", defaults.entryTtl(Duration.ofMinutes(10)));
		cacheConfigs.put("providersByCity", defaults.entryTtl(Duration.ofMinutes(10)));
		cacheConfigs.put("providersByDistrict", defaults.entryTtl(Duration.ofMinutes(10)));
		cacheConfigs.put("providersByCityDistrict", defaults.entryTtl(Duration.ofMinutes(10)));
		cacheConfigs.put("providersById", defaults.entryTtl(Duration.ofMinutes(10)));
		cacheConfigs.put("providersByMersis", defaults.entryTtl(Duration.ofMinutes(10)));

		cacheConfigs.put("vehiclesAll", defaults.entryTtl(Duration.ofMinutes(10)));
		cacheConfigs.put("vehiclesSortedByYear", defaults.entryTtl(Duration.ofMinutes(10)));
		cacheConfigs.put("vehiclesSortedByBrand", defaults.entryTtl(Duration.ofMinutes(10)));
		cacheConfigs.put("vehiclesByPlate", defaults.entryTtl(Duration.ofMinutes(10)));
		cacheConfigs.put("vehiclesByType", defaults.entryTtl(Duration.ofMinutes(10)));
		cacheConfigs.put("vehiclesByBrand", defaults.entryTtl(Duration.ofMinutes(10)));
		cacheConfigs.put("vehiclesByModel", defaults.entryTtl(Duration.ofMinutes(10)));
		cacheConfigs.put("vehiclesByYear", defaults.entryTtl(Duration.ofMinutes(10)));
		cacheConfigs.put("vehiclesByCourierId", defaults.entryTtl(Duration.ofMinutes(10)));
		cacheConfigs.put("vehiclesByCourierIdentity", defaults.entryTtl(Duration.ofMinutes(10)));
		cacheConfigs.put("vehiclesByCourierNameSurname", defaults.entryTtl(Duration.ofMinutes(10)));
		cacheConfigs.put("vehiclesById", defaults.entryTtl(Duration.ofMinutes(10)));

		RedisCacheWriter cacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);
		return RedisCacheManager.builder(cacheWriter)
				.cacheDefaults(defaults)
				.withInitialCacheConfigurations(cacheConfigs)
				.build();
	}
}
