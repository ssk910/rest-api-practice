package com.sparta.restapipractice.dto;

import lombok.Getter;

@Getter
public class StudentRequestDto {
  /**
   * ID (엔티티 고유번호)
   */
  private final Long id;

  /**
   * 학번(e.g. 20245111)
   */
  private final String studentNumber;

  /**
   * 이름
   */
  private final String name;

  /**
   * 이메일
   */
  private final String email;

  public StudentRequestDto(Long id, String studentNumber, String name, String email) {
    this.id = id;
    this.studentNumber = studentNumber;
    this.name = name;
    this.email = email;
  }


}
