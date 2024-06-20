package com.github.EmilyMartins.imageliteapi.domain.service;

import java.util.Optional;

import java.util.List;

import com.github.EmilyMartins.imageliteapi.domain.entity.Image;
import com.github.EmilyMartins.imageliteapi.domain.enums.ImageExtension;

public interface ImageService {
    Image  save(Image image);
    Optional<Image> findById(String id);
    List<Image> search(ImageExtension extension, String query);
}
