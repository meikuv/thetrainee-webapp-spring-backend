package meikuv.springthetraineeappbackend.changeUserDetails;

import meikuv.springthetraineeappbackend.appuser.User;
import meikuv.springthetraineeappbackend.appuser.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class ChangeUserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordCheckService passwordCheckService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public int changePassword(User user, String newPassword) throws Exception {
        String username = user.getUsername();
        String userPassword = user.getPassword();

        boolean checkPassword = passwordCheckService.isTruePassword(username, userPassword);

        if (checkPassword) {
            userRepository.changeUserPassword(username, passwordEncoder.encode(newPassword));

            return 1;
        }
        return 0;
    }
}
