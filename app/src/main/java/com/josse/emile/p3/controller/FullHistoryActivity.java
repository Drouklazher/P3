package com.josse.emile.p3.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.josse.emile.p3.R;
import com.josse.emile.p3.model.DAO;
import com.josse.emile.p3.model.Mood;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class FullHistoryActivity extends AppCompatActivity {
    private PieChartView mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_history);
        mChart = findViewById(R.id.fullHistory_pcv);
        DAO mDAO = new DAO(this);
        updateChart(mDAO.retieveAllProportionMap());
        final Button mButtonBack = findViewById(R.id.fullHistory_but_Back);
        mButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fullHistoryActivity = new Intent( FullHistoryActivity.this,HistoryActivity.class);
                startActivity(fullHistoryActivity);
            }
        });
        final Button mButtonLabels = findViewById(R.id.fullHistory_but_Labels);
        mButtonLabels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PieChartData pieChartData = mChart.getPieChartData();
                pieChartData.setHasLabels(true);
                mChart.setPieChartData(pieChartData);
            }
        });
    }

    private void updateChart(HashMap<Mood,Integer> colorsOccurence){
        List<SliceValue> pieData = new ArrayList<>();
        for (HashMap.Entry<Mood,Integer> entry:colorsOccurence.entrySet()){
            int color = getResources().getColor((entry.getKey()).getColorRes());
            int proportion = entry.getValue();
            String moodName = getString(entry.getKey().getName());

            pieData.add(new SliceValue(proportion,color).setLabel( moodName));
        }
        PieChartData pieChartData = new PieChartData(pieData);
        mChart.setPieChartData(pieChartData);

    }

}
