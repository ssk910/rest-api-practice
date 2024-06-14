package com.sparta.restapipractice.controller;


import com.sparta.restapipractice.db.StudentJdbcManager;
import com.sparta.restapipractice.db.StudentMap;
import com.sparta.restapipractice.dto.StudentRequestDto;
import com.sparta.restapipractice.entity.Student;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jdbc-students")
public class StudentJdbcController {

  private final StudentJdbcManager jdbcStudentManager;

  public StudentJdbcController(StudentJdbcManager jdbcStudentManager) {
    this.jdbcStudentManager = jdbcStudentManager;
  }

  // 학생 등록.
  // (POST)  /students
  @PostMapping
  public ResponseEntity<?> addStudent(
      @RequestBody StudentRequestDto studentRequestDto) {
    this.jdbcStudentManager.add(studentRequestDto.getId(), studentRequestDto);

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body("학생 등록이 완료되었습니다.");
  }

  // id로 학생 조회.
  // (GET)  /students/{id}
  @GetMapping("/{id}")
  public ResponseEntity<?> getStudentById(@PathVariable Long id) {
    Student student = this.jdbcStudentManager.getById(id);
    return ResponseEntity.status(HttpStatus.OK).body(student);
  }

  // 학생 전체 조회.
  @GetMapping
  public ResponseEntity<?> getStudents(
      @RequestParam(value = "number", required = false) String studentNumber) {
    if (StringUtils.hasLength(studentNumber)) {
      Student student = this.jdbcStudentManager.getByNumber(studentNumber);
      return ResponseEntity.status(HttpStatus.OK).body(student);
    }

    List<Student> studentList = this.jdbcStudentManager.getAll();
    return ResponseEntity.status(HttpStatus.OK).body(studentList);
  }
}
