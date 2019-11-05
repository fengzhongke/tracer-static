package com.ali.trace.statics.print;


import com.ali.trace.statics.analize.MetaFactory;
import com.ali.trace.statics.analize.MetaFactory.ClassMeta;
import com.ali.trace.statics.analize.MetaFactory.MethodMeta;
import com.ali.trace.statics.util.MatchUtils;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * @auther hanlang
 * @date 2019-11-05 11:51
 */
public class AllPrinter implements IPrinter {
    private Map<String, ClassMeta> metas;
    private String regex;

    public AllPrinter(Map<String, ClassMeta> metas, String regex) {
        this.metas = metas;
        this.regex = regex == null ? null : regex.trim();
    }

    public void print(boolean printEmpty) {
        System.out.println("services are:");
        if (regex == null || regex.length() == 0) {
            for (String cname : metas.keySet()) {
                System.out.println("   " + cname);
            }
        } else {
            for (String cname : metas.keySet()) {
                if(MatchUtils.matching(cname, regex)) {
                    System.out.println("   " + cname);
                }
            }
        }
    }
}
