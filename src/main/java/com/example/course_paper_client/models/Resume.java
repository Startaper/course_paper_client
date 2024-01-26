package com.example.course_paper_client.models;

import com.example.course_paper_client.models.enums.*;
import com.example.course_paper_client.utils.MainUtil;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Resume {

    private String id;
    private String area;
    private String email;
    private String title;
    private String metro;
    private String photo;
    private String skills;
    private String lastName;
    private String skillSet;
    private String schedules;
    private String firstName;
    private String createdAt;
    private String updatedAt;
    private String birthDate;
    private String mainPhone;
    private String middleName;
    private String workTickets;
    private String employments;
    private String citizenship;
    private String searchField;
    private String urlDownloadPdf;
    private String urlDownloadRtf;
    private String specializations;
    private String driverLicenseTypes;
    private int age;
    private int salary;
    private SimpleFloatProperty rating;
    private int totalExperienceInMonth;
    private boolean viewed;
    private boolean favorited;
    private boolean canViewFullInfo;
    private SimpleObjectProperty<Gender> gender;
    private SimpleObjectProperty<ResumeStatus> status;
    private SimpleObjectProperty<EducationLevel> educationLevel;
    private TravelTimeType travelTime;
    private BusinessTripReadinessType businessTripReadiness;
    private List<Site> site;
    private List<Contact> contacts;
    private List<Language> languages;
    private List<Education> educations;
    private List<Experience> experience;
    private List<Recommendation> recommendations;

    public Resume() {
    }

    public Resume(JSONObject jsonObject) throws JSONException {
        this.id = jsonObject.optString("id");
        this.area = jsonObject.optString("area");
        this.title = jsonObject.optString("title");
        this.metro = jsonObject.optString("metro");
        this.skills = jsonObject.optString("skills");
        this.lastName = jsonObject.optString("lastName");
        this.firstName = jsonObject.optString("firstName");
        this.middleName = jsonObject.optString("middleName");
        this.urlDownloadPdf = jsonObject.optString("urlDownloadPdf");
        this.urlDownloadRtf = jsonObject.optString("urlDownloadRtf");
        this.photo = jsonObject.optString("photo");
        this.skillSet = jsonObject.optString("skillSet");
        this.schedules = jsonObject.optString("schedules");
        this.employments = jsonObject.optString("employments");
        this.driverLicenseTypes = jsonObject.optString("driverLicenseTypes");
        this.createdAt = jsonObject.optString("createdAt");
        this.updatedAt = jsonObject.optString("updatedAt");
        this.birthDate = jsonObject.optString("birthDate");
        this.age = jsonObject.optInt("age");
        this.salary = jsonObject.optInt("salary");
        this.rating = new SimpleFloatProperty(Float.parseFloat(jsonObject.optString("rating")));
        this.totalExperienceInMonth = jsonObject.optInt("totalExperienceInMonth");
        this.viewed = jsonObject.optBoolean("viewed");
        this.favorited = jsonObject.optBoolean("favorited");
        this.canViewFullInfo = jsonObject.optBoolean("canViewFullInfo");
        this.gender = new SimpleObjectProperty<>(Gender.valueOf(jsonObject.optString("gender").toUpperCase()));
        this.status = new SimpleObjectProperty<>(ResumeStatus.valueOf(jsonObject.optString("status").toUpperCase()));
        this.travelTime = TravelTimeType.valueOf(jsonObject.optString("travelTime").toUpperCase());
        this.educationLevel = new SimpleObjectProperty<>(EducationLevel.valueOf(jsonObject.optString("educationLevel").toUpperCase()));
        this.businessTripReadiness = BusinessTripReadinessType.valueOf(jsonObject.optString("businessTripReadiness").toUpperCase());
        this.site = MainUtil.convertJsonArrayToSiteList(jsonObject.optJSONArray("site"));
        this.workTickets = jsonObject.optString("workTickets");
        this.citizenship = jsonObject.optString("citizenship");
        this.contacts = MainUtil.convertJsonArrayToContactList(jsonObject.optJSONArray("contacts"));
        this.languages = MainUtil.convertJsonArrayToLanguageList(jsonObject.optJSONArray("languages"));
        this.educations = MainUtil.convertJsonArrayToEducationList(jsonObject.optJSONArray("educations"));
        this.experience = MainUtil.convertJsonArrayToExperienceList(jsonObject.optJSONArray("experience"));
        this.recommendations = MainUtil.convertJsonArrayToRecommendationList(jsonObject.optJSONArray("recommendations"));
        this.specializations = jsonObject.optString("specializations");
        this.searchField = String.format("%s %s %s %s",
                this.getLastName(), this.getFirstName(), this.getMiddleName(), this.getTitle()).toLowerCase();

        Contact contact = MainUtil.getContactByContactType(this.getContacts(), ContactType.EMAIL);
        if (contact != null) {
            this.email = contact.getValue();
        }

        contact = MainUtil.getContactByContactType(this.getContacts(), ContactType.CELL);
        if (contact != null) {
            this.mainPhone = contact.getValue();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMetro() {
        return metro;
    }

    public void setMetro(String metro) {
        this.metro = metro;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getUrlDownloadPdf() {
        return urlDownloadPdf;
    }

    public void setUrlDownloadPdf(String urlDownloadPdf) {
        this.urlDownloadPdf = urlDownloadPdf;
    }

    public String getUrlDownloadRtf() {
        return urlDownloadRtf;
    }

    public void setUrlDownloadRtf(String urlDownloadRtf) {
        this.urlDownloadRtf = urlDownloadRtf;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public float getRating() {
        return rating.get();
    }

    public SimpleFloatProperty ratingProperty() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating.set(rating);
    }

    public int getTotalExperienceInMonth() {
        return totalExperienceInMonth;
    }

    public void setTotalExperienceInMonth(int totalExperienceInMonth) {
        this.totalExperienceInMonth = totalExperienceInMonth;
    }

    public boolean isViewed() {
        return viewed;
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }

    public boolean isCanViewFullInfo() {
        return canViewFullInfo;
    }

    public void setCanViewFullInfo(boolean canViewFullInfo) {
        this.canViewFullInfo = canViewFullInfo;
    }

    public String getGender() {
        return gender.getValue().getName();
    }

    public void setGender(Gender gender) {
        this.gender.setValue(gender);
    }

    public String getStatus() {
        return status.getValue().getName();
    }

    public void setStatus(ResumeStatus status) {
        this.status.setValue(status);
    }

    public TravelTimeType getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(TravelTimeType travelTime) {
        this.travelTime = travelTime;
    }

    public String getEducationLevel() {
        return educationLevel.getValue().getName();
    }

    public void setEducationLevel(EducationLevel educationLevel) {
        this.educationLevel.setValue(educationLevel);
    }

    public BusinessTripReadinessType getBusinessTripReadiness() {
        return businessTripReadiness;
    }

    public void setBusinessTripReadiness(BusinessTripReadinessType businessTripReadiness) {
        this.businessTripReadiness = businessTripReadiness;
    }

    public List<Site> getSite() {
        return site;
    }

    public void setSite(List<Site> site) {
        this.site = site;
    }

    public String getSkillSet() {
        return skillSet;
    }

    public void setSkillSet(String skillSet) {
        this.skillSet = skillSet;
    }

    public String getSchedules() {
        return schedules;
    }

    public void setSchedules(String schedules) {
        this.schedules = schedules;
    }

    public String getWorkTickets() {
        return workTickets;
    }

    public void setWorkTickets(String workTickets) {
        this.workTickets = workTickets;
    }

    public String getEmployments() {
        return employments;
    }

    public void setEmployments(String employments) {
        this.employments = employments;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public String getDriverLicenseTypes() {
        return driverLicenseTypes;
    }

    public void setDriverLicenseTypes(String driverLicenseTypes) {
        this.driverLicenseTypes = driverLicenseTypes;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public List<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }

    public List<Education> getEducations() {
        return educations;
    }

    public void setEducations(List<Education> educations) {
        this.educations = educations;
    }

    public List<Experience> getExperience() {
        return experience;
    }

    public void setExperience(List<Experience> experience) {
        this.experience = experience;
    }

    public List<Recommendation> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<Recommendation> recommendations) {
        this.recommendations = recommendations;
    }

    public String getMainPhone() {
        return mainPhone;
    }

    public void setMainPhone(String mainPhone) {
        this.mainPhone = mainPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSearchField() {
        return searchField;
    }

    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }

    public String getSpecializations() {
        return specializations;
    }

    public void setSpecializations(String specializations) {
        this.specializations = specializations;
    }
}
