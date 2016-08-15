package zo2m4bie.com.myexpandlelistview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.zo2m4bie.firstitemexpandablelistview.view.SelfExpandableListView;

import zo2m4bie.com.myexpandlelistview.adapter.RecipeAdapter;
import zo2m4bie.com.myexpandlelistview.model.RecipeModel;

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
        RecipeModel[] testArray = new RecipeModel[10];
        testArray[0] = new RecipeModel("Smokey Salmon", R.drawable.smokey_salmon);
        testArray[1] = new RecipeModel("Tuna Macaroni Salad", R.drawable.tuna_sal);;
        testArray[2] = new RecipeModel("Smothered Beef Short Ribs", R.drawable.beef_ribs);;
        testArray[3] = new RecipeModel("Chicken Breast Cutlets with Artichokes and Capers", R.drawable.chicken_breast);;
        testArray[4] = new RecipeModel("Marinated Grilled Pork Tenderloin", R.drawable.marinated_grilled_pork);;
        testArray[5] = new RecipeModel("Oat and Blueberry Muffins", R.drawable.maffins);;
        testArray[6] = new RecipeModel("Breakfast Cups", R.drawable.ic_launcher);;
        testArray[7] = new RecipeModel("Blueberry Banana Breakfast Bars", R.drawable.breakfast_bars);;
        testArray[8] = new RecipeModel("Emmi’s Banana Wraps", R.drawable.wraps);;
        testArray[9] = new RecipeModel("Sassy Sangria", R.drawable.sassy_sangria);;
        RecipeAdapter adapter = new RecipeAdapter(this, testArray);
        mList.setAdapter(adapter);
    }
}
