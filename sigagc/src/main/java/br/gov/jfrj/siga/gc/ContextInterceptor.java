package br.gov.jfrj.siga.gc;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.caelum.vraptor.Accepts;
import br.com.caelum.vraptor.AroundCall;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.interceptor.SimpleInterceptorStack;
import br.gov.jfrj.siga.dp.dao.CpDao;
import br.gov.jfrj.siga.model.ContextoPersistencia;
import br.gov.jfrj.siga.vraptor.ParameterOptionalLoaderInterceptor;

@Intercepts(before = ParameterOptionalLoaderInterceptor.class)
public class ContextInterceptor  {

	private final static ThreadLocal<EntityManager> emByThread = new ThreadLocal<EntityManager>();
	
	private final static ThreadLocal<Result> resultByThread = new ThreadLocal<Result>();
	
	/**
	 * @deprecated CDI eyes only
	 */
	public ContextInterceptor() {
		super();
	}
	
	
	@Inject
	public ContextInterceptor(EntityManager manager, Result result) {
		resultByThread.set(result);
			ContextoPersistencia.setEntityManager(manager);
		CpDao.freeInstance();
		CpDao.getInstance();
	}
	
	static public EntityManager em() {
		return emByThread.get();
	}

	@AroundCall
	public void intercept(SimpleInterceptorStack stack)  {

		return;

	}

	@Accepts
	public boolean accepts(ControllerMethod method) {
		return false; // Will intercept all requests
	}

	public static Result result() {
		// TODO Auto-generated method stub
		return resultByThread.get();
	}
}
