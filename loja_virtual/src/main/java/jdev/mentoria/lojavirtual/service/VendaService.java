package jdev.mentoria.lojavirtual.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jdev.mentoria.lojavirtual.model.VendaCompraLojaVirtual;
import jdev.mentoria.lojavirtual.repository.VendaCompraLojaVirtualRepository;

@Service
public class VendaService {
	
	@Autowired
	private VendaCompraLojaVirtualRepository vendaCompraLojaVirtualRepository;
	
	@Transactional
	public VendaCompraLojaVirtual salvar(VendaCompraLojaVirtual venda) {
		
		return vendaCompraLojaVirtualRepository.saveAndFlush(venda);
		
	}

}
