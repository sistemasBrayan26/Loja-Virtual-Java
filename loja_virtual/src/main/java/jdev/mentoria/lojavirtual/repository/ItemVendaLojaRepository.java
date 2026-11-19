package jdev.mentoria.lojavirtual.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import jdev.mentoria.lojavirtual.model.ItemVendaLoja;

@Transactional
public interface ItemVendaLojaRepository extends JpaRepository<ItemVendaLoja, Long> {

}
