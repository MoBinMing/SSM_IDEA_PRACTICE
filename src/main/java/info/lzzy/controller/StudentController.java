package info.lzzy.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import info.lzzy.models.Option;
import info.lzzy.models.Practice;
import info.lzzy.models.Question;
import info.lzzy.models.Student;
import info.lzzy.utils.PracticesUtil;

@Controller
@RequestMapping("/Student")
public class StudentController extends BaceController{
	
	@RequestMapping("/studentApiIndex")
	   public String studentIndex() {
		return "Student/StudentApiIndex";
	}

	
}
