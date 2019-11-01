package info.lzzy.models.view;

import info.lzzy.models.Course;

public class CourseDao extends Course {
    
    private Integer practiceSize;
    
    public Integer getPracticeSize() {
		return practiceSize;
	}

	public void setPracticeSize(Integer practiceSize) {
		this.practiceSize = practiceSize;
	}
}