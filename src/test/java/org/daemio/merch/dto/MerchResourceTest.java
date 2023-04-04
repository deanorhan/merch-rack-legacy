package org.daemio.merch.dto;

import com.jparams.verifier.tostring.NameStyle;
import com.jparams.verifier.tostring.ToStringVerifier;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

public class MerchResourceTest {

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
    ToStringVerifier.forClass(MerchResource.class)
        .withClassName(NameStyle.SIMPLE_NAME)
        .withIgnoredFields("description")
        .verify();

    ToStringVerifier.forClass(MerchResource.MerchResourceBuilder.class)
        .withIgnoredFields("description")
        .verify();
  }
}
