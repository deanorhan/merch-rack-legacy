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
import org.daemio.merch.model.Image;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("unit-test")
@Import(PersistenceConfig.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ImageRepositoryTest {

  private final Faker faker = new Faker();

  @Autowired private ImageRepository repo;

  @Test
  public void shouldSaveImage() {
    var image = new Image().setTitle(faker.book().title()).setUri("http://simon.says.org/jack.png");

    var savedImage = repo.save(image);

    assertThat(savedImage)
        .usingRecursiveComparison()
        .ignoringFields("createdTime", "createdBy", "modifiedTime")
        .isEqualTo(image);

    assertThat(savedImage.getId()).isNotNull();
    assertThat(savedImage.getCreatedTime()).isNotNull();
    assertThat(savedImage.getModifiedTime()).isNotNull();
  }
}
