package meikuv.springthetraineeappbackend.userprofile;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Profile saveUserProfile(Profile profile) {
        return profileRepository.save(profile);
    }

    public int setUserProfile(
            String username,
            String email,
            String lastName,
            String firstName,
            String companyName,
            String userGithub,
            String userInstagram,
            String userTelegram,
            String userLinkedIn,
            String phone
    ) {
        return profileRepository.updateUserProfile(
                username, email, lastName, firstName, companyName, userGithub,
                userInstagram, userTelegram, userLinkedIn, phone, LocalDateTime.now(), LocalDateTime.now().plusDays(2));
    }

}
