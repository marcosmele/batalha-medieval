package br.com.marcosmele.batalha_medieval.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import br.com.marcosmele.batalha_medieval.excecao.BatalhaInvalidaException;
import br.com.marcosmele.batalha_medieval.servico.ServicoBatalha;

public class BatalhaApiInterceptor extends HandlerInterceptorAdapter {

	private ServicoBatalha servico;

	public BatalhaApiInterceptor(ServicoBatalha servico) {
		this.servico = servico;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String idBatalha = request.getHeader("ID_BATALHA");

		if (StringUtils.isEmpty(idBatalha) || !servico.existe(idBatalha)) {
			throw new BatalhaInvalidaException("Favor informar uma batalha em andamento válida.");
		}

		return true;
	}
}