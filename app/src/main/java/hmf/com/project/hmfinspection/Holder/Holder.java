package hmf.com.project.hmfinspection.Holder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import hmf.com.project.hmfinspection.R;

/**
 * Created by home on 5/8/2018.
 */

public class Holder extends RecyclerView.ViewHolder {

    public TextView add1,add2,add3,status,warehouseName,location,city,state,pincode,phoneNumber,alternatePhoneNumber,
            emailId,website,facilities,contactPerson,inOperationSince,standardsAndCertifications,materialsHandling;
    public CardView veera,venkat;
    public ImageView imageView;


    public Holder(View itemView) {
        super(itemView);


        add1=(TextView)itemView.findViewById(R.id.add1);
        add2=(TextView)itemView.findViewById(R.id.add2);
        add3=(TextView)itemView.findViewById(R.id.add3);
        status=(TextView)itemView.findViewById(R.id.status);
        venkat=(CardView)itemView.findViewById(R.id.venkat);
        veera=(CardView)itemView.findViewById(R.id.veera);

        warehouseName=(TextView)itemView.findViewById(R.id.warehouseName);
        location=(TextView)itemView.findViewById(R.id.location);
        city=(TextView)itemView.findViewById(R.id.city);
        state=(TextView)itemView.findViewById(R.id.state);
        pincode=(TextView)itemView.findViewById(R.id.pincode);
        phoneNumber=(TextView)itemView.findViewById(R.id.phoneNumber);
        alternatePhoneNumber=(TextView)itemView.findViewById(R.id.alternatePhoneNumber);
        emailId=(TextView)itemView.findViewById(R.id.emailId);
        website=(TextView)itemView.findViewById(R.id.website);
        facilities=(TextView)itemView.findViewById(R.id.facilities);
        contactPerson=(TextView)itemView.findViewById(R.id.contactPerson);
        inOperationSince=(TextView)itemView.findViewById(R.id.inOperationSince);
        standardsAndCertifications=(TextView)itemView.findViewById(R.id.standardsAndCertifications);
        materialsHandling=(TextView)itemView.findViewById(R.id.materialsHandling);

        imageView=(ImageView)itemView.findViewById(R.id.img);






    }
}
