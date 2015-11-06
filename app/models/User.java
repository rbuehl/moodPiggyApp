package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by robin on 04/11/15.
 */
@Entity
@Table(name="my_user")
public class User extends Model {

    @Id
    public long id;

    @Constraints.Required
    public String lastname;

    @Constraints.Required
    public String firstname;

    @Constraints.Required
    @Constraints.Email
    public String email;

    /***
     * Ebean finder
     */
    public static Finder<Long, User> find = new Finder<Long,User>(User.class);


}
