package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.factory.VisitFactory;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.repositories.VisitRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class VisitSDJpaServiceBDDTest {

    @Mock
    private VisitRepository visitRepository;

    @InjectMocks
    private VisitSDJpaService visitSDJpaService;

    @Test
    void findAll() {
        //given
        Set<Visit> visits = new HashSet<>();
        visits.add(VisitFactory.build());
        visits.add(VisitFactory.build());
        given(visitRepository.findAll()).willReturn(visits);

        //when
        Set<Visit> foundVisits = visitSDJpaService.findAll();

        //then
        then(visitRepository).should().findAll();
        assertThat(foundVisits).hasSize(2);
        foundVisits.forEach(
            visit -> assertTrue(visits.contains(visit))
        );
    }

    @Test
    void findById() {
        // given
        Visit visit = VisitFactory.build();
        Long id = visit.getId();
        given(visitRepository.findById(id)).willReturn(Optional.of(visit));

        // when
        Visit returnedVisit = visitSDJpaService.findById(id);

        // then
        then(visitRepository).should().findById(id);
        assertNotNull(returnedVisit);
        assertEquals(visit, returnedVisit);
    }

    @Test
    void findByIdReturnNull() {
        // given
        given(visitRepository.findById(1L)).willReturn(Optional.empty());

        // when
        Visit returnedVisit = visitSDJpaService.findById(1L);

        // then
        then(visitRepository).should().findById(anyLong());
        assertNull(returnedVisit);
    }

    @Test
    void save() {
        // given
        Visit visit = VisitFactory.build();
        visit.setId(null);
        given(visitRepository.save(visit)).willReturn(visit);

        // when
        Visit visitSaved = visitSDJpaService.save(visit);

        // then
        ArgumentCaptor<Visit> argumentCaptor = ArgumentCaptor.forClass(Visit.class);
        then(visitRepository).should().save(argumentCaptor.capture());
        assertEquals(visit.getPet(), visitSaved.getPet());
        assertEquals(visit.getDescription(), visitSaved.getDescription());
        assertEquals(visit.getDate(), visitSaved.getDate());
        assertEquals(visit, argumentCaptor.getValue());
    }

    @Test
    void delete() {
        // given
        Visit visit = VisitFactory.build();

        // when
        visitSDJpaService.delete(visit);

        //then
        then(visitRepository).should().delete(any(Visit.class));
    }

    @Test
    void deleteById() {
        // when
        visitSDJpaService.deleteById(1L);

        //then
        then(visitRepository).should().deleteById(anyLong());
    }
}