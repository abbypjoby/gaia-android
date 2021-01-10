package io.enteveedu.gaia.activities;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.enteveedu.gaia.R;
import io.enteveedu.gaia.model.Node;
import io.enteveedu.gaia.utilities.NodeListToRVAdapter;

public class NodeDetails extends AppCompatActivity {

    private static TextView responseTV;
    private static RecyclerView healthCardRV;
    private static List<Node> nodes = new ArrayList<>();
    private static RecyclerView.Adapter adapter;
    public static View.OnClickListener myOnClickListener;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node_details);
        myOnClickListener = new MyOnClickListener(this);
        responseTV = (TextView) findViewById(R.id.responseTV);
        healthCardRV = (RecyclerView) findViewById(R.id.healthCardRV);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        layoutManager = new LinearLayoutManager(this);
        healthCardRV.setLayoutManager(layoutManager);
        healthCardRV.setItemAnimator(new DefaultItemAnimator());

        new getAllNodesHealthTask().execute();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new getAllNodesHealthTask().execute();
            }
        });
    }




    private class getAllNodesHealthTask extends AsyncTask<Void, Void, List<Node>> {
        @Override
        protected List<Node> doInBackground(Void... params) {
            try {
                final String url = "https://pallathatta.herokuapp.com/node_health";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Node[] response = restTemplate.getForObject(url, Node[].class);
                nodes = Arrays.asList(response);
                System.out.println(nodes);
                return nodes;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return nodes;
        }
        @Override
        protected void onPostExecute(List<Node> nodes) {
            responseTV.setText((nodes.isEmpty() || (nodes == null))? "No response from API" : nodes.get(0).toString());
            adapter = new NodeListToRVAdapter(nodes);
            healthCardRV.setAdapter(adapter);
            swipeRefreshLayout.setRefreshing(false);
        }
    }





    private static class MyOnClickListener implements View.OnClickListener {
        private final Context context;
        private MyOnClickListener(Context context) {
            this.context = context;
        }
        @Override
        public void onClick(View v) {
            functionhere(v);
        }
        private void functionhere(View v) {
            int selectedItemPosition = healthCardRV.getChildAdapterPosition(v);
            RecyclerView.ViewHolder viewHolder = healthCardRV.findViewHolderForAdapterPosition(selectedItemPosition);
            TextView nodeId = (TextView) viewHolder.itemView.findViewById(R.id.nodeId);
            responseTV.setText(nodeId.getText());
        }
    }


}
