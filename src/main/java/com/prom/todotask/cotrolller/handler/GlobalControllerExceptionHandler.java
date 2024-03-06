package com.prom.todotask.cotrolller.handler;

import com.prom.todotask.cotrolller.handler.dto.ExceptionDTO;
import com.prom.todotask.exception.BadRequest400Exception;
import com.prom.todotask.exception.Unauthorized401Exception;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;


@Slf4j
@ControllerAdvice
public class GlobalControllerExceptionHandler {


    private static final String EXCEPTION_BY_ROUTE = "An exception was handled by route: {}";

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ExceptionDTO> exceptionInternal(Exception e, WebRequest request) {
        final String url = ((ServletWebRequest) request).getRequest().getRequestURI();
        return processException(e, url, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = BadRequest400Exception.class)
    public ResponseEntity<ExceptionDTO> badRequestParametersException(BadRequest400Exception e, WebRequest request) {
        final String url = ((ServletWebRequest) request).getRequest().getRequestURI();
        return processException(e, url, e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Unauthorized401Exception.class)
    public ResponseEntity<ExceptionDTO> unauthorizedException(Unauthorized401Exception e, WebRequest request) {
        final String url = ((ServletWebRequest) request).getRequest().getRequestURI();
        return processException(e, url, e.getMessage(), HttpStatus.UNAUTHORIZED);
    }


    private ResponseEntity<ExceptionDTO> processException(Exception e, String url, String message, HttpStatus httpStatus) {
        log.error(EXCEPTION_BY_ROUTE, url, e);
        ExceptionDTO exceptionDTO = new ExceptionDTO();
        exceptionDTO.setMessage(message);
        exceptionDTO.setUrl(url);
        return new ResponseEntity<>(exceptionDTO, httpStatus);
    }


}
