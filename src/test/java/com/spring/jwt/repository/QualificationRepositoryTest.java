package com.spring.jwt.repository;

import com.spring.jwt.entity.Application;
import com.spring.jwt.entity.Qualification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
public class QualificationRepositoryTest {

    @Mock
    private QualificationRepository qualificationRepository;

    private Application application;
    private Qualification qualification;

    @BeforeEach
    void setUp() {
        application = new Application();
        qualification = new Qualification();
        qualification.setApplication(application);
        qualification.setStandard("Bachelor's Degree");
        qualification.setUniversity("University X");
        qualification.setPassingYear("2020");
        qualification.setPercentage(85.5);
    }

    @Test
    void testFindByApplicationAndStandardAndUniversityAndPassingYearAndPercentage() {
        when(qualificationRepository.findByApplicationAndStandardAndUniversityAndPassingYearAndPercentage(
                any(Application.class),
                any(String.class),
                any(String.class),
                any(String.class),
                any(Double.class)))
                .thenReturn(Optional.of(qualification));

        Optional<Qualification> result = qualificationRepository.findByApplicationAndStandardAndUniversityAndPassingYearAndPercentage(
                application,
                "Bachelor's Degree",
                "University X",
                "2020",
                85.5
        );

        assertTrue(result.isPresent());
        assertEquals(application, result.get().getApplication());
        assertEquals("Bachelor's Degree", result.get().getStandard());
        assertEquals("University X", result.get().getUniversity());
        assertEquals("2020", result.get().getPassingYear());
        assertEquals(85.5, result.get().getPercentage());
    }
}
