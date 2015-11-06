package controllers;

import models.Token;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.submitVoteForm;

import java.util.UUID;

import static play.data.Form.form;

/**
 * Created by robin on 04/11/15.
 */
public class Tokens extends Controller {


    public Result list(){
        return ok(Json.toJson(Token.find.findList()));
    }

    public Result create(){
        Token token = new Token();
        token.save();

        return ok(Json.toJson(token));
    }



    public Result update(String id, String value){
        return TODO;
    }



}
