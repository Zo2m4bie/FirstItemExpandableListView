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
        testArray[0] = new CityModel("USA", "Most People Behind Bars. The U.S. has 5% of the world’s population but 25% of the world’s incarcerated population at 2.2 million people behind bars .", R.drawable.usa);
        testArray[1] = new CityModel("Nauru", "Nauru: With over 95% of its population overweight, the small island nation of Nauru is by far the fattest country on Earth and presumably has the highest per capita manssiere sale rates. ",
                R.drawable.nauru);
        testArray[2] = new CityModel("France", "France: If you count everything, including overseas territories, then France claims the title by covering 12 time zones.", R.drawable.france);
        testArray[3] = new CityModel("Guam", "Roads Made of Coral. Because Guam doesn’t have any natural sand, but rather coral, the island nation makes its asphalt using a mix of ground coral and oil rather than importing sand from abroad.", R.drawable.guam);
        testArray[4] = new CityModel("Canada", "Most Lakes in the World. With over 3 million lakes 9% of Canadian territory is actually fresh water and over 60% of all the lakes in the world are found within its borders.", R.drawable.canada);
        testArray[5] = new CityModel("Afghanistan", " Producing a whopping 95% of the world’s opium.", R.drawable.afghanistan);
        testArray[6] = new CityModel("Papua New Guinea", "Although English is its official language, only 1-2% of the population actually speak it. As the most linguistically diverse country in the world, over 820 languages are spoken in Papua New Guinea or 12% of the world’s total.", R.drawable.pap_new_guin);
        testArray[7] = new CityModel("Mongolia", "At 4 people per square mile, Mongolia is the least densely populated country on Earth. Compare this to the Mong Kok district of Hong Kong that has the highest population density in the world with 340,000 people per square mile.", R.drawable.mongolia);
        testArray[8] = new CityModel("Monaco", "Although Vatican City is smaller (.17 sq mi) than Monaco (.8 sq mi), unlike Monaco it doesn’t have any permanent residents which leaves Monaco as the smallest permanently inhabited nation in the world…smaller than Central Park.", R.drawable.monaco);
        testArray[9] = new CityModel("Ukraine", "With a natural decrease in population of .8% annually, between now and 2050 Ukraine is expected to lose around 30% of its people.", R.drawable.ukraine);
        mList.setAdapter(new CityAdapter(this, testArray));
    }
}
