package org.daemio.merch.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
    name = "image",
    indexes = {@Index(name = "merch_idx", columnList = "merch_id")})
@Getter
@Setter
public class Image {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "image_id")
  private int id;

  @Hidden
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "merch_id", nullable = false)
  private Merch merch;

  @NotBlank
  @Column(nullable = false)
  private String uri;

  private String title;
}
