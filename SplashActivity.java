package nhj0401.googlemapexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Simon on 2017-11-22.
 */
//스플래시 화면의 해상도 동적구성은 코드상으로는 할 수 없음
//비트맵으로 가져온 이미지의 크기를 동적으로 변경할 수 없기에
//각 해상도에 적합한 이미지를 따로 준비해야함.

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
        startActivity(intent);

        finish();
    }
}
