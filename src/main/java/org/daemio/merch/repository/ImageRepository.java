package org.daemio.merch.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.daemio.merch.model.Image;

public interface ImageRepository
    extends JpaRepository<Image, UUID>, JpaSpecificationExecutor<Image> {}
