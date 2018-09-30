package nhj0401.googlemapexample;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try
        {
            Thread.sleep(3000);
        }catch (InterruptedException e)
        {

        }

    }
    public void tpClick(View view)
    {
        Intent intent = new Intent(Main2Activity.this, This_Position.class);
        startActivity(intent);
    }



    public void onClick(View v) {






        Toast.makeText(this, "Info", Toast.LENGTH_SHORT).show();

    }
}
