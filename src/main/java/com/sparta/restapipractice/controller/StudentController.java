package com.sparta.restapipractice.controller;


import com.sparta.restapipractice.db.StudentMap;
import com.sparta.restapipractice.dto.StudentRequestDto;
import com.sparta.restapipractice.entity.Student;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
public class StudentController {

  private final StudentMap studentMap;

  public StudentController(StudentMap studentMap) {
    this.studentMap = studentMap;
  }

  // 학생 등록.
  // (POST)  /students
  @PostMapping
  public ResponseEntity<?> addStudent(
      @RequestBody StudentRequestDto studentRequestDto) {
    this.studentMap.add(studentRequestDto.getId(), studentRequestDto);

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body("학생 등록이 완료되었습니다.");
  }

  // id로 학생 조회.
  // (GET)  /students/{id}
  @GetMapping("/{id}")
  public ResponseEntity<?> getStudentById(@PathVariable Long id) {
    Student student = this.studentMap.getById(id);
    return ResponseEntity.status(HttpStatus.OK).body(student);
  }

  // 학생 전체 조회.
  // (GET)  /students
  @GetMapping
  public ResponseEntity<?> getStudents() {
    List<Student> studentList = this.studentMap.getAll();
    return ResponseEntity.status(HttpStatus.OK).body(studentList);
  }

  // 특정 학번에 해당하는 학생 조회
  // (GET)  /students?number=20245190
  @GetMapping(params = "number")
  public ResponseEntity<?> getStudentByNumber(
      @RequestParam(value = "number") String studentNumber) {
    Student student = this.studentMap.getByNumber(studentNumber);
    return ResponseEntity.status(HttpStatus.OK).body(student);
  }
}