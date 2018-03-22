package com.brevitaz.errors;

import com.brevitaz.model.ErrorDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.xml.crypto.Data;
import java.util.Date;

@ControllerAdvice
public class CenteralizedErrorController
{
   /* public CenteralizedErrorController() {
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ErrorDetail> employeeNotFoundException(EmployeeNotFoundException e, WebRequest w)
    {
        ErrorDetail errorDetail = new ErrorDetail(new Date() , e.getMessage() ,
                w.getDescription(false));
        return new ResponseEntity<ErrorDetail>(errorDetail, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(FieldEmptyException.class)
    public ResponseEntity<ErrorDetail> fieldEmptyException(FieldEmptyException e, WebRequest w)
    {
        ErrorDetail errorDetail = new ErrorDetail(new Date() , e.getMessage() ,
                w.getDescription(false));
        return new ResponseEntity<ErrorDetail>(errorDetail, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IndexNotFoundException.class)
    public ResponseEntity<ErrorDetail> indexNotFoundException(IndexNotFoundException e, WebRequest w)
    {
        ErrorDetail errorDetail = new ErrorDetail(new Date() , e.getMessage() ,
                w.getDescription(false));
        return new ResponseEntity<ErrorDetail>(errorDetail, HttpStatus.NO_CONTENT);
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

    @ExceptionHandler(LeaveApplicationNotFoundException.class)
    public ResponseEntity<ErrorDetail> leaveApplicationNotFoundException(LeaveApplicationNotFoundException e , WebRequest w)
    {
        ErrorDetail errorDetail = new ErrorDetail(new Date(), e.getMessage(),
                w.getDescription(false));
        return new ResponseEntity<ErrorDetail>(errorDetail,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetail> methodArgumentNotValidException(MethodArgumentNotValidException e , WebRequest w)
    {
        ErrorDetail errorDetail = new ErrorDetail(new Date(), e.getMessage(), w.getDescription(false));
        return new ResponseEntity<ErrorDetail>(errorDetail,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoContentException.class)
    public ResponseEntity<ErrorDetail> noContentException(NoContentException e , WebRequest w)
    {
        ErrorDetail errorDetail = new ErrorDetail(new Date(), e.getMessage(), w.getDescription(false));
        return new ResponseEntity<ErrorDetail>(errorDetail,HttpStatus.NO_CONTENT);
    }
*/
}
