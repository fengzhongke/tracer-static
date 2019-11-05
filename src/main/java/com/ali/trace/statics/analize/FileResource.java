package com.ali.trace.statics.analize;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * @auther hanlang
 * @date 2019-11-05 10:28
 */
public class FileResource {
    private MetaFactory factory;
    public FileResource(MetaFactory factory){
        this.factory = factory;
    }

    public void parseFile(String[] paths) throws IOException {
        if(paths != null){
            for(String path : paths){
                File file = new File(path);
                if(!file.exists() || !file.canRead()){
                    System.err.println("file:[" + path + "] not exists or have no read privileges!");
                }else{
                    if(file.isDirectory()){
                        String[] subFiles = file.list();
                        String[] subPaths = new String[subFiles.length];
                        for(int i=0; i<subFiles.length; i++){
                            subPaths[i] = path + "/" + subFiles[i];
                        }
                        parseFile(subPaths);
                    }else if(path.endsWith(".class")){
                        new ClassResource(factory).parseInputStream(new FileInputStream(path));
                    }else if(path.endsWith(".jar")){
                        new JarResource(factory).parseInputStream(new FileInputStream(path));
                    }
                }
            }
        }
    }
}
