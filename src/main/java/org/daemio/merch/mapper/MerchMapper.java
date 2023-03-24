package org.daemio.merch.mapper;

import org.daemio.merch.domain.Merch;
import org.daemio.merch.model.MerchPage;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper
public interface MerchMapper {
    
    default MerchPage pageToResponse(Page<Merch> page) {
        MerchPage response = new MerchPage();

        response.setMerch(page.getContent());
        response.setPage(page.getNumber());
        response.setSize(page.getSize());
        response.setTotalPages(page.getTotalPages());

        return response;
    }
}
