package com.github.EmilyMartins.imageliteapi.application.images;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.util.stream.Collectors;

import com.github.EmilyMartins.imageliteapi.domain.entity.Image;
import com.github.EmilyMartins.imageliteapi.domain.enums.ImageExtension;
import com.github.EmilyMartins.imageliteapi.domain.service.ImageService;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/v1/images")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ImagesControllers {

    private final ImageService service;
    private final ImagesMapper mapper;
    
    @PostMapping("")
    public ResponseEntity save(@RequestParam("file") MultipartFile file, @RequestParam("name") String name, @RequestParam("tags") List<String> tags ) throws IOException{
        Image image = mapper.mapToImage(file, name, tags);
        Image savedImage = service.save(image);
        URI imageUri = buildImageURL(savedImage);

        return ResponseEntity.created(imageUri).build();
    }

    
    @GetMapping("{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable("id") String id) {
        var possibleImage = service.findById(id);
        if(possibleImage.isEmpty()){
            return ResponseEntity.notFound().build();
        }
         var image = possibleImage.get();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(image.getExtension().getMediaType());
        headers.setContentLength(image.getSize());

        headers.setContentDispositionFormData("inline; filename=\"" + image.getFileName() +  "\"", image.getFileName());

        return new ResponseEntity<>(image.getFile(), headers, HttpStatus.OK);

    }
    @GetMapping

    public ResponseEntity<List<ImageDTO>> search(
        @RequestParam(value = "extension", required = false, defaultValue = "") String extension,
        @RequestParam(value = "query", required = false, defaultValue = "") String query) {

            var result = service.search(ImageExtension.ofName(extension), query);

            var images = result.stream().map(image -> {
                var url = buildImageURL(image);
                return mapper.imageToDTO(image, url.toString());
            }).collect(Collectors.toList());
    
            return ResponseEntity.ok(images);
}
private URI buildImageURL(Image image){
    String imagePath = "/" + image.getId();
    return ServletUriComponentsBuilder
            .fromCurrentRequestUri()
            .path(imagePath)
            .build().toUri();
}
}
