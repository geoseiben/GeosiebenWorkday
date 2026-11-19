package com.geosieben.gsbworkday.service;

import com.geosieben.gsbworkday.repository.AssesmentQuestionsRepository;
import com.geosieben.gsbworkday.repository.CourseAssignmentRepository;
import com.geosieben.gsbworkday.repository.CourseContentRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.geosieben.gsbworkday.dto.AssesmentsQuestionsDto;
import com.geosieben.gsbworkday.dto.AssigneesDto;
import com.geosieben.gsbworkday.dto.ContentResponse;
import com.geosieben.gsbworkday.dto.CourseAssesmentDto;
import com.geosieben.gsbworkday.dto.CourseDto;
import com.geosieben.gsbworkday.dto.CourseDtoInt;
import com.geosieben.gsbworkday.entity.AssesmentQuestions;
import com.geosieben.gsbworkday.entity.Course;
import com.geosieben.gsbworkday.entity.CourseAssesment;
import com.geosieben.gsbworkday.entity.CourseAssignment;
import com.geosieben.gsbworkday.entity.CourseCompletion;
import com.geosieben.gsbworkday.entity.CourseContent;
import com.geosieben.gsbworkday.repository.BasicInfoRepository;
import com.geosieben.gsbworkday.repository.CourseAssesmentRepository;
import com.geosieben.gsbworkday.repository.CourseCompletionRepository;
import com.geosieben.gsbworkday.repository.CourseRepository;

import jakarta.servlet.http.HttpSession;
@Service
public class CourseService {
    private final AssesmentQuestionsRepository assesmentQuestionsRepository;
    private final CourseAssignmentRepository courseAssignmentRepository;
    private final CourseContentRepository courseContentRepository;
    private final CourseRepository courseRepository;
    private final BasicInfoRepository basicInfoRepository;
    private final HttpSession httpSession;
    private final CourseCompletionRepository courseCompletionRepository;
    private  final CourseAssesmentRepository courseAssesmentRepository;

    public CourseService(CourseRepository courseRepository,BasicInfoRepository basicInfoRepository,
         HttpSession httpSession, CourseContentRepository courseContentRepository, CourseAssignmentRepository courseAssignmentRepository,
        CourseCompletionRepository courseCompletionRepository,CourseAssesmentRepository courseAssesmentRepository,  AssesmentQuestionsRepository assesmentQuestionsRepository) {
        this.courseRepository = courseRepository;
        this.basicInfoRepository=basicInfoRepository;
        this.httpSession=httpSession;
        this.courseContentRepository = courseContentRepository;
        this.courseAssignmentRepository = courseAssignmentRepository;
        this.courseCompletionRepository=courseCompletionRepository;
        this.courseAssesmentRepository =courseAssesmentRepository;
        this.assesmentQuestionsRepository = assesmentQuestionsRepository;
    }

    public Map<String,String> createCourse(String title,String description,String category,String filepath,String proficiency){
        Map<String,String> response =new HashMap<>();
        try{
            Course course=new Course();
            course.setCourseTitle(title);
            course.setCourseDescription(description);
            course.setCourseImage(filepath);
            course.setCategory(category);
            course.setProfeciencyLevel(proficiency);
            course.setCreatedBy(basicInfoRepository.findEmployeeBasicInfoByEID((String)httpSession.getAttribute("eid")));
            if(courseRepository.save(course).getCourseId()!=null){
            response.put("status", "success");
             response.put("message", "Saved");
            }
            else{

            response.put("status", "error");
             response.put("message", "Not Found");
            }
        }catch(Exception e){
            response.put("status", "error");
             response.put("message", e.getMessage());
        }

        return response;        
    }

    public List<CourseDto> getCourses(){
        List<CourseDtoInt> courses=courseRepository.getCourses();
        List<CourseDto> response=new ArrayList<>();
        for (CourseDtoInt course : courses) {
            List<AssigneesDto> dto=courseAssignmentRepository.getCourseAssignees(course.getCourseId());
            CourseDto c=new CourseDto();
            c.setAssignees(dto);
            c.setCourseCategory(course.getCourseCategory());
            c.setCourseDescription(course.getCourseDescription());
            c.setCourseId(course.getCourseId());
            c.setCourseImage(course.getCourseImage());
            c.setCourseTitle(course.getCourseTitle());
            c.setLastName(course.getLastName());
            c.setFirstName(course.getFirstName());
            response.add(c);
        }
        return response;
    }
    public Map<String,String> addContents(int courseId,String title,int type,int sequenceno,String summary,String filepath,String originalFileName,int duration){
        Map<String,String> response =new HashMap<String,String>();
        try{
            CourseContent content=new CourseContent();
            content.setContentTitle(title);
            content.setContentDescription(summary);
            if (filepath !=null) 
                {
                    content.setContentFilePath(filepath);
                    content.setOriginalFileName(originalFileName);
                }
            content.setDurationInSec(duration*60);
            content.setSequenceNo(sequenceno);
            content.setFileType(type);
            content.setCourse(courseRepository.findById(courseId).orElse(null));
            content.setCreatedBy(basicInfoRepository.findEmployeeBasicInfoByEID((String)httpSession.getAttribute("eid")));
            if(courseContentRepository.save(content).getContentId()>0){
                response.put("status", "success");
                 response.put("message", "Content Added Succesfully");
            }
            else{
                                response.put("status", "error");
                 response.put("message", "Unable to save content");
            }

        }
        catch(Exception exception){
            response.put("status", "error");
        }
        return response;
    }

    public List<ContentResponse> getCouseContents(int courseid){
        return courseContentRepository.getContents(courseid);
    }

    public Map<String,String> updateContent(Integer contentId,String title,int type,int sequenceno,String summary,String filepath,String originalFileName){
        System.out.println("ContentId->"+contentId+"\t Title->"+title+"\t Type->"+type+"\t Sequenceno->"+sequenceno+"\t Summary->"+summary+"\t Filepath->"+filepath+"\t OriginalFileName->"+originalFileName);
                Map<String,String> response =new HashMap<String,String>();

        CourseContent content=courseContentRepository.findById(contentId).orElse(null);
        if(content !=null){
        try{
            content.setContentTitle(title);
            content.setContentDescription(summary);
            if (filepath !=null) 
                {
                    content.setContentFilePath(filepath);
                    content.setOriginalFileName(originalFileName);
                }

            content.setSequenceNo(sequenceno);
            content.setFileType(type);
            content.setCreatedBy(basicInfoRepository.findEmployeeBasicInfoByEID((String)httpSession.getAttribute("eid")));
            if(courseContentRepository.save(content).getContentId()>0){
                response.put("status", "success");
                 response.put("message", "Content Added Succesfully");
            }
            else{
                                response.put("status", "error");
                 response.put("message", "Unable to save content");
            }

        }
        catch(Exception exception){
            response.put("status", "error");
        }
        }
        return response;
    }

        public ResponseEntity<Map<String,String>> markContentCompleted(int assignmentid,int contentid){
                Map<String,String> response =new HashMap<String,String>();
                try{
                CourseCompletion comp=new CourseCompletion();
                comp.setCourseAssignment(courseAssignmentRepository.findById(assignmentid).orElse(null));
                comp.setCourseContent(courseContentRepository.findById(contentid).orElse(null));
                comp.setCompletedDate(LocalDate.now());
                comp.setIsCompleted(1);
                courseCompletionRepository.save(comp);
                 response.put("status", "success");
   }
        catch(Exception exception){
            response.put("status", "error");
            response.put("message", exception.getMessage());
        }
                return ResponseEntity.ok(response);
        }

public ResponseEntity<Map<String,String>> markCourseCompleted(int assignmentid){
        Map<String,String> response =new HashMap<String,String>();
                    try{
                CourseAssignment comp=courseAssignmentRepository.findById(assignmentid).orElse(null);      
                if(comp!=null){
                comp.setIsCompleted(1);
                comp.setCompletionDate(LocalDate.now());
                courseAssignmentRepository.save(comp);
            response.put("status", "success");

                }
                else {
                response.put("status", "error");
                response.put("message", "Assignment Data not Found");
                }
}catch(Exception e){
                response.put("status", "error");
                response.put("message", e.getMessage());


}
return ResponseEntity.ok(response);
}
public ResponseEntity<Map<String,String>> assignCourse(int courseid,List<String> eids){
            Map<String,String> response =new HashMap<String,String>();

    try{
for (String  eid: eids) {
    CourseAssignment assign=new CourseAssignment();
    assign.setAssigneTo(basicInfoRepository.findEmployeeBasicInfoByEID(eid));
    assign.setAssignedOn(LocalDate.now());
    assign.setAssignedBy(basicInfoRepository.findEmployeeBasicInfoByEID((String) httpSession.getAttribute("eid")));
    assign.setCourse(courseRepository.findById(courseid).orElse(null));
    courseAssignmentRepository.save(assign);

}
    response.put("status", "success");
}
catch(Exception e){
        response.put("status", "error");
            response.put("message", e.getMessage());
}
return ResponseEntity.ok(response);
}

 public  ResponseEntity<Map<String,String>> createAssesmentService( String assesmentname,String desc,double passScore, int courseid,int duration){
   
    Map<String,String> response=new HashMap<>();
    try{
        CourseAssesment assesment =new CourseAssesment();
        assesment.setAssesmentDescription(desc);
        assesment.setAssesmentTitle(assesmentname);
        assesment.setDurationInMinutes(duration);
        assesment.setPassingScore(passScore);
        assesment.setCreatedBy(basicInfoRepository.findEmployeeBasicInfoByEID((String) httpSession.getAttribute("eid")));
        assesment.setCourse(courseRepository.findById(courseid).orElse(null));
        courseAssesmentRepository.save(assesment);
        response.put("status", "success");
        response.put("message", "Assesment Created SuccessFully");

    }
    catch(Exception e){
        response.put("status", "error");
        response.put("message", "Assesment Creation Failed");
    }

    return ResponseEntity.ok(response);
}

public  ResponseEntity<Map<String,String>> createAssesmentQuestionService(int assesmentid,String a,String b,String c,String d,String correct,String questions){
        Map<String,String> response=new HashMap<>();

    try{
        AssesmentQuestions question=new AssesmentQuestions();
        question.setQuestion(questions);
        question.setOptionA(a);
        question.setOptionB(b);
        question.setOptionC(c);
        question.setOptionD(correct);
        question.setAnswer(correct);
        question.setAssesment(courseAssesmentRepository.findById(assesmentid).orElse(null));
        assesmentQuestionsRepository.save(question);
             response.put("status", "success");
        response.put("message", "Question Created SuccessFully");
    }

    catch(Exception e){
                response.put("status", "error");
            response.put("message", e.getMessage());
    }
    return ResponseEntity.ok(response);
}

public List<CourseAssesmentDto> getCourseAssesmentDtos(int courseid){
    return assesmentQuestionsRepository.getCourseAssesments(courseid);
}
public List<AssesmentsQuestionsDto> getQuestionsDtos(int assesmentid){
    List<AssesmentQuestions> questions=assesmentQuestionsRepository.getCourseAssesmentQuestions(assesmentid);
    List<AssesmentsQuestionsDto> response=new ArrayList<>();
    for (AssesmentQuestions assesmentQuestion : questions) {
        AssesmentsQuestionsDto dto=new AssesmentsQuestionsDto();
        dto.setId(assesmentQuestion.getQid());
        List<String> options=new ArrayList<>();
        options.add(assesmentQuestion.getOptionA());
        options.add(assesmentQuestion.getOptionB());
        options.add(assesmentQuestion.getOptionC());
        options.add(assesmentQuestion.getOptionD());
        dto.setOptions(options);
        int correctIndex=assesmentQuestion.getOptionA().equals(assesmentQuestion.getAnswer())?0:
        (assesmentQuestion.getOptionB().equals(assesmentQuestion.getAnswer())?1:
        (assesmentQuestion.getOptionC().equals(assesmentQuestion.getAnswer())?2:3
        ));
        dto.setCorrectIndex(correctIndex);
        dto.setText(assesmentQuestion.getQuestion());
        response.add(dto);
    }
    return response;
}
}
