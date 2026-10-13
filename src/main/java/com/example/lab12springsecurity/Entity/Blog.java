package com.example.lab12springsecurity.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Title should not be empty")
    @Size(min = 3, max = 50, message = "Title length must be between 3 and 50 characters")
    @Column(nullable = false)
    private String title;

    @NotEmpty(message = "Body should not be empty")
    @Size(min = 10, max = 1000, message = "Body length must be between 10 and 1000 characters")
    @Column(nullable = false, length = 1000)
    private String body;

    @ManyToOne
    @JsonIgnore
    private User user;
}
