package ch.sbb.confbuddy.model;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public abstract class OptimisticLockingEntity {
    @Version
    private Long version;

    public OptimisticLockingEntity() {
    }

    public Long getVersion() {
      return this.version;
    }
}
