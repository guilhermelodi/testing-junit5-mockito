package guru.springframework.sfgpetclinic.factory;

import com.github.javafaker.Faker;
import guru.springframework.sfgpetclinic.model.Owner;

public class OwnerFactory {

    public static Owner build() {
        final Faker faker = FakerSingleton.getInstance();

        return new Owner(faker.number().randomNumber(),
                faker.name().firstName(),
                faker.name().lastName());
    }

}
