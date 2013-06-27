/**
 * ProjectsTest.java
 */
package controllers;

import java.util.List;
import java.util.Map;

import models.*;

import org.junit.Before;
import org.junit.Test;

import com.avaje.ebean.Ebean;

import play.Logger;
import play.libs.Yaml;
import play.mvc.Result;
import play.test.*;
import util.TestHelper;
import static org.junit.Assert.*;
import static play.test.Helpers.*;
import com.google.common.collect.ImmutableMap;

/**
 * <p>
 * Created on 2013-06-26 at 4:04:23 PM.
 * </p>
 * 
 */
public class ProjectsTest extends WithApplication {

	@Before
	public void setUp() {
		start(fakeApplication(inMemoryDatabase(), fakeGlobal()));
		TestHelper.TestData.insert("test-data.yml");
	}

	@Test
	public void newProject() {
		Result result = callAction(
				controllers.routes.ref.Projects.add(),
				fakeRequest().withSession("email", "guillaume@sample.com")
						.withFormUrlEncodedBody(
								ImmutableMap.of("group", "Some Group")));
		assertEquals(200, status(result));
		Project project = Project.find.where().eq("folder", "Some Group")
				.findUnique();
		assertNotNull(project);
		Logger.info("new project: " + project.toString());
		assertEquals("New project", project.name);
		assertEquals(1, project.members.size());
		assertEquals("guillaume@sample.com", project.members.get(0).email);
	}
	
	@Test
	public void renameProject() {
	    long id = Project.find.where()
	        .eq("members.email", "guillaume@sample.com")
	        .eq("name", "Private").findUnique().id;
	    Result result = callAction(
	        controllers.routes.ref.Projects.rename(id),
	        fakeRequest().withSession("email", "guillaume@sample.com")
	            .withFormUrlEncodedBody(ImmutableMap.of("name", "New name"))
	    );
	    assertEquals(200, status(result));
	    assertEquals("New name", Project.find.byId(id).name);
	}	

	@Test
	public void renameProjectForbidden() {
	    long id = Project.find.where()
	        .eq("members.email", "sadek@sample.com")
	        .eq("name", "Private").findUnique().id;
	    Logger.info("renameProjectForbidden() project.id = " + id);
	    Result result = callAction(
	        controllers.routes.ref.Projects.rename(id),
	        fakeRequest().withSession("email", "erwan@sample.com")
	            .withFormUrlEncodedBody(ImmutableMap.of("name", "New name"))
	    );
	    assertEquals(403, status(result));
	    assertEquals("Private", Project.find.byId(id).name);
	}	
}
