package meikuv.springthetraineeappbackend.vacancy;

import meikuv.springthetraineeappbackend.vacancy.models.Respond;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Repository
@Transactional
public interface RespondRepository extends JpaRepository<Respond, Long> {

    @Query("SELECT r FROM Respond r WHERE r.userId = :userId and r.vacancyId = :vacancyId and r.resumeId = :resumeId")
    Optional<Respond> existsByUserId(@Param("userId") Long userId,
                                     @Param("vacancyId") Long vacancyId, @Param("resumeId") Long resumeId);

    @Modifying
    @Query("UPDATE Respond r " +
            "SET r.resultFlag = :resultFlag, r.resultMessage = :resultMessage " +
            "WHERE r.id = :respondId")
    int respondResult(@Param("resultFlag") String resultFlag,
                      @Param("resultMessage") String resultMessage,
                      @Param("respondId") Long respondId);
}
