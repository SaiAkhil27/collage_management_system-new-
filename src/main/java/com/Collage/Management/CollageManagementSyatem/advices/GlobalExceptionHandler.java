package com.Collage.Management.CollageManagementSyatem.advices;

import com.Collage.Management.CollageManagementSyatem.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice

public class GlobalExceptionHandler {
//    @ExceptionHandler(NoSuchElementException.class)
//    public ResponseEntity<String> handleResourceNotFound(NoSuchElementException exception){
//        return new ResponseEntity<>("Resource not found", HttpStatus.NOT_FOUND);
//    }

//


//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<ApiError> handleResourceNotFound(ResourceNotFoundException exception){
//        ApiError apiError = ApiError.builder()
//                .status(HttpStatus.NOT_FOUND)
//                .message(exception.getMessage())
//                .build();
//        return new ResponseEntity<>(apiError,HttpStatus.NOT_FOUND);

    //used when user enter an id which does not exists
@ExceptionHandler(ResourceNotFoundException.class)
public ResponseEntity<ApiResponse<?>> handleResourceNotFound(ResourceNotFoundException exception){
    ApiError apiError = ApiError.builder()
            .status(HttpStatus.NOT_FOUND)
            .message(exception.getMessage())
            .build();
    return buildErrorResponEntity(apiError);
}



    @ExceptionHandler(Exception.class)
public ResponseEntity<ApiResponse<?>> handleInternalServerError(Exception exception){
    ApiError apiError = ApiError.builder()
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .message(exception.getMessage())
            .build();
        return buildErrorResponEntity(apiError);

}
@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleInputValidationErrors(MethodArgumentNotValidException exception){
        List<String> errors = exception
                .getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> error.getDefaultMessage() )
                .collect(Collectors.toList());
    ApiError apiError = ApiError.builder()
            .status(HttpStatus.BAD_REQUEST)
            .message("Input Validation failed")
            .subErrors(errors)
            .build();
    return buildErrorResponEntity(apiError);

}
    private ResponseEntity<ApiResponse<?>> buildErrorResponEntity(ApiError apiError) {
        return new ResponseEntity<>(new ApiResponse<>(apiError),apiError.getStatus());
    }

}
