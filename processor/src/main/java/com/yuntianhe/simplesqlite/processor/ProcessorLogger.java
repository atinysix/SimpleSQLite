package com.yuntianhe.simplesqlite.processor;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.Diagnostic;

/**
 * desc:
 * author: daiwj on 2020-04-10 21:43
 */
public class ProcessorLogger {

    private Messager logger;

    public ProcessorLogger(ProcessingEnvironment environment) {
        logger = environment.getMessager();
    }

    public void w(String message) {
        w(getClass().getSimpleName(), message);
    }

    public void w(String tag, String message) {
        logger.printMessage(Diagnostic.Kind.ERROR, tag + " " + message);
    }

    public void e(String message) {
        e(getClass().getSimpleName(), message);
    }

    public void e(String tag, String message) {
        logger.printMessage(Diagnostic.Kind.ERROR, tag + " " + message);
    }

}
