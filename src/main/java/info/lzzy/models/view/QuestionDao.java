package info.lzzy.models.view;

import info.lzzy.models.Option;
import info.lzzy.models.Question;

import java.util.List;

public class QuestionDao extends Question {
    private List<Option> options;

    private String strQuestionType;

    public QuestionDao(Question question,List<Option> options){
        this.setId(question.getId());
        this.setAnalysis(question.getAnalysis());
        this.setContent(question.getContent());
        this.setNumber(question.getNumber());
        this.setQuestionType(question.getQuestionType());
        this.setPracticeId(question.getPracticeId());
        QuestionType type=QuestionType.getInstance(question.getQuestionType());
        if (type!=null){
            this.strQuestionType = type.toString();
        }else {
            this.strQuestionType = "null";
        }
        this.options = options;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public String getStrQuestionType() {
        return strQuestionType;
    }

    public void setStrQuestionType(String strQuestionType) {
        this.strQuestionType = strQuestionType;
    }
}
