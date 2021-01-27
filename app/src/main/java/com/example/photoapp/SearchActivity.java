package com.example.photoapp;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent; import android.os.Bundle;
import android.view.View; import android.widget.EditText;
import java.text.DateFormat; import java.text.SimpleDateFormat;
import java.util.Calendar; import java.util.Date;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity{
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
        i.putExtra("STARTTIMESTAMP", from.getText() != null ? from.getText().toString() : "");
        i.putExtra("ENDTIMESTAMP", to.getText() != null ? to.getText().toString() : "");
        i.putExtra("KEYWORDS", keywords.getText() != null ? keywords.getText().toString() : "");
        setResult(RESULT_OK, i);
        finish();
    }

}
