package com.gabriela.Pojisteni;

import com.gabriela.Pojisteni.model.User;
import com.gabriela.Pojisteni.repository.RoleRepository;
import com.gabriela.Pojisteni.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {
    @Autowired
    private UserRepository repo;
    @Autowired
    private RoleRepository roleRepository;

    // test, který ověří přidání uživatele (pojištěnce)
    @Test
    public void testAddNew() {
        User user = new User();
        user.setEmail("andrea.cerna@gmail.com");
        user.setName("Andrea");
        user.setSurname("Černá");
        // heslo lze zašifrovat ve třídě PasswordEncoder
        user.setPassword("$2a$10$m1/Kh0RzxZb5tO0dhOXw4.fyf1WUX/5jKYszr3jkQHDGtR2/uEIoW");
        user.setEnabled(true);
        user.setRole(roleRepository.findByName("Editor"));
        User savedUser = repo.save(user);
        // ověření, že se záznam uložil
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isGreaterThan(0);

    }

    @Test
    public void testFindUserByEmail() {
        String email = "andrea.cerna@gmail.com";
        User user = repo.findByEmail(email);
        assertThat(user).isNotNull();
    }

    // test, který ověří, že existují záznamy v tabulce users a vypíše je
    @Test
    public void testListAll() {
        Iterable<User> users = repo.findAll();
        assertThat(users).hasSizeGreaterThan(0);
        for (User user : users) {
            System.out.println(user);
        }
    }

    // test, který ověří změnu hesla u 1. uživatele
    @Test
    public void testUpdate() {
        Integer userID = 1;
        Optional<User> optionalUser = repo.findById(userID);
        User user = optionalUser.get();
        user.setPassword("hello2000");
        repo.save(user);
        User updatedUser = repo.findById(userID).get();
        assertThat(updatedUser.getPassword()).isEqualTo("hello2000");
    }

    // test, který ověří, že existuje druhý záznam, a vypíše ho
    @Test
    public void testGet() {
        Integer userID = 2;
        Optional<User> optionalUser = repo.findById(userID);
        assertThat(optionalUser).isPresent();
        System.out.println(optionalUser.get());
    }

    // test, který ověří vymazání uživatele s id 3
    @Test
    public void testDelete() {
        Integer userId = 3;
        repo.deleteById(userId);
        Optional<User> optionalUser = repo.findById(userId);
        assertThat(optionalUser).isNotPresent();
    }


}
