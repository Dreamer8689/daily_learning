package com.dreamer.classLoader;

import com.dreamer.classLoader.demo.User;
import sun.misc.Launcher;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;

public class App {

    public static void main(String[] args) {

        //测试一下JDK ClassLoader
       //  TestJDKClassLoader();

        // 双亲委派机制
         // TestParentsDelegate();

        // 打破双亲委派
        TestBreakParentsDelegate();
    }


    // 测试一下JDK ClassLoader
    public static void TestJDKClassLoader() {

        // 获取 user 类加载器名称
        System.out.println(String.class.getClassLoader());
        System.out.println(com.sun.crypto.provider.DESKeyFactory.class.getClassLoader().getClass().getName());
        System.out.println(User.class.getClassLoader().getClass().getName());


        // 获取各个类加载器的父加载器
        System.out.println();
        ClassLoader appClassLoader = ClassLoader.getSystemClassLoader();
        ClassLoader extClassloader = appClassLoader.getParent();
        ClassLoader bootstrapLoader = extClassloader.getParent();
        System.out.println("bootstrapLoader : " + bootstrapLoader);
        System.out.println("extClassloader : " + extClassloader);
        System.out.println("appClassLoader : " + appClassLoader);


        // 获取 bootstrapLoader加载以下文件
        System.out.println();
        System.out.println("bootstrapLoader加载以下文件：");
        URL[] urls = Launcher.getBootstrapClassPath().getURLs();
        for (int i = 0; i < urls.length; i++) {
            System.out.println(urls[i]);
        }

        // 获取extClassloader加载以下文件
        System.out.println();
        System.out.println("extClassloader加载以下文件：");
        System.out.println(System.getProperty("java.ext.dirs"));

        // appClassLoader加载以下文件
        System.out.println();
        System.out.println("appClassLoader加载以下文件：");
        System.out.println(System.getProperty("java.class.path"));


    }


    public static void TestParentsDelegate() {
        //初始化自定义类加载器，会先初始化父类ClassLoader，其中会把自定义类加载器的父加载器设置为应用程序类加载器AppClassLoader
        MyClassLoader classLoader = new MyClassLoader("/Users/tomcat/workplace/Java/daily_learning/classLoader/target/classes/com/dreamer/classLoader/demo");
        // 几级目录，将User类的复制类User1.class丢入该目录
        Class clazz = null;
        try {
            clazz = classLoader.loadClass("com.dreamer.classLoader.demo.User");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        Object obj = null;
        try {
            obj = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        Method method = null;
        try {
            method = clazz.getDeclaredMethod("toString", null);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        try {
            method.invoke(obj, null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        System.out.println(clazz.getClassLoader().getClass().getName());
    }


    // 打破双亲委派
    public static void TestBreakParentsDelegate() {

        //初始化自定义类加载器，会先初始化父类ClassLoader，其中会把自定义类加载器的父加载器设置为应用程序类加载器AppClassLoader
        BreakParentsDelegateClassLoader classLoader = new BreakParentsDelegateClassLoader("/Users/tomcat/workplace/Java/daily_learning/classLoader/target/classes/");
        Class clazz = null;
        try {
            clazz = classLoader.loadClass("com.dreamer.classLoader.demo.User");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        Object obj = null;
        try {
            obj = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        Method method = null;
        try {
            method = clazz.getDeclaredMethod("toString", null);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        try {
            method.invoke(obj, null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        System.out.println(clazz.getClassLoader().getClass().getName());


    }

}


