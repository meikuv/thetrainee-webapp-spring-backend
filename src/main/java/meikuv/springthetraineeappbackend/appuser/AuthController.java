package meikuv.springthetraineeappbackend.appuser;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import javax.validation.Valid;

import lombok.AllArgsConstructor;
import meikuv.springthetraineeappbackend.appuserrole.ERole;
import meikuv.springthetraineeappbackend.appuserrole.Role;
import meikuv.springthetraineeappbackend.email.EmailSender;
import meikuv.springthetraineeappbackend.email.EmailService;
import meikuv.springthetraineeappbackend.emailconfirmtoken.ConfirmationTokenService;
import meikuv.springthetraineeappbackend.payload.request.LoginRequest;
import meikuv.springthetraineeappbackend.payload.request.SignUpRequest;
import meikuv.springthetraineeappbackend.payload.response.LoginResponse;
import meikuv.springthetraineeappbackend.payload.response.MessageResponse;
import meikuv.springthetraineeappbackend.appuserrole.RoleRepository;
import meikuv.springthetraineeappbackend.security.jwt.JwtUtils;
import meikuv.springthetraineeappbackend.security.service.UserDetailsImpl;
import meikuv.springthetraineeappbackend.emailconfirmtoken.ConfirmationToken;
import meikuv.springthetraineeappbackend.userprofile.Profile;
import meikuv.springthetraineeappbackend.userprofile.ProfileService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;
    private final EmailService emailService;
    private final ProfileService profileService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Optional<User> user = userRepository.findByUsername(loginRequest.getUsername());

        if (!user.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("User not found with username: "  + loginRequest.getUsername()));
        }

        if (!encoder.matches(loginRequest.getPassword(), user.get().getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Wrong username or password"));
        }

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwt = jwtUtils.generateTokenFromUsername(String.valueOf(authentication));

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);


        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new LoginResponse(
                        userDetails.getId(),
                        userDetails.getUsername(),
                        userDetails.getEmail(),
                        roles.get(0),
                        jwt,
                        "Success"
                ));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByRole(ERole.USER_ROLE)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByRole(ERole.ADMIN_ROLE)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "company":
                        Role modRole = roleRepository.findByRole(ERole.COMPANY_ROLE)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByRole(ERole.USER_ROLE)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );

        confirmationTokenService.saveConfirmationToken(
                confirmationToken
        );

        Profile profile = new Profile(
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                user,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        profileService.saveUserProfile(
                profile
        );

        String link = "http://localhost:8080/api/v1/signup/confirm?token=" + token;
        emailSender.send(
                signUpRequest.getEmail(),
                emailService.buildEmail(signUpRequest.getUsername(), link));

        return ResponseEntity.ok(new MessageResponse("Check your email and verify"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!Objects.equals(principle.toString(), "anonymousUser")) {
            Long userId = ((UserDetailsImpl) principle).getId();
        }

        ResponseCookie jwtCookie = jwtUtils.getCleanJwtCookie();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new MessageResponse("You've been signed out!"));
    }
}
