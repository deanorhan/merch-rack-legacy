package org.daemio.merch.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(
    name = "merch",
    indexes = {@Index(name = "status_idx", columnList = "status")})
@Getter
@Setter
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class Merch {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "merch_id")
  private UUID id;

  @NotBlank
  @Column(nullable = false)
  private String title;

  @NotNull @Enumerated(EnumType.ORDINAL)
  private MerchStatus status;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "merch", fetch = FetchType.EAGER)
  private List<Image> images = new ArrayList<>();

  @NotNull @Positive @Column(nullable = false)
  private BigDecimal price;

  @Column(name = "created_at", nullable = false, updatable = false)
  @CreatedDate
  private Instant createdTime;

  @Column(name = "modified_at")
  @LastModifiedDate
  private Instant modifiedTime;
}
