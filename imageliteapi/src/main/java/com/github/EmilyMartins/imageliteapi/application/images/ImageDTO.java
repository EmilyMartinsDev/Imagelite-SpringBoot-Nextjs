package com.github.EmilyMartins.imageliteapi.application.images;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
@Data
@Builder
public class ImageDTO {
    private String url;
    private String name;
    private String extension;
    private Long size;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate uploadDate;
}