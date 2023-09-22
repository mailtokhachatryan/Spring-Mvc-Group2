package com.smartcode.web.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;

@Setter
@Getter
@Entity
@ToString
@NoArgsConstructor
@Table(name = "users")
public class UserEntity {

    public UserEntity(String name, String lastName, String middleName, String email, Integer age, String password, BigDecimal balance) {
        this.name = name;
        this.lastName = lastName;
        this.middleName = middleName;
        this.email = email;
        this.age = age;
        this.password = password;
        this.balance = balance;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private boolean isVerified;

}
