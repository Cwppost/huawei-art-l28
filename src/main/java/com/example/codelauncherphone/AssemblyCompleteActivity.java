package com.example.codelauncherphone;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class AssemblyCompleteActivity extends AppCompatActivity {

    private TextView textSoftwareName;
    private GridView gridFinalTags;
    private ArrayList<String> finalTagList;
    private android.widget.ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assembly_complete);

        textSoftwareName = findViewById(R.id.textSoftwareName);
        gridFinalTags = findViewById(R.id.gridFinalTags);
        Button btnBackToAssembly = findViewById(R.id.btnBackToAssembly);

        Intent intent = getIntent();
        finalTagList = intent.getStringArrayListExtra("tagList");

        if (finalTagList != null && !finalTagList.isEmpty()) {
            String softwareName = finalTagList.get(0).replace("软件名称：", "");
            textSoftwareName.setText("软件名称：" + softwareName);

            ArrayList<String> displayTags = new ArrayList<>(finalTagList);
            displayTags.remove(0);
            adapter = new android.widget.ArrayAdapter<>(this, android.R.layout.simple_list_item_1, displayTags);
            gridFinalTags.setAdapter(adapter);
        } else {
            textSoftwareName.setText("软件名称：未设置");
        }

        btnBackToAssembly.setOnClickListener(v -> {
            Intent backIntent = new Intent(this, AssemblyPage1Activity.class);
            backIntent.putStringArrayListExtra("tagList", finalTagList);
            startActivity(backIntent);
            finish();
        });
    }
}