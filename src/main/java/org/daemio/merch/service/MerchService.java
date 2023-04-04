package org.daemio.merch.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.daemio.merch.domain.Merch;
import org.daemio.merch.domain.MerchStatus;
import org.daemio.merch.domain.Merch_;
import org.daemio.merch.error.MerchNotFoundException;
import org.daemio.merch.mapper.MerchMapper;
import org.daemio.merch.model.MerchModel;
import org.daemio.merch.model.MerchPage;
import org.daemio.merch.repository.MerchRepository;

@Service
@Slf4j
public class MerchService {

  @Autowired private transient MerchRepository repo;
  @Autowired private transient MerchMapper mapper;

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

    var results =
        repo.findAll((root, query, builder) -> root.get(Merch_.status).in(statusList), pageable);

    return mapper.pageToResponse(results);
  }

  public MerchModel getMerch(String merchId) {
    return getMerch(UUID.fromString(merchId));
  }

  public MerchModel getMerch(UUID merchId) {
    log.info("Getting a piece of merch from data {}", merchId);

    var merch = repo.findById(merchId);
    if (merch.isEmpty()) {
      throw new MerchNotFoundException();
    }

    return mapper.entityToModel(merch.get());
  }

  public MerchModel saveMerch(MerchModel merchRequest) {
    var merch = mapper.modelToEntity(merchRequest);
    merch.setStatus(MerchStatus.LOADED);

    // merch.getImages().forEach(i -> i.setMerch(merch));

    return mapper.entityToModel(repo.save(merch));
  }
}
