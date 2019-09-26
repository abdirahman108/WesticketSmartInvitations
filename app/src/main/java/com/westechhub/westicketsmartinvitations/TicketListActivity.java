package com.westechhub.westicketsmartinvitations;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.westechhub.westicketsmartinvitations.Prevalent.Prevalent;

import org.w3c.dom.Text;

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

        String activationCodeKey = Paper.book().read(Prevalent.ticketActivationCode);
        String sessionKey = Paper.book().read(Prevalent.SessionKey);
        String usernameKey = Paper.book().read(Prevalent.UsernameKey);
        String passwordKey = Paper.book().read(Prevalent.UserPasswordKey);

        Paper.init(this );

        List<String> allKeys = Paper.book().getAllKeys();
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,allKeys);
        guestList.setAdapter(adapter);

        //Counting Tickets
        int ticketCount = guestList.getAdapter().getCount();
        numberOfGuests.setText(String.valueOf(ticketCount - 2));

//        if (TextUtils.isEmpty(activationCodeKey)){
//            // If there is no activation code
//            numberOfGuests.setText(String.valueOf(ticketCount - 1));
//        }else if (TextUtils.isEmpty(sessionKey)) {
//            // if there is no session
//            numberOfGuests.setText(String.valueOf(ticketCount - 2));
//        }

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
