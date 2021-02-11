package com.example.photoapp;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent; import android.os.Bundle;
import android.os.Environment;
import android.view.View; import android.widget.EditText;

import java.io.File;
import java.text.DateFormat; import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar; import java.util.Date;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity{
    public ArrayList<String> photos = null;
    public ArrayList<Integer> loclist = new ArrayList<Integer>();
    float flat = 0;
    float flong = 0;


    // Create the Search Activity Page
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        try {
            Calendar calendar = Calendar.getInstance();
            DateFormat format = new SimpleDateFormat("yyyyMMdd");
            Date now = calendar.getTime();
            String todayStr = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(now);
            Date today = format.parse((String) todayStr);
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            String tomorrowStr = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format( calendar.getTime());
            Date tomorrow = format.parse((String) tomorrowStr);
            ((EditText) findViewById(R.id.etStartDateTime)).setText(new SimpleDateFormat(
                    "yyyyMMdd HHmmss", Locale.getDefault()).format(today));
            ((EditText) findViewById(R.id.etEndDateTime)).setText(new SimpleDateFormat(
                    "yyyyMMdd HHmmss", Locale.getDefault()).format(tomorrow));
        } catch (Exception ex) { }
    }

    // Close Search activity without passing anything back to main activity
    public void cancel(final View v) {
        finish();
    }

    // Ok button, pushes the filter options back to main activities "find photos" 
    public void go(final View v) {
        Intent i = new Intent();
        EditText from = (EditText) findViewById(R.id.etStartDateTime);
        EditText to = (EditText) findViewById(R.id.etEndDateTime);
        EditText keywords = (EditText) findViewById(R.id.etKeywords);
        EditText leftlat = (EditText) findViewById(R.id.etlatitude);
        EditText leftlong = (EditText) findViewById(R.id.etlongitude);
        EditText rightlat = (EditText) findViewById(R.id.etlatitude2);
        EditText rightlong = (EditText) findViewById(R.id.etlongitude2);
        i.putExtra("STARTTIMESTAMP", from.getText() != null ? from.getText().toString() : "");
        i.putExtra("ENDTIMESTAMP", to.getText() != null ? to.getText().toString() : "");
        i.putExtra("KEYWORDS", keywords.getText() != null ? keywords.getText().toString() : "");


        //photos = findPhotos(new Date(Long.MIN_VALUE), new Date(), "");
        float deflatmin = -90;
        float deflatmax = 90;
        float deflonmin = -180;
        float deflonmax = 180;

        String latmax_str = leftlat.getText().toString();
        String lonmin_str = leftlong.getText().toString();
        String latmin_str = rightlat.getText().toString();
        String lonmax_str = rightlong.getText().toString();

        //top left
        i.putExtra("LATMAX", latmax_str.equals("")? deflatmax : Float.parseFloat(latmax_str));
        i.putExtra("LONGMIN", lonmin_str.equals("")? deflonmin : Float.parseFloat(lonmin_str));
        //bottom right
        i.putExtra("LATMIN", latmin_str.equals("")? deflatmin : Float.parseFloat(latmin_str));
        i.putExtra("LONGMAX", lonmax_str.equals("")? deflonmax : Float.parseFloat(lonmax_str));

        setResult(RESULT_OK, i);
        finish();
    }

}
