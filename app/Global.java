import java.util.List;
import java.util.Map;

import com.avaje.ebean.Ebean;

import play.Application;
import play.GlobalSettings;
import play.libs.Yaml;

/**
 * Global.java
 */

/**
 * <p>Created on 2013-05-27 at 4:55:43 PM.</p>
 * @author 1615871 (<a href="mailto:dmitri_zakharov@ftn.fedex.com">Dmitri Zakharov</a>)
 *
 */
public class Global extends GlobalSettings {
	@Override
	public void onStart(Application app) {
		Map data = (Map) Yaml.load("initial-data.yml");
		
		Ebean.save((List) data.get("users"));
		Ebean.save((List) data.get("projects"));
		Ebean.save((List) data.get("tasks"));
	}
}
