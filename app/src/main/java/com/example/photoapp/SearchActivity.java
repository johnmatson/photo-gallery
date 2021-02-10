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

    public void cancel(final View v) {
        finish();
    }

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

        photos = findPhotos(new Date(Long.MIN_VALUE), new Date(), "");

        //top left
        float latmax = Float.parseFloat(leftlat.getText().toString());
        float longmin = Float.parseFloat(leftlong.getText().toString());

        //bottom right
        float latmin = Float.parseFloat(rightlat.getText().toString());
        float longmax = Float.parseFloat(rightlong.getText().toString());

        for(int j=0 ; j < photos.size() ; j++){
            String name = photos.get(j);
            String[] splitname = name.split("/");
            String[] splitname2 = splitname[splitname.length-1].split("_");
            flat = Float.parseFloat(splitname2[3]);
            flong = Float.parseFloat(splitname2[4]);
            if(flat > latmin && flat < latmax && flong > longmin && flong < longmax){
                loclist.add(1);
            }
            else {
                loclist.add(0);
            }
        }

        setResult(RESULT_OK, i);
        finish();
    }

    public static ArrayList<String> findPhotos(Date startTimestamp, Date endTimestamp, String keywords) {
        File file = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath(), "/Android/data/com.example.photoapp/files/Pictures");
        ArrayList<String> photos = new ArrayList<String>();
        File[] fList = file.listFiles();
        if (fList != null) {
            for (File f : fList) {
                if (((startTimestamp == null && endTimestamp == null) || (f.lastModified() >= startTimestamp.getTime() && f.lastModified() <= endTimestamp.getTime())) && (keywords == null || f.getPath().contains(keywords)))
                    photos.add(f.getPath());
            }
        }
        return photos;
    }

}
