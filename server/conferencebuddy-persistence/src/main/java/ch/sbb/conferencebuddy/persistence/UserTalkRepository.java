package ch.sbb.conferencebuddy.persistence;

import ch.sbb.conferencebuddy.model.UserTalk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Gilles Zimmermann
 *
 * @since 0.0.1, 2014
 */
public interface UserTalkRepository extends JpaRepository<UserTalk, Long> {

    @Query("select ut.pid from UserTalk ut where upper(ut.userId) = ?1 order by ut.startTime asc")
    List<String> findByUserId(final String userId);

    @Modifying
    @Query("delete from UserTalk ut where ut.pid = ?1")
    void removeByPid(final String pid);
}
