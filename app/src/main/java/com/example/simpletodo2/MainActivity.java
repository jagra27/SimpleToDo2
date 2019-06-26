package com.example.simpletodo2;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //declaring stateful objects here; these will be null before onCreate is called
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //obtain a reference to the ListView
        lvItems = (ListView) findViewById(R.id.lvItems);
        // initialize items list
        readItems();
        //initialize the adapter
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        // wire the adapter to the view
        lvItems.setAdapter(itemsAdapter);

        //add some mock items to the list
       //items.add("First todo item");
       // items.add("Second todo item");
        setupListViewListener();

    }

    public void onAddItem(View v){
        //obtain a reference to the EditText created with the layout
            EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
            // grab the EditText's content as a String
            String itemText = etNewItem.getText().toString();
            itemsAdapter.add(itemText);
            writeItems();
            // clear EditText by setting to empty string
            etNewItem.setText("");
            Toast.makeText(getApplicationContext(), "Item added to list", Toast.LENGTH_SHORT).show();
        }
    private void setupListViewListener(){
        // set ListView's itemLongClickListener
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        } );
    }
    private File getDataFile() {
        return new File(getFilesDir(), "todo.txt");
    }
    private void readItems() {
        try {
            items = new ArrayList<String>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        }catch (IOException e){
            e.printStackTrace();
            items = new ArrayList<>();

            }
    }
    private void writeItems(){
        try {
            // save the item list as a line-delimited text file
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            // print the error to the console
            e.printStackTrace();
        }
    }
    }




