package nhj0401.googlemapexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Simon on 2017-11-30.
 */

public class EngInfo extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eng_info);
    }

    public void kClick(View view)
    {
        Intent intent = new Intent(this, Info.class);
        startActivity(intent);
        Toast.makeText(this, "한국어", Toast.LENGTH_SHORT).show();
    }
    public void gmeClick(View view)
    {
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }
}
