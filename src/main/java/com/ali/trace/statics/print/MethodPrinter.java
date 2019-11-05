package com.ali.trace.statics.print;


import com.ali.trace.statics.analize.MetaFactory.MethodMeta;

import java.util.Map;

/**
 * @auther hanlang
 * @date 2019-11-05 11:51
 */
public class MethodPrinter implements IPrinter{
    private MethodMeta meta;

    public MethodPrinter(MethodMeta meta){
        this.meta = meta;
    }

    public void print(boolean printEmpty) {
        if(meta == null){
            System.err.println("method not exits!");
        }else {
            Map<String, MethodMeta> invokes = meta.getInvokes();
            Map<String, MethodMeta> beInvokes = meta.getBeInvokes();
            if(printEmpty || !invokes.isEmpty() || !beInvokes.isEmpty()){
                System.out.println("method:[" + meta.getClazz() + "." + meta + "]");
            }
            if(printEmpty || !invokes.isEmpty()){
                System.out.println("  invokes:");
                for (MethodMeta invoke : invokes.values()) {
                    System.out.println("   " + invoke.getClazz() + "." + invoke);
                }
            }
            if(printEmpty || !beInvokes.isEmpty()) {
                System.out.println("  beInvokes:");
                for (MethodMeta invoke : beInvokes.values()) {
                    System.out.println("   " + invoke.getClazz() + "." + invoke);
                }
            }
        }
    }
}
