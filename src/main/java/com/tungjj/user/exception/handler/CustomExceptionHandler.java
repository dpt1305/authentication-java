package com.tungjj.user.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.tungjj.user.dto.commonDTO.CommonResponse;
import com.tungjj.user.exception.BadSqlException;
import com.tungjj.user.exception.InvalidDateFormat;
import com.tungjj.user.exception.InvalidRequestException;
import com.tungjj.user.exception.ResourceNotFoundException;
import com.tungjj.user.exception.UnauthorizationException;
import com.tungjj.user.log.AppLogger;
import com.tungjj.user.log.LoggerFactory;
import com.tungjj.user.log.LoggerType;

@ControllerAdvice
public class CustomExceptionHandler {

    private static final AppLogger APP_LOGGER = LoggerFactory.getLogger(LoggerType.APPLICATION);

    @ExceptionHandler(BadSqlException.class)
    public ResponseEntity<CommonResponse<String>> handleBadSqlException(BadSqlException e) {
        APP_LOGGER.error(e.getMessage());
        return new ResponseEntity<CommonResponse<String>>(
                new CommonResponse<String>(false, null, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()), null,
                HttpStatus.OK.value());
    }

    @ExceptionHandler(InvalidDateFormat.class)
    public ResponseEntity<CommonResponse<String>> handleInvalidDateFormatException(InvalidDateFormat e) {
        APP_LOGGER.error(e.getMessage());
        return new ResponseEntity<>(
                new CommonResponse<String>(false, null, e.getMessage(), HttpStatus.BAD_REQUEST.value()), null,
                HttpStatus.OK.value());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CommonResponse<String>> handleResourceNotFoundException(ResourceNotFoundException e) {
        APP_LOGGER.error(e.getMessage());
        return new ResponseEntity<>(
                new CommonResponse<String>(false, null, e.getMessage(), HttpStatus.BAD_REQUEST.value()), null,
                HttpStatus.OK.value());
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<CommonResponse<String>> handleInvalidRequestException(InvalidRequestException e) {
        APP_LOGGER.error(e.getMessage());
        return new ResponseEntity<>(
                new CommonResponse<String>(false, null, e.getMessage(), HttpStatus.BAD_REQUEST.value()), null,
                HttpStatus.OK.value());
    }

    @ExceptionHandler(UnauthorizationException.class)
    public ResponseEntity<CommonResponse<String>> handleInvalidRequestException(UnauthorizationException e) {
        APP_LOGGER.error(e.getMessage());
        return new ResponseEntity<>(
                new CommonResponse<String>(false, null, e.getMessage(), HttpStatus.UNAUTHORIZED.value()), null,
                HttpStatus.OK.value());
    }
    // @ExceptionHandler(HttpMessageNotReadableException.class)
    // public ResponseEntity<CommonResponse<String>>
    // handleHttpMessageNotReadableException(
    // HttpMessageNotReadableException e) {
    // APP_LOGGER.error(e.getMessage());
    // return new ResponseEntity<>(
    // new CommonResponse<String>(false, null, e.getMessage(),
    // HttpStatus.BAD_REQUEST.value()), null,
    // HttpStatus.OK.value());
    // }

    // @ExceptionHandler(NotEncodePasswordException.class)
    // public ResponseEntity<CommonResponse<String>>
    // handleNotEncodePasswordException(NotEncodePasswordException e) {
    // APP_LOGGER.error(e.getMessage());
    // return new ResponseEntity<>(
    // new CommonResponse<String>(false, null, e.getMessage(),
    // HttpStatus.BAD_REQUEST.value()), null,
    // HttpStatus.OK.value());
    // }

    // @ExceptionHandler(InvalidPasswordException.class)
    // public ResponseEntity<CommonResponse<String>>
    // handleInvalidPasswordException(InvalidPasswordException e) {
    // APP_LOGGER.error(e.getMessage());
    // return new ResponseEntity<>(
    // new CommonResponse<String>(false, null, e.getMessage(),
    // HttpStatus.BAD_REQUEST.value()), null,
    // HttpStatus.OK.value());
    // }

    // @ExceptionHandler(BadSqlException.class)
    // public ResponseEntity<CommonResponse<String>>
    // handleBadSqlException(BadSqlException e) {
    // APP_LOGGER.error(e.getMessage());
    // return new ResponseEntity<CommonResponse<String>>(
    // new CommonResponse<String>(false, null, e.getMessage(),
    // HttpStatus.INTERNAL_SERVER_ERROR.value()), null,
    // HttpStatus.OK.value());
    // }

    // @ExceptionHandler(UnauthorizedException.class)
    // public ResponseEntity<CommonResponse<String>>
    // handleUnAuthorizedException(UnauthorizedException e) {
    // APP_LOGGER.error(e.getMessage());
    // return new ResponseEntity<CommonResponse<String>>(
    // new CommonResponse<String>(false, null, e.getMessage(),
    // HttpStatus.UNAUTHORIZED.value()), null,
    // HttpStatus.OK.value());
    // }

    // @ExceptionHandler(ForbiddenException.class)
    // public ResponseEntity<CommonResponse<String>>
    // handleForbidden(ForbiddenException e) {
    // APP_LOGGER.error(e.getMessage());
    // return new ResponseEntity<CommonResponse<String>>(
    // new CommonResponse<String>(false, null, "Access denied!",
    // HttpStatus.FORBIDDEN.value()), null,
    // HttpStatus.OK.value());
    // }

    // @ExceptionHandler(SignatureException.class)
    // public ResponseEntity<CommonResponse<String>>
    // handleSignatureException(SignatureException e) {
    // APP_LOGGER.error(e.getMessage());
    // return new ResponseEntity<CommonResponse<String>>(
    // new CommonResponse<String>(false, null, e.getMessage(),
    // HttpStatus.UNAUTHORIZED.value()), null,
    // HttpStatus.OK.value());
    // }

    // @ExceptionHandler(InternalServerException.class)
    // public ResponseEntity<CommonResponse<String>>
    // handleInternalServerError(InternalServerException e) {
    // APP_LOGGER.error(e.getMessage());
    // return new ResponseEntity<CommonResponse<String>>(
    // new CommonResponse<String>(false, null, e.getMessage(),
    // HttpStatus.INTERNAL_SERVER_ERROR.value()), null,
    // HttpStatus.OK.value());
    // }
}
