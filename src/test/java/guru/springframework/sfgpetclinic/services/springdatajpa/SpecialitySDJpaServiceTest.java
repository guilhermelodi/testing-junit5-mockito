package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.repositories.SpecialtyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {

    @Mock
    private SpecialtyRepository specialtyRepository;

    @InjectMocks
    private SpecialitySDJpaService specialitySDJpaService;

    @Test
    void findById() {
        final Speciality speciality = new Speciality(1L, "Cardiologia");

        when(specialtyRepository.findById(1L)).thenReturn(Optional.of(speciality));

        final Speciality specialityReturned = specialitySDJpaService.findById(1L);

        assertNotNull(specialityReturned);
        assertEquals(speciality, specialityReturned);
        verify(specialtyRepository).findById(1L);
    }

    @Test
    void deleteById() {
        specialitySDJpaService.deleteById(1L);

        verify(specialtyRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteByIdAtLeastOnce() {
        specialitySDJpaService.deleteById(1L);

        verify(specialtyRepository, atLeastOnce()).deleteById(1L);
    }

    @Test
    void deleteByIdAtMost() {
        specialitySDJpaService.deleteById(1L);

        verify(specialtyRepository, atMost(5)).deleteById(1L);
    }

    @Test
    void shouldNeverDeleteId5() {
        specialitySDJpaService.deleteById(1L);

        verify(specialtyRepository, times(1)).deleteById(1L);
        verify(specialtyRepository, times(1)).deleteById(anyLong());

        verify(specialtyRepository, never()).deleteById(5L);
    }

    @Test
    void delete() {
        final Speciality speciality = new Speciality(1L, "Cardiologia");
        ArgumentCaptor<Speciality> argumentCaptor = ArgumentCaptor.forClass(Speciality.class);

        specialitySDJpaService.delete(speciality);
        verify(specialtyRepository).delete(argumentCaptor.capture());

        verify(specialtyRepository).delete(any(Speciality.class));
        assertEquals(speciality, argumentCaptor.getValue());
    }

    @Test
    void testDoThrow() {
        doThrow(new RuntimeException("boom")).when(specialtyRepository).delete(any());

        assertThrows(RuntimeException.class, () -> specialtyRepository.delete(new Speciality()));

        verify(specialtyRepository).delete(any());
    }

    @Test
    void testFindByIdThrowBDD() {
        // given
        given(specialtyRepository.findById(anyLong())).willThrow(new RuntimeException("boom"));

        // when
        assertThrows(RuntimeException.class, () -> specialitySDJpaService.findById(1L));

        // then
        then(specialtyRepository).should().findById(1L);
    }

    @Test
    void testDeleteBDD() {
        willThrow(new RuntimeException("boom")).given(specialtyRepository).delete(any());

        assertThrows(RuntimeException.class, () -> specialitySDJpaService.delete(new Speciality()));

        then(specialtyRepository).should().delete(any());
    }
}