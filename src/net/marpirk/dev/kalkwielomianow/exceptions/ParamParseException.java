package net.marpirk.dev.kalkwielomianow.exceptions;

import net.marpirk.dev.kalkwielomianow.Calc;

/**
 *
 * @author Marek Pikuła
 */
public class ParamParseException extends Exception {
    
    public String param, error, msg;

    public ParamParseException() { }
    public ParamParseException(String param, String error, String msg) {
        this.param = param;
        this.error = error;
        this.msg = msg;
    }
            
    @Override
    public String getMessage() {
        return Calc.i18n.getString("PARAM_PARSE_ERROR_IN_PARAM") + ": \"" + param + "\"" + 
                (error != null ? " >" + error + "<" : "") +
                (msg != null ? " - " + msg : "");
    }

}
