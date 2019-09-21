package com.westechhub.westicketsmartinvitations;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import io.paperdb.Paper;

public class TicketListActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_list);

        Paper.init(this );

        List<String> allKeys = Paper.book().getAllKeys();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,allKeys);

        listView = findViewById(R.id.chklist);

        listView.setAdapter(adapter);

    }

}
