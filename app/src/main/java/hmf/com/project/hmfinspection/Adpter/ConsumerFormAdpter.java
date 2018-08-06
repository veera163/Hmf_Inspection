package hmf.com.project.hmfinspection.Adpter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hmf.com.project.hmfinspection.Holder.Holder;
import hmf.com.project.hmfinspection.R;
import hmf.com.project.hmfinspection.activity.ConsumerFormUpadteActivity;
import hmf.com.project.hmfinspection.domains.ConsumerFormRes;

import java.util.List;

/**
 * Created by home on 5/11/2018.
 */

public class ConsumerFormAdpter extends RecyclerView.Adapter<Holder> {

    private Context context;
    private List<ConsumerFormRes> consumerFormRes;
    String id;


    public ConsumerFormAdpter(Context context, List<ConsumerFormRes> consumerFormRes) {
        this.context = context;
        this.consumerFormRes = consumerFormRes;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.inspectionlist, parent, false);


        return new Holder(itemView);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {


        id = consumerFormRes.get(position).getId();
        holder.add1.setText(consumerFormRes.get(position).getName());
        holder.add2.setText(consumerFormRes.get(position).getAppointmentDate());
        holder.add3.setText(consumerFormRes.get(position).getPhone());
        holder.venkat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ConsumerFormUpadteActivity.class);
                i.putExtra("id", consumerFormRes.get(position).getId());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {

        return consumerFormRes.size();
    }
}