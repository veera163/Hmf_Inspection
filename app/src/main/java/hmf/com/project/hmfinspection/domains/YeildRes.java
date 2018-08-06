package hmf.com.project.hmfinspection.domains;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by home on 5/7/2018.
 */

public class YeildRes {

    @SerializedName("cropdCode")
    @Expose
    private String cropdCode;
    @SerializedName("cropName")
    @Expose
    private String cropName;
    @SerializedName("expectedQuantity")
    @Expose
    private String expectedQuantity;
    @SerializedName("productionCost")
    @Expose
    private String productionCost;
    @SerializedName("productionCostForResale")
    @Expose
    private String productionCostForResale;
    @SerializedName("usage")
    @Expose
    private String usage;

    @Override
    public String toString() {
        return "YeildRes{" +
                "cropdCode='" + cropdCode + '\'' +
                ", cropName='" + cropName + '\'' +
                ", expectedQuantity='" + expectedQuantity + '\'' +
                ", productionCost='" + productionCost + '\'' +
                ", productionCostForResale='" + productionCostForResale + '\'' +
                ", usage='" + usage + '\'' +
                '}';
    }

    public String getCropdCode() {
        return cropdCode;
    }

    public void setCropdCode(String cropdCode) {
        this.cropdCode = cropdCode;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getExpectedQuantity() {
        return expectedQuantity;
    }

    public void setExpectedQuantity(String expectedQuantity) {
        this.expectedQuantity = expectedQuantity;
    }

    public String getProductionCost() {
        return productionCost;
    }

    public void setProductionCost(String productionCost) {
        this.productionCost = productionCost;
    }

    public String getProductionCostForResale() {
        return productionCostForResale;
    }

    public void setProductionCostForResale(String productionCostForResale) {
        this.productionCostForResale = productionCostForResale;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }
}
