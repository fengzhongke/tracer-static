package com.ali.trace.statics.analize;

import java.io.IOException;
import java.io.InputStream;

/**
 * @auther hanlang
 * @date 2019-11-05 16:25
 */
public interface InputStreamResource{
    void parseInputStream(InputStream in) throws IOException;
}
