package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.factory.OwnerFactory;
import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    private static final String OWNERS_CREATE_OR_UPDATE_OWNER_FORM = "owners/createOrUpdateOwnerForm";
    private static final String REDIRECT_OWNERS = "redirect:/owners/";

    @Mock
    private OwnerService ownerService;

    @InjectMocks
    private OwnerController ownerController;

    @Mock
    private BindingResult bindingResult;

    @Captor
    private ArgumentCaptor<String> stringArgumentCaptor;

    @Test
    void processFindFormWildcardString() {
        // given
        Owner owner = OwnerFactory.build();
        List<Owner> owners = Collections.singletonList(owner);
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        when(ownerService.findAllByLastNameLike(captor.capture())).thenReturn(owners);

        // when
        ownerController.processFindForm(owner, bindingResult, null);

        // then
        assertEquals("%"+ owner.getLastName() + "%", captor.getValue());
    }

    @Test
    void processFindFormWildcardStringAnnotation() {
        // given
        Owner owner = OwnerFactory.build();
        List<Owner> owners = Collections.singletonList(owner);
        when(ownerService.findAllByLastNameLike(stringArgumentCaptor.capture())).thenReturn(owners);

        // when
        ownerController.processFindForm(owner, bindingResult, null);

        // then
        assertEquals("%"+ owner.getLastName() + "%", stringArgumentCaptor.getValue());
    }

    @Test
    void processFindFormWildcardStringBDD() {
        // given
        Owner owner = OwnerFactory.build();
        List<Owner> owners = Collections.singletonList(owner);
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        given(ownerService.findAllByLastNameLike(captor.capture())).willReturn(owners);

        // when
        ownerController.processFindForm(owner, bindingResult, null);

        // then
        assertEquals("%"+ owner.getLastName() + "%", captor.getValue());
    }

    @Test
    void processCreationFormSuccess() {
        Owner owner = OwnerFactory.build();
        owner.setId(5L);

        when(bindingResult.hasErrors()).thenReturn(false);
        when(ownerService.save(any())).thenReturn(owner);

        // when
        String result = ownerController.processCreationForm(owner, bindingResult);

        // then
        verify(ownerService).save(any(Owner.class));
        assertEquals(result, REDIRECT_OWNERS + "5");
    }

    @Test
    void processCreationFormSuccessBDD() {
        // given
        Owner owner = OwnerFactory.build();
        owner.setId(5L);
        given(bindingResult.hasErrors()).willReturn(false);
        given(ownerService.save(any())).willReturn(owner);

        // when
        String result = ownerController.processCreationForm(owner, bindingResult);

        // then
        then(ownerService).should().save(any(Owner.class));
        assertEquals(result, REDIRECT_OWNERS + "5");
    }

    @Test
    void processCreationFormWithErrors() {
        when(bindingResult.hasErrors()).thenReturn(true);

        // when
        String result = ownerController.processCreationForm(OwnerFactory.build(), bindingResult);

        // then
        verify(bindingResult).hasErrors();
        assertEquals(result, OWNERS_CREATE_OR_UPDATE_OWNER_FORM);
    }
}