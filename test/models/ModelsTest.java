/**
 * ModelsTest.java
 */
package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.avaje.ebean.Ebean;

import play.Logger;
import play.libs.Yaml;
import play.test.WithApplication;
import static org.junit.Assert.*;
import static play.test.Helpers.*;

/**
 * <p>Created on 2013-05-24 at 3:49:07 PM.</p>
 *
 */
public class ModelsTest extends WithApplication {

	@Before
    public void setUp() {
        start(fakeApplication(inMemoryDatabase(), fakeGlobal()));
    }

    @Test
    public void createAndRetrieveUser() {
        new User("bob@gmail.com", "Bob", "secret").save();
        User bob = User.find.where().eq("email", "bob@gmail.com").findUnique();
        assertNotNull(bob);
        assertEquals("Bob", bob.name);
        Logger.info(bob.toString());
    }	
    
    @Test
    public void tryAuthenticateUser() {
    	new User("bob@gmail.com", "Bob", "secret").save();
    	
    	assertNotNull(User.authenticate("bob@gmail.com", "secret"));
    	assertNull(User.authenticate("bob@gmail.com", "badpassword"));
    	assertNull(User.authenticate("tom@gmail.com", "secret"));
    }
    
    @Test
    public void findProjectsInvolving() {
    	new User("bob@gmail.com", "Bob", "secret").save();
    	new User("jane@gmail.com", "Jane", "secret").save();
    	
    	Project.create("Play 2", "play", "bob@gmail.com");
    	Project.create("Play 1", "play", "jane@gmail.com");
    	
    	List<Project> results = Project.findInvolving("bob@gmail.com");
    	assertEquals(1, results.size());
    	assertEquals("Play 2", results.get(0).name);
    }
    
    @Test
    public void findTodoTasksInvolving() {
        User bob = new User("bob@gmail.com", "Bob", "secret");
        bob.save();

        Project project = Project.create("Play 2", "play", "bob@gmail.com");
        Task t1 = new Task();
        t1.title = "Write tutorial";
        t1.assignedTo = bob;
        t1.done = true;
        t1.save();

        Task t2 = new Task();
        t2.title = "Release next version";
        t2.project = project;
        t2.save();

        List<Task> results = Task.findTodoInvolving("bob@gmail.com");
        assertEquals(1, results.size());
        assertEquals("Release next version", results.get(0).title);
    }
    
    @Test
    public void fullTest() {
    	Object obj = Yaml.load("test-data.yml");
    	HashMap db = (HashMap) obj;
    	ArrayList users = (ArrayList) db.get("users");
    	ArrayList projects = (ArrayList) db.get("projects");
    	ArrayList tasks = (ArrayList) db.get("tasks");
    	
//    	Logger.info(users.toString());
//    	Logger.info(projects.toString());
//    	Logger.info(tasks.toString());

    	Ebean.save(users);
    	Ebean.save(projects);
    	Ebean.save(tasks);
//        Ebean.save((List) Yaml.load("test-data.yml"));

        // Count things
        assertEquals(4, User.find.findRowCount());
        assertEquals(11, Project.find.findRowCount());
        assertEquals(6, Task.find.findRowCount());

        // Try to authenticate as users
        assertNotNull(User.authenticate("guillaume@sample.com", "secret"));
        assertNotNull(User.authenticate("maxime@sample.com", "secret"));
        assertNull(User.authenticate("sadek@sample.com", "badpassword"));
        assertNull(User.authenticate("tom@example.com", "secret"));

        // Find all Guillaume's projects
        List<Project> gProjects = Project.findInvolving("guillaume@sample.com");
        Logger.info("Guillaume's project count: " + gProjects.size());
        assertEquals(7, gProjects.size());

        // Find all Guillaume's todo tasks
        List<Task> gTasks = Task.findTodoInvolving("guillaume@sample.com");
        Logger.info("Guillaume's tasks count: " + gTasks.size());
        for (Task t : gTasks) {
        	Logger.info(t.toString());
        }
//        assertEquals(3, gTasks.size());
    }    
}
