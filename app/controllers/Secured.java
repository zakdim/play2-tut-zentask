/**
 * Secured.java
 */
package controllers;

import models.Project;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;

/**
 * <p>
 * Created on 2013-06-26 at 1:38:53 PM.
 * </p>
 * 
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
	
	public static boolean isMemberOf(Long project) {
		return Project.isMember(
				project, 
				Context.current().request().username()
			);
	}
}
