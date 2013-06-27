/**
 * User.java
 */
package models;

import java.util.List;

import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.Table;

import play.db.ebean.Model;
import play.data.validation.Constraints;
import play.data.format.Formats;

/**
 * <p>Created on 2013-05-24 at 1:49:24 PM.</p>
 *
 */
@Entity
@Table(name = "account")
public class User extends Model {
	
	@Id
	@Constraints.Required
	@Formats.NonEmpty
	public String email;
	
	@Constraints.Required	
	public String name;
	
	@Constraints.Required	
	public String password;

	public static Finder<String, User> find = new Finder<String, User>(String.class, User.class);
	
	public User() {}
	
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

    /**
     * Retrieve all users.
     */
    public static List<User> findAll() {
        return find.all();
    }

    /**
     * Retrieve a User from email.
     */
    public static User findByEmail(String email) {
        return find.where().eq("email", email).findUnique();
    }
	
	public static User authenticate(String email, String password) {
		return find.where()
				.eq("email", email)
				.eq("password", password)
				.findUnique();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
    public String toString() {
        return "User(" + email + ")";
    }
}
