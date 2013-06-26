/**
 * Project.java
 */
package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import play.db.ebean.Model;

/**
 * <p>Created on 2013-05-27 at 1:52:40 PM.</p>
 *
 */
@Entity
public class Project extends Model {
	
    @Id 
    public Long id;
    public String name;
    public String folder;
    
    @ManyToMany(cascade = CascadeType.REMOVE)
    public List<User> members = new ArrayList<User>();

    public Project(String name, String folder, User owner) {
        this.name = name;
        this.folder = folder;
        this.members.add(owner);
    }
	
    public static Model.Finder<Long,Project> find = 
    		new Model.Finder<Long, Project>(Long.class, Project.class);

    public static Project create(String name, String folder, String owner) {
        Project project = new Project(name, folder, User.find.ref(owner));
        project.save();
        project.saveManyToManyAssociations("members");
        return project;
    }
    
    public static List<Project> findInvolving(String user) {
        return find.where()
            .eq("members.email", user)
            .findList();
    }

    public static boolean isMember(Long project, String user) {
        return find.where()
            .eq("members.email", user)
            .eq("id", project)
            .findRowCount() > 0;
    }
    
    public static String rename(Long projectId, String newName) {
        Project project = find.ref(projectId);
        project.name = newName;
        project.update();
        return newName;
    }    
    
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Project [id=" + id + ", name=" + name + "]";
	}
    
}
