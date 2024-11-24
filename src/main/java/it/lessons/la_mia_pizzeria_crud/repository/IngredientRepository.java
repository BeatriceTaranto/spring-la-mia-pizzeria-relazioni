package it.lessons.la_mia_pizzeria_crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.lessons.la_mia_pizzeria_crud.model.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

}
