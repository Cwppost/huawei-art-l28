package com.example.codelauncherphone;

import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private GridView tagGridView;
    private EditText editNormalCode, editErrorCode, editFixedCode, editUpdateLog;
    private ArrayList<String> tagList;
    private ArrayAdapter<String> tagAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tagGridView = findViewById(R.id.tagGridView);
        editNormalCode = findViewById(R.id.editNormalCode);
        editErrorCode = findViewById(R.id.editErrorCode);
        editFixedCode = findViewById(R.id.editFixedCode);
        editUpdateLog = findViewById(R.id.editUpdateLog);

        tagList = new ArrayList<>();
        tagList.add("功能A");
        tagList.add("功能B");
        tagList.add("功能C");
        tagList.add("功能D");
        tagList.add("功能E");
        tagList.add("功能F");

        tagAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tagList);
        tagGridView.setAdapter(tagAdapter);

        tagGridView.setOnItemClickListener((parent, view, position, id) -> showRenameTagDialog(position));
        tagGridView.setOnItemLongClickListener((parent, view, position, id) -> {
            if (position >= 0 && position < tagList.size()) {
                tagList.remove(position);
                tagAdapter.notifyDataSetChanged();
                Toast.makeText(this, "标签已删除", Toast.LENGTH_SHORT).show();
            }
            return true;
        });
    }

    private void showRenameTagDialog(final int position) {
        final EditText input = new EditText(this);
        input.setHint("输入新标签名");
        input.setText(tagList.get(position));

        new AlertDialog.Builder(this)
            .setTitle("修改标签名称")
            .setView(input)
            .setPositiveButton("确定", (dialog, which) -> {
                String newName = input.getText().toString().trim();
                if (!newName.isEmpty()) {
                    tagList.set(position, newName);
                    tagAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(this, "名称不能为空", Toast.LENGTH_SHORT).show();
                }
            })
            .setNegativeButton("取消", null)
            .show();
    }
}