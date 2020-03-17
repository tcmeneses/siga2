package br.com.caelum.vraptor.jpa;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.Specializes;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.http.HttpServletRequest;

import br.com.caelum.vraptor.Accepts;
import br.com.caelum.vraptor.AroundCall;
import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.http.MutableResponse;
import br.com.caelum.vraptor.interceptor.SimpleInterceptorStack;
import br.com.caelum.vraptor.jpa.event.AfterCommit;
import br.com.caelum.vraptor.jpa.event.AfterRollback;
import br.com.caelum.vraptor.jpa.event.BeforeCommit;
import br.com.caelum.vraptor.validator.Validator;
import br.gov.jfrj.siga.model.ContextoPersistencia;
import br.gov.jfrj.siga.vraptor.Transacional;

/**
 * An interceptor that manages Entity Manager Transaction. All requests are
 * intercepted and a transaction is created before execution. If the request has
 * no erros, the transaction will commited, or a rollback occurs otherwise.
 * 
 * @author Lucas Cavalcanti
 */
@Specializes
@Intercepts
public class SigaTransacionalnterceptor extends br.com.caelum.vraptor.jpa.JPATransactionInterceptor {

	private final BeanManager beanManager;
	private final EntityManager manager;
	private final Validator validator;
	private final MutableResponse response;
	private HttpServletRequest request;
	private ControllerMethod method;

	/**
	 * @deprecated CDI eyes only.
	 */
	protected SigaTransacionalnterceptor() {
		this(null, null,  null, null);
	}

	@SuppressWarnings("deprecation")
	@Inject
	public SigaTransacionalnterceptor(BeanManager beanManager,   @Any Validator validator,
			MutableResponse response, HttpServletRequest request) {
		this.beanManager = beanManager;
		this.manager = ContextoPersistencia.em();
		this.validator = validator;
		this.response = response;
		this.request = request;
	}

	@Accepts
	public boolean accepts(ControllerMethod method) {
		this.method = method;
		return true;
	}

	@AroundCall
	public void intercept(SimpleInterceptorStack stack) {
		addRedirectListener();
		
		EntityTransaction transaction = null;
		
		try {
	 		if ((this.method.containsAnnotation(Transacional.class) || 
	 				this.method.containsAnnotation(Post.class) ||
	 				this.method.containsAnnotation(Put.class)  ||
	 				this.method.containsAnnotation(Delete.class))
		 			&& !(this.request.getRequestURI().startsWith("app/sigawf"))) {
				transaction = manager.getTransaction();
				transaction.begin();
		 	} 
			
			System.out.println("*** " + this.method.toString() + " - " + (transaction == null ? "NÃO" : "") + " Transacional");

			stack.next();
			
			commit(transaction);


		} 		
		
		finally {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
				beanManager.fireEvent(new AfterRollback());
			}
		}
	}

	private void commit(EntityTransaction trn) {
		if (trn == null)
			return;

		if (trn.isActive())
			beanManager.fireEvent(new BeforeCommit());

		if (!validator.hasErrors() && trn.isActive()) {
			trn.commit();
			beanManager.fireEvent(new AfterCommit());
		}
	}

	/**
	 * We force the commit before the redirect, this way we can abort the redirect
	 * if a database error occurs.
	 */
	private void addRedirectListener() {
		response.addRedirectListener(new MutableResponse.RedirectListener() {
			@Override
			public void beforeRedirect() {
				commit(manager.getTransaction());
			}
		});
	}


}