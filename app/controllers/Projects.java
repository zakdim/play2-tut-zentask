/**
 * Projects.java
 */
package controllers;

import static play.data.Form.form;

import java.util.ArrayList;

import models.Project;
import models.Task;
import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.*;
import views.html.projects.group;
import views.html.projects.item;

/**
 * <p>Created on 2013-06-26 at 3:10:26 PM.</p>
 *
 */
@Security.Authenticated(Secured.class)
public class Projects extends Controller {

    /**
     * Display the dashboard.
     */
    public static Result index() {
        return ok(
            dashboard.render(
                Project.findInvolving(request().username()),
                Task.findTodoInvolving(request().username()),
                User.find.byId(request().username())
            )
        );
    }
	
	// -- Projects
	
    /**
     * Add a project.
     */
	public static Result add() {
		Project newProject = Project.create(
				"New project", 
				Form.form().bindFromRequest().get("group"), 
				request().username()
			);
		return ok(item.render(newProject));
	}
	
    /**
     * Rename a project.
     */
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
	
    /**
     * Delete a project.
     */
	public static Result delete(Long project) {
	    if(Secured.isMemberOf(project)) {
	        Project.find.ref(project).delete();
	        return ok();
	    } else {
	        return forbidden();
	    }
	}	
	
	// -- Project groups
	
    /**
     * Add a new project group.
     */
	public static Result addGroup() {
	    return ok(
	        group.render("New group", new ArrayList<Project>())
	    );
	}	
	
    /**
     * Delete a project group.
     */
    public static Result deleteGroup(String group) {
        Project.deleteInFolder(group);
        return ok();
    }
	
    /**
     * Rename a project group.
     */
    public static Result renameGroup(String group) {
        return ok(
            Project.renameFolder(group, form().bindFromRequest().get("name"))
        );
    }
    
    /**
     * Add a project member.
     */
    public static Result addUser(Long project) {
        if(Secured.isMemberOf(project)) {
            Project.addMember(
                project,
                form().bindFromRequest().get("user")
            );
            return ok();
        } else {
            return forbidden();
        }
    }
  
    /**
     * Remove a project member.
     */
    public static Result removeUser(Long project) {
        if(Secured.isMemberOf(project)) {
            Project.removeMember(
                project,
                form().bindFromRequest().get("user")
            );
            return ok();
        } else {
            return forbidden();
        }
    }
}
