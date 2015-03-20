package ch.sbb.confbuddy.service.exception;

import java.lang.RuntimeException;import java.lang.String; /**
 * @author Gilles Zimmermann
 * @since 0.0.1, 2014
 */
public class UserUnauthorizedException extends RuntimeException{
    public UserUnauthorizedException(String message) {
        super(message);
    }

}
