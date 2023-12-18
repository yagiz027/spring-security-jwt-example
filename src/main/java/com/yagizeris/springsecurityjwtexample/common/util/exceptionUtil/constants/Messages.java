package com.yagizeris.springsecurityjwtexample.common.util.exceptionUtil.constants;

public class Messages {
    public class User{
        private User(){}
        public static final String NOT_EXISTS = "Kullanıcı bulunamadı";
        public static final String USER_ALREADY_EXISTS_EMAIL = "Bu emaile sahip kullanıcı daha önce kayıt olmuş.";
        public static final String USER_ALREADY_EXISTS_USERNAME = "Bu kullanıcı adıyla başka bir kullanıcı kayıt olmuş.";
    }

    public static class JwtPayload
    {
        private JwtPayload() {}

        public static final String ROLES = "roles";
        public static final String EMAIL = "email";
    }

    public static class Authentication
    {
        private Authentication() {}

        public static final String REGISTER_SUCCESSFUL = "Başarıyla kayıt oldunuz.";
        public static final String AUTH_SUCCESSFUL = "Giriş başarılı";
    }

    public static class JwtRequest
    {
        private JwtRequest() {}

        public static final String REQUEST_HEADER = "Authorization";
        public static final String TOKEN_PREFIX = "Bearer ";
        public static final String ROLE_PREFIX = "ROLE_";
    }

}
