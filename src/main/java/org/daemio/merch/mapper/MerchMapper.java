package org.daemio.merch.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.data.domain.Page;

import org.daemio.merch.dto.MerchPage;
import org.daemio.merch.dto.MerchResource;
import org.daemio.merch.model.Merch;
import org.daemio.merch.util.DateUtils;

@Mapper(imports = {DateUtils.class})
public interface MerchMapper {

  @Mapping(target = "id", source = "merchId")
  @Mapping(target = "vendor", ignore = true)
  @Mapping(target = "images", ignore = true)
  @Mapping(target = "createdTime", ignore = true)
  @Mapping(target = "modifiedTime", ignore = true)
  Merch modelToEntity(MerchResource model);

  @InheritInverseConfiguration
  @Mapping(target = "merchId", source = "id")
  @Mapping(
      target = "createdTime",
      expression = "java(DateUtils.truncateToSeconds(entity.getCreatedTime()))")
  @Mapping(
      target = "modifiedTime",
      expression = "java(DateUtils.truncateToSeconds(entity.getCreatedTime()))")
  MerchResource entityToModel(Merch entity);

  @Mapping(target = "createdTime", ignore = true)
  @Mapping(target = "modifiedTime", ignore = true)
  void update(@MappingTarget Merch existingMerch, Merch merch);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void delta(@MappingTarget Merch existingMerch, Merch merch);

  default MerchPage pageToResponse(Page<Merch> page) {
    MerchPage response = new MerchPage();

    page.getContent().forEach(m -> response.addMerch(entityToModel(m)));

    response.setPage(page.getNumber());
    response.setSize(page.getSize());
    response.setTotalPages(page.getTotalPages());

    return response;
  }
}
