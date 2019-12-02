package info.lzzy.models;

public class Enrollment {
    private Integer id;

    private Integer courseId;

    private String studentId;

    private Integer takeEffect;

    private String teacherId;

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId == null ? null : studentId.trim();
    }

    public Integer getTakeEffect() {
        return takeEffect;
    }

    public void setTakeEffect(Integer takeEffect) {
        this.takeEffect = takeEffect;
    }
}