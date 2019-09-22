package com.westechhub.westicketsmartinvitations;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import io.paperdb.Paper;

public class TicketListActivity extends AppCompatActivity implements View.OnClickListener {

    ListView guestList;
    TextView numberOfGuests;
    private String selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_list);

        guestList = findViewById(R.id.ticketList);
        numberOfGuests = findViewById(R.id.number_of_guests);

        Paper.init(this );

        List<String> allKeys = Paper.book().getAllKeys();
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,allKeys);
        guestList.setAdapter(adapter);

        int ticketCount = guestList.getAdapter().getCount();
        numberOfGuests.setText(String.valueOf(ticketCount));

        guestList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               selectedItem = (String) parent.getItemAtPosition(position);

            }
        });

    }


    @Override
    public void onClick(View v) {

    }
}
