package com.dreamer.classLoader;

import java.io.FileInputStream;

public class BreakParentsDelegateClassLoader extends ClassLoader {
    private String classPath;

    public BreakParentsDelegateClassLoader(String classPath) {
        this.classPath = classPath;
    }

    private byte[] loadByte(String name) throws Exception {
        name = name.replaceAll("\\.", "/");
        FileInputStream fis = new FileInputStream(classPath + "/" + name + ".class");
        int len = fis.available();
        byte[] data = new byte[len];
        fis.read(data);
        fis.close();
        return data;

    }

    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            byte[] data = loadByte(name);
            return defineClass(name, data, 0, data.length);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ClassNotFoundException();
        }
    }

    /**
     * 重写类加载方法，实现自己的加载逻辑，不委派给双亲加载
     *
     * @param name
     * @param resolve
     * @return
     * @throws ClassNotFoundException
     */
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        synchronized (getClassLoadingLock(name)) {
            // 检查是否已经加载过了
            Class<?> c = findLoadedClass(name);

            if (c == null) {
                // 如果自己没加载过，没找到
                long t1 = System.nanoTime();

                // 判断是否是JDK自己的类，如果是则不打破双亲，如果不是则按照自己方式加载。
                if (!name.startsWith("com.dreamer")) {
                    c = this.getParent().loadClass(name);
                } else {
                    c = findClass(name);
                }


                // this is the defining class loader; record the stats
                sun.misc.PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
                sun.misc.PerfCounter.getFindClasses().increment();
            }
            if (resolve) {
                resolveClass(c);
            }
            return c;
        }
    }
}
