package edu.upc.utils;

import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

@Component
public class MyLibraryUtil {
	
	public void validate(BindingResult result) {
		if (result == null) {
			// Nothing

		} else if (result.getTarget() == null) {
			throw new RuntimeException();

		} else if (result.hasErrors()) {
			final StringJoiner joiner = new StringJoiner(", ");

			final List<ObjectError> errors = result.getAllErrors();
			if (!CollectionUtils.isEmpty(errors)) {
				final List<String> emsgs = errors.stream().map(e -> e.getDefaultMessage()).distinct()
						.collect(Collectors.toList());

				for (String item : emsgs) {
					joiner.add(item);
				}
			}

			throw new RuntimeException(String.valueOf(joiner));

		}
	}

}
