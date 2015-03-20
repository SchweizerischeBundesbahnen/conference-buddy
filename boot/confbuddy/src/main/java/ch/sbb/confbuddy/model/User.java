package ch.sbb.confbuddy.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Gilles Zimmermann
 *
 * @since 0.0.1, 2014
 */
@XmlRootElement(name="user")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class User extends OptimisticLockingEntity implements Serializable {

    @Id
    private String id;

    private String name;
    @Size(max = 10)
    private String userId;
    @NotNull
    private String email;

    private boolean emailSent = false;

    @NotNull
    private LocalDateTime created = LocalDateTime.now();

    private int retryCount = 0;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEmailSent() {
        return emailSent;
    }

    public void setEmailSent(boolean emailSent) {
        this.emailSent = emailSent;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public String getId() {
      return this.id;
    }

    public void setId(String id) {
    this.id = id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, userId, email, emailSent, created, retryCount);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    final User other = (User) obj;
    return Objects.equals(this.id, other.id)
      && Objects.equals(this.name, other.name)
      && Objects.equals(this.userId, other.userId)
      && Objects.equals(this.email, other.email)
      && Objects.equals(this.emailSent, other.emailSent)
      && Objects.equals(this.created, other.created)
      && Objects.equals(this.retryCount, other.retryCount);
  }
}
