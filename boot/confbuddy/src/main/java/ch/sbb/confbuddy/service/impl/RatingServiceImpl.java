package ch.sbb.confbuddy.service.impl;

import ch.sbb.confbuddy.model.Rating;
import ch.sbb.confbuddy.service.RatingService;
import ch.sbb.confbuddy.service.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

import javax.transaction.Transactional;

/**
 * @author Gilles Zimmermann
 *
 * @since 0.0.1, 2014
 */
@Service
@Transactional
public class RatingServiceImpl extends AbstractServiceImpl implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;


    @Override
    public Rating loadUserRating(String pid, String userId) {
        Rating rating = ratingRepository.findByPidAndUserFk(pid, userId);
        if(rating == null) {
            rating = new Rating();
            rating.setPid(pid);
        }
        setAverageRating(rating);
        return rating;
    }

    @Override
    public Rating saveOrUpdate(final String pid, final Long rate, final String userId) {
        // pre condition
        if(StringUtils.isEmpty(rate)){
          throw new IllegalArgumentException("argument rate is null or empty");
        }
        if(StringUtils.isEmpty(pid)){
          throw new IllegalArgumentException("argument pid is null or empty");
        }
        validateUser(userId);

        Rating rating = ratingRepository.findByPidAndUserFk(pid, userId);

        if(rating != null){
            rating = update(rating, rate, userId);
        }
        else{
            rating = createNewRating(pid, rate, userId);
            rating = save(rating, userId);
        }

        return rating;
    }

    /**
     * package private for testing only.
     */
    Rating createNewRating(final String pid, final Long rate, final String userId) {
        final Rating rating = new Rating();
        rating.setRate(rate);
        rating.setPid(pid);
        rating.setUserFk(userId);
        return rating;
    }

    private Rating update(final Rating rating, final Long rate, String userId) {
        // post condition
        if(rating == null){
          throw new IllegalArgumentException("argument rating is null or empty");
        }
        validateUser(rating.getUserFk(), userId);
        // set new value
        rating.setRate(rate);
        ratingRepository.save(rating);
        setAverageRating(rating);
        return rating;
    }

    @Override
    public Rating save(Rating value, String userId) {
        // pre condition
        if(StringUtils.isEmpty(value)){
          throw new IllegalArgumentException("argument value is null or empty");
        }
        validateUser(userId);

        // check userId
        if (value.getUserFk() == null) {
          value.setUserFk(userId);
        }
        // post condition
        validateUser(value.getUserFk(), userId);

        final Rating rating = ratingRepository.save(value);
        setAverageRating(rating);
        return rating;
    }

    @Override
    public List<Rating> loadAll(String pid) {

        // pre condition
        if(StringUtils.isEmpty(pid)){
          throw new IllegalArgumentException("argument pid is null or empty");
        }

        final List<Rating> ratings = ratingRepository.findByPid(pid);
        // n+1 select problem, could be solved with a local cache, but since this method is used only for testing, it's not worth the time
        for(Rating r : ratings)  {
            setAverageRating(r);
        }
        return ratings;
    }

    private void setAverageRating(final Rating rating){
        rating.setAverageRate(ratingRepository.averageByPid(rating.getPid()));
    }
}
