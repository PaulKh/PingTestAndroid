package com.khvorostov.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import com.khvorostov.test.adapters.PingListViewAdapter;
import com.khvorostov.test.enums.RequestStatus;
import com.khvorostov.test.model.Ping;
import com.khvorostov.test.rows.PingRowHeader;
import com.khvorostov.test.web_service.PingHandler;
import com.khvorostov.test.web_service.WebRequestHandler;
import com.khvorostov.test.web_service.async_tasks.PingAddressAsyncTask;
import com.khvorostov.test.web_service.callbacks.PingCreatedCallback;
import com.khvorostov.test.web_service.callbacks.PingMadeCallback;
import com.khvorostov.test.web_service.callbacks.PingRequestReceivedCallback;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements PingRequestReceivedCallback, PingCreatedCallback, PingMadeCallback{
    private Spinner spinner;
    private EditText pingEditText;
    private PingListViewAdapter adapter;
    private WebRequestHandler handler = new WebRequestHandler();

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.main);
        buildView();
        refreshList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_refresh:
                refreshList();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void refreshList() {
        handler.requestAllPings(this);
        startProgressHUD();
    }

    @Override
    public void pingsReceived(List<Ping> pings) {
        stopProgressHUD();
        adapter.clearAdapter();
        for (Ping ping: pings){
            adapter.addRow(ping);
        }
        adapter.notifyDataSetChanged();
    }

    private void buildView(){
        List<Integer> spinnerValues = new ArrayList<>();
        for(int i = 1; i <= 20; i++){
            spinnerValues.add(i);
        }
        ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<Integer>(this,
                android.R.layout.simple_spinner_item, spinnerValues);
        spinner = (Spinner) findViewById(R.id.pings_count_spinner);
        spinner.setAdapter(dataAdapter);
        ExpandableListView listView = (ExpandableListView) findViewById(R.id.ping_listView);
        adapter = new PingListViewAdapter(this);
        pingEditText = (EditText) findViewById(R.id.ping_address_edittext);
        findViewById(R.id.ping_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pingResult = null;
                String insertedValue = pingEditText.getText().toString();
                if (insertedValue != null && !insertedValue.equals("")) {
                    startProgressHUD();
                    PingAddressAsyncTask asyncTask = new PingAddressAsyncTask(MainActivity.this, (Integer) spinner.getSelectedItem(), insertedValue.toString());
                    asyncTask.execute();
                }

            }
        });
        listView.setAdapter(adapter);
    }
    private void startProgressHUD(){
        setProgressBarIndeterminateVisibility(true);
    }
    private void stopProgressHUD(){
        setProgressBarIndeterminateVisibility(false);
    }
    @Override
    public void pingRequestSent(RequestStatus requestStatus, Ping ping) {
        if (requestStatus == RequestStatus.SUCCESS){
            adapter.addRow(ping);
            adapter.notifyDataSetChanged();
        }
        else{
            Toast.makeText(this, R.string.error_saving_the_ping, Toast.LENGTH_LONG).show();
        }
        stopProgressHUD();
    }

    @Override
    public void pingMade(String pingResult, String destinationAddress) {
        if (pingResult != null && pingResult != "") {
            Ping ping = new Ping(pingResult, handler.getThisDeviceIp(), destinationAddress);
            handler.postPing(ping, MainActivity.this);
        }
        else{
            stopProgressHUD();
            Toast.makeText(this, R.string.ip_not_recognized, Toast.LENGTH_LONG).show();
        }
    }
}
