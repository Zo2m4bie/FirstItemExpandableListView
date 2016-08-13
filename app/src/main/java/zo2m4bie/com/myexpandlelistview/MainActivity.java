package zo2m4bie.com.myexpandlelistview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zo2m4bie.floatingtoggle.FloatingToggleButton;
import com.zo2m4bie.floatingtoggle.IStateSelected;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, IStateSelected {

    public static final int STATE_MIN     = 0;
    public static final int STATE_MIN_MAX = 1;
    public static final int STATE_MAX     = 2;

    private FloatingToggleButton mFloatingToggleButton;
    private TextView mDescription;

    private String[] mDescriptionsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFloatingToggleButton = (FloatingToggleButton) findViewById(R.id.select_state);
        mFloatingToggleButton.setStateSelected(this);
        mDescription = (TextView)findViewById(R.id.tv_description);
        findViewById(R.id.start_activity).setOnClickListener(this);

        mDescriptionsArray = getResources().getStringArray(R.array.description_array);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start_activity:
                startNextActivity(getClassByState(mFloatingToggleButton.getCurrentSelectedState()));
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        selectState(mFloatingToggleButton.getCurrentSelectedState());
    }

    private void startNextActivity(Class<? extends AppCompatActivity> haveMinActivityClass) {
        Intent intent = new Intent(this, haveMinActivityClass);
        startActivity(intent);
    }

    private Class<? extends AppCompatActivity> getClassByState(int state){
        switch (state){
            case STATE_MIN:
                return HaveMinActivity.class;
            case STATE_MIN_MAX:
                return HaveMinMaxActivity.class;
            case STATE_MAX:
                return HaveMaxActivity.class;
            default:
                throw new RuntimeException("Wring state");
        }
    }
    @Override
    public void selectState(int state) {
        mDescription.setText(mDescriptionsArray[state]);
    }
}
