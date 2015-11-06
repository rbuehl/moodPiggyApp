package controllers;

import Utils.Utils;
import models.Poll;
import models.Token;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.pollCreateForm;
import views.html.pollShowForm;
import views.html.submitVoteForm;

import java.util.Map;

import static play.data.Form.form;

/**
 * Created by robin on 03/11/15.
 */
public class Polls extends Controller {

    public Result list(){
        return ok(Json.toJson(Poll.find.findList()));
    }


    // TODO: 05/11/15 Consolidation with create. Use expected return type for differentation 
    public Result add(){
        Poll poll = new Poll();
        poll.dueDate = Utils.nextMonday(9, 15);
        poll.createTokensForPoll();
        poll.save();

        flash("success", "Poll " + poll.id + " has been created");
        return Application.GO_HOME;
    }


    // TODO: 06/11/15 use query?
    public Result close(long id){

        Poll poll = Poll.find.byId(id);

        poll.close();
        poll.save();

        return ok("Poll closed [ID:" + poll.id + "]");
    }

    public Result showVote(Long poll_id, String token_id){

        //Check if id is valid UUID
        try{
            Token token = Token.get(token_id);
            if(token != null){
                Form<Token> pollForm = form(Token.class).fill(token);
                return ok(
                        submitVoteForm.render(poll_id, pollForm)
                );
            } else
                return badRequest("No such token");

        } catch(IllegalArgumentException iae){
            return badRequest("Not a valid token");
        }
    }

    public Result score(long poll_id, String token_id){

        Poll poll = Poll.find.byId(poll_id);
        Token token = Token.get(token_id);

        if(token.value.equals(Token.values.SUBMITTED.toString())){
            return badRequest("Token was already used");
        }

        DynamicForm requestData = form().bindFromRequest();
        Map<String, String> requestDataMap = requestData.data();

        //Add to score
        int mood = Integer.parseInt(requestDataMap.get("mood"));
        poll.updateScore(mood);
        token.submitted();

        //Save changes
        token.save();
        poll.save();

        flash("success", "Token" + token.id + " has been redeemed");
        return Application.GO_HOME;
    }



    public Result get(long id){

        Form<Poll> pollForm = form(Poll.class).fill(Poll.find.byId(id));
        return ok(
                pollShowForm.render(pollForm)
        );
    }

    public Result getAsJson(long id){
        return ok(Json.toJson(Poll.find.byId(id)));
    }

    public Result create(){

        Form<Poll> pollForm = form(Poll.class);
        return ok(
                pollCreateForm.render(pollForm)
        );
    }

    public Result save(){

        Form<Poll> pollForm = form(Poll.class).bindFromRequest();
        if(pollForm.hasErrors()){
            return badRequest(pollCreateForm.render(pollForm));
        }

        pollForm.get().save();
        flash("success", "Poll " + pollForm.get().id + " has been created");
        return Application.GO_HOME;
    }

    public Result getTokens(long id){
        return ok(Json.toJson(Poll.find.byId(id).tokens));
    }

}
