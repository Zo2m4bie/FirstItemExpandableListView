package zo2m4bie.com.myexpandlelistview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.min_max).setOnClickListener(this);
        findViewById(R.id.min).setOnClickListener(this);
        findViewById(R.id.max).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.min:
                startNextActivity(HaveMinActivity.class);
                break;
            case R.id.max:
                startNextActivity(HaveMaxActivity.class);
                break;
            case R.id.min_max:
                startNextActivity(HaveMinMaxActivity.class);
                break;
        }
    }

    private void startNextActivity(Class<? extends AppCompatActivity> haveMinActivityClass) {
        Intent intent = new Intent(this, haveMinActivityClass);
        startActivity(intent);
    }
}
