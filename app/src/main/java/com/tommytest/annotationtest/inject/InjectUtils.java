package com.tommytest.annotationtest.inject;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.RequiresApi;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class InjectUtils {

    public static void injectOnClick(Activity activity) {
        Class<? extends Activity> cls = activity.getClass();
        Method[] declaredMethods = cls.getDeclaredMethods();

        for (Method declaredMethod: declaredMethods) {
            if (declaredMethod.isAnnotationPresent(InjectOnClick.class)) {
                InjectOnClick injectOnClick = declaredMethod.getAnnotation(InjectOnClick.class);
                if (injectOnClick == null) {
                    continue;
                }
                int[] ids = injectOnClick. value();
                if (ids.length == 0) {
                    continue;
                }

                declaredMethod.setAccessible(true);
                Object onClickProxy = Proxy.newProxyInstance(cls.getClassLoader(),
                        new Class[]{View.OnClickListener.class},
                        new InvocationHandler() {
                            final Method cacheMethod = declaredMethod;
                            @Override
                            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                                return cacheMethod.invoke(activity, (View)(args[0]));
                            }
                        });

                for (int id : ids) {
                    View view = activity.findViewById(id);
                    view.setOnClickListener((View.OnClickListener) (onClickProxy));
                }
            }
        }
    }


    public static void injectView(Activity activity) {
        Class<? extends Activity> cls = activity.getClass();

        // 获得activity的所有成员
        Field[] declaredFields = cls.getDeclaredFields();

        for (Field declaredField: declaredFields) {
            if (declaredField.isAnnotationPresent(InjectView.class)) {
                InjectView injectView = declaredField.getAnnotation(InjectView.class);
                int id = injectView.value();
                View view = activity.findViewById(id);
                // 反射设置属性的值
                declaredField.setAccessible(true);
                try {
                    declaredField.set(activity, view);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public static void injectIntentParameters(Activity activity) {
        // 先判断intent 是否存在Extra
        Intent intent = activity.getIntent();
        Bundle extras = intent.getExtras();
        if (extras == null) {
            return;
        }

        // 获得Actvitiy的Class对象
        Class<? extends Activity> cls = activity.getClass();
        // 获得Acvitiy的成员变量
        Field[] declaredFields =  cls.getDeclaredFields();

        for (Field declaredField: declaredFields) {
            // 只处理含有InjectIntentParam 注解的成员
            if (declaredField.isAnnotationPresent(InjectIntentParam.class)) {
                InjectIntentParam injectIntentParam = declaredField.getAnnotation(InjectIntentParam.class);
                if (injectIntentParam == null) {
                    continue;
                }
                // 获得对应的intent的Extra字段的Key
                String intentExtraKey = injectIntentParam.value();
                if (TextUtils.isEmpty(intentExtraKey)) {
                    // 若没有指定，则Key即为成员的命名
                    intentExtraKey = declaredField.getName();
                }


                // 使用Extra的值，赋值成员
                if (extras.containsKey(intentExtraKey)) {
                    Object intentExtraValue = extras.get(intentExtraKey);

                    // 如果是Parcelable数组，不能直接赋值
                    if (declaredField.getType().isArray()) {
                       Class<?> componentType = declaredField.getType().getComponentType();
                       if (Parcelable.class.isAssignableFrom(componentType)) {
                           Object[] objs = (Object[])intentExtraValue;
                           Object[] objects = Arrays.copyOf(objs, objs.length, (Class<? extends Object[]>) declaredField.getType());
                           intentExtraValue = objects;
                       }
                    }

                    try {
                        declaredField.setAccessible(true);
                        declaredField.set(activity, intentExtraValue);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }
}
