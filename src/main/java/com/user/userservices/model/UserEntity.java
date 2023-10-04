package com.user.userservices.model;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name ="User_Details")
public class UserEntity {

    @Id
    @Column(nullable = false,name = "user_ID")
    private String userID;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,name = "user_regNo")
    private long regNo;
    @Column(nullable = false,name = "password")
    private String password;
    @Column(nullable = false,name = "user_fullName")
    private String userName;
    @Column(nullable = false,name="user_emailID")
    private String userEmail;
    @Column(nullable = false,name="user_mobile",length = 10)
    private long userMobile;
    @Column(nullable = false,name="user_age")
    private int age;
    @Column(nullable = false,name="user_gender")
    private String gender;

}
