/**
 * Projects.java
 */
package controllers;

import java.util.ArrayList;

import models.Project;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.projects.group;
import views.html.projects.item;

/**
 * <p>Created on 2013-06-26 at 3:10:26 PM.</p>
 *
 */
@Security.Authenticated(Secured.class)
public class Projects extends Controller {

	public static Result add() {
		Project newProject = Project.create(
				"New project", 
				Form.form().bindFromRequest().get("group"), 
				request().username()
			);
		return ok(item.render(newProject));
	}
	
	public static Result rename(Long project) {
	    if (Secured.isMemberOf(project)) {
	        return ok(
	            Project.rename(
	                project,
	                Form.form().bindFromRequest().get("name")
	            )
	        );
	    } else {
	        return forbidden();
	    }
	}	
	
	public static Result delete(Long project) {
	    if(Secured.isMemberOf(project)) {
	        Project.find.ref(project).delete();
	        return ok();
	    } else {
	        return forbidden();
	    }
	}	
	
	public static Result addGroup() {
	    return ok(
	        group.render("New group", new ArrayList<Project>())
	    );
	}	
}
