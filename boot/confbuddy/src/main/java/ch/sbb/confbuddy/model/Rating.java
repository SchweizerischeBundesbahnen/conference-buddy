package ch.sbb.confbuddy.model;

import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * @author Gilles Zimmermann
 *
 * @since 0.0.1, 2014
 */
@Entity
public class Rating extends Talk {

    private Long rate;

    @Transient
    private Double averageRate;

    public Long getRate() {
        return rate;
    }

    public void setRate(Long rate) {
        this.rate = rate;
    }

    public Double getAverageRate() {
        return averageRate;
    }

    public void setAverageRate(Double averageRate) {
        this.averageRate = averageRate;
    }
}
