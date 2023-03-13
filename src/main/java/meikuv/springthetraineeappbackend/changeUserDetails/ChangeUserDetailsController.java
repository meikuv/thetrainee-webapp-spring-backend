package meikuv.springthetraineeappbackend.changeUserDetails;

import lombok.AllArgsConstructor;
import meikuv.springthetraineeappbackend.appuser.User;
import meikuv.springthetraineeappbackend.payload.request.ChangePasswordRequest;
import meikuv.springthetraineeappbackend.payload.response.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Objects;

@RestController
@RequestMapping("/user/profile")
@Transactional
@AllArgsConstructor
public class ChangeUserDetailsController {

    private final ChangeUserDetailsService changeUserDetailsService;

    @Transactional
    @PutMapping("/changePassword/{username}")
    public ResponseEntity<?> changePassword(
            @PathVariable(value = "username") String username,
            @RequestBody ChangePasswordRequest changePasswordRequest) throws Exception {

        User user = new User();
        user.setUsername(username);
        user.setPassword(changePasswordRequest.getOldPassword());

        if (Objects.equals(changePasswordRequest.getOldPassword(),
                changePasswordRequest.getNewPassword())) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("OldPassword and NewPassword are the same"));
        }

        int check = changeUserDetailsService.changePassword(user, changePasswordRequest.getNewPassword());

        if (check == 1) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new MessageResponse("Password changed successfully"));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new MessageResponse("Something wrong !"));
    }
}
