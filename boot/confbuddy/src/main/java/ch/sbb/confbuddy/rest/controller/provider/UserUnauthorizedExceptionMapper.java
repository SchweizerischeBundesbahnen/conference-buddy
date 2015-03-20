package ch.sbb.confbuddy.rest.controller.provider;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import ch.sbb.confbuddy.service.exception.UserUnauthorizedException;


/**
 * @author Gilles Zimmermann
 * @since 0.0.1, 2014
 */
@Provider
public class UserUnauthorizedExceptionMapper implements ExceptionMapper<UserUnauthorizedException> {

    @Override
    public Response toResponse(UserUnauthorizedException e) {
        Response response = Response.status(Response.Status.UNAUTHORIZED).build();
        return response;
    }
}
