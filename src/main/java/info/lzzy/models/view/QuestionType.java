package info.lzzy.models.view;

/**
 * @author lzzy_gxy on 2019/4/17.
 * Description:
 */
public enum QuestionType {
    /**
     * 题目类型
     */
    SINGLE_CHOICE("单项选择"),MULTI_CHOICE("多选选择"),JUDGE("判断题");

    private String name; 

    QuestionType(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static QuestionType getInstance(int ordinal){
        for (QuestionType type : QuestionType.values()){
            if (type.ordinal() == ordinal){
                return type;
            }
        }
        return null;
    }
}
