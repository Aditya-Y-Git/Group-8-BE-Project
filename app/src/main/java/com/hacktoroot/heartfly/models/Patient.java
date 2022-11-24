package com.hacktoroot.heartfly.models;

public class Patient {
    private String Email;
    private String Name;
    private String Phone;
    private String Password;
    private String Image;
    private String Age;
    private String Gender;
    private String ChestType;
    private String RestingBloodPressure;
    private String SerumCholestrol;
    private String FastingBloodSugar;
    private String ECG;
    private String MaxHeartRate;
    private String Angina;
    private String OldPeak;
    private String Slope;
    private String Vessals;
    private String Thal;

    Patient(){

    }

    public Patient(String Email, String Name, String Phone, String Password){
        this.Email = Email;
        this.Name = Name;
        this.Phone = Phone;
        this.Password = Password;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }



    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }


    public String getECG() {
        return ECG;
    }

    public void setECG(String ECG) {
        this.ECG = ECG;
    }

    public String getMaxHeartRate() {
        return MaxHeartRate;
    }

    public void setMaxHeartRate(String maxHeartRate) {
        MaxHeartRate = maxHeartRate;
    }

    public String getAngina() {
        return Angina;
    }

    public void setAngina(String angina) {
        Angina = angina;
    }

    public String getOldPeak() {
        return OldPeak;
    }

    public void setOldPeak(String oldPeak) {
        OldPeak = oldPeak;
    }

    public String getSlope() {
        return Slope;
    }

    public void setSlope(String slope) {
        Slope = slope;
    }

    public String getVessals() {
        return Vessals;
    }

    public void setVessals(String vessals) {
        Vessals = vessals;
    }

    public String getThal() {
        return Thal;
    }

    public void setThal(String thal) {
        Thal = thal;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getChestType() {
        return ChestType;
    }

    public void setChestType(String chestType) {
        ChestType = chestType;
    }

    public String getRestingBloodPressure() {
        return RestingBloodPressure;
    }

    public void setRestingBloodPressure(String restingBloodPressure) {
        RestingBloodPressure = restingBloodPressure;
    }

    public String getSerumCholestrol() {
        return SerumCholestrol;
    }

    public void setSerumCholestrol(String serumCholestrol) {
        SerumCholestrol = serumCholestrol;
    }

    public String getFastingBloodSugar() {
        return FastingBloodSugar;
    }

    public void setFastingBloodSugar(String fastingBloodSugar) {
        FastingBloodSugar = fastingBloodSugar;
    }


}

