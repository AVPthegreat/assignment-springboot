package com.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "students", uniqueConstraints = {
    @UniqueConstraint(columnNames = "email")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @Column(nullable = false, length = 100)
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    @Column(nullable = false, length = 15)
    private String phone;

    @NotBlank(message = "Department is required")
    @Column(nullable = false, length = 100)
    private String department;

    @NotNull(message = "Academic year is required")
    @Column(name = "academic_year", nullable = false)
    private Integer year;    // Academic year (1 to 4)

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "student_course",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private Set<Course> courses = new HashSet<>();

    // Helper methods
    public void enrollInCourse(Course course) {
        this.courses.add(course);
        course.getStudents().add(this);
    }

    public void dropCourse(Course course) {
        this.courses.remove(course);
        course.getStudents().remove(this);
    }
}
