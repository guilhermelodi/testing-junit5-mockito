package guru.springframework.sfgpetclinic.factory;

import com.github.javafaker.Faker;

import java.util.Locale;

public class FakerSingleton {

    private static Faker faker;

    private FakerSingleton() {
    }

    public static Faker getInstance() {
        if (faker == null) {
            faker = new Faker(new Locale("pt", "BR"));
        }
        return faker;
    }

}
