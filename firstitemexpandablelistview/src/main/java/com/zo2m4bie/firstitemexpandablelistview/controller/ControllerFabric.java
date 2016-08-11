package com.zo2m4bie.firstitemexpandablelistview.controller;

/**
 * Created by dima on 8/11/16.
 */
public class ControllerFabric {

    public static final String HAVE_MAX = "HAVE_MAX";
    public static final String HAVE_MIN = "HAVE_MIN";
    public static final String HAVE_MAX_MIN = "HAVE_MAX_MIN";

    public static IMaxMinController getMinMaxController(String type){
        if(HAVE_MAX_MIN.equals(type)){
            return new MaxMinController();
        } else if(HAVE_MAX.equals(type)){
            return new HaveMaxController();
        } else if(HAVE_MIN.equals(type)){
            return new HaveMinController();
        } else {
            throw new IllegalArgumentException("You didn't set up max min type or did it wrong. minMaxType has to be HAVE_MAX, HAVE_MINor HAVE_MAX_MIN");
        }
    }

}
