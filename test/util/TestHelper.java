package util;
import java.util.List;
import java.util.Map;

import play.libs.Yaml;
import models.User;

import com.avaje.ebean.Ebean;

/**
 * TestHelper.java
 */

/**
 * <p>Created on 2013-06-27 at 11:28:16 AM.</p>
 * @author 1615871 (<a href="mailto:dmitri_zakharov@ftn.fedex.com">Dmitri Zakharov</a>)
 *
 */
public class TestHelper {
	
	public static class TestData {
		
		public static void insert() {
			TestData.insert("test-data.yml");
		}
		
		public static void insert(String ymlDataFile) {
			if (Ebean.find(User.class).findRowCount() == 0) {
				Map<String, List<Object>> all = (Map<String, List<Object>>) Yaml.load(ymlDataFile);
				
				// Insert users first
				Ebean.save(all.get("users"));
				
				// Insert projects
				Ebean.save(all.get("projects"));
				for (Object project : all.get("projects")) {
					// Insert the project/user relationship
					Ebean.saveManyToManyAssociations(project, "members");
				}
				
				// Insert tasks
				Ebean.save(all.get("tasks"));
			}
		}
	}
}
