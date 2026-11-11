package jdev.mentoria.lojavirtual;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import jakarta.mail.MessagingException;
import jdev.mentoria.lojavirtual.model.dto.ObjetoErroDTO;
import jdev.mentoria.lojavirtual.service.ServiceSendEmail;

@RestControllerAdvice
@ControllerAdvice
public class ControleExcecoes {

	@Autowired
	private ServiceSendEmail serviceEmail;

	// Trata a sua exceção personalizada
	@ExceptionHandler(ExceptionMentoria.class)
	public ResponseEntity<Object> handleExceptionCustom(ExceptionMentoria ex) {
		ObjetoErroDTO objetoErroDTO = new ObjetoErroDTO();
		objetoErroDTO.setError(ex.getMessage());
		objetoErroDTO.setCode(HttpStatus.OK.toString());

		return new ResponseEntity<>(objetoErroDTO, HttpStatus.OK);
	}

	// Trata erros de validação de DTO (@Valid)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
		ObjetoErroDTO objetoErroDTO = new ObjetoErroDTO();
		String msg = "";

		List<ObjectError> list = ex.getBindingResult().getAllErrors();
		for (ObjectError objectError : list) {
			msg += objectError.getDefaultMessage() + "\n";
		}

		objetoErroDTO.setError(msg);
		objetoErroDTO.setCode(HttpStatus.BAD_REQUEST.value() + " ==> " + HttpStatus.BAD_REQUEST.getReasonPhrase());

		return new ResponseEntity<>(objetoErroDTO, HttpStatus.BAD_REQUEST);
	}

	// Trata corpo da requisição ausente ou mal formatado
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<Object> handleMessageNotReadable(HttpMessageNotReadableException ex) {
		ObjetoErroDTO objetoErroDTO = new ObjetoErroDTO();
		objetoErroDTO.setError("Não está sendo enviado dados para o BODY corpo da requisição");
		objetoErroDTO.setCode(HttpStatus.BAD_REQUEST.value() + " ==> " + HttpStatus.BAD_REQUEST.getReasonPhrase());

		return new ResponseEntity<>(objetoErroDTO, HttpStatus.BAD_REQUEST);
	}

	// Captura erros de integridade no banco de dados com segurança contra
	// NullPointerException
	@ExceptionHandler({ DataIntegrityViolationException.class, ConstraintViolationException.class, SQLException.class })
	public ResponseEntity<Object> handleExceptionDataIntegrity(Exception ex) {
		ObjetoErroDTO objetoErroDTO = new ObjetoErroDTO();
		String msg = "";

		Throwable rootCause = ex;
		while (rootCause.getCause() != null) {
			rootCause = rootCause.getCause();
		}
		msg = "Erro no banco de dados: " + rootCause.getMessage();

		objetoErroDTO.setError(msg);
		objetoErroDTO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());

		return new ResponseEntity<>(objetoErroDTO, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// Captura qualquer outra exceção genérica não mapeada acima
	@ExceptionHandler({ Exception.class, RuntimeException.class, Throwable.class })
	public ResponseEntity<Object> handleGeneralException(Exception ex) {
		ObjetoErroDTO objetoErroDTO = new ObjetoErroDTO();

		objetoErroDTO.setError(ex.getMessage());
		objetoErroDTO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value() + " ==> "
				+ HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());

		try {
			serviceEmail.enviarEmailHtml("Erro na loja Virtual", ExceptionUtils.getStackTrace(ex),
					"brayan.iub10@hotmail.com");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseEntity<>(objetoErroDTO, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
