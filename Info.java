package nhj0401.googlemapexample;

import android.content.Intent;
import android.os.Bundle;
//import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Simon on 2017-11-29.
 */

public class Info extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
    }

    public void eClick(View view)
    {
        Intent intent = new Intent(this, EngInfo.class);
        startActivity(intent);
        Toast.makeText(this, "English", Toast.LENGTH_SHORT).show();

    }
    public void gmClick(View view)
    {
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }
}
