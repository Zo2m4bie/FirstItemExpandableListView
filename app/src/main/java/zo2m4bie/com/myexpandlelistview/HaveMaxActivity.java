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
        testArray[0] = new PuzzleModel("What is in the middle of Paris?", "The letter \"R\".", Color.CYAN);
        testArray[1] = new PuzzleModel("What occurs once in a minute, twice in a moment and never in a thousand years?", "The letter \"M\".", Color.BLUE);
        testArray[2] = new PuzzleModel("What is found over your head but under your hat?", "Your Hair.", Color.parseColor("#fde8e7"));
        testArray[3] = new PuzzleModel("The more you have of it, the less you see. What is it?", "Darkness.", Color.GREEN);
        testArray[4] = new PuzzleModel("What English word has three consecutive double letters?", "Bookkeeper", Color.GRAY);
        testArray[5] = new PuzzleModel("We have legs but cannot walk.", "Tables and chairs.", Color.RED);
        testArray[6] = new PuzzleModel("Why is a wise man like a pin?", "He has a head and comes to a point.", Color.MAGENTA);
        testArray[7] = new PuzzleModel("Why don't pigs drive cars?", "They would become road hogs.", Color.YELLOW);
        testArray[8] = new PuzzleModel("Which alphabet is a hot drink?", "T(tea).", Color.parseColor("#e06b04"));
        testArray[9] = new PuzzleModel("What asks no question but demands an answer?", "A doorbell.", Color.parseColor("#fcecc9"));
        mList.setAdapter(new PuzzleAdapter(this, testArray));
    }
}
