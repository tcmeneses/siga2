package br.gov.jfrj.siga.tp.interceptor;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import br.com.caelum.vraptor.Accepts;
import br.com.caelum.vraptor.AroundCall;
import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.core.MethodInfo;
import br.com.caelum.vraptor.interceptor.SimpleInterceptorStack;
import br.com.caelum.vraptor.validator.Validator;
import br.gov.jfrj.siga.tp.validation.annotation.UpperCase;
import br.gov.jfrj.siga.vraptor.JPATransactionCustomInterceptor;

/**
 * Interceptor utilizado para transformar os campos String anotados com {@link UpperCase} 
 * 
 * @author DB1
 *
 */

@RequestScoped
@Intercepts(after=MotivoLogInterceptor.class, before=JPATransactionCustomInterceptor.class)
public class UpperCaseInterceptor {
	
	@Inject
	private MethodInfo methodInfo;
	
	@Inject
	private Validator validator;


	@AroundCall
	public void intercept(SimpleInterceptorStack stack) {
	try {
		Object[] parametros = methodInfo.getParametersValues();
		if (parametros != null) {
			for (int indiceDoParametro = 0; indiceDoParametro < parametros.length; indiceDoParametro++) {
				if (parametros[indiceDoParametro] != null) {
					validator.validate(parametros[indiceDoParametro]);
				}
			}
		}
		stack.next(); 
	}
	catch (Exception e) {
		throw new InterceptionException(e);
} finally {
	
}
	}


	@Accepts
	public boolean accepts(ControllerMethod method) {
		 return true;
	}

}
