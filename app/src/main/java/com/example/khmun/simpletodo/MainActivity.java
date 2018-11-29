package com.example.khmun.simpletodo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList <String> Items;
    ArrayAdapter <String> ItemsAdapter;
    ListView lvitems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readItems();
        ItemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Items);
        lvitems = (ListView) findViewById(R.id.lvItems);
        lvitems.setAdapter(ItemsAdapter);

        // Mock Data
        //Items.add("item 1");
       //Items.add("item 2");
        setupListViewListener();
    } //onCreate Method

    public void onAddItem(View v) {
        EditText etNewItem =  findViewById(R.id.ed);
        String ItemText = etNewItem.getText().toString();
        ItemsAdapter.add(ItemText);
        writeItem();
        etNewItem.setText("");
        Toast.makeText(getApplicationContext(), "Item Added To List", Toast.LENGTH_SHORT).show();
    } //onEditText Method

    private void  setupListViewListener(){
        Log.i("MainActivity", "Setting Up Listener" );
        lvitems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("MainActivity", "Item Removed from List " + position );
                Items.remove(position);
                ItemsAdapter.notifyDataSetChanged();
                writeItem();
                return true;
            }
        });
    } // setUpListViewListener
    /* Persistence Methods */

    private File getDataFile(){
            return new File(getFilesDir(), "todo.txt");
        } //getDataFIle Method

    private void readItems(){
            try {
                Items = new ArrayList(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
            }
            catch (IOException e ){
                Log.i("MainActivity", "Error Reading File");
                Items = new ArrayList<>();
            }
        } // readItems Method

    private  void writeItem(){
        try {
            FileUtils.writeLines(getDataFile(), Items);
        } catch (IOException e) {
            Log.i("MainActivity", "Error Reading File");
        }
    } // writeItem Method

} // Main Class
