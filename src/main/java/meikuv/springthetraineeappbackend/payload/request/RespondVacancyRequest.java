package meikuv.springthetraineeappbackend.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class RespondVacancyRequest {

    private Long vacancyId;

    @NotBlank
    private String companyName;

    @NotBlank
    private String username;

    private Long resumeId;

    @NotBlank
    private String jobName;
}
