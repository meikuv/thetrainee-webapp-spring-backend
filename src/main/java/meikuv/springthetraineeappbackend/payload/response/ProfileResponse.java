package meikuv.springthetraineeappbackend.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProfileResponse {
    private String username;
    private String email;
    private String lastName;
    private String firstName;
    private String companyName;
    private String userGithub;
    private String userInstagram;
    private String userTelegram;
    private String userLinkedIn;
    private String phone;
}
