package com.sparta.restapipractice.db;

import com.sparta.restapipractice.dto.StudentRequestDto;
import com.sparta.restapipractice.entity.Student;
import java.util.List;

/**
 * create on 2024/06/14 create by IntelliJ IDEA.
 *
 * <p> New Project. </p>
 *
 * @author HoChan Son (hcson)
 * @version 1.0
 * @since 1.0
 */
public interface StudentRepository {

  void add(Long id, StudentRequestDto studentRequestDto);

  Student getById(Long id);

  Student getByNumber(String studentNumber);

  List<Student> getAll();

  // DTO를 Student로 변환.
  default Student convertDtoToStudent(StudentRequestDto studentRequestDto) {
    return new Student(
        studentRequestDto.getId(),
        studentRequestDto.getStudentNumber(),
        studentRequestDto.getName(),
        studentRequestDto.getEmail()
    );
  }
}
