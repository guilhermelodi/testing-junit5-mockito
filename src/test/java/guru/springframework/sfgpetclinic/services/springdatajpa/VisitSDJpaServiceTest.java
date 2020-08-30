package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.factory.VisitFactory;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.repositories.VisitRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VisitSDJpaServiceTest {

    @Mock
    private VisitRepository visitRepository;

    @InjectMocks
    private VisitSDJpaService visitSDJpaService;

    @Test
    void findAll() {
        Set<Visit> visits = new HashSet<>();
        visits.add(VisitFactory.build());
        visits.add(VisitFactory.build());

        when(visitRepository.findAll()).thenReturn(visits);
        Set<Visit> foundVisits = visitSDJpaService.findAll();

        verify(visitRepository).findAll();
        assertThat(foundVisits).hasSize(2);
        foundVisits.forEach(
            visit -> assertTrue(visits.contains(visit))
        );
    }

    @Test
    void findById() {
        Visit visit = VisitFactory.build();

        Long id = visit.getId();
        when(visitRepository.findById(id)).thenReturn(Optional.of(visit));

        Visit returnedVisit = visitSDJpaService.findById(id);

        verify(visitRepository).findById(id);
        assertNotNull(returnedVisit);
        assertEquals(visit, returnedVisit);
    }

    @Test
    void findByIdReturnNull() {
        when(visitRepository.findById(1L)).thenReturn(Optional.empty());

        Visit returnedVisit = visitSDJpaService.findById(1L);

        verify(visitRepository).findById(anyLong());
        assertNull(returnedVisit);
    }

    @Test
    void save() {
        Visit visit = VisitFactory.build();
        visit.setId(null);

        when(visitRepository.save(visit)).thenReturn(visit);
        Visit visitSaved = visitSDJpaService.save(visit);

        ArgumentCaptor<Visit> argumentCaptor = ArgumentCaptor.forClass(Visit.class);
        verify(visitRepository).save(argumentCaptor.capture());

        assertEquals(visit.getPet(), visitSaved.getPet());
        assertEquals(visit.getDescription(), visitSaved.getDescription());
        assertEquals(visit.getDate(), visitSaved.getDate());
        assertEquals(visit, argumentCaptor.getValue());
    }

    @Test
    void delete() {
        Visit visit = VisitFactory.build();

        doAnswer(answer -> {
            System.out.println("checking argument");

            assertEquals(visit, answer.getArgument(0));

            return null;
        }).when(visitRepository).delete(any(Visit.class));

        visitSDJpaService.delete(visit);

        verify(visitRepository).delete(any(Visit.class));
    }

    @Test
    void deleteById() {
        Visit visit = VisitFactory.build();

        doAnswer(answer -> {
            System.out.println("checking argument");

            assertEquals(visit.getId(), answer.getArgument(0));

            return null;
        }).when(visitRepository).deleteById(anyLong());

        visitSDJpaService.deleteById(visit.getId());

        verify(visitRepository).deleteById(anyLong());
    }
}