import com.avaje.ebean.Ebean;
import models.User;
import play.*;
import play.libs.Yaml;

import java.util.*;

public class Global extends GlobalSettings {

    @Override
    public void onStart(Application app) {
        System.out.println("Starting data insertion");

        Map<String, List<Object>>  all = (Map<String, List<Object>>) Yaml.load("initial-data.yml");

        // Check if the database is empty
        if (User.find.findRowCount() == 0) {
            if (Ebean.find(User.class).findRowCount() == 0) {
                if (all.get("users") == null){
                    System.out.println( );
                    System.out.println("****no users found****");
                    System.out.println( );
                }
                else
                    //Insert AttributeTypes
                    Ebean.save(all.get("users"));
            }
        }

        System.out.println("end of data insertion");
    }

}