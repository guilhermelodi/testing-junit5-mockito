package guru.springframework.sfgpetclinic.factory;

import com.github.javafaker.Faker;
import guru.springframework.sfgpetclinic.model.Pet;

import java.time.LocalDate;
import java.time.ZoneId;

public class PetFactory {

    public static Pet build() {
        final Faker faker = FakerSingleton.getInstance();
        final Pet pet = new Pet();

        pet.setId(faker.number().randomNumber());
        pet.setName(faker.pokemon().name());
        pet.setBirthDate(LocalDate.ofInstant(
            faker.date().birthday().toInstant(),
            ZoneId.systemDefault())
        );
        pet.setOwner(OwnerFactory.build());
        pet.setPetType(PetTypeFactory.build());

        return pet;
    }
}
