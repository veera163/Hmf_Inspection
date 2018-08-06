package hmf.com.project.hmfwarehouse.domains;

import java.util.List;

/**
 * Created by home on 7/6/2018.
 */

public class FilterInfo {

    private String crop;
    private String warehouseLocation;
    private String farmerLocation;
    private List<String> facilitiesList;

    public String getCrop() {
        return crop;
    }

    public void setCrop(String crop) {
        this.crop = crop;
    }

    public String getWarehouseLocation() {
        return warehouseLocation;
    }

    public void setWarehouseLocation(String warehouseLocation) {
        this.warehouseLocation = warehouseLocation;
    }

    public String getFarmerLocation() {
        return farmerLocation;
    }

    public void setFarmerLocation(String farmerLocation) {
        this.farmerLocation = farmerLocation;
    }

    public List<String> getFacilitiesList() {
        return facilitiesList;
    }

    public void setFacilitiesList(List<String> facilitiesList) {
        this.facilitiesList = facilitiesList;
    }

    @Override
    public String toString() {
        return "FilterInfo{" +
                "crop='" + crop + '\'' +
                ", warehouseLocation='" + warehouseLocation + '\'' +
                ", farmerLocation='" + farmerLocation + '\'' +
                ", facilitiesList=" + facilitiesList +
                '}';
    }
}


