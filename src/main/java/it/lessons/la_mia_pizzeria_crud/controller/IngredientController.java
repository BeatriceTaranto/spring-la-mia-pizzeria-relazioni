package it.lessons.la_mia_pizzeria_crud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.lessons.la_mia_pizzeria_crud.model.Ingredient;
import it.lessons.la_mia_pizzeria_crud.model.Pizza;
import it.lessons.la_mia_pizzeria_crud.repository.IngredientRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/ingredients")
public class IngredientController {

	@Autowired
	private IngredientRepository ingredientRepository;

	@GetMapping
	public String index(Model model) {

		List<Ingredient> all = ingredientRepository.findAll();

		model.addAttribute("ingredients", all);
		model.addAttribute("ing", new Ingredient());

		return "ingredients/index";
	}

	@PostMapping("/create")
	public String create(@Valid @ModelAttribute(name = "ing") Ingredient ingredient, BindingResult bindingResult,
			Model model) {

		if (bindingResult.hasErrors()) {
			List<Ingredient> all = ingredientRepository.findAll();

			model.addAttribute("ingredients", all);
			model.addAttribute("ing", new Ingredient());

			return "ingredients/index";
		}

		ingredientRepository.save(ingredient);

		return "redirect:/ingredients";
	}

	@PostMapping("/delete/{id}")
	public String delete(@PathVariable Long id, Model model) {

		Ingredient ingredient = ingredientRepository.findById(id).get();

		for (Pizza pizza : ingredient.getPizzas()) {

			pizza.getIngredients().remove(ingredient);
		}

		ingredientRepository.deleteById(id);

		return "redirect:/ingredients";
	}
}
