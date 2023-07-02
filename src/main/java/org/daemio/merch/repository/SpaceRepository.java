package org.daemio.merch.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.daemio.merch.model.RackSpace;

public interface SpaceRepository
    extends JpaRepository<RackSpace, UUID>, JpaSpecificationExecutor<RackSpace> {}
