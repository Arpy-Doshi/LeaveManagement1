package com.brevitaz.errors;

import com.brevitaz.model.ErrorDetail;
import com.brevitaz.util.ValidationUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import java.util.Date;
import org.springframework.web.bind.MethodArgumentNotValidException;

@ControllerAdvice
public class CenteralizedErrorController
{
    public CenteralizedErrorController() {
    }


    @ExceptionHandler(InvalidDateException.class)
    public ResponseEntity<ErrorDetail> invalidDateException(InvalidDateException e, WebRequest w)
    {
        ErrorDetail errorDetail = new ErrorDetail(new Date() , e.getMessage() ,
                w.getDescription(false));
        return new ResponseEntity<ErrorDetail>(errorDetail, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidIdException.class)
    public ResponseEntity<ErrorDetail> invalidIdException(InvalidIdException e , WebRequest w)
    {
        ErrorDetail errorDetail = new ErrorDetail(new Date(), e.getMessage(), w.getDescription(false));
        return new ResponseEntity<ErrorDetail>(errorDetail,HttpStatus.NOT_FOUND);
    }


  /*  @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetail> methodArgumentNotValidException(MethodArgumentNotValidException e , WebRequest w)
    {
        ErrorDetail errorDetail = new ErrorDetail(new Date(), e.getMessage(), w.getDescription(false));
        return new ResponseEntity<ErrorDetail>(errorDetail,HttpStatus.BAD_REQUEST);
    }
*/
    @ExceptionHandler(NoContentException.class)
    public ResponseEntity<ErrorDetail> noContentException(NoContentException e , WebRequest w)
    {
        ErrorDetail errorDetail = new ErrorDetail(new Date(), e.getMessage(), w.getDescription(false));
        return new ResponseEntity<ErrorDetail>(errorDetail,HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(NotAllowedException.class)
    public ResponseEntity<ErrorDetail> notAllowedException(NotAllowedException e , WebRequest w)
    {
        ErrorDetail errorDetail = new ErrorDetail(new Date(), e.getMessage(), w.getDescription(false));
        return new ResponseEntity<ErrorDetail>(errorDetail,HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetail> methodArgumentNotValidException(MethodArgumentNotValidException ex ,WebRequest w ) {
        BindingResult result = ex.getBindingResult();
        ErrorDetail errorDetail = new ErrorDetail(new Date(), w.getDescription(false), ValidationUtil.fromBindingErrors(result));
       return new ResponseEntity<ErrorDetail>(errorDetail, HttpStatus.BAD_REQUEST);
    }
















}
