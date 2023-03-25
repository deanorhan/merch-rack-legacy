package org.daemio.merch.service;

import java.util.ArrayList;
import java.util.List;

import org.daemio.merch.domain.Merch;
import org.daemio.merch.domain.MerchStatus;
import org.daemio.merch.domain.Merch_;
import org.daemio.merch.error.MerchNotFoundException;
import org.daemio.merch.mapper.MerchMapper;
import org.daemio.merch.model.MerchPage;
import org.daemio.merch.repository.MerchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MerchService {

    @Autowired
    private MerchRepository repo;
    @Autowired
    private MerchMapper mapper;
    
    public List<Merch> getMerchList() {
        log.info("Getting a merch list from data");

        List<Merch> list = new ArrayList<>();

        repo.findAll().forEach(list::add);

        return list;
    }

    public MerchPage getMerchPage(Pageable pageable) {
        log.info("Getting a page of merch from data");

        var results = repo.findAll(pageable);

        return mapper.pageToResponse(results);
    }

    public MerchPage getMerchPage(Pageable pageable, List<MerchStatus> statusList) {
        log.info("Getting a page of merch from data");

        var results = repo.findAll((root, query, builder) -> {
            return root.get(Merch_.status).in(statusList);
        }, pageable);

        return mapper.pageToResponse(results);
    }

    public Merch getMerch(int merchId) {
        log.info("Getting a piece of merch from data");

        var merch = repo.findById(merchId);
        if (merch.isEmpty()) {
            throw new MerchNotFoundException();
        }

        return merch.get();
    }

    public Merch saveMerch(Merch merch) {
        merch.getImages().forEach(i -> i.setMerch(merch));

        return repo.save(merch);
    }
}
