package br.com.wesleyyps.cobranca.infrastructure.advices;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.wesleyyps.cobranca.domain.exceptions.CobrancaException;
import br.com.wesleyyps.cobranca.application.dtos.responses.ErrorResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class ExceptionHandlerControllerAdvice extends ResponseEntityExceptionHandler
{
    @Value("${app.show-stacktraces}")
    protected Boolean showStacktraces;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(
        Exception exception,
        WebRequest webRequest
    ) {
        this.logger.error("Uncaught general exception", exception);

        ErrorResponse errorDetails = this.getErrorResponse(
            exception,
            webRequest
        );
        return new ResponseEntity<>(
            errorDetails,
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(CobrancaException.class)
    public ResponseEntity<ErrorResponse> handleCobrancaException(
        CobrancaException exception,
        WebRequest webRequest
    ) {
        this.logger.error("Uncaught cobranca exception", exception);

        ErrorResponse errorDetails = this.getErrorResponse(
            exception,
            webRequest
        );
        return new ResponseEntity<>(
            errorDetails,
            exception.getHttpStatusCode()
        );
    }

    @Override
    public ResponseEntity<Object> handleHttpMessageNotReadable(
        HttpMessageNotReadableException exception, 
        HttpHeaders headers, 
        HttpStatusCode status, 
        WebRequest request
    ) {
        this.logger.error("Uncaught HttpMessageNotReadableException exception", exception);

        ErrorResponse errorDetails = this.getErrorResponse(
            exception,
            request
        );
        return new ResponseEntity<>(
            errorDetails,
            HttpStatus.BAD_REQUEST
        );
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
		MethodArgumentNotValidException exception, 
        HttpHeaders headers, 
        HttpStatusCode status, 
        WebRequest request
    ) {
        this.logger.error("Uncaught MethodArgumentNotValidException exception", exception);

        Map<String, String> errors = new HashMap<>();
        exception
            .getBindingResult()
            .getAllErrors()
            .forEach(error -> {
                String fieldName = ((FieldError)error).getField();
                String message = error.getDefaultMessage();
                errors.put(fieldName, message);
            });
        ErrorResponse errorDetails = new ErrorResponse(
            new Date(),
            "Validation error",
            errors
        );
        return new ResponseEntity<>(
            errorDetails,
            HttpStatus.BAD_REQUEST
        );
    }

    protected ErrorResponse getErrorResponse(
        Exception exception,
        WebRequest webRequest
    ) {
        return new ErrorResponse(
            new Date(), 
            this.showStacktraces.equals(true)
                ? exception.getLocalizedMessage()
                : "Erro interno do servidor",
            !Objects.isNull(webRequest)
                ? webRequest.getDescription(false)
                : null,
            this.showStacktraces.equals(true)
                ? this.getStacktrace(exception)
                : new ArrayList<String>()
        );
    }

    protected ErrorResponse getErrorResponse(
        CobrancaException exception,
        WebRequest webRequest
    ) {
        return new ErrorResponse(
            new Date(), 
            exception.getMessage(),
            !Objects.isNull(webRequest) 
                ? webRequest.getDescription(false)
                : null,
            this.showStacktraces.equals(true)
                ? this.getStacktrace(exception)
                : new ArrayList<String>()
        );
    }

    protected List<String> getStacktrace(Exception e) {
        List<String> stacktrace = new ArrayList<>();
        for (StackTraceElement element : e.getStackTrace()) {
            stacktrace.add(element.toString());
        }
        return stacktrace;
    }
}