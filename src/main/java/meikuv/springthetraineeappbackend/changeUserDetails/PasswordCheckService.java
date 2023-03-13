package meikuv.springthetraineeappbackend.changeUserDetails;

import meikuv.springthetraineeappbackend.appuser.User;
import meikuv.springthetraineeappbackend.appuser.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordCheckService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public boolean isTruePassword(String username, String password) throws Exception {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        if (user != null) {
            return passwordEncoder.matches(password, user.getPassword());
        } else {
            return false;
        }
    }
}
