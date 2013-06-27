/**
 * Dashboard.java
 */
package pages;

import org.fluentlenium.core.*;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.fluentlenium.FluentLeniumAssertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.*;

import components.*;

/**
 * <p>
 * Created on 2013-06-27 at 2:16:35 PM.
 * </p>
 * 
 */
public class Dashboard extends FluentPage {
	
	public String getUrl() {
		return "/";
	}

	public void isAt() {
		assertThat(find("h4", withText("Dashboard"))).hasSize(1);
	}

	public Drawer drawer() {
		return Drawer.from(this);
	}

}
