package com.ymk.project.soa.handler;

import java.nio.file.AccessDeniedException;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ymk.project.soa.constants.ApiErrorConstants;
import com.ymk.project.soa.exception.AuthorizationException;
import com.ymk.project.soa.exception.BadRequestException;
import com.ymk.project.soa.exception.ErrorResponse;
import com.ymk.project.soa.exception.InvalidDataTypeException;
import com.ymk.project.soa.exception.MethodNotAllowedException;
import com.ymk.project.soa.exception.NotFoundException;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    //private static InsightsLogger log = LoggerFactory.getLogger(ResponseExceptionHandler.class);

    @Autowired
    private MessageSource errorMessageSource;

    @Autowired
    private HttpServletRequest request;

    private static final String correlationid = "correlationid";

    @ExceptionHandler({Throwable.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    ErrorResponse handleInternalError(Throwable e, WebRequest request) {
        Locale locale = LocaleContextHolder.getLocale();
        String error = errorMessageSource.getMessage(ApiErrorConstants.ERROR_GENERIC, null, locale);
        String errorMessage = errorMessageSource.getMessage(ApiErrorConstants.ERROR_GENERIC_MSG, null, locale);
    
        return new ErrorResponse(error, errorMessage, traceProperties().get(correlationid));
    }

    @ExceptionHandler({AuthorizationException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    ErrorResponse handleUnAuthorizedException(AuthorizationException e, WebRequest request) {

        Locale locale = LocaleContextHolder.getLocale();
        String error = errorMessageSource.getMessage(e.getErrorCode(), null, locale);
        String errorMessage = errorMessageSource.getMessage(e.getErrorMessage(), null, locale);
        //log.error(error, e);
        return new ErrorResponse(error, errorMessage, traceProperties().get(correlationid));
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    ErrorResponse handleNotFoundException(NotFoundException e, WebRequest request) {
        Locale locale = LocaleContextHolder.getLocale();
        String error = errorMessageSource.getMessage(e.getErrorCode(), null, locale);
        String errorMessage = errorMessageSource.getMessage(e.getErrorMessageCode(), null, locale);
        return new ErrorResponse(error, errorMessage, traceProperties().get(correlationid));
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    ErrorResponse
    handleAccessDeniedException(AccessDeniedException e, WebRequest request) {
        Locale locale = LocaleContextHolder.getLocale();
        String error = errorMessageSource.getMessage(ApiErrorConstants.ERROR_AUTH_ACCESS_DENIED, null, locale);
        String errorMessage = errorMessageSource.getMessage(ApiErrorConstants.ERROR_AUTH_ACCESS_DENIED_MSG, null, locale);
        return new ErrorResponse(error, errorMessage, traceProperties().get(correlationid));
    }

    @ExceptionHandler(MethodNotAllowedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    ErrorResponse handleAccessDeniedException(MethodNotAllowedException e, WebRequest request) {
        Locale locale = LocaleContextHolder.getLocale();
        String error = errorMessageSource.getMessage(e.getErrorCode(), null, locale);
        String errorMessage = errorMessageSource.getMessage(e.getErrorMessage(), null, locale);
        return new ErrorResponse(error, errorMessage, traceProperties().get(correlationid));
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Locale locale = LocaleContextHolder.getLocale();
        String error = errorMessageSource.getMessage(ApiErrorConstants.ERROR_GENERIC_METHOD_NOT_ALLOWED, null, locale);
        String errorMessage = errorMessageSource.getMessage(ApiErrorConstants.ERROR_GENERIC_METHOD_NOT_ALLOWED_MSG, null, locale);
        ErrorResponse errorBody = new ErrorResponse(error, errorMessage, traceProperties().get(correlationid));
        return this.handleExceptionInternal(ex, errorBody, headers, status, request);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorResponse handleBadRequestException(BadRequestException e, WebRequest request) {
        Locale locale = LocaleContextHolder.getLocale();
        String error = errorMessageSource.getMessage(e.getErrorCode(), null, locale);
        String errorMessage = errorMessageSource.getMessage(e.getErrorMessage(), null, locale);
        return new ErrorResponse(error, errorMessage, traceProperties().get(correlationid));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorResponse handleMethodArgumentTypeMismatchExceptionException(MethodArgumentTypeMismatchException e, WebRequest request) {
        Locale locale = LocaleContextHolder.getLocale();
        String error = errorMessageSource.getMessage(ApiErrorConstants.ERROR_GENERIC_METHOD_NOT_MATCHING, null, locale);
        String errorMessage = errorMessageSource.getMessage(ApiErrorConstants.ERROR_GENERIC_METHOD_NOT_MATCHING_MSG, null, locale);

        return new ErrorResponse(error, errorMessage, traceProperties().get(correlationid));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorKey = e.getBindingResult().getFieldError().getDefaultMessage();
        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append(e.getBindingResult().getFieldError().getDefaultMessage());
        errorMessage.append(".msg");
        String error = errorMessageSource.getMessage(errorKey, null, locale);
        String message = errorMessageSource.getMessage(errorMessage.toString(), null, locale);
        return super.handleExceptionInternal(e, new ErrorResponse(error, message, traceProperties().get(correlationid)), headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Locale locale = LocaleContextHolder.getLocale();
        String error = errorMessageSource.getMessage(ApiErrorConstants.ERROR_GENERIC_CONTENTTYPE_MISMATCH, null, locale);
        String errorMessage = errorMessageSource.getMessage(ApiErrorConstants.ERROR_GENERIC_CONTENTTYPE_MISMATCH_MSG, null, locale);
        ErrorResponse errorResponse = new ErrorResponse(error, errorMessage, traceProperties().get(correlationid));

        return new ResponseEntity(errorResponse, headers, HttpStatus.BAD_REQUEST);
    }


    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        Locale locale = LocaleContextHolder.getLocale();
        String error;
        String errorMessage;

        if (ex.getCause().getCause() instanceof InvalidDataTypeException) {
            InvalidDataTypeException rootException = (InvalidDataTypeException) ex.getCause().getCause();
            String parameterName = rootException.getParameterName();
            error = errorMessageSource.getMessage(ApiErrorConstants.ERROR_GENERIC_PARAMETER_INVALID, new Object[]{parameterName}, locale);
            errorMessage = errorMessageSource.getMessage(ApiErrorConstants.ERROR_GENERIC_PARAMETER_INVALID_MSG, new Object[]{parameterName}, locale);
        } else {
            error = errorMessageSource.getMessage(ApiErrorConstants.ERROR_REQUEST_JSON_INVALID, null, locale);
            errorMessage = errorMessageSource.getMessage(ApiErrorConstants.ERROR_REQUEST_JSON_INVALID_MSG, null, locale);
        }
        ErrorResponse errorResponse = new ErrorResponse(error, errorMessage, traceProperties().get(correlationid));
    
        return new ResponseEntity(errorResponse, headers, status);
    }

    private Map<String, String> traceProperties() {

        Object obj = request.getAttribute("traceproperties");
        Map<String, String> traceProperties = new ConcurrentHashMap<>();

        if (obj != null) {
            traceProperties = (Map) obj;
        }

        return traceProperties;
    }
}