package com.geosieben.gsbworkday.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TestDto {
private String name;
private String age;
private String phone;
public TestDto(String name, String age, String phone) {
    this.name = name;
    this.age = age;
    this.phone = phone;
}

}
