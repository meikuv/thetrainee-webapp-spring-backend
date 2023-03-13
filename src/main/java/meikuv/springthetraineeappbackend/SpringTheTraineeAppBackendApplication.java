package meikuv.springthetraineeappbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "meikuv")
public class SpringTheTraineeAppBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringTheTraineeAppBackendApplication.class, args);
    }

}
