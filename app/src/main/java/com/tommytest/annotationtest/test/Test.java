package com.tommytest.annotationtest.test;

import androidx.annotation.IntDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class Test {
    @WeekDay private static int mCurrentDay;

    public static final int SUNDAY = 0;
    public static final int MONDAY = 1;

    @IntDef({SUNDAY, MONDAY})
    @Target({ElementType.FIELD, ElementType.PARAMETER})
    @Retention(RetentionPolicy.SOURCE)
    @interface WeekDay {
    }

    public static void setCurrentDay(@WeekDay int currentDay) {
        mCurrentDay = currentDay;
    }


    public static void main(String[] args) {
        Test.setCurrentDay(3);
    }

    @Retention(RetentionPolicy.SOURCE)
    @interface Channel {
        int QQ = 1;
        int WX = 2;
    }

    @Channel public static int mChannel;

    public static void setmChannel(int channel) {
        mChannel = channel;
    }
}
