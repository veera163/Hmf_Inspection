package hmf.anyasoft.es.Hmf_inspection.domains;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by home on 5/3/2018.
 */

public class IrigationRes {

    @SerializedName("labels")
    @Expose
    private List<String> labels = null;
    @SerializedName("data")
    @Expose
    private List<String> data = null;

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

}
