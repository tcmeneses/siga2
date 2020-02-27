package br.gov.jfrj.siga.gc;

import javax.enterprise.context.RequestScoped;
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

@RequestScoped
@Intercepts(before = { 
		ParameterOptionalLoaderInterceptor.class })
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
	public ContextInterceptor(EntityManager em, Result result) throws Exception {
		ContextoPersistencia.setEntityManager(em);
		resultByThread.set(result);
		CpDao.freeInstance();
		CpDao.getInstance();
	}

	static public EntityManager em() {
		return emByThread.get();
	}

	static public Result result() {
		return resultByThread.get();
	}

	@Accepts
	public boolean accepts(ControllerMethod method) {
		return false;
	}

	@AroundCall
	public void intercept(SimpleInterceptorStack stack) {
		return;
	}

}