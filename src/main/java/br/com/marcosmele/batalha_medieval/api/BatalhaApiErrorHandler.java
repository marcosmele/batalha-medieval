package br.com.marcosmele.batalha_medieval.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	ResponseEntity<Object> battleExistentHandler(BatalhaExistenteException ex) {
		StringBuilder retorno = new StringBuilder();
		retorno.append("{");
		retorno.append("\"error\":\"" + ex.getMessage() + "\",");
		retorno.append("\"idBatalha\":\"" + ex.getId() + "\",");
		retorno.append("\"proximoPasso\":\"" + ex.getProximaAcao()+ "\"");
		retorno.append("}");
		
		return new ResponseEntity<Object>(retorno.toString(), HttpStatus.BAD_REQUEST);
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
