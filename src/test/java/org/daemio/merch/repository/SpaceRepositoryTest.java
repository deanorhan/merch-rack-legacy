package org.daemio.merch.repository;

import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import org.daemio.merch.config.PersistenceConfig;
import org.daemio.merch.model.Space;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("unit-test")
@Import(PersistenceConfig.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class SpaceRepositoryTest {

  private final Faker faker = new Faker();

  @Autowired private SpaceRepository repo;

  @Test
  public void shouldSaveSpace() {
    var space = new Space().setName(faker.book().title());

    var savedSpace = repo.save(space);

    assertThat(savedSpace)
        .usingRecursiveComparison()
        .ignoringFields("parent", "createdTime", "createdBy", "modifiedTime")
        .isEqualTo(space);

    assertThat(savedSpace.getId()).isNotNull();
    assertThat(savedSpace.getCreatedTime()).isNotNull();
    assertThat(savedSpace.getModifiedTime()).isNotNull();
  }
}
