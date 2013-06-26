/**
 * LoginTest.java
 */
package controllers;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import play.libs.Yaml;
import play.mvc.*;
import play.test.*;
import static org.junit.Assert.*;
import static play.test.Helpers.*;

import com.avaje.ebean.Ebean;
import com.google.common.collect.ImmutableMap;

/**
 * <p>Created on 2013-06-26 at 10:32:58 AM.</p>
 * @author 1615871 (<a href="mailto:dmitri_zakharov@ftn.fedex.com">Dmitri Zakharov</a>)
 *
 */
public class LoginTest extends WithApplication {
	@Before
	public void setUp() {
		start(fakeApplication(inMemoryDatabase(), fakeGlobal()));
		Map data = (Map) Yaml.load("test-data.yml");

		Ebean.save((List) data.get("users"));
		Ebean.save((List) data.get("projects"));
		Ebean.save((List) data.get("tasks"));
	}
	
	@Test
	public void authenticateSuccess() {
		Result result = callAction(
				controllers.routes.ref.Application.authenticate(), 
				fakeRequest().withFormUrlEncodedBody(ImmutableMap.of(
						"email", "guillaume@sample.com",
						"password", "secret"))
		);
		assertEquals(303, status(result));
		assertEquals("guillaume@sample.com", session(result).get("email"));
	}
	
	@Test
	public void authenticateFailure() {
	    Result result = callAction(
	        controllers.routes.ref.Application.authenticate(),
	        fakeRequest().withFormUrlEncodedBody(ImmutableMap.of(
	            "email", "guillaume@sample.com",
	            "password", "badpassword"))
	    );
	    assertEquals(400, status(result));
	    assertNull(session(result).get("email"));
	}	
	
	@Test
	public void authenticated() {
		Result result = callAction(
				controllers.routes.ref.Application.index(),
				fakeRequest().withSession("email", "guillaume@sample.com")
			);
		// Http.Status.OK = 200
		assertEquals(Http.Status.OK, status(result));
	}
	
	@Test
	public void notAuthenticated() {
	    Result result = callAction(
	        controllers.routes.ref.Application.index(),
	        fakeRequest()
	    );
	    // Http.Status.SEE_OTHER = 303
	    assertEquals(303, status(result));
	    assertEquals("/login", header("Location", result));
	}	
}
