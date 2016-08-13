package zo2m4bie.com.myexpandlelistview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.zo2m4bie.firstitemexpandablelistview.view.SelfExpandableListView;

import zo2m4bie.com.myexpandlelistview.adapter.PuzzleAdapter;
import zo2m4bie.com.myexpandlelistview.model.PuzzleModel;

/**
 * Created by dima on 8/7/16.
 */
public class HaveMaxActivity extends AppCompatActivity {

    private SelfExpandableListView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_have_max);
        mList = (SelfExpandableListView) findViewById(R.id.my_list);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Click on item", "click on item = " + position);
                mList.scrollToAnimated(position);
            }
        });
        PuzzleModel[] testArray = new PuzzleModel[10];
        testArray[0] = new PuzzleModel("Test1", "Test1", Color.CYAN);
        testArray[1] = new PuzzleModel("Test1", "Test1", Color.CYAN);
        testArray[2] = new PuzzleModel("Test1", "Test1", Color.CYAN);
        testArray[3] = new PuzzleModel("Test1", "Test1", Color.CYAN);
        testArray[4] = new PuzzleModel("Test1", "Test1", Color.CYAN);
        testArray[5] = new PuzzleModel("Test1", "Test1", Color.CYAN);
        testArray[6] = new PuzzleModel("Test1", "Test1", Color.CYAN);
        testArray[7] = new PuzzleModel("Test1", "Test1", Color.CYAN);
        testArray[8] = new PuzzleModel("Test1", "Test1", Color.CYAN);
        testArray[9] = new PuzzleModel("Test1", "Test1", Color.CYAN);
        mList.setAdapter(new PuzzleAdapter(this, testArray));
    }
}
