package kr.teammangers.dev.auth.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class OneTimeCodeService {

    private final RedisTemplate<String, String> redisTemplate;
    private static final long CODE_EXPIRATION_SECONDS = 180;

    public String generateAndStore(String accessToken) {
        String oneTimeCode = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(
                "otc:" + oneTimeCode,
                accessToken,
                CODE_EXPIRATION_SECONDS,
                TimeUnit.SECONDS
        );
        return oneTimeCode;
    }

    public String exchangeCodeForToken(String oneTimeCode) {
        String key = "otc:" + oneTimeCode;
        String accessToken = redisTemplate.opsForValue().get(key);

        if (accessToken == null) {
            throw new RuntimeException("유효하지 않거나 만료된 코드입니다."); // TODO: Custom Exception
        }

        redisTemplate.delete(key);

        return accessToken;
    }
}
