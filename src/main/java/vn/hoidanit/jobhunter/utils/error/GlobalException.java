package vn.hoidanit.jobhunter.utils.error;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import vn.hoidanit.jobhunter.domain.RestResponse;

@RestControllerAdvice
public class GlobalException {
	@ExceptionHandler(value = IdInvalidException.class)
	public ResponseEntity<RestResponse<Object>> handleException(IdInvalidException IdException) {

		RestResponse<Object> res = new RestResponse<Object>();
		res.setStatusCode(HttpStatus.BAD_REQUEST.value());
		res.setMessage("IdInvalidException");
		res.setError(IdException.getMessage());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);

	}

	// @ExceptionHandler(value = MethodArgumentNotValidException.class)
	// public ResponseEntity<RestResponse<Object>>
	// handleMethodNotValidException(Exception e, WebRequest request) {

	// RestResponse<Object> res = new RestResponse<Object>();
	// res.setStatusCode(HttpStatus.BAD_REQUEST.value());
	// res.setError(((MethodArgumentNotValidException) e).getBody().getDetail());
	// res.setPath(request.getDescription(false).replace("uri=", ""));
	// // xu ly cat chuoi
	// String message = e.getMessage();
	// int start = message.lastIndexOf("[");
	// int end = message.lastIndexOf("]");
	// message = message.substring(start + 1, end - 1);
	// res.setMessage(message);

	// return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
	// }
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<RestResponse<Object>> handleMethodNotValidException(MethodArgumentNotValidException e,
			WebRequest request) {
		BindingResult result = e.getBindingResult();
		final List<FieldError> fieldErrors = result.getFieldErrors();

		RestResponse<Object> res = new RestResponse<Object>();
		res.setStatusCode(HttpStatus.BAD_REQUEST.value());
		res.setError(((MethodArgumentNotValidException) e).getBody().getDetail());
		res.setPath(request.getDescription(false).replace("uri=", ""));

		// List<String> errors = fieldErrors.stream().map(f ->
		// f.getDefaultMessage()).collect(Collectors.toList());
		// res.setMessage(errors.size() > 1 ? errors : errors.get(0));

		List<String> errors = new ArrayList<>();
		for (FieldError fieldError : fieldErrors) {
			errors.add(fieldError.getDefaultMessage());
		}
		res.setMessage(errors.size() > 1 ? errors : errors.get(0));

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
	}

}
