package com.sparta.restapipractice.db;

import com.sparta.restapipractice.dto.StudentRequestDto;
import com.sparta.restapipractice.entity.Student;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

/**
 * create on 2024/06/14 create by IntelliJ IDEA.
 *
 * <p> New Project. </p>
 *
 * @author HoChan Son (hcson)
 * @version 1.0
 * @since 1.0
 */
@Component
public class StudentJdbcTemplate implements StudentRepository {

  private final JdbcTemplate jdbcTemplate;

  public StudentJdbcTemplate(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public void add(Long id, StudentRequestDto studentRequestDto) {
    String sql = """
        INSERT INTO student (id, student_number, name, email)
        VALUES (?, ?, ?, ?)
        """;
    jdbcTemplate.update(sql, id, studentRequestDto.getStudentNumber(), studentRequestDto.getName(), studentRequestDto.getEmail());

  }

  @Override
  public Student getById(Long id) {
    String sql = """
        SELECT id,
               student_number,
               name,
               email
        FROM student
        WHERE id = ?
        """;
    return jdbcTemplate.queryForObject(sql, new StudentRowMapper(), id);
  }

  @Override
  public Student getByNumber(String studentNumber) {
    String sql = """
        SELECT id,
               student_number,
               name,
               email
        FROM student
        WHERE student_number = ?
        """;

    return jdbcTemplate.queryForObject(sql, new StudentRowMapper(), studentNumber);
  }

  @Override
  public List<Student> getAll() {
    String sql = """
        SELECT id,
               student_number,
               name,
               email
        FROM student
        """;
    return jdbcTemplate.query(sql, new StudentRowMapper());
  }

  class StudentRowMapper implements RowMapper<Student> {

    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
      Long id = rs.getLong("id");
      String studentNumber = rs.getString("student_number");
      String name = rs.getString("name");
      String email = rs.getString("email");
      return new Student(id, studentNumber, name, email);
    }
  }
}
