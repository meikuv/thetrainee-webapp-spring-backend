package meikuv.springthetraineeappbackend.payload.response;

import lombok.Getter;
import lombok.Setter;
import meikuv.springthetraineeappbackend.appuserrole.Role;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class UserInfoResponse {
    private String accessToken;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;

    private LocalDateTime createdAt;
    private Set<Role> roles;

    private String status;

    public UserInfoResponse(String accessToken, String status) {
        this.accessToken = accessToken;
        this.status = status;
    }

    public UserInfoResponse(Long id, String username, String email, LocalDateTime createdAt, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.createdAt = createdAt;
    }
}
