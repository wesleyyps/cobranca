package br.com.wesleyyps.cobranca.domain.exceptions;

import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public class CobrancaException extends Exception {
    protected final HttpStatusCode httpStatusCode;    
 
    public CobrancaException(HttpStatusCode httpStatusCode, String message) {
       super(message);
       this.httpStatusCode = httpStatusCode;
    }
 
    public CobrancaException(HttpStatusCode httpStatusCode, String message, Throwable cause) {
       super(message, cause);
       this.httpStatusCode = httpStatusCode;
    }
 
    public CobrancaException(HttpStatusCode httpStatusCode, Throwable cause) {
       super(cause);
       this.httpStatusCode = httpStatusCode;
    }
 
    protected CobrancaException(HttpStatusCode httpStatusCode, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
       super(message, cause, enableSuppression, writableStackTrace);
       this.httpStatusCode = httpStatusCode;
    }
}
