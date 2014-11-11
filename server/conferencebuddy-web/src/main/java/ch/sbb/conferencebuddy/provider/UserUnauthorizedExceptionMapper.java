package ch.sbb.conferencebuddy.provider;

import ch.sbb.conferencebuddy.service.exception.UserUnauthorizedException;
import ch.sbb.esta.core.webservice.rest.handler.AbstractExceptionMapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * @author Gilles Zimmermann
 * @since 0.0.1, 2014
 */
@Provider
public class UserUnauthorizedExceptionMapper extends AbstractExceptionMapper<UserUnauthorizedException> {

    @Override
    public Response toResponse(UserUnauthorizedException e) {
        Response response = Response.status(Response.Status.UNAUTHORIZED).build();
        setExceptionDefaultsIntoHTTPHeader(response, e);
        return response;
    }
}
