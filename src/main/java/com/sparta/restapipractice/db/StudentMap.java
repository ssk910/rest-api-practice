package com.sparta.restapipractice.db;

import com.sparta.restapipractice.dto.StudentRequestDto;
import com.sparta.restapipractice.entity.Student;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

// DB 대용.
@Component
public class StudentMap implements StudentRepository {
  private final Map<Long, Student> studentMap;

  public StudentMap() {
    this.studentMap = new ConcurrentHashMap<>();
  }

  @Override
  public void add(Long id, StudentRequestDto studentRequestDto) {
    Student student = this.convertDtoToStudent(studentRequestDto);
    this.studentMap.put(id, student);
  }

  // 특정 ID의 Student를 가져오는 메소드.
  @Override
  public Student getById(Long id) {
    return this.studentMap.get(id);
  }

  @Override
  public Student getByNumber(String studentNumber) {
    // Student list에서 studentNumber에 해당하는 Student 찾는다.
    List<Student> studentList = this.studentMap.values().stream()
        .filter(student -> student.getStudentNumber().equals(studentNumber))
        .toList();

    return studentList.get(0);
  }

  @Override
  public List<Student> getAll() {
    return this.studentMap.values().stream().toList();
  }
}
