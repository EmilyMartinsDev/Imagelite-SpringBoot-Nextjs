package com.github.EmilyMartins.imageliteapi.application.images;

import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import com.github.EmilyMartins.imageliteapi.domain.entity.Image;
import com.github.EmilyMartins.imageliteapi.domain.enums.ImageExtension;

@Component
public class ImagesMapper {
    public Image mapToImage(MultipartFile file, String name, List<String> tags) throws IOException {
        return Image.builder()
                .name(name)
                .tags(String.join(",", tags))
                .size(file.getSize())
                .extension(ImageExtension.fromMediaType(MediaType.valueOf(file.getContentType())))
                .file(file.getBytes())
                .build();
    }

    public ImageDTO imageToDTO(Image image, String url){
        return ImageDTO.builder()
                .url(url)
                .extension(image.getExtension().name())
                .name(image.getName())
                .size(image.getSize())
                .uploadDate(image.getUploadDate().toLocalDate())
                .build();
    }
}
