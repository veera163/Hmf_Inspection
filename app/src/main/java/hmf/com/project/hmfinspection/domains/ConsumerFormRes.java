package hmf.com.project.hmfinspection.domains;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by home on 5/11/2018.
 */

public class ConsumerFormRes {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("interestedCrop")
    @Expose
    private String interestedCrop;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("gridType")
    @Expose
    private String gridType;
    @SerializedName("investmentAmount")
    @Expose
    private String investmentAmount;
    @SerializedName("investmentPlan")
    @Expose
    private String investmentPlan;
    @SerializedName("appointmentDate")
    @Expose
    private String appointmentDate;
    @SerializedName("investmentPeriod")
    @Expose
    private String investmentPeriod;
    @SerializedName("currentProcessed")
    @Expose
    private String currentProcessed;
    @SerializedName("acknowledgment")
    @Expose
    private String acknowledgment;
    @SerializedName("currentAddress")
    @Expose
    private String currentAddress;
    @SerializedName("deliveryAddress")
    @Expose
    private String deliveryAddress;
    @SerializedName("profession")
    @Expose
    private String profession;
    @SerializedName("employeeOf")
    @Expose
    private String employeeOf;
    @SerializedName("aadharNumber")
    @Expose
    private String aadharNumber;
    @SerializedName("pan")
    @Expose
    private String pan;
    @SerializedName("bankAccount")
    @Expose
    private String bankAccount;
    @SerializedName("ifsc")
    @Expose
    private String ifsc;
    @SerializedName("registeredMobileNumber")
    @Expose
    private String registeredMobileNumber;
    @SerializedName("photoUrl")
    @Expose
    private String photoUrl;
  
    @SerializedName("inspectionStatus")
    @Expose
    private String inspectionStatus;

    public String getInspectionStatus() {
        return inspectionStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getInterestedCrop() {
        return interestedCrop;
    }

    public void setInterestedCrop(String interestedCrop) {
        this.interestedCrop = interestedCrop;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGridType() {
        return gridType;
    }

    public void setGridType(String gridType) {
        this.gridType = gridType;
    }

    public String getInvestmentAmount() {
        return investmentAmount;
    }

    public void setInvestmentAmount(String investmentAmount) {
        this.investmentAmount = investmentAmount;
    }

    public String getInvestmentPlan() {
        return investmentPlan;
    }

    public void setInvestmentPlan(String investmentPlan) {
        this.investmentPlan = investmentPlan;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getInvestmentPeriod() {
        return investmentPeriod;
    }

    public void setInvestmentPeriod(String investmentPeriod) {
        this.investmentPeriod = investmentPeriod;
    }

    public String getCurrentProcessed() {
        return currentProcessed;
    }

    public void setCurrentProcessed(String currentProcessed) {
        this.currentProcessed = currentProcessed;
    }

    public String getAcknowledgment() {
        return acknowledgment;
    }

    public void setAcknowledgment(String acknowledgment) {
        this.acknowledgment = acknowledgment;
    }

    public String getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getEmployeeOf() {
        return employeeOf;
    }

    public void setEmployeeOf(String employeeOf) {
        this.employeeOf = employeeOf;
    }

    public String getAadharNumber() {
        return aadharNumber;
    }

    public void setAadharNumber(String aadharNumber) {
        this.aadharNumber = aadharNumber;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public String getRegisteredMobileNumber() {
        return registeredMobileNumber;
    }

    public void setRegisteredMobileNumber(String registeredMobileNumber) {
        this.registeredMobileNumber = registeredMobileNumber;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
    
    public void setInspectionStatus(String inspectionStatus) {
        this.inspectionStatus = inspectionStatus;
    }

    @Override
    public String toString() {
        return "ConsumerFormRes{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", interestedCrop='" + interestedCrop + '\'' +
                ", name='" + name + '\'' +
                ", gridType='" + gridType + '\'' +
                ", investmentAmount='" + investmentAmount + '\'' +
                ", investmentPlan='" + investmentPlan + '\'' +
                ", appointmentDate='" + appointmentDate + '\'' +
                ", investmentPeriod='" + investmentPeriod + '\'' +
                ", currentProcessed='" + currentProcessed + '\'' +
                ", acknowledgment='" + acknowledgment + '\'' +
                ", currentAddress='" + currentAddress + '\'' +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", profession='" + profession + '\'' +
                ", employeeOf='" + employeeOf + '\'' +
                ", aadharNumber='" + aadharNumber + '\'' +
                ", pan='" + pan + '\'' +
                ", bankAccount='" + bankAccount + '\'' +
                ", ifsc='" + ifsc + '\'' +
                ", registeredMobileNumber='" + registeredMobileNumber + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", inspectionStatus='" + inspectionStatus + '\'' +
                '}';
    }
}
