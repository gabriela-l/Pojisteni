package com.gabriela.Pojisteni;

import com.gabriela.Pojisteni.model.Insurance;
import com.gabriela.Pojisteni.repository.InsuranceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class InsuranceRepositoryTest {
    @Autowired
    private InsuranceRepository repo;
    @Test
    public void testAddNew() {
        Insurance insurance = new Insurance();
        insurance.setTitle("Pojištění nemovitosti");
        insurance.setDetail("MAXIMUS");
        insurance.setStart("2020-09-23");
        insurance.setEnd("2040-09-23");
        insurance.setContract("2793026442");
        Insurance savedInsurance = repo.save(insurance);

        // ověření, že se záznam uložil
        assertThat(savedInsurance).isNotNull();
        assertThat(savedInsurance.getInsurance_id()).isGreaterThan(0);
    }
}
