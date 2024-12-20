package com.yubi.yuaccessjourney.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Journey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String journeyName;

    @NotNull
    private String prompt;

    @NotNull
    private String fileType;

    private String userEmail;  // You can keep this to store the email, but User object is used for DB relation

    @Lob
    private String outputJson;  // Field to store the output JSON

    @ManyToOne(fetch = FetchType.LAZY)  // Many Journeys can be associated with one User
    @JoinColumn(name = "user_id")  // Join column for user_id
    private User user;

    // Getters and Setters for all fields
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJourneyName() {
        return journeyName;
    }

    public void setJourneyName(String journeyName) {
        this.journeyName = journeyName;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getOutputJson() {
        return outputJson;
    }

    public void setOutputJson(String outputJson) {
        this.outputJson = outputJson;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}