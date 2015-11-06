package controllers;

import models.Poll;
import play.*;
import play.mvc.*;

import views.html.*;

import java.util.List;

public class Application extends Controller {

    public static Result GO_HOME = redirect(
            routes.Application.index()
    );

    public Result index() {

        List<Poll> polls = Poll.find.findList();

        return ok(index.render(polls));

    }

}
