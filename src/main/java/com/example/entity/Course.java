package com.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "courses", uniqueConstraints = {
    @UniqueConstraint(columnNames = "courseCode")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Course name is required")
    @Size(min = 3, max = 150, message = "Course name must be between 3 and 150 characters")
    @Column(nullable = false, length = 150)
    private String courseName;

    @NotBlank(message = "Course code is required")
    @Pattern(regexp = "^[A-Z]{2,4}[0-9]{3,4}$",
             message = "Course code must follow the pattern: 2-4 uppercase letters followed by 3-4 digits (e.g. CS101)")
    @Column(nullable = false, unique = true, length = 20)
    private String courseCode;

    @NotBlank(message = "Instructor name is required")
    @Column(nullable = false, length = 100)
    private String instructor;

    @NotNull(message = "Credits are required")
    @Min(value = 1, message = "Credits must be at least 1")
    @Max(value = 6, message = "Credits cannot exceed 6")
    @Column(nullable = false)
    private Integer credits;

    @NotBlank(message = "Department is required")
    @Column(nullable = false, length = 100)
    private String department;

    @ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private Set<Student> students = new HashSet<>();
}
