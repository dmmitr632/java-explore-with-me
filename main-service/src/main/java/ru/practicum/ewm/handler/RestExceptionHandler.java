package ru.practicum.ewm.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.WebRequest;
import ru.practicum.ewm.exception.*;

import java.sql.SQLException;
import java.util.List;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(NotFoundException.class) // 404 error
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFoundException(final NotFoundException e, WebRequest request) {
        return ApiError.builder()
                .errors(List.of(e.getClass().getName()))
                .message(e.getLocalizedMessage())
                .reason("Не найден объект" + request.getDescription(false))
                .status(HttpStatus.NOT_FOUND)
                .build();
    }

//    @ExceptionHandler({TimeException.class, PublishingException.class, FieldValidationException.class}) // 403 error
//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    public ApiError handleValidationException(final BadRequestException e, WebRequest request) {
//        return ApiError.builder()
//                .errors(List.of(e.getClass().getName()))
//                .message(e.getLocalizedMessage())
//                .reason(request.getDescription(false))
//                .status(HttpStatus.FORBIDDEN)
//                .build();
//    }

    @ExceptionHandler({PublishingException.class, FieldValidationException.class}) // 400 error
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidationException(final RuntimeException e, WebRequest request) {
        return ApiError.builder()
                .errors(List.of(e.getClass().getName()))
                .message(e.getLocalizedMessage())
                .reason(request.getDescription(false))
                .status(HttpStatus.BAD_REQUEST)
                .build();
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500 error
    public ApiError handleInternalServerErrorException(final HttpServerErrorException.InternalServerError e,
                                                       WebRequest request) {
        return ApiError.builder()
                .errors(List.of(e.getClass().getName()))
                .message(e.getLocalizedMessage())
                .reason(request.getDescription(false))
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
    }


    @ExceptionHandler({ConflictException.class})
    @ResponseStatus(HttpStatus.CONFLICT) // 409 error
    public ApiError handleConflictExceptions(final ConflictException e) {
        return ApiError.builder()
                .errors(List.of(e.getClass().getName()))
                .message(e.getLocalizedMessage())
                .reason("Conflict")
                .status(HttpStatus.CONFLICT)
                .build();
    }

    @ExceptionHandler({SQLException.class})
    @ResponseStatus(HttpStatus.CONFLICT) // 409 error
    public ApiError handleConflictExceptions(SQLException e) {
        return ApiError.builder()
                .errors(List.of(e.getClass().getName()))
                .message(e.getLocalizedMessage())
                .reason("Conflict in database")
                .status(HttpStatus.CONFLICT)
                .build();
    }


    @ExceptionHandler(MethodArgumentNotValidException.class) // 400 error
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleThrowableExceptions(final Throwable e) {
        return ApiError.builder()
                .errors(List.of(e.getClass().getName()))
                .message(e.getLocalizedMessage())
                .reason("Bad Request")
                .status(HttpStatus.BAD_REQUEST)
                .build();
    }


}
