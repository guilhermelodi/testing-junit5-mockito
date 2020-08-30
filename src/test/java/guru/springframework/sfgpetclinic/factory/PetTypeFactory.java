package guru.springframework.sfgpetclinic.factory;

import com.github.javafaker.Faker;
import guru.springframework.sfgpetclinic.model.PetType;

public class PetTypeFactory {

    public static PetType build() {
        final Faker faker = FakerSingleton.getInstance();

        return new PetType(faker.animal().name());
    }

}
