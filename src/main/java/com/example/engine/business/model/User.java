package com.example.engine.business.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @JsonProperty(value = "email")
    @NotBlank(message = "Email address is required!")
    @Pattern(regexp = "^\\w[\\w.]+@\\w+\\.\\w+$", message = "Invalid format!")
    private final String username;

    @NotBlank(message = "Password must be at least 5 characters in length")
    @Size(min = 5, message = "Password must be at least 5 characters in length")
    private String password;

    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<Quiz> quizzes;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String roles;

    @OneToMany(mappedBy = "solvedBy", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<QuizCompleted> quizzesSolved;

    public User() {
        this(null, null, null, null, null);
    }

    public User(String email, String password, Set<Quiz> quizzes, String roles, List<QuizCompleted> quizzesSolved) {
        this.username = Optional.ofNullable(email).isPresent() ? email : "";
        this.password = Optional.ofNullable(password).isPresent() ? password : "";
        this.quizzes = Optional.ofNullable(quizzes).isPresent() ? quizzes : new HashSet<>();
        this.roles = Optional.ofNullable(roles).isPresent() ? roles : "";
        this.quizzesSolved = Optional.ofNullable(quizzesSolved).isPresent() ? quizzesSolved : new ArrayList<>();
    }

    public User(UserBuilder userBuilder) {
        this.username = userBuilder.username;
        this.password = userBuilder.password;
        this.quizzes = userBuilder.quizzes;
        this.roles = userBuilder.roles;
        this.quizzesSolved = userBuilder.quizzesSolved;
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public static class UserBuilder {
        private String username;
        private String password;
        private Set<Quiz> quizzes;
        private String roles;
        private List<QuizCompleted> quizzesSolved;

        public UserBuilder() {

        }

        public UserBuilder username(String username) {
            this.username = username;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder quizzes(Set<Quiz> quizzes) {
            this.quizzes = quizzes;
            return this;
        }

        public UserBuilder roles(String roles) {
            this.roles = roles;
            return this;
        }

        public UserBuilder quizzesSolved(List<QuizCompleted> quizzesSolved) {
            this.quizzesSolved = quizzesSolved;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", quizzes=" + quizzes.size() +
                ", roles='" + roles + '\'' +
                ", quizzesSolved=" + quizzesSolved.size() +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Quiz> getQuizzes() {
        return quizzes;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}
