package io.enteveedu.gaia.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ProgressBar;

import io.enteveedu.gaia.R;

public class Dashboard extends AppCompatActivity {

    private CardView circularProgressCard;
    private ProgressBar circularProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        circularProgressBar = (ProgressBar) findViewById(R.id.circle_progress_bar);
        circularProgressCard = (CardView) findViewById(R.id.circularProgressCard);

        circularProgressBar.setProgress(70);

        circularProgressCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Dashboard.this, NodeDetails.class);
                Dashboard.this.startActivity(myIntent);
            }
        });
    }
}
