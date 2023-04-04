package org.daemio.merch.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MerchPage {

  @EqualsAndHashCode.Exclude private List<MerchResource> merch = new ArrayList<>();

  private int page;
  private int size;
  private int totalPages;

  public void addMerch(MerchResource merchModel) {
    merch.add(merchModel);
  }
}
