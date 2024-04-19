package br.edu.utfpr.bankapi.validations;

import br.edu.utfpr.bankapi.model.Account;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureTestEntityManager
public class AvailableAccountValidationTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    AvailableAccountValidation availableAccountValidation;

    @Test
    @Transactional
    void shouldBeValidWhenAccountExists() throws Exception {
        // Arrange
        var account = new Account("Casanova", 12345, 1000, 0);
        entityManager.persist(account);

        // Act
        var response = availableAccountValidation.validate(account.getNumber());

        // Assert
        Assertions.assertEquals(account, response);
    }

    @Test
    void shouldThrowExceptionWhenAccountDoesNotExist() {
        // Arrange
        long number = 54321;

        // Act + Assert
        Assertions.assertThrows(Exception.class, () -> availableAccountValidation.validate(number));
    }
}