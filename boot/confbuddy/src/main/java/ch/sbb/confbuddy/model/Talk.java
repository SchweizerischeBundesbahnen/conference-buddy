package ch.sbb.confbuddy.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Gilles Zimmermann
 *
 * @since 0.0.1, 2014
 */
@MappedSuperclass
public abstract class Talk extends OptimisticLockingEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    /**
     * vortrag-Id.
     */
    @NotNull
    private String pid;
    /**
     * uuid as FK to {@link ch.sbb.confbuddy.model.User#id}
     */
    @NotNull
    private String userFk;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getUserFk() {
        return userFk;
    }

    public void setUserFk(String userFk) {
        this.userFk = userFk;
    }

    public Long getId() {
    return this.id;
  }
}
