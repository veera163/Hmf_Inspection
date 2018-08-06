package hmf.com.project.hmfinspection.domains;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by home on 5/8/2018.
 */

public class InspectionPendingRes {

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
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("quatityUnits")
    @Expose
    private String quatityUnits;
    @SerializedName("appointmentDate")
    @Expose
    private String appointmentDate;
    @SerializedName("organizationName")
    @Expose
    private String organizationName;
    @SerializedName("totalInvestment")
    @Expose
    private String totalInvestment;
    @SerializedName("expectedQuantity")
    @Expose
    private String expectedQuantity;
    @SerializedName("expectedDelivery")
    @Expose
    private String expectedDelivery;
    @SerializedName("currentlyProcessed")
    @Expose
    private String currentlyProcessed;
    @SerializedName("typeOfConsumer")
    @Expose
    private String typeOfConsumer;
    @SerializedName("typeOfInvestmentPlan")
    @Expose
    private String typeOfInvestmentPlan;
    @SerializedName("inspectionStatus")
    @Expose
    private String inspectionStatus;
    @SerializedName("gstNumber")
    @Expose
    private String gstNumber;
    @SerializedName("turnover")
    @Expose
    private String turnover;
    @SerializedName("pointOfContact")
    @Expose
    private String pointOfContact;
    @SerializedName("contactNumber")
    @Expose
    private String contactNumber;
    @SerializedName("currentAddress")
    @Expose
    private String currentAddress;
    @SerializedName("companyPAN")
    @Expose
    private String companyPAN;
    @SerializedName("companyRegisteredPhone")
    @Expose
    private String companyRegisteredPhone;
    @SerializedName("wareHouseAddress")
    @Expose
    private String wareHouseAddress;
    @SerializedName("deliveryAddress")
    @Expose
    private String deliveryAddress;
    @SerializedName("investmentInfo")
    @Expose
    private String investmentInfo;
    @SerializedName("gridType")
    @Expose
    private String gridType;
    @SerializedName("digitalSigns")
    @Expose
    private List<String> digitalSigns = null;

    @SerializedName("images")

    @Expose
    private List<String> images = null;

    @SerializedName("audios")

    @Expose
    private List<String> audios = null;

    public List<String> getAudios() {
        return audios;
    }

    public void setAudios(List<String> audios) {
        this.audios = audios;
    }

    public List<String> getDigitalSigns() {
        return digitalSigns;
    }

    public void setDigitalSigns(List<String> digitalSigns) {
        this.digitalSigns = digitalSigns;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getRegisteredAddress() {
        return registeredAddress;
    }

    public void setRegisteredAddress(String registeredAddress) {
        this.registeredAddress = registeredAddress;
    }

    @SerializedName("registeredAddress")
    @Expose

    private String registeredAddress;



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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getQuatityUnits() {
        return quatityUnits;
    }

    public void setQuatityUnits(String quatityUnits) {
        this.quatityUnits = quatityUnits;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getTotalInvestment() {
        return totalInvestment;
    }

    public void setTotalInvestment(String totalInvestment) {
        this.totalInvestment = totalInvestment;
    }

    public String getExpectedQuantity() {
        return expectedQuantity;
    }

    public void setExpectedQuantity(String expectedQuantity) {
        this.expectedQuantity = expectedQuantity;
    }

    public String getExpectedDelivery() {
        return expectedDelivery;
    }

    public void setExpectedDelivery(String expectedDelivery) {
        this.expectedDelivery = expectedDelivery;
    }

    public String getCurrentlyProcessed() {
        return currentlyProcessed;
    }

    public void setCurrentlyProcessed(String currentlyProcessed) {
        this.currentlyProcessed = currentlyProcessed;
    }

    public String getTypeOfConsumer() {
        return typeOfConsumer;
    }

    public void setTypeOfConsumer(String typeOfConsumer) {
        this.typeOfConsumer = typeOfConsumer;
    }

    public String getTypeOfInvestmentPlan() {
        return typeOfInvestmentPlan;
    }

    public void setTypeOfInvestmentPlan(String typeOfInvestmentPlan) {
        this.typeOfInvestmentPlan = typeOfInvestmentPlan;
    }

    public String getInspectionStatus() {
        return inspectionStatus;
    }

    public void setInspectionStatus(String inspectionStatus) {
        this.inspectionStatus = inspectionStatus;
    }

    public String getGstNumber() {
        return gstNumber;
    }

    public void setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
    }

    public String getTurnover() {
        return turnover;
    }

    public void setTurnover(String turnover) {
        this.turnover = turnover;
    }

    public String getPointOfContact() {
        return pointOfContact;
    }

    public void setPointOfContact(String pointOfContact) {
        this.pointOfContact = pointOfContact;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }

    public String getCompanyPAN() {
        return companyPAN;
    }

    public void setCompanyPAN(String companyPAN) {
        this.companyPAN = companyPAN;
    }

    public String getCompanyRegisteredPhone() {
        return companyRegisteredPhone;
    }

    public void setCompanyRegisteredPhone(String companyRegisteredPhone) {
        this.companyRegisteredPhone = companyRegisteredPhone;
    }

    public String getWareHouseAddress() {
        return wareHouseAddress;
    }

    public void setWareHouseAddress(String wareHouseAddress) {
        this.wareHouseAddress = wareHouseAddress;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getInvestmentInfo() {
        return investmentInfo;
    }

    public void setInvestmentInfo(String investmentInfo) {
        this.investmentInfo = investmentInfo;
    }

    public String getGridType() {
        return gridType;
    }

    public void setGridType(String gridType) {
        this.gridType = gridType;
    }

    @Override
    public String toString() {
        return "InspectionPendingRes{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", interestedCrop='" + interestedCrop + '\'' +
                ", quantity='" + quantity + '\'' +
                ", quatityUnits='" + quatityUnits + '\'' +
                ", appointmentDate='" + appointmentDate + '\'' +
                ", organizationName='" + organizationName + '\'' +
                ", totalInvestment='" + totalInvestment + '\'' +
                ", expectedQuantity='" + expectedQuantity + '\'' +
                ", expectedDelivery='" + expectedDelivery + '\'' +
                ", currentlyProcessed='" + currentlyProcessed + '\'' +
                ", typeOfConsumer='" + typeOfConsumer + '\'' +
                ", typeOfInvestmentPlan='" + typeOfInvestmentPlan + '\'' +
                ", inspectionStatus='" + inspectionStatus + '\'' +
                ", gstNumber='" + gstNumber + '\'' +
                ", turnover='" + turnover + '\'' +
                ", pointOfContact='" + pointOfContact + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", currentAddress='" + currentAddress + '\'' +
                ", companyPAN='" + companyPAN + '\'' +
                ", companyRegisteredPhone='" + companyRegisteredPhone + '\'' +
                ", wareHouseAddress='" + wareHouseAddress + '\'' +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", investmentInfo='" + investmentInfo + '\'' +
                ", gridType='" + gridType + '\'' +
                ", digitalSigns=" + digitalSigns +
                ", images=" + images +
                ", audios=" + audios +
                ", registeredAddress='" + registeredAddress + '\'' +
                '}';
    }
}
