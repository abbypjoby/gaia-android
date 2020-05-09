package io.enteveedu.gaia.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import io.enteveedu.gaia.R;
import io.enteveedu.gaia.model.Node;
import pl.droidsonroids.gif.GifImageView;

public class Dashboard extends AppCompatActivity {

    private Button developerButton;
    private Button stopButton;
    private Button openButton;
    private Button closeButton;
    private TextView textView;
    private TextView gateStateTextView;
    private GifImageView gifImageView;
    private ProgressBar progressBar;

    private static Node node = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        developerButton = (Button) findViewById(R.id.developerButton);
        stopButton = (Button) findViewById(R.id.gateButton);
        openButton = (Button) findViewById(R.id.openButton);
        closeButton = (Button) findViewById(R.id.closeButton);
        textView = (TextView) findViewById(R.id.gateResponseTV);
        gateStateTextView = (TextView) findViewById(R.id.gateState);
        gifImageView = (GifImageView) findViewById(R.id.gateGif);
        progressBar = (ProgressBar) findViewById(R.id.progress_loader);

        stopButton.setVisibility(View.INVISIBLE);
        gifImageView.setVisibility(View.INVISIBLE);
        new getAllNodesHealthTask().execute();

        developerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Dashboard.this, NodeDetails.class);
                Dashboard.this.startActivity(myIntent);
            }
        });


        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopButton.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                if (node == null){
                    new getAllNodesHealthTask().execute();
                } else {
                    new stopGate().execute();
                    node=null;
                }
            }
        });

        openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openButton.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                if (node == null){
                    new getAllNodesHealthTask().execute();
                } else {
                    new openGate().execute();
                    node=null;
                }
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeButton.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                if (node == null){
                    new getAllNodesHealthTask().execute();
                } else {
                    new closeGate().execute();
                    node=null;
                }
            }
        });
    }

    public void checkAndSetScreenBasedOnNodes(Node node){
        gifImageView.setVisibility(View.VISIBLE);
        if (node != null && node.getIsOn() == 1){
            gateStateTextView.setText("Open");
            gifImageView.setImageResource(R.drawable.open_gate);
        }else if (node != null && node.getIsOn() == 0){
            gateStateTextView.setText("Close");
            gifImageView.setImageResource(R.drawable.close_gate);
        }else {
            gateStateTextView.setText("Stop");
        }
    }


    private class getAllNodesHealthTask extends AsyncTask<Void, Void, Node> {
        @Override
        protected Node doInBackground(Void... params) {
            try {
                final String url = "https://pallathatta.herokuapp.com/node/1";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                node = restTemplate.getForObject(url, Node.class);
                System.out.println(node);
                return node;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return node;
        }
        @Override
        protected void onPostExecute(Node node) {
            if ((node == null) || node.getId() == null){
                textView.setText("No response from API");
            } else {
                checkAndSetScreenBasedOnNodes(node);
                textView.setText(node.toString());
            }
            progressBar.setVisibility(View.INVISIBLE);
            openButton.setVisibility(View.VISIBLE);
            closeButton.setVisibility(View.VISIBLE);
            stopButton.setVisibility(View.VISIBLE);
        }
    }


    private class closeGate extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            String response = "closing";
            try {
                final String url = "https://pallathatta.herokuapp.com/node/update?node_id=1&is_on=0";
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
                final String url = "https://pallathatta.herokuapp.com/node/update?node_id=1&is_on=1";
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


    private class stopGate extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            String response = "opening";
            try {
                final String url = "https://pallathatta.herokuapp.com/node/update?node_id=1&is_on=-1";
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
