package com.geosieben.gsbworkday.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.geosieben.gsbworkday.dto.AssesmentsQuestionsDto;
import com.geosieben.gsbworkday.dto.AssignedCourseDto;
import com.geosieben.gsbworkday.dto.ContentResponse;
import com.geosieben.gsbworkday.dto.CourseAssesmentDto;
import com.geosieben.gsbworkday.dto.CourseDto;
import com.geosieben.gsbworkday.dto.CourseDtoInt;
import com.geosieben.gsbworkday.dto.UnAssignedEmployeesDto;
import com.geosieben.gsbworkday.entity.CourseContent;
import com.geosieben.gsbworkday.repository.CourseAssignmentRepository;
import com.geosieben.gsbworkday.repository.CourseCompletionRepository;
import com.geosieben.gsbworkday.repository.CourseContentRepository;
import com.geosieben.gsbworkday.repository.CourseRepository;
import com.geosieben.gsbworkday.service.CourseService;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;





@RestController
@RequestMapping("/course")
public class CourseRestController {
private final CourseService courseService;
private final CourseContentRepository courseContentRepository;
private final CourseAssignmentRepository courseAssignmentRepository;
private final HttpSession httpSession;
private final CourseCompletionRepository courseCompletionRepository;
private final CourseRepository courseRepository;



 public CourseRestController(CourseService courseService, CourseContentRepository courseContentRepository,
        CourseAssignmentRepository courseAssignmentRepository, HttpSession httpSession,
        CourseCompletionRepository courseCompletionRepository, CourseRepository courseRepository) {
    this.courseService = courseService;
    this.courseContentRepository = courseContentRepository;
    this.courseAssignmentRepository = courseAssignmentRepository;
    this.httpSession = httpSession;
    this.courseCompletionRepository = courseCompletionRepository;
    this.courseRepository = courseRepository;
}

 @PostMapping("/addCourse")
    public ResponseEntity<Map<String,String>> addCourse(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("category") String category,
            @RequestParam("image") MultipartFile image,
            @RequestParam("proficiency") String proficiency
    ) {
        // Log inputs
  Map<String,String> response =new HashMap<>();
        if (!image.isEmpty()) {
            System.out.println("Image captured: " + image.getOriginalFilename());
            
            try {
                // 1. Point to your desired workspace directory
                String projectPath = System.getProperty("user.dir");
                String uploadDirectory = projectPath + "/src/main/resources/static/course/thumbnails/";

                // 2. Ensure directories physically exist
                File directory = new File(uploadDirectory);
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                // 3. Generate a safe unique name to prevent name collisions
                String originalFilename = image.getOriginalFilename();
                String fileExtension = "";
                if (originalFilename != null && originalFilename.contains(".")) {
                    fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                }
                String safeNewFilename = UUID.randomUUID().toString() + fileExtension;

                // 4. Save file to disk
                Path targetPath = Paths.get(uploadDirectory + safeNewFilename);
                Files.write(targetPath, image.getBytes());
                
                System.out.println("Saved successfully to workspace as: " + safeNewFilename);
                String filePath="/course/thumbnails/" + safeNewFilename;
                
              response= courseService.createCourse(title, description, category, filePath,proficiency);

            } catch (IOException e) {
                System.err.println("File system write error: " + e.getMessage());
              
                response.put("status", "error");
             response.put("message", e.getMessage());
               
            }
        }
 return ResponseEntity.ok(response); 
    }
    
    @GetMapping("/getCourses")
    public List<CourseDto> getCourses() {
        List<CourseDtoInt> c =courseRepository.getCourses();
        List<CourseDto> response=new ArrayList<>();
        for(CourseDtoInt cr:c){
            CourseDto course=new CourseDto();
            course.setAssignees(courseAssignmentRepository.getCourseAssignees(cr.getCourseId()));
            course.setCourseId(cr.getCourseId());
            course.setCourseCategory(cr.getCourseCategory());
            course.setFirstName(cr.getFirstName());
            course.setLastName(cr.getLastName());
            course.setCourseDescription(cr.getCourseDescription());
            course.setProficiencyLevel(cr.getProfeciencyLevel());
            course.setCourseTitle(cr.getCourseTitle());
            course.setCourseImage(cr.getCourseImage());
            response.add(course);
        }
                return response;
    }

@PostMapping("/addSequence")
public ResponseEntity<Map<String,String>> addSequence(
                            @RequestParam("courseId") int courseId,
                              @RequestParam(value="contentId",required = false) Integer contentId,
                            @RequestParam("title") String title,
                            @RequestParam("type") int type,
                            @RequestParam("sequenceno") int sequenceno,
                            @RequestParam("summary") String summary,
                            @RequestParam("duration") int duration,
                            @RequestParam(value="file",required = false) MultipartFile file
                            ) throws IOException {
                                System.out.println(duration);
                Map<String,String> response =new HashMap<String,String>();
   String finalPath=null;
   String originalFileName=null;
           String projectPath="src/main/resources/static/course/contentfiles/";

   if (type>0 && !file.isEmpty()) {
try{
        System.out.println(file.getOriginalFilename());

        originalFileName=file.getOriginalFilename();

        File directory=new File(projectPath);
        if(!directory.exists()){
            directory.mkdirs();
        }
        String extension="";
        if(originalFileName!=null && originalFileName.contains(".")){
            extension=originalFileName.substring(originalFileName.lastIndexOf("."));
        }

        String newFileName=UUID.randomUUID().toString() +extension;

        Path targetPath=Paths.get(projectPath+newFileName);
        Files.write(targetPath, file.getBytes());
         finalPath="/course/contentfiles/"+newFileName;
        System.out.println(finalPath);
}
catch(IOException exception){

}

    }
    if(contentId == null){
    response=courseService.addContents(courseId, title, type, sequenceno, summary,finalPath,originalFileName,duration);
    }
    else{
            CourseContent content=courseContentRepository.findById(contentId).orElse(null);
            if(content!=null){
                if(originalFileName !=null){
                    Path existingPath=Paths.get("src/main/resources/static/"+content.getContentFilePath());
                    Files.deleteIfExists(existingPath);
                }
               response=  courseService.updateContent(contentId, title, type, sequenceno, summary, finalPath, originalFileName);
            }

    }
    return ResponseEntity.ok(response);
}    
@GetMapping("/getCourseContents")
public List<ContentResponse> getCourseContents(@RequestParam("courseId") int  courseId) {
    return courseService.getCouseContents(courseId);
}

@DeleteMapping("/deleteContent")
public ResponseEntity<Map<String,String>> deleteContent(@RequestParam("contentId") int contentId){
                    Map<String,String> response =new HashMap<String,String>();

    try{
        courseContentRepository.deleteById(contentId);
        response.put("status", "success");
    }
    catch(Exception e){
        response.put("status","error");
    }
    return ResponseEntity.ok(response);
}
@GetMapping("/getAssignedCourses")
public List<AssignedCourseDto> getAssignedCourses() {   
 return courseAssignmentRepository.getAssignedCourses((String) httpSession.getAttribute("eid"));
}
@PostMapping("/markCompleted")
public ResponseEntity<Map<String, String>> markCompleted(@RequestParam("assignmentid") int assignmentid,@RequestParam("contentid") int contentid) {
    
    return courseService.markContentCompleted(assignmentid, contentid);
}

@GetMapping("/getCompletedContent")
public Integer[] getCompletedContent(@RequestParam("assignmentid") int assignmentid) {
    return courseCompletionRepository.getCompletedContentIds(assignmentid);
}

@PutMapping("/markCourseComplete")
public ResponseEntity<Map<String,String>> markCourseComplete(@RequestParam("assignmentid") int assignmentid) {  
    return courseService.markCourseCompleted(assignmentid);
}
@GetMapping("/getUnAssigned")
public List<UnAssignedEmployeesDto> getUnAssignedList(@RequestParam int  courseid) {
    return courseAssignmentRepository.getunAssignedList(courseid);
}
@PostMapping("/assignCourse")
public ResponseEntity<Map<String,String>> assignCourse(@RequestParam("courseId") int courseId,@RequestParam("eids") List<String> eids) {
    //TODO: process POST request
    
return courseService.assignCourse(courseId,eids);

}

@PostMapping("/createAssesment")
public  ResponseEntity<Map<String,String>> createAssesment(
@RequestParam("assesmentname") String assesmentname,
@RequestParam("desc") String desc,
@RequestParam("passScore") int passScore,
@RequestParam("courseid") int courseid,
@RequestParam("duration") int duration
){
System.out.println(assesmentname+"\t"+desc+"\t"+courseid+"\t"+passScore+"\t"+duration);
return courseService.createAssesmentService(assesmentname, desc, passScore, courseid, duration);
}

@PostMapping("/saveQuestion")
public  ResponseEntity<Map<String,String>> addAssesmentQuestion(
@RequestParam("assesmentid") int assesmentid,
@RequestParam("a") String a,
@RequestParam("b") String b,
@RequestParam("c") String c,
@RequestParam("d") String d,
@RequestParam("correct") String correct,
@RequestParam("question") String question
){

return courseService.createAssesmentQuestionService(assesmentid, a, b, c, d, correct, question);
}
@GetMapping("/getAssesments")
public List<CourseAssesmentDto> getCourseAssesments(@RequestParam("courseid") int courseid) {
    return courseService.getCourseAssesmentDtos(courseid);
}

@GetMapping("/getQuestions")
public List<AssesmentsQuestionsDto> getQuestions(@RequestParam("assesmentid") int assesmentid) {
    return courseService.getQuestionsDtos(assesmentid);
}
}
    