package it.lessons.la_mia_pizzeria_crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.lessons.la_mia_pizzeria_crud.model.Offer;

public interface OfferRepository extends JpaRepository<Offer, Long> {

}
