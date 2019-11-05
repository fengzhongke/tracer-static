package com.ali.trace.statics.analize;


import java.io.IOException;
import java.io.InputStream;

/**
 * @auther hanlang
 * @date 2019-11-05 11:51
 */
public class ClassResource implements InputStreamResource{

    private MetaFactory factory;
    public ClassResource(MetaFactory factory){
        this.factory = factory;
    }

    public void parseInputStream(InputStream in) throws IOException {
        byte[] bytes = new byte[in.available()];
        in.read(bytes, 0, bytes.length);
        new ClassViewer(factory, bytes);
    }
}
