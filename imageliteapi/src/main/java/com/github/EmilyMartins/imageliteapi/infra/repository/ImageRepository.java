package com.github.EmilyMartins.imageliteapi.infra.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import static org.springframework.data.jpa.domain.Specification.anyOf;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.util.StringUtils;
import static org.springframework.data.jpa.domain.Specification.where;
import com.github.EmilyMartins.imageliteapi.domain.entity.Image;
import com.github.EmilyMartins.imageliteapi.domain.enums.ImageExtension;
import com.github.EmilyMartins.imageliteapi.infra.repository.specs.GenericSpecs;
import com.github.EmilyMartins.imageliteapi.infra.repository.specs.ImageSpecs;
public interface ImageRepository extends JpaRepository<Image, String>, JpaSpecificationExecutor<Image>{
    default List<Image> findByExtensionAndNameOrTagsLike(ImageExtension extension, String query){
      Specification<Image> spec = where(GenericSpecs.conjunction());

        if(extension != null){
            spec = spec.and(ImageSpecs.extensionEqual(extension));
        }

        if(StringUtils.hasText(query)){
            spec = spec.and(anyOf(ImageSpecs.nameLike(query), ImageSpecs.tagsLike(query)));
        }

        return findAll(spec);
    }
}
