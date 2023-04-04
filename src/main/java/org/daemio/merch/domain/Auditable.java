package org.daemio.merch.domain;

import java.time.Instant;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable {

  @Column(name = "created_at", nullable = false, updatable = false)
  @CreatedDate
  private Instant createdTime;

  @Column(name = "modified_at")
  @LastModifiedDate
  private Instant modifiedTime;
}
