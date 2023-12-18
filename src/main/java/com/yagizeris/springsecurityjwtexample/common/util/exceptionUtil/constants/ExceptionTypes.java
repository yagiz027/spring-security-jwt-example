package com.yagizeris.springsecurityjwtexample.common.util.exceptionUtil.constants;

public class ExceptionTypes {
    private ExceptionTypes(){}

    public static class Exceptions{
        private Exceptions(){}
        public static final String VALIDATION_EXCEPTION = "VALIDATION_EXCEPTION";
        public static final String BUSINESS_EXCEPTION = "BUSINESS_EXCEPTION";
        public static final String RUNTIME_EXCEPTION = "RUNTIME_EXCEPTION";
        public static final String DATA_INTEGRITY_VIOLATION = "DATA_INTEGRITY_VIOLATION";
        public static final String EXPIRED_JWT_EXCEPTION = "EXPIRED_JWT_EXCEPTION";
        public static final String RESOURCE_ACCESS_EXCEPTION = "RESOURCE_ACCESS_EXCEPTION";
    }
}
