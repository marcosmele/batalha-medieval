package br.com.marcosmele.batalha_medieval.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.marcosmele.batalha_medieval.excecao.BatalhaExistenteException;
import br.com.marcosmele.batalha_medieval.excecao.BatalhaInvalidaException;
import br.com.marcosmele.batalha_medieval.excecao.MovimentoIncorretoException;

/**
 * Handler de erros na utilização da API.
 * @author marcos
 *
 */
@ControllerAdvice
class BatalhaApiErrorHandler {

	@ResponseBody
	@ExceptionHandler(BatalhaExistenteException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	String battleExistentHandler(BatalhaExistenteException ex) {
		//TODO Melhorar para tratar o id como dado em separado para o cliente.
		return ex.getMessage();
	}
	
	@ResponseBody
	@ExceptionHandler(BatalhaInvalidaException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	String battleNotFoundHandler(BatalhaInvalidaException ex) {
		return ex.getMessage();
	}
	
	@ResponseBody
	@ExceptionHandler(MovimentoIncorretoException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	String MovimentoIncorretoHandler(MovimentoIncorretoException ex) {
		return ex.getMessage();
	}

}
