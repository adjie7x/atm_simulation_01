package com.mitrais.bootcamp.domain;

/**
 * @author Aji Atin Mulyadi
 * @version $Id: CSVReaderUtilException.java, v 0.1 2020‐08‐04 8:56 Aji Atin Mulyadi Exp $$
 */
public class CSVReaderUtilException extends RuntimeException {
    private ErrorContext errorContext;

    public CSVReaderUtilException(ErrorContext errorContext) {
        this.errorContext = errorContext;
    }

    public ErrorContext getErrorContext() {
        return errorContext;
    }
}