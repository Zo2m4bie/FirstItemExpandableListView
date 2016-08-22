# MyExpandleListView

This is a list view which expand first item while user scroll this list.

![](https://github.com/Zo2m4bie/MyExpandleListView/blob/master/ezgif-3966487148.gif)

# Usage

For working implementation of this project see the `/app` folder.

1. Include following string in your `build.gradle` file:

```gradle
    compile 'com.zo2m4bie.firstitemexpandablelistview:firstitemexpandablelistview:0.0.6'
```

2. Include the SelfExpandableListView into your layout.

```xml
    <com.zo2m4bie.firstitemexpandablelistview.view.SelfExpandableListView
        android:id="@+id/my_list"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        customNS:minMaxType="HAVE_MAX_MIN"
        customNS:itemMaxHeight="600px"
        customNS:itemMinHeight="100px"
        />
```
NOTE: Make sure that you set one of the three states. To read more about states watch `States` block
NOTE: Don't forget  include custom view attribute in top layout

    xmlns:customNS="http://schemas.android.com/apk/res-auto"

3. Implement your adapter from `IFirstExpandableAdapter`.

```java
     public class CityAdapter extends IFirstExpandableAdapter<CityModel>
```

4. Use view holder pattern into your adapter. Put holder into view tag. Make sure that your holder implemented `ISelfExpandableHolder`.

```java
     class ViewHolder implements ISelfExpandableHolder
```

5. Bind into your activity/fragment and set adapter into it

```java
     mList = (SelfExpandableListView) findViewById(R.id.my_list);
     mList.setAdapter(new PuzzleAdapter(this, testArray));
```

# States

MyExpandleListView has 3 states:

* `HAVE_MAX_MIN` - this state means that you will set  `itemMaxHeight` and `itemMinHeight` attributes. If you use this state all views in list will start with height equals to `itemMinHeight`. First view will grow to value which set in `itemMaxHeight` attribute.
* `HAVE_MAX` - this state means that you will set  `itemMaxHeight` attribute. If you use this state all views in list will start with their own height(which will be measured in onmeasure method). First view will grow to value which set in `itemMaxHeight` attribute.
* `HAVE_MIN` - this state means that you will set  `itemMinHeight` attribute. If you use this state all views in list will start with height equals to `itemMinHeight`. First view will grow to default item height.

# License

     Copyright (C) 2016 Dmytro Kovalenko
     Licensed under the Apache License, Version 2.0
