package ch.sbb.conferencebuddy.service.exception;

/**
 * @author Gilles Zimmermann
 * @since 0.0.1, 2014
 */
public class UserUnauthorizedException extends RuntimeException{
    public UserUnauthorizedException(String message) {
        super(message);
    }

}
