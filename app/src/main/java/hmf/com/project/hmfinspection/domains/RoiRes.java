package hmf.com.project.hmfinspection.domains;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by home on 5/7/2018.
 */

public class RoiRes {

    @SerializedName("cropName")
    @Expose
    private String cropName;
    @SerializedName("cropCode")
    @Expose
    private String cropdCode;
    @SerializedName("amountInvested")
    @Expose
    private String amountInvested;
    @SerializedName("roiOnCrop")
    @Expose
    private String roiOnCrop;

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getCropdCode() {
        return cropdCode;
    }

    public void setCropdCode(String cropdCode) {
        this.cropdCode = cropdCode;
    }

    public String getAmountInvested() {
        return amountInvested;
    }

    public void setAmountInvested(String amountInvested) {
        this.amountInvested = amountInvested;
    }

    public String getRoiOnCrop() {
        return roiOnCrop;
    }

    public void setRoiOnCrop(String roiOnCrop) {
        this.roiOnCrop = roiOnCrop;
    }
}
