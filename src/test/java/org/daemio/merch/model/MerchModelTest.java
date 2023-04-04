package org.daemio.merch.model;

import com.jparams.verifier.tostring.NameStyle;
import com.jparams.verifier.tostring.ToStringVerifier;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

import org.daemio.merch.dto.MerchResource;

public class MerchModelTest {

  @Test
  public void testEquals() {
    EqualsVerifier.simple()
        .forClass(MerchResource.class)
        .suppress(Warning.BIGDECIMAL_EQUALITY)
        .withIgnoredFields("createdTime", "modifiedTime")
        .verify();
  }

  @Test
  public void testToString() {
    ToStringVerifier.forClass(MerchResource.class).withClassName(NameStyle.SIMPLE_NAME).verify();
  }
}
