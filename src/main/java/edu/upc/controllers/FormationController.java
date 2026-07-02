package edu.upc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.upc.models.dtos.FormationDto;
import edu.upc.services.FormationService;
import edu.upc.utils.Mapper;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/formations")
public class FormationController {

	@Autowired
	private FormationService service;

	@Autowired
	private Mapper mapper;

	@Autowired
	private MessageSource messageSource;

	@GetMapping
	public String list(Model model) {
		model.addAttribute("rows", service.get());
		return "formations-view";
	}

	@GetMapping("/create")
	public String createForm(Model model) {
		model.addAttribute("form", new FormationDto());
		return "formations-form";
	}

	@GetMapping("/{id}/edit")
	public String editForm(@PathVariable("id") long id, Model model) {
		model.addAttribute("form", mapper.toFormationDto(service.getById(id)));
		model.addAttribute("id", id);
		return "formations-form";
	}

	@PostMapping("/save")
	public String save(@RequestParam(value = "id", required = false) Long id,
			@Valid @ModelAttribute("form") FormationDto form, BindingResult result, Model model,
			RedirectAttributes redirect) {
		if (result.hasErrors()) {
			model.addAttribute("id", id);
			return "formations-form";
		}
		if (id == null) {
			service.create(form, result);
		} else {
			service.update(id, form, result);
		}
		redirect.addFlashAttribute("message",
				messageSource.getMessage("message.saved", null, LocaleContextHolder.getLocale()));
		return "redirect:/formations";
	}

	@GetMapping("/{id}/delete")
	public String delete(@PathVariable("id") long id, RedirectAttributes redirect) {
		service.delete(id);
		redirect.addFlashAttribute("message",
				messageSource.getMessage("message.deleted", null, LocaleContextHolder.getLocale()));
		return "redirect:/formations";
	}

}
