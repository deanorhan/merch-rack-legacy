package org.daemio.merch.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MerchPage {

    private List<MerchModel> merch = new ArrayList<>();

    private int page;
    private int size;
    private int totalPages;

    public void addMerch(MerchModel merchModel) {
        merch.add(merchModel);
    }
}
