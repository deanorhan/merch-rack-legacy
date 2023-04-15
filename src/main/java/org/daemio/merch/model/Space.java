package org.daemio.merch.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(
    name = "merch",
    indexes = {@Index(name = "status_idx", columnList = "status")})
@Getter
@Setter
@Accessors(chain = true)
public class Space extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "space_id")
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent", nullable = false)
  private Space parent;

  @NotBlank
  @Column(nullable = false)
  private String name;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "parent", fetch = FetchType.LAZY)
  private Collection<Space> children = new ArrayList<>();
}
