package meikuv.springthetraineeappbackend.resume;

import lombok.AllArgsConstructor;
import meikuv.springthetraineeappbackend.payload.request.CreateResumeRequest;
import meikuv.springthetraineeappbackend.payload.response.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/resume")
@AllArgsConstructor
public class ResumeController {
    private ResumeRepository resumeRepository;

    @PostMapping("/createResume/{username}")
    public ResponseEntity<MessageResponse> createResume(@PathVariable(name = "username") String username,
                                                         @Valid @RequestBody CreateResumeRequest createResumeRequest) {
        Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (Objects.equals(principle.toString(), username)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("You are not logged in !"));
        }

        resumeRepository.save(createResumeRequest.getResume());

        return ResponseEntity.status(HttpStatus.OK)
                .body(new MessageResponse("Resume created successfully !"));
    }

    @Transactional
    @GetMapping(path = "/userResume/{username}")
    public List<Resume> getUserResumes(@PathVariable(name = "username") String username) {
        Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (Objects.equals(principle.toString(), "anonymousUser")) {
            throw new UsernameNotFoundException("You are not logged in !");
        }

        return resumeRepository.findAllByUsername(username);
    }

    @DeleteMapping(path = "/delete/{id}")
    public void deleteVacancy(@PathVariable(name = "id") Long id) {
        Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (Objects.equals(principle.toString(), "anonymousUser")) {
            throw new UsernameNotFoundException("You are not logged in !");
        }

        resumeRepository.deleteById(id);
    }
}
