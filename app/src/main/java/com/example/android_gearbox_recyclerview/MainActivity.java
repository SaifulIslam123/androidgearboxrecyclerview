package com.example.android_gearbox_recyclerview;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.android_gearbox_recyclerview.weidongjianview.LoopView;
import com.example.android_gearbox_recyclerview.weidongjianview.OnItemSelectedListener;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private RecyclerView recyclerView;
    Button button;
    String[] data = new String[]{"season 1", "season 2", "season 3", "season 4", "season 5",
            "season 6", "season 7", "season 8", "season 9", "season 10", "season 11", "season 12",
            "season 13", "season 14", "season 15"};
    private LoopView loopView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        button = findViewById(R.id.defaultDialogBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogDefaultSize();
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showDialogFullScreenSize();
                startActivity(new Intent(MainActivity.this, NumberPickerVirticalActivity.class));

            }
        });


        //prepareNumberPicker();
      /*  prepareRecyclerView();
        prepareNumberPicker();
        prepareWheelView();
*/

        prepareWeidongjianView();

    }

    private void prepareWeidongjianView() {

        int itemSize = 2;

        loopView = (LoopView) findViewById(R.id.loopView);

        final ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < itemSize; i++) {
            list.add("Season" + i);
        }

        loopView.setItems(list);
        loopView.setInitPosition((itemSize / 2) - 1);
        loopView.setCenterTextColor(Color.WHITE);
        loopView.setOuterTextColor(Color.LTGRAY);
        loopView.setDividerColor(Color.BLACK);
        loopView.setTextSize(22.0f);
        loopView.setLineSpacingMultiplier(.5f);
        loopView.setNotLoop();


        loopView.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                Toast.makeText(MainActivity.this, index + " " + list.get(index), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void showDialogDefaultSize() {
        View outerView = LayoutInflater.from(this).inflate(R.layout.wheel_view, null);
        WheelView wv = (WheelView) outerView.findViewById(R.id.wheel_view_wv);
        wv.setOffset(2);
        wv.setItems(Arrays.asList(data));
        wv.setSeletion(4);
        wv.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                Log.d(TAG, "[Dialog]selectedIndex: " + selectedIndex + ", item: " + item);
                Toast.makeText(getBaseContext(), "[Dialog]selectedIndex: " + selectedIndex +
                        ", item: " + item, Toast.LENGTH_LONG).show();
            }
        });

        new AlertDialog.Builder(this)
                .setTitle("WheelView in Dialog")
                .setView(outerView)
                .setPositiveButton("OK", null)
                .show();

       /* Dialog dialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(outerView);
        dialog.show();*/
    }

    private void showDialogFullScreenSize() {
        View outerView = LayoutInflater.from(this).inflate(R.layout.wheel_view, null);
        WheelView wv = (WheelView) outerView.findViewById(R.id.wheel_view_wv);
        wv.setOffset(2);
        wv.setItems(Arrays.asList(data));
        wv.setSeletion(4);
        wv.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                Log.d(TAG, "[Dialog]selectedIndex: " + selectedIndex + ", item: " + item);
                Toast.makeText(getBaseContext(), "[Dialog]selectedIndex: " + selectedIndex +
                        ", item: " + item, Toast.LENGTH_LONG).show();
            }
        });

       /* new AlertDialog.Builder(this)
                .setTitle("WheelView in Dialog")
                .setView(outerView)
                .setPositiveButton("OK", null)
                .show();*/
        Dialog dialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(outerView);
        dialog.show();
    }


    private void prepareWheelView() {

        WheelView wva = (WheelView) findViewById(R.id.main_wv);
        wva.setOffset(1);
        wva.setItems(Arrays.asList(data));
        wva.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                Log.d(TAG, "selectedIndex: " + selectedIndex + ", item: " + item);
            }
        });

    }


    private void prepareNumberPicker() {
        NumberPicker picker = findViewById(R.id.picker);

        picker.setMinValue(0);
        picker.setMaxValue(data.length - 1);
        picker.setDisplayedValues(data);

    }

    private void prepareRecyclerView() {
      /*  MyListData[] myListData = new MyListData[]{
                new MyListData("Email", android.R.drawable.ic_dialog_email),
                new MyListData("Info", android.R.drawable.ic_dialog_info),
                new MyListData("Delete", android.R.drawable.ic_delete),
                new MyListData("Dialer", android.R.drawable.ic_dialog_dialer),
                new MyListData("Alert", android.R.drawable.ic_dialog_alert),
                new MyListData("Map", android.R.drawable.ic_dialog_map),
                new MyListData("Email", android.R.drawable.ic_dialog_email),
                new MyListData("Info", android.R.drawable.ic_dialog_info),
                new MyListData("Delete", android.R.drawable.ic_delete),
                new MyListData("Dialer", android.R.drawable.ic_dialog_dialer),
                new MyListData("Alert", android.R.drawable.ic_dialog_alert),
                new MyListData("Map", android.R.drawable.ic_dialog_map),
        };

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        MyListAdapter adapter = new MyListAdapter(myListData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);*/
    }
}
