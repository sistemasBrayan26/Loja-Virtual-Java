package jdev.mentoria.lojavirtual.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jdev.mentoria.lojavirtual.model.Endereco;
import jdev.mentoria.lojavirtual.model.ItemVendaLoja;
import jdev.mentoria.lojavirtual.model.VendaCompraLojaVirtual;
import jdev.mentoria.lojavirtual.repository.EnderecoRepository;
import jdev.mentoria.lojavirtual.repository.VendaCompraLojaVirtualRepository;

@Service
public class VendaService {
	
	@Autowired
	private VendaCompraLojaVirtualRepository vendaCompraLojaVirtualRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Transactional
	public VendaCompraLojaVirtual salvar(VendaCompraLojaVirtual venda) {
		
		for (ItemVendaLoja item : venda.getItemVendaLojas()) {
			item.setEmpresa(venda.getEmpresa());
			item.setVendaCompraLojaVirtual(venda);
		}
		
		
		return vendaCompraLojaVirtualRepository.saveAndFlush(venda);
		
	}

}
