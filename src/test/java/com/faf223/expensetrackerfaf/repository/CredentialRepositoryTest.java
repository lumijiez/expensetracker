package com.faf223.expensetrackerfaf.repository;

import com.faf223.expensetrackerfaf.model.Credential;
import com.faf223.expensetrackerfaf.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CredentialRepositoryTest {

    @Autowired
    private CredentialRepository credentialRepository;

    @Autowired
    private UserRepository userRepository;
    private User user;
    private Credential credential;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setFirstName("Hamon");
        user.setLastName("User");
        user.setUsername("UserHamon");

        credential = Credential.builder()
                .email("NewEmail@gmail.com")
                .password("12345")
                .user(user)
                .build();

        userRepository.save(user);
        credentialRepository.save(credential);
    }
    @Test
    void findByEmail() {
        // when
        boolean exists = credentialRepository.findByUser(user).isPresent();
        // then
        assertThat(exists).isTrue();
    }

    @Test
    void itShouldCheckIfCredentialExistsByUser() {
        // when
        boolean exists = credentialRepository.findByUser(user).isPresent();
        // then
        assertThat(exists).isTrue();
    }

    @Test
    void deleteByEmail() {
        // when
        credentialRepository.deleteByEmail(credential.getEmail());
        boolean exists = credentialRepository.findByUser(user).isPresent();
        // then
        assertThat(exists).isFalse();

    }
}