package br.gov.jfrj.siga.gc.vraptor;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.caelum.vraptor.Accepts;
import br.com.caelum.vraptor.AroundCall;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.interceptor.SimpleInterceptorStack;
import br.com.caelum.vraptor.jpa.JPATransactionCustomInterceptor;
import br.com.caelum.vraptor.validator.Validator;
import br.gov.jfrj.siga.model.ContextoPersistencia;

@Intercepts(before = JPATransactionCustomInterceptor.class)
public class GcInterceptor  {

	private final EntityManager manager;
	
	/**
	 * @deprecated CDI eyes only
	 */
	public GcInterceptor() {
		super();
		this.manager = null;
	}
	
	@Inject
	public GcInterceptor(EntityManager manager, Validator validator,
			ServletContext context, HttpServletRequest request,
			HttpServletResponse response) {
		this.manager = manager;
	}

	@AroundCall
	public void intercept(SimpleInterceptorStack stack)  {

		ContextoPersistencia.setEntityManager(this.manager);
		stack.next();

	}

	@Accepts
	public boolean accepts(ControllerMethod method) {
		return true; // Will intercept all requests
	}
}