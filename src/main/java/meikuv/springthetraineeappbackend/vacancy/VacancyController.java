package meikuv.springthetraineeappbackend.vacancy;

import lombok.AllArgsConstructor;
import meikuv.springthetraineeappbackend.appuser.User;
import meikuv.springthetraineeappbackend.appuser.UserRepository;
import meikuv.springthetraineeappbackend.payload.request.CreateVacancyRequest;
import meikuv.springthetraineeappbackend.payload.request.RespondResultRequest;
import meikuv.springthetraineeappbackend.payload.request.RespondVacancyRequest;
import meikuv.springthetraineeappbackend.payload.response.MessageResponse;
import meikuv.springthetraineeappbackend.vacancy.models.Respond;
import meikuv.springthetraineeappbackend.vacancy.models.Vacancy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/vacancy")
@AllArgsConstructor
public class VacancyController {
    private VacancyService vacancyService;
    private final RespondRepository respondRepository;
    private final UserRepository userRepository;

    @PostMapping(path = "/createVacancy/{username}")
    public ResponseEntity<MessageResponse> createVacancy(@PathVariable(name = "username") String username,
                                                         @Valid @RequestBody CreateVacancyRequest createVacancyRequest) {

        Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (Objects.equals(principle.toString(), username)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("You are not logged in !"));
        }

        vacancyService.saveVacancy(createVacancyRequest.getVacancy());

        return ResponseEntity.status(HttpStatus.OK)
                .body(new MessageResponse("Vacancy created successfully !"));
    }

    @PostMapping(path = "/respond")
    public ResponseEntity<MessageResponse> respondVacancy(@Valid @RequestBody RespondVacancyRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<User> user = userRepository.findByUsername(request.getUsername());
        if (user.isPresent() && Objects.equals(username, request.getUsername())) {
            Optional<Respond> respond = respondRepository.existsByUserId(user.get().getId(), request.getVacancyId());
            if (respond.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new MessageResponse("You are already responded !"));
            }

            Respond newRespond = new Respond(
                    request.getVacancyId(),
                    request.getCompanyName(),
                    user.get().getId(),
                    request.getUsername(),
                    request.getResumeId(),
                    request.getJobName(),
                    LocalDateTime.now()
                    );
            respondRepository.save(newRespond);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new MessageResponse("Respond successfully !"));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new MessageResponse("You are not logged in !"));
    }

    @PostMapping(path = "/respondResult")
    public ResponseEntity<MessageResponse> respondResult(@Valid @RequestBody RespondResultRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<User> user = userRepository.findByUsername(request.getUsername());
        if (user.isPresent() && Objects.equals(username, request.getUsername())) {
            respondRepository.respondResult(
                    request.getResultFlag(),
                    request.getResultMessage(),
                    request.getRespondId()
            );

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new MessageResponse("Message delivered !"));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new MessageResponse("You are not logged in !"));
    }

    @GetMapping(path = "/allVacancy")
    @Transactional
    public List<Vacancy> getAllVacancy() {
        Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (Objects.equals(principle.toString(), "anonymousUser")) {
            throw new UsernameNotFoundException("You are not logged in !");
        }

        return vacancyService.getAllVacancy();
    }

    @GetMapping(path = "/{id}")
    @Transactional
    public Optional<Vacancy> getVacancyById(@PathVariable(name = "id") Long id) {
        Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (Objects.equals(principle.toString(), "anonymousUser")) {
            throw new UsernameNotFoundException("You are not logged in !");
        }

        return vacancyService.getById(id);
    }

    @GetMapping(path = "/companyVacancy/{company}")
    public List<Vacancy> getCompanyVacancy(@PathVariable(name = "company") String company) {
        Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (Objects.equals(principle.toString(), "anonymousUser")) {
            throw new UsernameNotFoundException("You are not logged in !");
        }

        return vacancyService.getCompanyVacancy(company);
    }

    @DeleteMapping(path = "/delete/{id}")
    public void deleteVacancy(@PathVariable(name = "id") Long id) {
        Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (Objects.equals(principle.toString(), "anonymousUser")) {
            throw new UsernameNotFoundException("You are not logged in !");
        }

        vacancyService.deleteVacancyById(id);
    }
}