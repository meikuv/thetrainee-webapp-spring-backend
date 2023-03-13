package meikuv.springthetraineeappbackend.resume;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ResumeRepository extends JpaRepository<Resume, Long> {
    List<Resume> findAllByUsername(String username);
}
