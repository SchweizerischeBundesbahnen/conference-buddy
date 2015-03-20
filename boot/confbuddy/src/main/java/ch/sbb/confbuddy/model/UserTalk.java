package ch.sbb.confbuddy.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalTime;

/**
 * As exported from e-tutor.
 *
 * @author Gilles Zimmermann
 *
 * @since 0.0.1, 2014
 */
@Entity
public class UserTalk extends OptimisticLockingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    /**
     * vortrag-Id.
     */
    @NotNull
    private String pid;
    /**
     * u-Nummer.
     */
    @NotNull
    private String userId;

    @NotNull
    private LocalTime startTime;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public Long getId() {
    return this.id;
  }
}
