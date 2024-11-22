package it.lessons.la_mia_pizzeria_crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.lessons.la_mia_pizzeria_crud.model.Offer;
import it.lessons.la_mia_pizzeria_crud.repository.OfferRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/offer")
public class OfferController {

	@Autowired
	private OfferRepository offerRepository;

	@PostMapping("/create")
	public String store(@Valid @ModelAttribute("offer") Offer offer, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			return "offers/edit";
		}

		offerRepository.save(offer);

		return "redirect:/pizza/show/" + offer.getPizza().getId();
	}

	@GetMapping("/edit/{id}")
	public String editForm(@PathVariable("id") Long id, Model model) {
		Offer offer = offerRepository.findById(id).get();
		model.addAttribute("offer", offer);
		model.addAttribute("edit", true);
		return "offers/edit";
	}

	@PostMapping("/edit/{id}")
	public String edit(@Valid @ModelAttribute("offer") Offer offer, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("edit", true);
			return "offers/edit";
		}
		
		offer.setValid(false);
		offerRepository.save(offer);
		
		return "redirect:/pizza/show/" + offer.getPizza().getId();
	}
}
