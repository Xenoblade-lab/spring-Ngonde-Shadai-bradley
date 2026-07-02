package edu.upc.utils.exceptions;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class WebExceptionHandler {

	@ExceptionHandler(NotFoundException.class)
	public String handleNotFound(NotFoundException ex, Model model) {
		model.addAttribute("error", ex.getMessage());
		return "error";
	}

	@ExceptionHandler(RuntimeException.class)
	public String handleRuntime(RuntimeException ex, Model model) {
		model.addAttribute("error", ex.getMessage());
		return "error";
	}

}
