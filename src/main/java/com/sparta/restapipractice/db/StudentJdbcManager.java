package com.sparta.restapipractice.db;

import com.sparta.restapipractice.dto.StudentRequestDto;
import com.sparta.restapipractice.entity.Student;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

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
public class StudentJdbcManager implements StudentRepository {

  @Value("${spring.datasource.url}")
  private String url;

  @Value("${spring.datasource.username}")
  private String username;

  @Value("${spring.datasource.password}")
  private String password;

  @Value("${spring.datasource.driver-class-name}")
  private String driverClassName;

  private Connection getConnection() throws SQLException, ClassNotFoundException {
    Class.forName(driverClassName);
    return DriverManager.getConnection(url, username, password);
  }


  @Override
  public void add(Long id, StudentRequestDto studentRequestDto) {
    String sql = """
        INSERT INTO student (id, student_number, name, email)
        VALUES (?, ?, ?, ?)
        """;
    Student student = convertDtoToStudent(studentRequestDto);

    try (Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setLong(1, id);
      statement.setString(2, student.getStudentNumber());
      statement.setString(3, student.getName());
      statement.setString(4, student.getEmail());
      statement.executeUpdate();
    } catch (SQLException | ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
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
    try (Connection connection = getConnection()) {
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setLong(1, id);
      ResultSet rs = statement.executeQuery();

      if (rs.next()) {
        return convertToStudent(rs);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
    return null;
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
    try (Connection connection = getConnection()) {
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setString(1, studentNumber);
      ResultSet rs = statement.executeQuery();

      if (rs.next()) {
        return convertToStudent(rs);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
    return null;
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

    List<Student> students = new ArrayList<>();

    try (Connection conn = getConnection()) {
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(sql);

      while (rs.next()) {
        students.add(convertToStudent(rs));
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
    if (CollectionUtils.isEmpty(students)) {
      return Collections.emptyList();
    }
    return students;
  }

  private Student convertToStudent(ResultSet rs) throws SQLException {
    Long id = rs.getLong(1);
    String studentNumber = rs.getString(2);
    String name = rs.getString(3);
    String email = rs.getString(4);

    return new Student(id, studentNumber, name, email);
  }
}
