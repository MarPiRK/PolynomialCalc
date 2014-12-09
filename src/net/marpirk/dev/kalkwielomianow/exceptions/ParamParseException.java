package net.marpirk.dev.kalkwielomianow.exceptions;

import net.marpirk.dev.kalkwielomianow.i18n.i18n;

public class ParamParseException extends Exception {
    
    public String param, error, msg;

    public ParamParseException(String param) {
        this.param = param;
    }
    
    public ParamParseException(String param, String error, String msg) {
        this.param = param;
        this.error = error;
        this.msg = msg;
    }
            
    @Override
    public String getMessage() {
        return i18n.ex.getString("PARAM_PARSE_ERROR_IN_PARAM") + ": \"" + param + "\"" + 
                (error != null ? " >" + error + "<" : "") +
                (msg != null ? " - " + msg : "");
    }

}
