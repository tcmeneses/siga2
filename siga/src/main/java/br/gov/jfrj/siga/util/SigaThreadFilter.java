package br.gov.jfrj.siga.util;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import br.gov.jfrj.siga.base.auditoria.filter.ThreadFilter;
import br.gov.jfrj.siga.model.ContextoPersistencia;

public class SigaThreadFilter extends ThreadFilter {

	public void doFiltro(final ServletRequest request,
			final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException {

		EntityManager em = SigaStarter.emf.createEntityManager();
		ContextoPersistencia.setEntityManager(em);

		System.out.println("SIgaThreadFilter - Iniciando Transacao");
		
		em.getTransaction().begin();

		try {
			chain.doFilter(request, response);
			em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();

			throw new ServletException(e);
		} finally {
			em.close();
			ContextoPersistencia.setEntityManager(null);
		}
		
		System.out.println("SIgaThreadFilter - Finalizando Transacao");
	}

	@Override
	protected String getLoggerName() {
		return "br.gov.jfrj.siga";
	}
}