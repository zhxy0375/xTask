package org.xtask;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;


@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(XtaskImportSelector.class)
public @interface EnableXtask {
}
