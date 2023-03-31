package org.daemio.merch.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import org.daemio.merch.domain.Merch;
import org.daemio.merch.model.MerchModel;
import org.daemio.merch.model.MerchPage;
import org.daemio.merch.util.DateUtils;

@Mapper(imports = {DateUtils.class})
public interface MerchMapper {

    @Mapping(target = "id", source = "merchId")
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "images", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "modifiedTime", ignore = true)
    Merch modelToEntity(MerchModel model);

    @InheritInverseConfiguration
    @Mapping(target = "merchId", source = "id")
    @Mapping(target = "createdTime", expression = "java(DateUtils.truncateToSeconds(entity.getCreatedTime()))")
    @Mapping(target = "modifiedTime", expression = "java(DateUtils.truncateToSeconds(entity.getCreatedTime()))")
    MerchModel entityToModel(Merch entity);

    default MerchPage pageToResponse(Page<Merch> page) {
        MerchPage response = new MerchPage();

        page.getContent().forEach(m -> response.addMerch(entityToModel(m)));

        response.setPage(page.getNumber());
        response.setSize(page.getSize());
        response.setTotalPages(page.getTotalPages());

        return response;
    }
}
