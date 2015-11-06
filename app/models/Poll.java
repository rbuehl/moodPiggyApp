package models;

import com.avaje.ebean.Expr;
import com.avaje.ebean.Model;
import controllers.Application;
import play.data.DynamicForm;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by robin on 03/11/15.
 */
@Entity
public class Poll extends Model {

    public static final int BEST_MOOD_SCORE = 2;
    @Id
    public Long id;

    public int score = 0;

    @Formats.DateTime(pattern="yy-mm-dd")
    @Constraints.Required
    public Date dueDate;

    @OneToMany(targetEntity = Token.class, cascade = CascadeType.ALL)
    public List<Token> tokens;


    public static Finder<Long, Poll> find = new Finder<Long,Poll>(Poll.class);

    public void createTokensForPoll(){
        List<User> users = User.find.all();

        for (User user : users){
            Token token = new Token();
            token.value = "" + Token.values.NEW;
            this.tokens.add(token);
        }
    }


    public void close(){

        if(tokens.isEmpty()){
            throw new IllegalArgumentException("Poll is already closed [ID:" + id + "]");
        }

        List<Token> tokens_submitted = new ArrayList<Token>();

        for (Token token : tokens){
            System.out.println("token = " + token);
            if(token.value.equals(Token.values.SUBMITTED.toString())){
                tokens_submitted.add(token);
            }
        }

        System.out.println("tokens_submitted = " + tokens_submitted);

        int token_count = tokens.size();
        int submit_count = tokens_submitted.size();
        this.setFinalScore(token_count, submit_count);

        for (Token token : tokens){
            token.delete();
        }
    }


    public void updateScore(int mood_score){
        System.out.println("mood_score = " + mood_score);
        this.score += mood_score;
        System.out.println("score = " + score);
    }


    public void setFinalScore(int token_count, int submit_count){

        // TODO: 06/11/15 to this only once -> after dueDate
        // if(dueDate.before(new Date())){

        int perfect_score = BEST_MOOD_SCORE * token_count;
        int max_score = BEST_MOOD_SCORE * submit_count;
        System.out.println("max_score = " + max_score);
        System.out.println("perfect_score = " + perfect_score);
        System.out.println("submit_count = " + submit_count);

        double score_percentage =  (double) score / (double) max_score * 100;

        System.out.println("score_percentage = " + score_percentage);

        this.score = (int) score_percentage;

    }

}
