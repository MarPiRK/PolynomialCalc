package net.marpirk.dev.kalkwielomianow.exceptions;

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
        return "Błąd w parametrze: \"" + param + "\"" + 
                (error != null ? ": >" + error + "<" : "") +
                (msg != null ? " - " + msg : "");
    }

}
