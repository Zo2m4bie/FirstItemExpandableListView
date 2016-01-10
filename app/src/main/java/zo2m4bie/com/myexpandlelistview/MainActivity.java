package zo2m4bie.com.myexpandlelistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zo2m4bie.firstitemexpandablelistview.view.SelfExpandebleListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SelfExpandebleListView list = (SelfExpandebleListView) findViewById(R.id.my_list);
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
        list.setAdapter(new MyAdapter(this,
            R.layout.item_test,
            android.R.id.text1, testArray));
    }
}
