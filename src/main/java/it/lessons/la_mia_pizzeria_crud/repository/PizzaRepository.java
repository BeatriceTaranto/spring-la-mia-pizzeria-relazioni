package it.lessons.la_mia_pizzeria_crud.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import it.lessons.la_mia_pizzeria_crud.model.Pizza;

public interface PizzaRepository extends JpaRepository<Pizza, Long> {
	
	List<Pizza> findByNameContaining(String name);
}
