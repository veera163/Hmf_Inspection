package hmf.anyasoft.es.Hmf_inspection.domains;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by home on 5/3/2018.
 */

public class CropRes {

    @SerializedName("labels")
    @Expose
    private List<String> labels = null;
    @SerializedName("datasets")
    @Expose
    private List<Dataset> datasets = null;

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public List<Dataset> getDatasets() {
        return datasets;
    }

    public void setDatasets(List<Dataset> datasets) {
        this.datasets = datasets;
    }
}
