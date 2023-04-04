package org.daemio.merch.dto;

import com.jparams.verifier.tostring.NameStyle;
import com.jparams.verifier.tostring.ToStringVerifier;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

public class MerchPageTest {

  @Test
  public void testEquals() {
    EqualsVerifier.simple().forClass(MerchPage.class).withIgnoredFields("merch").verify();
  }

  @Test
  public void testToString() {
    ToStringVerifier.forClass(MerchPage.class).withClassName(NameStyle.SIMPLE_NAME).verify();
  }
}
