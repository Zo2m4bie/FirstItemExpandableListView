package zo2m4bie.com.myexpandlelistview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.zo2m4bie.firstitemexpandablelistview.view.SelfExpandableListView;

/**
 * Created by dima on 8/7/16.
 */
public class HaveMinActivity extends AppCompatActivity {

    private SelfExpandableListView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_have_min);
        mList = (SelfExpandableListView) findViewById(R.id.my_list);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Click on item", "click on item = " + position);
                mList.scrollToAnimated(position);
            }
        });
        String[] testArray = new String[10];
        testArray[0] = "Test1";
        testArray[1] = "Test2";
        testArray[2] = "Test3";
        testArray[3] = "Test4";
        testArray[4] = "Test5";
        testArray[5] = "Test6";
        testArray[6] = "Test7";
        testArray[7] = "Test8";
        testArray[8] = "Test9";
        testArray[9] = "Test10";
        mList.setAdapter(new MyAdapter(this,
                R.layout.item_test,
                android.R.id.text1, testArray));
    }
}
