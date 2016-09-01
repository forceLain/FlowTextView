package com.forcelain.flowtest;

import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Rect bounds = new Rect(0, 0, 400, 400);

        FlowTextView flowText11 = (FlowTextView) findViewById(R.id.flow_text_1_1);
        flowText11.setText(getString(R.string.lorem));
        flowText11.setFlowBounds(bounds, FlowTextView.ALIGN_RIGHT);

        FlowTextView flowText12 = (FlowTextView) findViewById(R.id.flow_text_1_2);
        flowText12.setText(getString(R.string.lorem));
        flowText12.setFlowBounds(bounds, FlowTextView.ALIGN_LEFT);

        FlowTextView2 flowText21 = (FlowTextView2) findViewById(R.id.flow_text_2_1);
        flowText21.setText(getString(R.string.lorem));
        flowText21.setFlowBounds(bounds, FlowTextView.ALIGN_RIGHT);

        FlowTextView2 flowText22 = (FlowTextView2) findViewById(R.id.flow_text_2_2);
        flowText22.setText(getString(R.string.lorem));
        flowText22.setFlowBounds(bounds, FlowTextView.ALIGN_LEFT);
    }
}
