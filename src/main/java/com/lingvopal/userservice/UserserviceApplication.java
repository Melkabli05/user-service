package com.lingvopal.userservice;

import com.lingvopal.userservice.repository.UserRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
@EnableCaching
public class UserserviceApplication {

  @Autowired private UserRepository userRepository;

  public static void main(String[] args) {
    SpringApplication.run(UserserviceApplication.class, args);
  }
  /*
    @Bean
    CommandLineRunner runner() {
      return args -> {
        Faker faker = new Faker();
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
          User user = new User();
          user.setFirstName(faker.name().firstName());
          user.setLastName(faker.name().lastName());
          user.setProfilePictureUrl(faker.internet().image());
          user.setCountry(faker.country().name());
          user.setBio(faker.lorem().sentence());
          user.setInterests(List.of(Interests.ART, Interests.BOOKS));
          user.setNativeLanguage(LanguageCode.aa);
          user.setSpokenLanguages(Map.of(LanguageCode.ar, ProficiencyLevel.valueOf("ADVANCED")));
          user.setLearningLanguages(Map.of(LanguageCode.fr, ProficiencyLevel.valueOf("ADVANCED")));
          user.setPassword(faker.internet().password());

          user.setEmail(faker.internet().emailAddress());

          users.add(user);
        }

        userRepository.saveAll(users).subscribe(user -> System.out.println("User saved to DB: " + user));

        System.out.println("Hello World from Spring Boot 2!");
      };
    }
  */
  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }
}
