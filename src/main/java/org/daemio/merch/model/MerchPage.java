package org.daemio.merch.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MerchPage {

  @EqualsAndHashCode.Exclude private List<MerchModel> merch = new ArrayList<>();

  private int page;
  private int size;
  private int totalPages;

  public void addMerch(MerchModel merchModel) {
    merch.add(merchModel);
  }
}
