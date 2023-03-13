package meikuv.springthetraineeappbackend.vacancy;

import meikuv.springthetraineeappbackend.vacancy.models.Vacancy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VacancyService {

    private final VacancyRepository vacancyRepository;

    public VacancyService(VacancyRepository vacancyRepository) {
        this.vacancyRepository = vacancyRepository;
    }

    public Vacancy saveVacancy(Vacancy vacancy) {
        return vacancyRepository.save(vacancy);
    }

    public List<Vacancy> getAllVacancy() {
        return vacancyRepository.findAll();
    }

    public Optional<Vacancy> getById(Long id) {
        return vacancyRepository.findById(id);
    }

    public List<Vacancy> getCompanyVacancy(String username) {
        return vacancyRepository.findAllByUsername(username);
    }

    public void deleteVacancyById(Long id) {
        vacancyRepository.deleteById(id);
    }
}