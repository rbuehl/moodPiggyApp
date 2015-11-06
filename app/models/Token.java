package models;


import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;
import java.util.UUID;

/**
 * Created by robin on 04/11/15.
 *
 * Token as key-value-store
 */
@Entity
public class Token extends Model {

    public enum values { NEW, SUBMITTED}

    @Id
    public UUID id;

    @Constraints.Required
    public String value;


    /***
     * Ebean finder
     */
    public static Finder<Long, Token> find = new Finder<Long,Token>(Token.class);


    public static Token get(String id){
        return Token.find.where().idEq(id).findUnique();
    }


    public void submitted(){
        this.value = "" + values.SUBMITTED;
    }


}
