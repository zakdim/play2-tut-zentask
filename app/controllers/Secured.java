/**
 * Secured.java
 */
package controllers;

import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;

/**
 * <p>
 * Created on 2013-06-26 at 1:38:53 PM.
 * </p>
 * 
 * @author 1615871 (<a href="mailto:dmitri_zakharov@ftn.fedex.com">Dmitri
 *         Zakharov</a>)
 * 
 */
public class Secured extends Security.Authenticator {
	
	@Override
	public String getUsername(Context ctx) {
		return ctx.session().get("email");
	}

	@Override
	public Result onUnauthorized(Context ctx) {
		return redirect(routes.Application.login());
	}
}
