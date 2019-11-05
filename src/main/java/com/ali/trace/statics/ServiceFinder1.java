package com.ali.trace.statics;

import com.ali.trace.statics.analize.FileResource;
import com.ali.trace.statics.analize.MetaFactory;
import com.ali.trace.statics.analize.MetaFactory.ClassMeta;
import com.ali.trace.statics.analize.MetaFactory.MethodMeta;
import com.ali.trace.statics.print.AllPrinter;
import com.ali.trace.statics.print.ClassPrinter;
import com.ali.trace.statics.print.MethodPrinter;
import org.apache.commons.cli.ParseException;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

/**
 * @auther hanlang
 * @date 2019-11-05 14:45
 */
public class ServiceFinder1 {


    public static void main(String[] args) throws IOException, ParseException {
//        Options options = new Options();
//        options.addOption("e", false, "with no interact mode");
//
//        OptionGroup optionGroup = new OptionGroup();
//        optionGroup.addOption(new Option("f", "jar/class file"));
//        optionGroup.addOption(new Option("d", "directory"));
//        optionGroup.setRequired(true);
//        CommandLineParser parser = new PosixParser();
//
//        CommandLine cmd = parser.parse(options, args);
//        String file = cmd.getOptionValue("e");
//        System.out.println(file);
//
//
//        if (args.length == 0) {
//            args = new String[]{new File("").getAbsolutePath()};
//        }

        String file = args[0];
        String inter = args[1];


        
        MetaFactory factory = new MetaFactory();
        new FileResource(factory).parseFile(args);
        Map<String, ClassMeta> metas = factory.getMetas();




        System.out.println("files:[" + Arrays.toString(args) + "]classes:[" + metas.size() + "]");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String line = scanner.nextLine().trim();
            if (line == null || line.length() == 0) {
            } else if ("quit".equalsIgnoreCase(line)) {
                System.exit(0);
            } else if (line.startsWith("ls")) {
                String service = line.substring(2).trim();
                if (service.length() == 0 || service.indexOf("*") != -1) {
                    new AllPrinter(metas, service).print(true);
                } else if (service.indexOf("(") == -1) {
                    ClassMeta clazzMeta = metas.get(service);
                    if (clazzMeta == null) {
                        System.err.println("class:[" + service + "] not exits!");
                    } else {
                        new ClassPrinter(metas.get(service)).print(true);
                    }
                } else {
                    int idx = service.indexOf("(");
                    idx = service.lastIndexOf(".", idx);
                    String cname = service.substring(0, idx);
                    String mname = service.substring(idx + 1);
                    ClassMeta clazzMeta = metas.get(cname);
                    if (clazzMeta == null) {
                        System.err.println("class:[" + cname + "] not exits!");
                    } else {
                        MethodMeta methodMeta = clazzMeta.getMethod(mname);
                        if (methodMeta == null) {
                            System.err.println("method:[" + mname + "] not exits!");
                        } else {
                            new MethodPrinter(methodMeta).print(true);
                        }
                    }
                }
            } else {
                System.out.println("can only use ls [service]");
            }
        }


//        String path = "/u01/project/ump-coupon/ump-coupon-service/target/ump-coupon.jar";
    }

}
