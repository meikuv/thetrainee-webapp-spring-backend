package meikuv.springthetraineeappbackend.vacancy;

import meikuv.springthetraineeappbackend.vacancy.models.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface VacancyRepository extends JpaRepository<Vacancy, Long> {
    Optional<Vacancy> findById(Long id);

    List<Vacancy> findAllByUsername(String username);
}
