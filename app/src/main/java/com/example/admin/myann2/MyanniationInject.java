package com.example.admin.myann2;

import android.content.Context;

import com.example.libanniation.Myanniaton;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;



public class MyanniationInject {

    public static void inject(Context context) {
        Class cz = context.getClass();
        Method[] methods = cz.getDeclaredMethods();
        for (Method m : methods) {
            if (m.getAnnotation(Myanniaton.class) != null) {
                Myanniaton myanniaton = m.getAnnotation(Myanniaton.class);
                String value = myanniaton.myvalue();//拿取注解的值
                run(context,value);
            }
        }
    }


    //拿到自动生成的类
    private static void run(Context context,String value) {
        try {
                Class z = Class.forName("com.example.admin.myanniation.build.MyT");
               Method mym= z.getDeclaredMethod("Mytoast", new Class[]{Context.class, String.class});
               mym.invoke(null,new Object[]{context,value});
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }

    }


}
