package org.daemio.merch.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.daemio.merch.model.Space;

public interface SpaceRepository
    extends JpaRepository<Space, UUID>, JpaSpecificationExecutor<Space> {}
