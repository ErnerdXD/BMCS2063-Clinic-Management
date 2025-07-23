package entity;
 
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Objects;


public class Doctor {
    
    //private attributes
    private String doctorId;
    private String name;
    private String specialization;
    private String contactNumber;
    private String email;
    private double consultationFee;
    private boolean isAvailable;
    private String[] workingDays;
    private LocalTime startTime;
    private LocalTime endTime;
    private int maxPatientsPerDay;
    private int currentPatientCount;


    //default constructors
    public Doctor() {
        this.doctorId = "";
        this.name = "";
        this.specialization = "";
        this.contactNumber = "";
        this.email = "";
        this.consultationFee = 0.0;
        this.isAvailable = true;
        this.workingDays = new String[0];
        this.startTime = LocalTime.of(9, 0); //Default 9:00 AM
        this.endTime = LocalTime.of(17, 0);   //Default 5:00 PM
        this.maxPatientsPerDay = 20;
        this.currentPatientCount = 0;
    }

    //parameterized constructor
    public Doctor(String doctorId, String name, String specialization, String contactNumber){
        this();
        this.doctorId = doctorId;
        this.name = name; 
        this.specialization = specialization;
        this.contactNumber = contactNumber;
    }

    //full parameterized constructor
    public Doctor(String doctorId, String name, String specialization, String contactNumber, 
                  String email, double consultationFee, String[] workingDays, 
                  LocalTime startTime, LocalTime endTime, int maxPatientsPerDay) {
        this.doctorId = doctorId;
        this.name = name;
        this.specialization = specialization;
        this.contactNumber = contactNumber;
        this.email = email;
        this.consultationFee = consultationFee;
        this.isAvailable = true;
        this.workingDays = workingDays != null ? workingDays.clone() : new String[0];
        this.startTime = startTime;
        this.endTime = endTime;
        this.maxPatientsPerDay = maxPatientsPerDay;
        this.currentPatientCount = 0;
    }

    // Getters
    public String getDoctorId() {
        return doctorId;
    }

    public String getName() {
        return name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public double getConsultationFee() {
        return consultationFee;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public String[] getWorkingDays() {
        return workingDays != null ? workingDays.clone() : new String[0];
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public int getMaxPatientsPerDay() {
        return maxPatientsPerDay;
    }

    public int getCurrentPatientCount() {
        return currentPatientCount;
    }

    // Setters
    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setConsultationFee(double consultationFee) {
        this.consultationFee = consultationFee;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public void setWorkingDays(String[] workingDays) {
        this.workingDays = workingDays != null ? workingDays.clone() : new String[0];
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public void setMaxPatientsPerDay(int maxPatientsPerDay) {
        this.maxPatientsPerDay = maxPatientsPerDay;
    }

    public void setCurrentPatientCount(int currentPatientCount) {
        this.currentPatientCount = currentPatientCount;
    }


    // Business logic methods
    public boolean canTakeMorePatients() {
        return currentPatientCount < maxPatientsPerDay && isAvailable;
    }  

    //check if doctor is allowed to accept more patients for the day
    //b4 adding new patient to doc schedule, this can ensure doctor free


    public void incrementPatientCount() {
        if (canTakeMorePatients()) {
            currentPatientCount++;
        }
    }

    

    public void decrementPatientCount() {
        if (currentPatientCount > 0) {
            currentPatientCount--;
        }
    }

    //reduce num of current patients if at least one patient is present

    public boolean isWorkingToday(String day) {
        if (workingDays == null) return false;
        for (String workingDay : workingDays) {
            if (workingDay.equalsIgnoreCase(day)) {
                return true;
            }
        }
        return false;
    }

    //check whether doc work on particular day

    public boolean isWithinWorkingHours(LocalTime time) {
        return time != null && startTime != null && endTime != null &&
               !time.isBefore(startTime) && !time.isAfter(endTime);
    }

    public double calculateWorkingHours() {
        if (startTime == null || endTime == null) return 0;
        return (endTime.toSecondOfDay() - startTime.toSecondOfDay()) / 3600.0;
    }

    // Override toString method
    @Override
    public String toString() {
        return String.format("Doctor{ID='%s', Name='%s', Specialization='%s', " +
                           "Contact='%s', Email='%s', Fee=%.2f, Available=%s, " +
                           "WorkingDays=%s, Hours=%s-%s, Patients=%d/%d}",
                           doctorId, name, specialization, contactNumber, email,
                           consultationFee, isAvailable, Arrays.toString(workingDays),
                           startTime, endTime, currentPatientCount, maxPatientsPerDay);
    }

    // Override equals method (important for ADT operations like search/remove)
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Doctor doctor = (Doctor) obj;
        return Objects.equals(doctorId, doctor.doctorId);
    }

    // Override hashCode method (good practice when overriding equals)
    @Override
    public int hashCode() {
        return Objects.hash(doctorId);
    }

    // Comparable implementation (useful for sorting in your ADT)
    public int compareByName(Doctor other) {
        return this.name.compareToIgnoreCase(other.name);
    }

    public int compareBySpecialization(Doctor other) {
        return this.specialization.compareToIgnoreCase(other.specialization);
    }

    public int compareByFee(Doctor other) {
        return Double.compare(this.consultationFee, other.consultationFee);
    }
}
