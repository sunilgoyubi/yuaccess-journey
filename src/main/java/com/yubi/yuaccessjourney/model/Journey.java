package com.yubi.yuaccessjourney.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;

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

    private String userEmail;

    @Lob
    private String outputJson;

    @NotNull
    private String outputType;

    @Lob
    private String journeyDescription;

    @ElementCollection
    @CollectionTable(name = "document_section", joinColumns = @JoinColumn(name = "journey_id"))
    @Column(name = "section")
    private List<String> documentSection;

    @ElementCollection
    @CollectionTable(name = "configurations", joinColumns = @JoinColumn(name = "journey_id"))
    @Column(name = "configuration")
    private List<String> configurations;

    @ElementCollection
    @CollectionTable(name = "error_configuration", joinColumns = @JoinColumn(name = "journey_id"))
    @Column(name = "error")
    private List<String> errorConfiguration;

    @ElementCollection
    @CollectionTable(name = "validation", joinColumns = @JoinColumn(name = "journey_id"))
    @Column(name = "validation")
    private List<String> validation;

    @ElementCollection
    @CollectionTable(name = "verification", joinColumns = @JoinColumn(name = "journey_id"))
    @Column(name = "verification")
    private List<String> verification;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
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

    public String getOutputType() {
        return outputType;
    }

    public void setOutputType(String outputType) {
        this.outputType = outputType;
    }

    public String getJourneyDescription() {
        return journeyDescription;
    }

    public void setJourneyDescription(String journeyDescription) {
        this.journeyDescription = journeyDescription;
    }

    public List<String> getDocumentSection() {
        return documentSection;
    }

    public void setDocumentSection(List<String> documentSection) {
        this.documentSection = documentSection;
    }

    public List<String> getConfigurations() {
        return configurations;
    }

    public void setConfigurations(List<String> configurations) {
        this.configurations = configurations;
    }

    public List<String> getErrorConfiguration() {
        return errorConfiguration;
    }

    public void setErrorConfiguration(List<String> errorConfiguration) {
        this.errorConfiguration = errorConfiguration;
    }

    public List<String> getValidation() {
        return validation;
    }

    public void setValidation(List<String> validation) {
        this.validation = validation;
    }

    public List<String> getVerification() {
        return verification;
    }

    public void setVerification(List<String> verification) {
        this.verification = verification;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
