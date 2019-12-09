package mz.org.fgh.mentoring.util;

/**
 * Created by Damaceno Lopes on 6/12/19.
 *
 * This class contains global variables that can be used to
 * Handle some form validations it is beeing used by the form
 * 'Monitoria do ATS'
 */
public class Validations{
    private static Validations instance;

    // Global variables
    private int question1,question2,question3,question4,question5;

    // Restrict the constructor from being instantiated
    private Validations(){}

    public int getQuestion1() {
        return question1;
    }

    public void setQuestion1(int question1) {
        this.question1 = question1;
    }

    public int getQuestion2() {
        return question2;
    }

    public void setQuestion2(int question2) {
        this.question2 = question2;
    }

    public int getQuestion3() {
        return question3;
    }

    public void setQuestion3(int question3) {
        this.question3 = question3;
    }

    public int getQuestion4() {
        return question4;
    }

    public void setQuestion4(int question4) {
        this.question4 = question4;
    }

    public int getQuestion5() {
        return question5;
    }

    public void setQuestion5(int question5) {
        this.question5 = question5;
    }

    public static synchronized Validations getInstance(){
        if(instance==null){
            instance=new Validations();
        }
        return instance;
    }
}
