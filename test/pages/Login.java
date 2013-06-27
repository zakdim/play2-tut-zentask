/**
 * Login.java
 */
package pages;

import org.fluentlenium.core.FluentPage;

import controllers.routes;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.fluentlenium.FluentLeniumAssertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.*;

import components.*;

/**
 * <p>Created on 2013-06-27 at 1:58:26 PM.</p>
 *
 */
public class Login extends FluentPage {

	@Override
	public String getUrl() {
		return routes.Application.login().url();
	}

	@Override
	public void isAt() {
		assertThat(find("h1", withText("Sign in"))).hasSize(1);
	}

	public void login(String email, String password) {
		fill("input").with(email, password);
		click("button");
	}
}
