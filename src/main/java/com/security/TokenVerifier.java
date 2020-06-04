package com.security;

import java.util.function.Function;

public class TokenVerifier {

    private Function<Object, Boolean> isTokenValid = (token -> TokenUtil.parse(String.valueOf(token))
            .map(claims -> true) // for now just check if was parsed successfully
            .orElse(false));

    public boolean isValid(String authentication) {
        return isTokenValid.apply(authentication);
    }
}
