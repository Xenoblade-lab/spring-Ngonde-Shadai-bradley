package edu.upc.config;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class NavModelAdvice {

	@ModelAttribute("navLocaleCode")
	public String navLocaleCode() {
		return LocaleContextHolder.getLocale().getLanguage().startsWith("en") ? "en" : "fr";
	}

	@ModelAttribute("navFrUrl")
	public String navFrUrl(HttpServletRequest request) {
		return localeUrl(request, "fr");
	}

	@ModelAttribute("navEnUrl")
	public String navEnUrl(HttpServletRequest request) {
		return localeUrl(request, "en");
	}

	@ModelAttribute("navHome")
	public boolean navHome(HttpServletRequest request) {
		return "/".equals(request.getRequestURI());
	}

	@ModelAttribute("navProfessions")
	public boolean navProfessions(HttpServletRequest request) {
		return request.getRequestURI().startsWith("/professions");
	}

	@ModelAttribute("navCandidats")
	public boolean navCandidats(HttpServletRequest request) {
		String path = request.getRequestURI();
		return path.startsWith("/candidats") && !path.startsWith("/candidats-formations");
	}

	@ModelAttribute("navFormations")
	public boolean navFormations(HttpServletRequest request) {
		return request.getRequestURI().startsWith("/formations");
	}

	@ModelAttribute("navInscriptions")
	public boolean navInscriptions(HttpServletRequest request) {
		return request.getRequestURI().startsWith("/candidats-formations");
	}

	@ModelAttribute("navLocaleFr")
	public boolean navLocaleFr() {
		return !LocaleContextHolder.getLocale().getLanguage().startsWith("en");
	}

	@ModelAttribute("navLocaleEn")
	public boolean navLocaleEn() {
		return LocaleContextHolder.getLocale().getLanguage().startsWith("en");
	}

	private String localeUrl(HttpServletRequest request, String locale) {
		return ServletUriComponentsBuilder.fromRequest(request)
				.replaceQueryParam(LocaleChangeInterceptor.DEFAULT_PARAM_NAME, locale)
				.build()
				.toUriString();
	}

}
