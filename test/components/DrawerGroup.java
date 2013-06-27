/**
 * DrawerGroup.java
 */
package components;

import org.fluentlenium.core.*;
import org.fluentlenium.core.domain.*;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.fluentlenium.FluentLeniumAssertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.*;
import java.util.*;
import com.google.common.base.Predicate;

/**
 * <p>
 * Created on 2013-06-27 at 2:25:18 PM.
 * </p>
 * 
 */
public class DrawerGroup {
	private final FluentWebElement element;

	public DrawerGroup(FluentWebElement element) {
		this.element = element;
	}

	public List<DrawerProject> projects() {
		List<DrawerProject> projects = new ArrayList<DrawerProject>();
		for (FluentWebElement e : (Iterable<FluentWebElement>) element.find("ul > li")) {
			projects.add(new DrawerProject(e));
		}
		return projects;
	}

	public DrawerProject project(String name) {
		for (DrawerProject p : projects()) {
			if (p.name().equals(name)) {
				return p;
			}
		}
		return null;
	}

	public Predicate hasProject(final String name) {
		return new Predicate() {
			public boolean apply(Object o) {
				return project(name) != null;
			}
		};
	}

	public void newProject() {
		element.findFirst(".newProject").click();
	}
}
