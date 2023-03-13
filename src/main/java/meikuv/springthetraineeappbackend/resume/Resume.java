package meikuv.springthetraineeappbackend.resume;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "s_user_resume")
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String firstName;
    private String lastName;
    private String phone;
    private String city;
    private String gender;
    private String birthDate;
    private String position;
    private String salary;
    private String salaryMode;
    private String aboutMe;

    @OneToMany(targetEntity = CoreSkill.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "cs_fk", referencedColumnName = "id")
    private List<CoreSkill> coreSkills;

    @OneToMany(targetEntity = WorkExperience.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "we_fk", referencedColumnName = "id")
    private List<WorkExperience> workExperiences;

    @OneToMany(targetEntity = StudyPlaces.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "sp_fk", referencedColumnName = "id")
    private List<StudyPlaces> studyPlaces;

    @OneToMany(targetEntity = Languages.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "lg_fk", referencedColumnName = "id")
    private List<Languages> languages;
}
