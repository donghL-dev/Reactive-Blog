package com.donghun.reactiveblog.domain;

import com.donghun.reactiveblog.domain.dto.SignUpDTO;
import com.donghun.reactiveblog.domain.dto.UserDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author donghL-dev
 * @since  2019-12-01
 */
@Getter @Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Document @Builder
public class User {

    @Id
    private String id;

    private String email;

    private String username;

    @JsonIgnore
    private String password;

    private String image;

    private String bio;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updatedAt;

    private String token;

    public User createUser(SignUpDTO user, PasswordEncoder passwordEncoder) {
        return User.builder()
                .id(UUID.randomUUID().toString())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .bio("")
                .image("")
                .token("")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now()).build();
    }

    public User updateUser(UserDTO userDTO) {
        this.id = userDTO.getId() == null ? this.getId() : userDTO.getId();
        this.username = userDTO.getUsername() == null ? this.getUsername() : userDTO.getUsername();
        this.email = userDTO.getEmail() == null ? this.getEmail() : userDTO.getEmail();
        this.bio = userDTO.getBio() == null ? this.getBio() : userDTO.getBio();
        this.image = userDTO.getImage() == null ? this.getImage() : userDTO.getImage();
        this.updatedAt = LocalDateTime.now();

        return this;
    }
}
