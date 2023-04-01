package org.daemio.merch.model;

import com.jparams.verifier.tostring.NameStyle;
import com.jparams.verifier.tostring.ToStringVerifier;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

public class MerchModelTest {

  @Test
  public void testEquals() {
    EqualsVerifier.simple()
        .forClass(MerchModel.class)
        .suppress(Warning.BIGDECIMAL_EQUALITY)
        .withIgnoredFields("createdTime", "modifiedTime")
        .verify();
  }

  @Test
  public void testToString() {
    ToStringVerifier.forClass(MerchModel.class).withClassName(NameStyle.SIMPLE_NAME).verify();
  }
}
