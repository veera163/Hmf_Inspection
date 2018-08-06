package hmf.com.project.hmfinspection.Adpter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hmf.com.project.hmfinspection.Holder.Holder;
import hmf.com.project.hmfinspection.R;
import hmf.com.project.hmfinspection.activity.MainActivity;
import hmf.com.project.hmfinspection.domains.InspectionPendingRes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by home on 5/8/2018.
 */

public class InspectionPendingAdpter extends RecyclerView.Adapter<Holder>  {

    private Context context;
    private List<InspectionPendingRes> inspectionPendingRes;
    String id;


    public InspectionPendingAdpter(Context context, List<InspectionPendingRes> inspectionPendingRes) {
        this.context = context;
        this.inspectionPendingRes = inspectionPendingRes;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.inspectionlist, parent, false);


        return new Holder(itemView);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {


        id=inspectionPendingRes.get(position).getId();
        holder.add1.setText(inspectionPendingRes.get(position).getOrganizationName());
        holder.add2.setText(inspectionPendingRes.get(position).getAppointmentDate());
        holder.add3.setText(inspectionPendingRes.get(position).getPhone());

        holder.venkat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(context, MainActivity.class);

                i.putExtra("id",inspectionPendingRes.get(position).getId());
                i.putExtra("orgname",inspectionPendingRes.get(position).getOrganizationName());


                if(inspectionPendingRes.get(position).getDigitalSigns()==null){

                }
               else {

                    for(int v=0;v<inspectionPendingRes.get(position).getDigitalSigns().size();v++){

                        i.putExtra("sign",inspectionPendingRes.get(position).getDigitalSigns().get(v));
                        Log.e("veera",inspectionPendingRes.get(position).getDigitalSigns().get(v));
                    }

                }


                if(inspectionPendingRes.get(position).getImages()==null){

                }
                else {

                        ArrayList<String> imageslist= new ArrayList<String>();
                        imageslist.addAll(inspectionPendingRes.get(position).getImages());
                        i.putExtra("images",imageslist);
                }

                if(inspectionPendingRes.get(position).getAudios()==null){

                }
                else {

                    ArrayList<String> audiolist= new ArrayList<String>();
                    audiolist.addAll(inspectionPendingRes.get(position).getAudios());
                    i.putExtra("audio",audiolist);
                }


                context.startActivity(i);
            }
        });



    }

    @Override
    public int getItemCount() {

        return inspectionPendingRes.size();
    }


}
