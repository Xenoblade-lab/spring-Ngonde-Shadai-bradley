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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.upc.models.dtos.CandidatFormationDto;
import edu.upc.services.CandidatFormationService;
import edu.upc.services.CandidatService;
import edu.upc.services.FormationService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/candidats-formations")
public class CandidatFormationController {

	@Autowired
	private CandidatFormationService service;

	@Autowired
	private CandidatService candidatService;

	@Autowired
	private FormationService formationService;

	@Autowired
	private MessageSource messageSource;

	@GetMapping
	public String list(Model model) {
		model.addAttribute("rows", service.get());
		return "candidats-formations-view";
	}

	@GetMapping("/create")
	public String createForm(Model model) {
		prepareForm(model, new CandidatFormationDto());
		return "candidats-formations-form";
	}

	@PostMapping("/save")
	public String save(@Valid @ModelAttribute("form") CandidatFormationDto form, BindingResult result, Model model,
			RedirectAttributes redirect) {
		if (result.hasErrors()) {
			prepareForm(model, form);
			return "candidats-formations-form";
		}
		service.create(form, result);
		redirect.addFlashAttribute("message",
				messageSource.getMessage("message.enrolled", null, LocaleContextHolder.getLocale()));
		return "redirect:/candidats-formations";
	}

	@GetMapping("/{id}/delete")
	public String delete(@PathVariable("id") long id, RedirectAttributes redirect) {
		service.delete(id);
		redirect.addFlashAttribute("message",
				messageSource.getMessage("message.deleted", null, LocaleContextHolder.getLocale()));
		return "redirect:/candidats-formations";
	}

	private void prepareForm(Model model, CandidatFormationDto form) {
		model.addAttribute("form", form);
		model.addAttribute("candidats", candidatService.get());
		model.addAttribute("formations", formationService.get());
	}

}
