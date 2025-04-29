package br.com.wesleyyps.cobranca.application.dtos.responses;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

public class ErrorResponse
{
    private Date timestamp;
    private String message;
    private List<String> details;
    private Map<String,String> fields;

    public ErrorResponse(
        Date timestamp,
        String message,
        String details
    ) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = new ArrayList<>();
        this.details.add(message);
        if(!Objects.isNull(details)) {
            this.details.add(details);
        }
    }

    public ErrorResponse(
        Date timestamp,
        String message,
        String details,
        Map<String,String> fields
    ) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = new ArrayList<>();
        this.details.add(message);
        if(!Objects.isNull(details)) {
            this.details.add(details);
        }
        this.fields = fields;
    }

    public ErrorResponse(
        Date timestamp,
        String message,
        Map<String,String> fields
     ) {
        this.timestamp = timestamp;
        this.message = message;
        this.fields = fields;
    }

    public ErrorResponse(
        String message,
        String details
    ) {
        this.message = message;
        this.details.add(message);
        if(!Objects.isNull(details)) {
            this.details.add(details);
        }
    }

    public ErrorResponse(
        String message,
        String details,
        Map<String,String> fields
    ) {
        this.message = message;
        this.details.add(message);
        if(!Objects.isNull(details)) {
            this.details.add(details);
        }
        this.fields = fields;
    }

    public ErrorResponse(
        Date timestamp,
        String message,
        String details,
        List<String> stacktrace
    ) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = new ArrayList<>();
        this.details.add(message);
        if(!Objects.isNull(details)) {
            this.details.add(details);
        }
        if (!stacktrace.isEmpty()) {
            this.details = Stream.concat(this.details.stream(), stacktrace.stream()).toList();
        }
    }

    public ErrorResponse(
        Date timestamp,
        String message,
        String details,
        List<String> stacktrace,
        Map<String,String> fields
    ) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = new ArrayList<>();
        this.details.add(message);
        if(!Objects.isNull(details)) {
            this.details.add(details);
        }
        if (!stacktrace.isEmpty()) {
            this.details = Stream.concat(this.details.stream(), stacktrace.stream()).toList();
        }
        this.fields = fields;
    }

    public ErrorResponse(
        String message,
        String details,
        List<String> stacktrace
    ) {
        this.message = message;
        this.details.add(message);
        if(!Objects.isNull(details)) {
            this.details.add(details);
        }
        if (!stacktrace.isEmpty()) {
            this.details = Stream.concat(this.details.stream(), stacktrace.stream()).toList();
        }
    }

    public ErrorResponse(
        String message,
        String details,
        List<String> stacktrace,
        Map<String,String> fields
    ) {
        this.message = message;
        this.details.add(message);
        if(!Objects.isNull(details)) {
            this.details.add(details);
        }
        if (!stacktrace.isEmpty()) {
            this.details = Stream.concat(this.details.stream(), stacktrace.stream()).toList();
        }
        this.fields = fields;
    }

    public Date getTimestamp() {
        return this.timestamp;
    }

    public String getMessage() {
        return this.message;
    }

    public List<String> getDetails() {
        return this.details;
    }

    public Map<String,String> getFields()
    {
        return this.fields;
    }
}
