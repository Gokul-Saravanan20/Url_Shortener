package com.sparnix.URL_SHORTENER.domain.services;

import com.sparnix.URL_SHORTENER.domain.entities.ShortUrl;
import com.sparnix.URL_SHORTENER.domain.entities.User;
import com.sparnix.URL_SHORTENER.domain.model.ShortUrlDto;
import com.sparnix.URL_SHORTENER.domain.model.UserDto;
import org.springframework.stereotype.Component;

@Component
public class EntityMapper {
    public ShortUrlDto toShortUrlDto(ShortUrl shortUrl){
        UserDto userDto = null;
        if(shortUrl.getCreatedBy() != null){
            userDto = toUserDto(shortUrl.getCreatedBy());
        }
        return new ShortUrlDto(
                shortUrl.getId(),
                shortUrl.getShortKey(),
                shortUrl.getOriginalUrl(),
                shortUrl.getIsPrivate(),
                shortUrl.getExpiresAt(),
                userDto,
                shortUrl.getClickCount(),
                shortUrl.getCreatedAt()
        );
    }

    public UserDto toUserDto(User user){
        return new UserDto(user.getId(), user.getName());
    }
}
