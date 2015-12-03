package com.example.Servlet_Connection;

import android.app.Activity;
import android.content.*;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;


public class main extends Activity {
    int[] numbers = new int[3];

    Intent intentCallService5;
    BroadcastReceiver receiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        intentCallService5 = new Intent(this, MyService5Async.class);

        IntentFilter filter5 = new IntentFilter("NameOfIntent");

        receiver = new BroadcastReceiver1();
        registerReceiver(receiver, filter5);
        Button btn = (Button) findViewById(R.id.button3);
        btn.setEnabled(false);
        Button btn2 = (Button) findViewById(R.id.button);
        btn2.setEnabled(false);
    }



    public void Connect_to_server (View v){

        JSONObject json = new JSONObject();
        try {

            json.put("one", numbers[0]);
            json.put("two",numbers[1]);
            json.put("three",numbers[2]);
            json.put("four",numbers[3]);
            json.put("five",numbers[4]);


           String baseUrl = "http://10.3.4.66:8080/MinMax";
            new HttpAsyncTask().execute(baseUrl, json.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void Start (View v) throws InterruptedException {
        Button btn = (Button) findViewById(R.id.button3);
        startService(intentCallService5);
        btn.setEnabled(true);
    }
    public void Stop (View v) throws InterruptedException {
        Button btn2 = (Button) findViewById(R.id.button);
        btn2.setEnabled(true);
        stopService(intentCallService5);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref",0);
        SharedPreferences.Editor editor2 = pref.edit();
        editor2.putInt("one", numbers[0]);
        editor2.putInt("two", numbers[1]);
        editor2.putInt("three", numbers[2]);
        editor2.putInt("four", numbers[3]);
        editor2.putInt("five", numbers[4]);
        editor2.commit();
        Toast.makeText(getBaseContext(), "Numbers stored in Shared Preference", Toast.LENGTH_SHORT).show();
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            String jsonString = "";

            try {
                jsonString = HttpUtils.urlContentPost(urls[0], "num", urls[1]);
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println(jsonString);
            return jsonString;
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            JSONObject jsonResult = null;
            try {
                jsonResult = new JSONObject(result);

                String num1 = jsonResult.getString("max");
                String num2 = jsonResult.getString("min");
                String num3 = jsonResult.getString("sum");//new

                TextView view1 = (TextView)findViewById(R.id.textView);

                view1.setText("Min is: "+num1);
                view1.append("\nMax is: "+num2);
                view1.append("\nSum is: "+num3);//new


            } catch (JSONException e) {
                e.printStackTrace();
            }

            Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
        }
    }

    public class BroadcastReceiver1 extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            TextView txt2 = (TextView)findViewById(R.id.textView2);

            if (intent.getAction().equals("NameOfIntent")) {

                numbers = intent.getIntArrayExtra("array");
                txt2.setText("Random Numbers are: "+numbers[0]+", "+numbers[1]+", "+numbers[2]+", "+numbers[3]+", "+numbers[4]);

            }
        }//onReceive
    }// MyEmbeddedBroadcastReceiver

}
