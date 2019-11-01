package info.lzzy.models.view;

public enum TeacherValidType {
	WAITING_FOR_THE_PERMISSION("等待管理员许可(WAITING_FOR_THE_PERMISSION)"),PERMISSION("许可(PERMISSION)"),WITHOUT_PERMISSION("未许可(WITHOUT_PERMISSION)");
	private String name;

	TeacherValidType(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static TeacherValidType getInstance(int ordinal){
        for (TeacherValidType type : TeacherValidType.values()){
            if (type.ordinal() == ordinal){
                return type;
            }
        }
        return null;
    }
}
