package org.daemio.merch.repository;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import org.daemio.merch.domain.Merch;
import org.daemio.merch.domain.MerchStatus;

public interface MerchRepository
    extends JpaRepository<Merch, Integer>, JpaSpecificationExecutor<Merch> {

  @Query("select m from Merch m where m.status in (?1)")
  Page<Merch> findAllWithStatus(Collection<MerchStatus> statuses, Pageable pageable);
}
