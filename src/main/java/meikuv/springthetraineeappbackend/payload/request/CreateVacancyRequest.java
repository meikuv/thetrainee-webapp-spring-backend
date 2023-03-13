package meikuv.springthetraineeappbackend.payload.request;

import lombok.*;
import meikuv.springthetraineeappbackend.vacancy.models.Vacancy;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateVacancyRequest {

    private Vacancy vacancy;
}
