package com.ali.trace.statics.print;


import com.ali.trace.statics.analize.MetaFactory.MethodMeta;
import com.ali.trace.statics.analize.MetaFactory.ClassMeta;

import java.util.Map;

/**
 * @auther hanlang
 * @date 2019-11-05 11:51
 */
public class ClassPrinter implements IPrinter {
    private ClassMeta meta;

    public ClassPrinter(ClassMeta meta) {
        this.meta = meta;
    }

    public void print(boolean printEmpty) {
        Map<String, MethodMeta> methods = meta.getMethods();
        if(printEmpty || !methods.isEmpty()) {
            System.out.println("class name:[" + meta + "]");
            System.out.println("methods");
            for (MethodMeta mname : methods.values()) {
                System.out.println("   " + mname);
            }
        }
    }
}
