package hmf.anyasoft.es.Hmf_inspection.domains;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by home on 5/3/2018.
 */

public class Dataset {

    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("data")
    @Expose
    private List<Integer> data = null;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<Integer> getData() {
        return data;
    }

    public void setData(List<Integer> data) {
        this.data = data;
    }

}
