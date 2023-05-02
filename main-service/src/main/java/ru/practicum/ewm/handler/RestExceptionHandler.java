package ru.practicum.ewm.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.WebRequest;
import ru.practicum.ewm.exception.*;

import java.util.List;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFoundException(final NotFoundException e, WebRequest request) {
        return ApiError.builder()
                .errors(List.of(e.getClass().getName()))
                .message(e.getLocalizedMessage())
                .description("Не найден объект" + request.getDescription(false))
                .httpStatus(HttpStatus.NOT_FOUND)
                .build();
    }

    @ExceptionHandler({TimeException.class, PublishingException.class, FieldValidationException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError handleValidationException(final BadRequestException e, WebRequest request) {
        return ApiError.builder()
                .errors(List.of(e.getClass().getName()))
                .message(e.getLocalizedMessage())
                .description(request.getDescription(false))
                .httpStatus(HttpStatus.FORBIDDEN)
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleInternalServerErrorException(final HttpServerErrorException.InternalServerError e,
                                                       WebRequest request) {
        return ApiError.builder()
                .errors(List.of(e.getClass().getName()))
                .message(e.getLocalizedMessage())
                .description(request.getDescription(false))
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleThrowableExceptions(final Throwable e) {
        return ApiError.builder()
                .errors(List.of(e.getClass().getName()))
                .message(e.getLocalizedMessage())
                .description("Bad Request")
                .httpStatus(HttpStatus.BAD_REQUEST)
                .build();
    }
}
