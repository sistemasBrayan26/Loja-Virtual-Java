package jdev.mentoria.lojavirtual.service;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.mail.MessagingException;
import jdev.mentoria.lojavirtual.ExceptionMentoria;
import jdev.mentoria.lojavirtual.model.ImagemProduto;
import jdev.mentoria.lojavirtual.model.Produto;
import jdev.mentoria.lojavirtual.repository.ProdutoRepository;

@Service
public class CadastroProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ServiceSendEmail servicoEmail;
	
	@Transactional
	public Produto registrar(Produto produto) throws MessagingException, ExceptionMentoria, IOException {
		
		if (!produto.isNovo() && produto.getAlertaQtdeEstoque()) {
			if (produto.getQtdEstoque().compareTo(produto.getQtdAlertaEstoque()) < 0) {
				StringBuilder html = new StringBuilder();
				html.append("<h2>").append("Produto: ").append(produto.getNome())
				.append(" com estoque baixo: ").append(produto.getQtdEstoque()).append("</h2>");
				
				html.append("<p> Código Prod: ").append(produto.getId()).append("</p>");
				
				if (produto.getEmpresa().getEmail() != null) {
					servicoEmail.enviarEmailHtml("Produto sem estoque", html.toString(), 
							produto.getEmpresa().getEmail());
				}
				
			}
		}
		
		if (produto.getImagens().isEmpty()) {
			throw new ExceptionMentoria("Deve ser informado imagens para o produto");
		}
		
		if (produto.getImagens().size() < 3) {
			throw new ExceptionMentoria("Deve ser informado ao menos 3 imagens");
		}
		
		if (produto.getImagens().size() > 6) {
			throw new ExceptionMentoria("Deve ser informado no maximo 6 imagens");
		}
		
		if (produto.isNovo()) {
			for (ImagemProduto imagem : produto.getImagens()) {
				imagem.setProduto(produto);
				imagem.setEmpresa(produto.getEmpresa());
				
				String base64image = "";
				
				if (imagem.getImagemOriginal().contains("data:image")) {
					base64image = imagem.getImagemOriginal().split(",")[1];
				} else {
					base64image = imagem.getImagemOriginal();
				}
				
				byte[] imagesBytes = Base64.getDecoder().decode(base64image);
				BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imagesBytes));
				
				if (base64image != null) {
					int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : 
						bufferedImage.getType();
					int largura = 800;
		            int altura = 600;
		            
		            BufferedImage resizedImage = new BufferedImage(largura, altura, type);
		            Graphics2D g = resizedImage.createGraphics();
		            g.drawImage(bufferedImage, 0, 0, largura, altura, null);
		            g.dispose();
		            
		            ByteArrayOutputStream baos = new ByteArrayOutputStream();
		            ImageIO.write(resizedImage, "png", baos);
		            
		            String miniImgBase64 = "data:image/png;base64," + Base64.getEncoder()
		            .encodeToString(baos.toByteArray());
		            
		            imagem.setImagemMiniatura(miniImgBase64);
		            bufferedImage.flush();
		            resizedImage.flush();
		            baos.flush();
		            baos.close();
		            
				}
				
			}
		}
		
		return produtoRepository.save(produto);
	}
	
	@Transactional
	public void ativarProduto(Long produtoId) throws ExceptionMentoria {
		Optional<Produto> produto = produtoRepository.findById(produtoId);
		
		if (produto.isEmpty()) {
			throw new ExceptionMentoria("Produto não localizado");
		}
		
		produto.get().setAtivo(true);
		
		produtoRepository.save(produto.get());
	}
	
	@Transactional
	public void inativarProduto(Long produtoId) throws ExceptionMentoria {
		Optional<Produto> produto = produtoRepository.findById(produtoId);
		
		if (produto.isEmpty()) {
			throw new ExceptionMentoria("Produto não localizado");
		}
		
		produto.get().setAtivo(false);
		
		produtoRepository.save(produto.get());
	}

}
