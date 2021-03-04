package kim.sihwan.trip_reviewer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

//@EnableCaching
@SpringBootApplication
public class TripReviewerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TripReviewerApplication.class, args);
    }

}
