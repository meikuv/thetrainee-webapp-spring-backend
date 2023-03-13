package meikuv.springthetraineeappbackend.userprofile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import meikuv.springthetraineeappbackend.appuser.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "s_user_profile")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String lastName;
    private String firstName;
    private String companyName;
    private String userTelegram;
    private String userGithub;
    private String userInstagram;
    private String userLinkedIn;
    private String phone;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdateAt;
    private LocalDateTime canUpdateAt;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            nullable = false,
            name = "user_id"
    )
    private User user;

    public Profile(String username,
                   String email,
                   User user,
                   LocalDateTime createdAt,
                   LocalDateTime lastUpdateAt) {
        this.username = username;
        this.email = email;
        this.createdAt = createdAt;
        this.lastUpdateAt = lastUpdateAt;
        this.user = user;
    }
}
