package com.example.android_gearbox_recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android_gearbox_recyclerview.weidongjianview.LoopView;
import com.example.android_gearbox_recyclerview.weidongjianview.OnItemSelectedListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    Button button;
    String[] data = new String[]{"season 1", "season 2", "season 3", "season 4", "season 5",
            "season 6", "season 7", "season 8", "season 9", "season 10", "season 11", "season 12",
            "season 13", "season 14", "season 15"};
    private LoopView loopView;

    private ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        button = findViewById(R.id.defaultDialogBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // showDialogDefaultSize();
                Toast.makeText(MainActivity.this, "Index: " + loopView.getSelectedItem() + " Data: " +
                        list.get(loopView.getSelectedItem()), Toast.LENGTH_LONG).show();
            }
        });

        prepareWeidongjianView();

    }

    private void prepareWeidongjianView() {

        int itemSize = 20;

        loopView = (LoopView) findViewById(R.id.loopView);

        list = new ArrayList<>();
        for (int i = 0; i < itemSize; i++) {
            list.add("Season " + (i + 1));
        }

        loopView.setItems(list);
        loopView.setInitPosition((itemSize / 2) - 1);
        loopView.setCenterTextColor(Color.WHITE);
        loopView.setOuterTextColor(Color.LTGRAY);
        loopView.setDividerColor(Color.BLACK);
        loopView.setTextSize(22.0f);
        loopView.setLineSpacingMultiplier(.5f);

        loopView.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {

                loopView.setCurrentPosition(index);
                Toast.makeText(MainActivity.this, "Index: " + loopView.getSelectedItem() + " Data: " +
                        list.get(loopView.getSelectedItem()), Toast.LENGTH_LONG).show();
            }
        });
    }

}
