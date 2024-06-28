package com.karatesan.WebAppApi.utility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.karatesan.WebAppApi.ulilityClassess.token;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CacheManager {

    private final RedisTemplate<String,Object> redisTemplate;
    private final ObjectMapper objectMapper;

    public void save(@NonNull final token token, @NonNull final Object value){
        Duration expirationTime = Duration.ofMinutes(token.validityDuration());

        redisTemplate.opsForValue().set(token.token(),value,expirationTime);
        log.info("Cached value with key '{}' for {} seconds", token.token(), expirationTime);
    }

    public void save(@NonNull final String key, @NonNull final Duration timeToLive) {
        redisTemplate.opsForValue().set(key, "", timeToLive);
        log.info("Cached non value key '{}' for {} seconds", key, timeToLive.toSeconds());
    }

    public void delete(@NonNull final String key){
        redisTemplate.delete(key);
    }

    public Boolean isPresent(@NonNull final String key) {
        final var fetchedValue = redisTemplate.opsForValue().get(key);
        return Optional.ofNullable(fetchedValue).isPresent();
    }

    public <T> Optional<T> fetch(@NonNull final String key, @NonNull final Class<T> targetClass){
        final var value = Optional.ofNullable(redisTemplate.opsForValue().get(key));
        if(value.isEmpty()){
            log.info("No cached value found for key '{}'", key);
            return Optional.empty();
        }
        T result = objectMapper.convertValue(value.get(), targetClass);
        log.info("Fetched cached value with key '{}'", key);
        return Optional.of(result);
    }
}
