package meikuv.springthetraineeappbackend.userprofile;

import lombok.AllArgsConstructor;
import meikuv.springthetraineeappbackend.payload.request.ProfileRequest;
import meikuv.springthetraineeappbackend.payload.response.MessageResponse;
import meikuv.springthetraineeappbackend.payload.response.ProfileResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;


@RestController
@RequestMapping("/api/v1/userInfo")
@AllArgsConstructor
public class ProfileController {
    private final ProfileRepository profileRepository;
    private final ProfileService profileService;

    @GetMapping(path = "/{username}")
    public ResponseEntity<?> getUserInfo(@PathVariable String username) {
        Profile profile = profileRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username:"  + username));

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ProfileResponse(
                        profile.getUsername(),
                        profile.getEmail(),
                        profile.getLastName(),
                        profile.getFirstName(),
                        profile.getCompanyName(),
                        profile.getUserGithub(),
                        profile.getUserInstagram(),
                        profile.getUserTelegram(),
                        profile.getUserLinkedIn(),
                        profile.getPhone()
                ));
    }

    @PostMapping(path = "/update")
    public ResponseEntity<?> userInfoUpdate(@Valid @RequestBody ProfileRequest profileRequest) {
        Profile profile = profileRepository.findByUsername(profileRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username:" + profileRequest.getUsername()));
        if (profile.getCanUpdateAt() != null) {
            int diffTime = profile.getCanUpdateAt().compareTo(LocalDateTime.now());
            if (diffTime > 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new MessageResponse("You can't update now !"));
            }
        }

        profileService.setUserProfile(
                profileRequest.getUsername(),
                profileRequest.getEmail(),
                profileRequest.getLastName(),
                profileRequest.getFirstName(),
                profileRequest.getCompanyName(),
                profileRequest.getUserGithub(),
                profileRequest.getUserInstagram(),
                profileRequest.getUserTelegram(),
                profileRequest.getUserLinkedIn(),
                profileRequest.getPhone()
        );

        return ResponseEntity.status(HttpStatus.OK)
                .body(new MessageResponse("Updated Successfully"));
    }
}
