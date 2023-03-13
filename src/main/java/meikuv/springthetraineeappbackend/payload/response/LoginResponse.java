package meikuv.springthetraineeappbackend.payload.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {

    private Long id;
    private String username;
    private String email;
    private String roles;
    private String accessToken;

    private String status;
}
