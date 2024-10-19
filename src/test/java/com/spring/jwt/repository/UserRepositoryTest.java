package com.spring.jwt.repository;

import com.spring.jwt.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1);
        user.setEmail("test@example.com");
        user.setName("Test User");
        user.setPassword("password123");
        user.setMobileNo("1234567890");

    }

    @Test
    void testFindByEmail() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(user);

        User result = userRepository.findByEmail("test@example.com");

        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
        assertEquals("Test User", result.getName());
        assertEquals("password123", result.getPassword());
        assertEquals("1234567890", result.getMobileNo()); // Assuming there's a getMobileNo method
        assertEquals("USER", result.getRoles());
        assertEquals(1, result.getId());
    }

    /*
    @Test
    void testDeleteById() {
        // Assume the deleteById method is uncommented and properly implemented in UserRepository
        userRepository.DeleteById(1);

        // Verify that the delete operation would have been executed.
        // Note: You may need to use Mockito's verify method if you want to check if it was called.
        // verify(userRepository, times(1)).DeleteById(1);
    }
    */
}
