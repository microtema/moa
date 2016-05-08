package de.seven.fate.moa.rest.interceptor;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;

public class RestErrorInterceptor {

	private static final Logger logger = Logger.getLogger(RestErrorInterceptor.class.getName());

    @AroundInvoke
	public Object intercept(InvocationContext invocationContext) throws Exception {

        try {
			return invocationContext.proceed();
		} catch (Exception e) {
			logger.log(Level.WARNING,
					"unable to handle REST#call " + invocationContext.getMethod().getName() + " with data: " + StringUtils.join(invocationContext.getParameters()), e);

			return handleException(e);
		}
	}

	private Response handleException(Exception e) {

		String message = getRootCause(e).getLocalizedMessage();
		if (StringUtils.isEmpty(message)) {
			message = e.getClass().getSimpleName();
        }

		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setMessage(message);
		errorMessage.setCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());

		return Response.serverError().entity(errorMessage).build();
	}

	private Throwable getRootCause(Throwable throwable) {

		if (throwable.getCause() != null) {
			return getRootCause(throwable.getCause());
		}

		return throwable;
    }
}
