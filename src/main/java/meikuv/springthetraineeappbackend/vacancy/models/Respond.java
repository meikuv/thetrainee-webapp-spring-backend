package meikuv.springthetraineeappbackend.vacancy.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Data
@NoArgsConstructor
@Entity
@Table(name = "s_vacancy_respond")
public class Respond {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long vacancyId;

    @NotNull
    private String companyName;

    @NotNull
    private Long userId;

    @NotNull
    private String username;

    @NotNull
    private Long resumeId;

    @NotNull
    private String jobName;

    @NotNull
    private LocalDateTime respondDt;

    private String resultFlag;

    private String resultMessage;

    public Respond(Long vacancyId,
                   String companyName,
                   Long userId,
                   String username,
                   Long resumeId,
                   String jobName,
                   LocalDateTime respondDt) {
        this.vacancyId = vacancyId;
        this.companyName = companyName;
        this.userId = userId;
        this.username = username;
        this.resumeId = resumeId;
        this.jobName = jobName;
        this.respondDt = respondDt;
    }
}
