package com.ali.trace.statics;

import com.ali.trace.statics.analize.FileResource;
import com.ali.trace.statics.analize.MetaFactory;
import com.ali.trace.statics.analize.MetaFactory.MethodMeta;
import com.ali.trace.statics.analize.MetaFactory.ClassMeta;
import com.ali.trace.statics.print.AllPrinter;
import com.ali.trace.statics.print.ClassPrinter;
import com.ali.trace.statics.print.MethodPrinter;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

/**
 * @auther hanlang
 * @date 2019-11-05 14:45
 */
public class ServiceFinder {

    public static void main(String[] args) throws IOException, ParseException {
        if(args.length < 2){
            System.err.println("first arg is jar/class/directory");
            System.err.println("second arg is interface to invoked");
            System.exit(0);
        }
        String file = args[0];
        String inter = args[1];

        MetaFactory factory = new MetaFactory();
        new FileResource(factory).parseFile(new String[]{file});
        Map<String, ClassMeta> metas = factory.getMetas();

        ClassMeta clazzMeta = metas.get(inter);
        if (clazzMeta == null) {
            System.err.println("class:[" + inter + "] not exits!");
        } else {
            Map<String, MethodMeta> methods = clazzMeta.getMethods();
            for (MethodMeta methodMeta : methods.values()) {
                new MethodPrinter(methodMeta).print(false);
            }
        }
    }
}
