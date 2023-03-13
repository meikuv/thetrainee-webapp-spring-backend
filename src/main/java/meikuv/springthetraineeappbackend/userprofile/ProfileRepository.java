package meikuv.springthetraineeappbackend.userprofile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@Transactional
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByUsername(String username);

    @Transactional
    @Modifying
    @Query("UPDATE Profile p " +
            "SET p.email = ?2, p.lastName = ?3, p.firstName = ?4, p.companyName = ?5, " +
            "p.userGithub = ?6, p.userInstagram = ?7, p.userTelegram = ?8, " +
            "p.userLinkedIn = ?9, p.phone = ?10, p.lastUpdateAt = ?11, p.canUpdateAt = ?12 " +
            "WHERE p.username = ?1")
    int updateUserProfile(
            String username,
            String email,
            String lastName,
            String firstName,
            String companyName,
            String userGithub,
            String userInstagram,
            String userTelegram,
            String userLinkedIn,
            String phone,
            LocalDateTime updatedAt,
            LocalDateTime canUpdateAt
    );
}
