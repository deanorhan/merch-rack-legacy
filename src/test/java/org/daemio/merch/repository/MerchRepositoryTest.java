package org.daemio.merch.repository;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import org.daemio.merch.domain.Merch;
import org.daemio.merch.domain.MerchStatus;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest(properties = {"spring.jpa.defer-datasource-initialization=true"})
@EnableJpaAuditing
public class MerchRepositoryTest {

    @Autowired
    private MerchRepository repo;

    @Test
    public void checkAllIsWell() {
        assertNotNull(repo, "Repository was not injected");
    }

    @Test
    public void whenSavingMerch_thenAuditDatesSet() {
        var merch = new Merch().setTitle("Tiele").setStatus(MerchStatus.LOADED).setPrice(BigDecimal.valueOf(7));

        var savedMerch = repo.save(merch);

        assertNotNull(savedMerch.getCreatedTime(), "Created time was not set");
        assertNotNull(savedMerch.getModifiedTime(), "Modified time was not set");
    }
}
