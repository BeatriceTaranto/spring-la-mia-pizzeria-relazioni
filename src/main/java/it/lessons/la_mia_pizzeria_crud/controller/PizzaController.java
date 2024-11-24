package it.lessons.la_mia_pizzeria_crud.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.lessons.la_mia_pizzeria_crud.model.Offer;
import it.lessons.la_mia_pizzeria_crud.model.Pizza;
import it.lessons.la_mia_pizzeria_crud.repository.IngredientRepository;
import it.lessons.la_mia_pizzeria_crud.repository.PizzaRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/pizza")
public class PizzaController {

	@Autowired
	private PizzaRepository pizzaRepository;

	@Autowired
	private IngredientRepository ingredientRepository;

	@GetMapping
	public String index(Model model, @RequestParam(name = "keyword", required = false) String keyword) {
		List<Pizza> allPizzas;
		if (keyword != null && !keyword.isBlank()) {
			model.addAttribute("keyword", keyword);
			allPizzas = pizzaRepository.findByNameContaining(keyword);
		} else {
			allPizzas = pizzaRepository.findAll();
		}

		model.addAttribute("pizzas", allPizzas);

		return "pizza/index";
	}

	@GetMapping("/show/{id}")
	public String show(@PathVariable(name = "id") Long id, Model model) {

		Optional<Pizza> pizzaById = pizzaRepository.findById(id);

		if (pizzaById.isPresent()) {
			model.addAttribute("pizza", pizzaById.get());
		}
		return "pizza/show";
	}

	@GetMapping("/create")
	public String create(Model model) {
		model.addAttribute("pizza", new Pizza());
		model.addAttribute("allIngredients", ingredientRepository.findAll());
		return "pizza/create";
	}

	@PostMapping("/store")
	public String store(@Valid @ModelAttribute(name = "pizza") Pizza pizza, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return ("pizza/create");
		}
		pizzaRepository.save(pizza);
		return "redirect:/pizza";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable Long id, Model model) {
		model.addAttribute("pizza", pizzaRepository.findById(id).get());
		model.addAttribute("allIngredients", ingredientRepository.findAll());
		return "pizza/edit";
	}

	@PostMapping("/edit/{id}")
	public String update(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "/pizza/edit";
		}
		pizzaRepository.save(formPizza);
		return "redirect:/pizza";
	}

	@PostMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id) {
		pizzaRepository.deleteById(id);
		return "redirect:/pizza";
	}

	@GetMapping("/{id}/offer")
	public String offer(@PathVariable Long id, Model model) {

		Pizza pizza = pizzaRepository.findById(id).get();

		Offer special_offer = new Offer();
		special_offer.setPizza(pizza);
		model.addAttribute("offer", special_offer);
		return "offers/edit";
	}
}