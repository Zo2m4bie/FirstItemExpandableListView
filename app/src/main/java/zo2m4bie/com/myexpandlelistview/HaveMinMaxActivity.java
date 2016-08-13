package zo2m4bie.com.myexpandlelistview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.zo2m4bie.firstitemexpandablelistview.view.SelfExpandableListView;

import zo2m4bie.com.myexpandlelistview.adapter.CityAdapter;
import zo2m4bie.com.myexpandlelistview.model.CityModel;

/**
 * Created by dima on 8/7/16.
 */
public class HaveMinMaxActivity extends AppCompatActivity {

    private SelfExpandableListView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_have_min_max);
        mList = (SelfExpandableListView) findViewById(R.id.my_list);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Click on item", "click on item = " + position);
                mList.scrollToAnimated(position);
            }
        });
        CityModel[] testArray = new CityModel[10];
        testArray[0] = new CityModel("Test1", "Test1", R.drawable.ic_launcher);
        testArray[1] = new CityModel("Test1", "Test1", R.drawable.ic_launcher);
        testArray[2] = new CityModel("Test1", "Test1", R.drawable.ic_launcher);
        testArray[3] = new CityModel("Test1", "Test1", R.drawable.ic_launcher);
        testArray[4] = new CityModel("Test1", "Test1", R.drawable.ic_launcher);
        testArray[5] = new CityModel("Test1", "Test1", R.drawable.ic_launcher);
        testArray[6] = new CityModel("Test1", "Test1", R.drawable.ic_launcher);
        testArray[7] = new CityModel("Test1", "Test1", R.drawable.ic_launcher);
        testArray[8] = new CityModel("Test1", "Test1", R.drawable.ic_launcher);
        testArray[9] = new CityModel("Test1", "Test1", R.drawable.ic_launcher);
        mList.setAdapter(new CityAdapter(this, testArray));
    }
}
