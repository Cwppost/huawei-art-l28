package com.example.codelauncherphone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class AssemblyPage1Activity extends AppCompatActivity {

    private GridView gridAssemblyTags;
    private Button btnAddTag, btnSetSoftwareName, btnFinishAssembly;
    private ArrayList<String> assemblyTagList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assembly_page1);

        gridAssemblyTags = findViewById(R.id.gridAssemblyTags);
        btnAddTag = findViewById(R.id.btnAddTag);
        btnSetSoftwareName = findViewById(R.id.btnSetSoftwareName);
        btnFinishAssembly = findViewById(R.id.btnFinishAssembly);

        assemblyTagList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, assemblyTagList);
        gridAssemblyTags.setAdapter(adapter);

        btnAddTag.setOnClickListener(v -> showAddTagDialog());
        btnSetSoftwareName.setOnClickListener(v -> showSetSoftwareNameDialog());
        btnFinishAssembly.setOnClickListener(v -> {
            Intent completeIntent = new Intent(AssemblyPage1Activity.this, AssemblyCompleteActivity.class);
            completeIntent.putStringArrayListExtra("tagList", assemblyTagList);
            startActivity(completeIntent);
        });

        gridAssemblyTags.setOnItemClickListener((parent, view, position, id) -> {
            if (position == 0 && assemblyTagList.get(0).startsWith("软件名称：")) {
                showSetSoftwareNameDialog();
            } else {
                showEditTagDialog(position);
            }
        });

        gridAssemblyTags.setOnItemLongClickListener((parent, view, position, id) -> {
            if (position == 0 && assemblyTagList.get(0).startsWith("软件名称：")) {
                Toast.makeText(this, "软件名称标签不可删除", Toast.LENGTH_SHORT).show();
                return true;
            }
            assemblyTagList.remove(position);
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "标签已删除", Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    private void showAddTagDialog() {
        final EditText input = new EditText(this);
        input.setHint("输入名词标签名");
        new AlertDialog.Builder(this)
            .setTitle("添加名词标签")
            .setView(input)
            .setPositiveButton("确定", (dialog, which) -> {
                String name = input.getText().toString().trim();
                if (!name.isEmpty()) {
                    assemblyTagList.add(name);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(this, "名称不能为空", Toast.LENGTH_SHORT).show();
                }
            })
            .setNegativeButton("取消", null)
            .show();
    }

    private void showSetSoftwareNameDialog() {
        final EditText input = new EditText(this);
        input.setHint("输入软件名称");
        if (assemblyTagList.size() > 0 && assemblyTagList.get(0).startsWith("软件名称：")) {
            input.setText(assemblyTagList.get(0).replace("软件名称：", ""));
        }
        new AlertDialog.Builder(this)
            .setTitle("设置软件名称")
            .setView(input)
            .setPositiveButton("确定", (dialog, which) -> {
                String name = input.getText().toString().trim();
                if (!name.isEmpty()) {
                    if (assemblyTagList.size() > 0 && assemblyTagList.get(0).startsWith("软件名称：")) {
                        assemblyTagList.set(0, "软件名称：" + name);
                    } else {
                        assemblyTagList.add(0, "软件名称：" + name);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(this, "名称不能为空", Toast.LENGTH_SHORT).show();
                }
            })
            .setNegativeButton("取消", null)
            .show();
    }

    private void showEditTagDialog(int position) {
        final EditText input = new EditText(this);
        input.setText(assemblyTagList.get(position));
        new AlertDialog.Builder(this)
            .setTitle("修改标签名称")
            .setView(input)
            .setPositiveButton("确定", (dialog, which) -> {
                String newName = input.getText().toString().trim();
                if (!newName.isEmpty()) {
                    assemblyTagList.set(position, newName);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(this, "名称不能为空", Toast.LENGTH_SHORT).show();
                }
            })
            .setNegativeButton("取消", null)
            .show();
    }
}