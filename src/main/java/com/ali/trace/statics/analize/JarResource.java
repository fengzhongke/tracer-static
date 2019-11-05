package com.ali.trace.statics.analize;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/**
 * @auther hanlang
 * @date 2019-11-05 11:51
 */
public class JarResource implements InputStreamResource {
    private MetaFactory factory;

    public JarResource(MetaFactory factory) {
        this.factory = factory;
    }

    public void parseInputStream(InputStream in) throws IOException {
        JarInputStream jarInput = new JarInputStream(in);
        JarEntry entry = null;
        while ((entry = jarInput.getNextJarEntry()) != null) {
            String entryName = entry.getName();
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            int chunk = 0;
            byte[] data = new byte[256];
            while (-1 != (chunk = jarInput.read(data))) {
                bytes.write(data, 0, chunk);
            }
            InputStream entryIn = new ByteArrayInputStream(bytes.toByteArray());
            if (entryName.endsWith(".jar")) {
                this.parseInputStream(entryIn);
            } else if (entryName.endsWith(".class")) {
                new ClassResource(factory).parseInputStream(entryIn);
            }
        }
    }

}
