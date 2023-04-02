package org.daemio.merch.repository;

import java.math.BigDecimal;

import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import org.daemio.merch.config.PersistenceConfig;
import org.daemio.merch.domain.Merch;
import org.daemio.merch.domain.MerchStatus;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("unit-test")
@Import(PersistenceConfig.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Slf4j
public class MerchRepositoryTest {

  private final Faker faker = new Faker();

  @Autowired private MerchRepository repo;

  @Test
  public void shouldSaveMerch() {
    var merch =
        new Merch()
            .setTitle(faker.book().title())
            .setStatus(MerchStatus.LOADED)
            .setPrice(BigDecimal.valueOf(faker.number().randomDouble(2, 10, 50)));

    var savedMerch = repo.save(merch);

    assertThat(savedMerch)
        .usingRecursiveComparison()
        .ignoringFields("merchId", "createdTime", "modifiedTime")
        .isEqualTo(merch);

    assertThat(savedMerch.getId()).isNotNull();
    assertThat(savedMerch.getCreatedTime()).isNotNull();
    assertThat(savedMerch.getModifiedTime()).isNotNull();
  }

  @Test
  public void shouldUpdateMerch() {
    var merch =
        new Merch()
            .setTitle(faker.book().title())
            .setStatus(MerchStatus.LOADED)
            .setPrice(BigDecimal.valueOf(faker.number().randomDouble(2, 10, 50)));

    repo.save(merch);
    log.info("1 Modified at {}", merch.getModifiedTime());

    var savedMerch = repo.findById(merch.getId()).get();
    var modifiedTime = savedMerch.getModifiedTime();
    savedMerch.setTitle(faker.book().title());
    log.info("2 Modified at {}", modifiedTime);

    var updatedMerch = repo.save(savedMerch);
    log.info("3 Modified at {}", modifiedTime);
    log.info("4 Modified at {}", updatedMerch.getModifiedTime());

    assertThat(updatedMerch)
        .usingRecursiveComparison()
        .ignoringFields("modifiedTime")
        .isEqualTo(savedMerch);

    assertThat(updatedMerch.getModifiedTime()).isAfter(modifiedTime);
  }
}
