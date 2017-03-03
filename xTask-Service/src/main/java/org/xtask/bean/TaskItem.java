package org.xtask.bean;

import java.io.Serializable;

/**
 * Created by zhxy on 16/12/19.
 */
public class TaskItem implements Serializable {
    private boolean enable ;
    private String className;
    private String methodName;
    private String description;// description

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
