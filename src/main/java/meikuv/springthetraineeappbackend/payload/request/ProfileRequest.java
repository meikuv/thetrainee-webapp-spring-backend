package meikuv.springthetraineeappbackend.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileRequest {

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
