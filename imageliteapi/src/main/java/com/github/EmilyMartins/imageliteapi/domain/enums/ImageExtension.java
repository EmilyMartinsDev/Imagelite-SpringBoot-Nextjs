package com.github.EmilyMartins.imageliteapi.domain.enums;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.http.MediaType;

import lombok.Getter;

public enum ImageExtension {
    PNG(MediaType.IMAGE_PNG),
    JPEG(MediaType.IMAGE_JPEG),
    GIF(MediaType.IMAGE_GIF);
    @Getter
    protected MediaType mediaType;

    ImageExtension(MediaType mediaType){
        this.mediaType = mediaType;
    }
    public static ImageExtension fromMediaType(MediaType mediaType) {
        return Arrays.stream(values())
                     .filter(ie -> ie.mediaType.equals(mediaType))
                     .findFirst()
                     .orElse(null);
    }
    public static ImageExtension ofName(String extension){
        return Arrays.stream(values())
                .filter(ie -> ie.name().equalsIgnoreCase(extension))
                .findFirst()
                .orElse(null);
    }
}
