package br.gov.jfrj.siga.pp.interceptor;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.caelum.vraptor.AroundCall;
import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.interceptor.SimpleInterceptorStack;
import br.com.caelum.vraptor.jpa.JPATransactionInterceptor;
import br.com.caelum.vraptor.validator.Validator;
import br.gov.jfrj.siga.model.ActiveRecord;
import br.gov.jfrj.siga.model.ContextoPersistencia;
import br.gov.jfrj.siga.pp.dao.PpDao;


/**
 * Interceptor que inicia a instancia do DAO a ser utilizado pelo sistema. O DAO deve ser utilizado quando se deseja realizar operacoes quando nao se pode utilizar o {@link ActiveRecord}.
 * 
 * @author db1.
 *
 */
@RequestScoped
@Intercepts(before = JPATransactionInterceptor.class)
public class ContextInterceptor  {

	private final EntityManager em;

   
	/**
	 * @deprecated CDI eyes only
	 */
	public ContextInterceptor() {
		super();
		em = null;
	}

	@Inject
	public ContextInterceptor(EntityManager em, Validator validator, ServletContext context, HttpServletRequest request,
			HttpServletResponse response) {
		this.em = em;
    }

	public boolean accepts(ControllerMethod arg0) {
		// TODO Auto-generated method stub
		return Boolean.TRUE;
	}

	@AroundCall
	public void intercept(SimpleInterceptorStack stack)  {
		// TODO Auto-generated method stub
		
		try {
            ContextoPersistencia.setEntityManager(em);
            
          
            PpDao.freeInstance();
            PpDao.getInstance();
            stack.next();
        } catch (Exception e) {
            throw new InterceptionException(e);
        }
		
	}

}