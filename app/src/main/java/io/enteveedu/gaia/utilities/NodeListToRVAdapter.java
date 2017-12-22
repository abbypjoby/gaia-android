package io.enteveedu.gaia.utilities;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.enteveedu.gaia.activities.NodeDetails;
import io.enteveedu.gaia.R;
import io.enteveedu.gaia.model.Node;

/**
 * Created by z002r13 on 12/20/17.
 */

public class NodeListToRVAdapter extends RecyclerView.Adapter<NodeListToRVAdapter.MyViewHolder> {

    private List<Node> dataSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nodeId;
        TextView nodeType;
        TextView nodeHealth;
        TextView nodeUpdatedTime;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.nodeId = (TextView) itemView.findViewById(R.id.nodeId);
            this.nodeType = (TextView) itemView.findViewById(R.id.nodeType);
            this.nodeHealth = (TextView) itemView.findViewById(R.id.nodeHealth);
            this.nodeUpdatedTime = (TextView) itemView.findViewById(R.id.nodeUpdatedTime);

        }
    }

    public NodeListToRVAdapter(List<Node> data) {
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.node_health_card, parent, false);
        view.setOnClickListener(NodeDetails.myOnClickListener);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {
        TextView nodeId = holder.nodeId;
        TextView nodeType = holder.nodeType;
        TextView nodeHealth = holder.nodeHealth;
        TextView nodeUpdatedTime = holder.nodeUpdatedTime;
        nodeId.setText("ID : " + dataSet.get(listPosition).getNodeId().toString());
        nodeType.setText("Type : " + dataSet.get(listPosition).getNodeType().toString());
        nodeHealth.setText("Health : " + ((dataSet.get(listPosition).getOn())?  "On / Active" : "Turned Off"));
        nodeUpdatedTime.setText("Last Sync : " + dataSet.get(listPosition).getUpdatedTimestamp().toString());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
