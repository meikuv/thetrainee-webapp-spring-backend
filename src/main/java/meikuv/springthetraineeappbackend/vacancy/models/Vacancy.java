package meikuv.springthetraineeappbackend.vacancy.models;

import lombok.*;
import meikuv.springthetraineeappbackend.vacancy.models.BasicSkill;
import meikuv.springthetraineeappbackend.vacancy.models.Condition;
import meikuv.springthetraineeappbackend.vacancy.models.Dependency;
import meikuv.springthetraineeappbackend.vacancy.models.Requirement;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "s_company_vacancy")
public class Vacancy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String companyName;
    private String jobName;
    private String city;

    @OneToMany(targetEntity = BasicSkill.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "vb_fk", referencedColumnName = "id")
    private List<BasicSkill> basicSkills;

    @OneToMany(targetEntity = Requirement.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "vrq_fk", referencedColumnName = "id")
    private List<Requirement> requirements;

    @OneToMany(targetEntity = Dependency.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "vd_fk", referencedColumnName = "id")
    private List<Dependency> dependencies;

    @OneToMany(targetEntity = Condition.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "vc_fk", referencedColumnName = "id")
    private List<Condition> conditions;
}
