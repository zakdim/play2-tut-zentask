/**
 * User.java
 */
package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

/**
 * <p>Created on 2013-05-24 at 1:49:24 PM.</p>
 *
 */
@Entity
public class User extends Model {
	
	@Id
	public String email;
	public String name;
	public String password;

	public static Finder<String, User> find = new Finder<String, User>(
			String.class, User.class);
	
	/**
	 * @param email
	 * @param name
	 * @param password
	 */
	public User(String email, String name, String password) {
		this.email = email;
		this.name = name;
		this.password = password;
	}

	public static User authenticate(String email, String password) {
		return find.where().eq("email", email)
				.eq("password", password).findUnique();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [email=" + email + ", name=" + name + "]";
	}

}
