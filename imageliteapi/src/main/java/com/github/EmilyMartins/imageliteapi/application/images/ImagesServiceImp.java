package com.github.EmilyMartins.imageliteapi.application.images;


import java.util.List;
import org.springframework.stereotype.Service;
import java.util.Optional;

import com.github.EmilyMartins.imageliteapi.domain.entity.Image;
import com.github.EmilyMartins.imageliteapi.domain.enums.ImageExtension;
import com.github.EmilyMartins.imageliteapi.domain.service.ImageService;
import com.github.EmilyMartins.imageliteapi.infra.repository.ImageRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImagesServiceImp implements ImageService{
    private final ImageRepository repository;

    @Override
    @Transactional
    public Image save(Image image) {
  
       return this.repository.save(image);
    }

    @Override
    public Optional<Image> findById(String id){
    return this.repository.findById(id);
    }

     @Override
    public List<Image> search(ImageExtension extension, String query) {
        return repository.findByExtensionAndNameOrTagsLike(extension, query);
    }
}
