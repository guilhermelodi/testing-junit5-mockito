package guru.springframework.sfgpetclinic.factory;

import com.github.javafaker.Faker;
import guru.springframework.sfgpetclinic.model.Visit;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.concurrent.TimeUnit;

public class VisitFactory {

    public static Visit build() {
        final Faker faker = FakerSingleton.getInstance();
        Visit visit = new Visit();

        visit.setId(faker.number().randomNumber());
        visit.setPet(PetFactory.build());
        visit.setDescription(faker.medical().diseaseName());
        visit.setDate(LocalDate.ofInstant(
                faker.date().past(20, TimeUnit.DAYS).toInstant(),
                ZoneId.systemDefault())
        );

        return visit;
    }

}
