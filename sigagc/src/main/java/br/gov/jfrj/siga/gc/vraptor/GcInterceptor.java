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
import br.com.caelum.vraptor.validator.Validator;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.caelum.vraptor.interceptor.SimpleInterceptorStack;
import br.com.caelum.vraptor.jpa.JPATransactionCustomInterceptor;
import br.gov.jfrj.siga.model.ContextoPersistencia;

@Intercepts(before = JPATransactionCustomInterceptor.class)
public class GcInterceptor  {

	private final EntityManager manager;
	private final Validator validator;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private ServletContext context;

	/**
	 * @deprecated CDI eyes only
	 */
	public GcInterceptor() {
		super();
		this.manager = null;
		this.validator = null;
		this.request = null;
		this.response = null;
		this.context = null;
	}
	
	@Inject
	public GcInterceptor(EntityManager manager, Validator validator,
			ServletContext context, HttpServletRequest request,
			HttpServletResponse response) {
		this.manager = manager;
		this.validator = validator;
		this.request = request;
		this.response = response;
		this.context = context;
	}

	@AroundCall
	public void intercept(SimpleInterceptorStack stack)  {

		ContextoPersistencia.setEntityManager(this.manager);

		try {
			stack.next();
		} finally {
			ContextoPersistencia.setEntityManager(null);
		}
	}

	@Accepts
	public boolean accepts(ControllerMethod method) {
		return true; // Will intercept all requests
	}
}