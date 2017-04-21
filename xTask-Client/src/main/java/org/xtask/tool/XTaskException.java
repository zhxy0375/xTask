package org.xtask.tool;

/**
 * Created by zhxy on 17/3/2.
 */
public class XTaskException extends Exception{
    public XTaskException(String message) {
        super(message);
    }

    public XTaskException(String message, Throwable cause){
        super(message,cause);
    }
}
