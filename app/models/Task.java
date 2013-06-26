/**
 * Task.java
 */
package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

/**
 * <p>Created on 2013-05-27 at 2:19:03 PM.</p>
 *
 */
@Entity
public class Task extends Model {

    @Id 
    public Long id;
    public String title;
    public boolean done = false;
    public Date dueDate;

    @ManyToOne
    public User assignedTo;
    
    public String folder;
	
    @ManyToOne
    public Project project;

    public static Model.Finder<Long,Task> find = 
    		new Model.Finder<Long, Task>(Long.class, Task.class);

    public static List<Task> findTodoInvolving(String user) {
        return find.fetch("project").where()
                 .eq("done", false)
                 .eq("project.members.email", user)
            .findList();
     }

     public static Task create(Task task, Long project, String folder) {
         task.project = Project.find.ref(project);
         task.folder = folder;
         task.save();
         return task;
     }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Task [id=" + id + ", title=" + title + ", done=" + done
				+ ", assignedTo=" + assignedTo + ", project=" + project + "]";
	}

}
