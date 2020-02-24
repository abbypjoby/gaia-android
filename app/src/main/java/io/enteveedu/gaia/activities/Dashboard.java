package io.enteveedu.gaia.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.enteveedu.gaia.R;
import io.enteveedu.gaia.model.Node;
import pl.droidsonroids.gif.GifImageView;

public class Dashboard extends AppCompatActivity {

    private Button developerButton;
    private Button gateButton;
    private TextView textView;
    private GifImageView gifImageView;
    private ProgressBar progressBar;

    private static List<Node> nodes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        developerButton = (Button) findViewById(R.id.developerButton);
        gateButton = (Button) findViewById(R.id.gateButton);
        textView = (TextView) findViewById(R.id.gateResponseTV);
        gifImageView = (GifImageView) findViewById(R.id.gateGif);
        progressBar = (ProgressBar) findViewById(R.id.progress_loader);

        gateButton.setVisibility(View.INVISIBLE);
        new getAllNodesHealthTask().execute();

        developerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Dashboard.this, NodeDetails.class);
                Dashboard.this.startActivity(myIntent);
            }
        });


        gateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gateButton.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                if (nodes==null || nodes.isEmpty()){
                    new getAllNodesHealthTask().execute();
                } else {
                    if (nodes.stream().filter(id -> id.getNodeId().equals(1)).findFirst().get().getIsOn()){
                        new closeGate().execute();
                    }else {
                        new openGate().execute();
                    }
                    nodes=null;
                }
            }
        });
    }

    public void checkAndSetScreenBasedOnNodes(List<Node> nodes){
        if (nodes.stream().filter(id -> id.getNodeId().equals(1)).findFirst().get().getIsOn()){
            gateButton.setText("Close");
            gifImageView.setImageResource(R.drawable.open_gate);
        }else {
            gateButton.setText("Open");
            gifImageView.setImageResource(R.drawable.close_gate);
        }
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
            if ((nodes == null) || nodes.isEmpty()){
                textView.setText("No response from API");
            } else {
                checkAndSetScreenBasedOnNodes(nodes);
                textView.setText(nodes.get(0).toString());
            }
            progressBar.setVisibility(View.INVISIBLE);
            gateButton.setVisibility(View.VISIBLE);
        }
    }


    private class closeGate extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            String response = "closing";
            try {
                final String url = "https://pallathatta.herokuapp.com/node/update?node_id=1&is_on=false";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                response = restTemplate.getForObject(url, String.class);
                System.out.println(response);
                return response;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            new getAllNodesHealthTask().execute();
            textView.setText(response);
        }
    }

    private class openGate extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            String response = "opening";
            try {
                final String url = "https://pallathatta.herokuapp.com/node/update?node_id=1&is_on=true";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                response = restTemplate.getForObject(url, String.class);
                System.out.println(response);
                return response;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            new getAllNodesHealthTask().execute();
            textView.setText(response);
        }
    }
}
