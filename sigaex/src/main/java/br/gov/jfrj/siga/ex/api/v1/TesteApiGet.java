package br.gov.jfrj.siga.ex.api.v1;

import com.crivano.swaggerservlet.SwaggerServlet;

import br.gov.jfrj.siga.ex.api.v1.IExApiV1.ITesteApiGet;
import br.gov.jfrj.siga.ex.api.v1.IExApiV1.TesteApiGetRequest;
import br.gov.jfrj.siga.ex.api.v1.IExApiV1.TesteApiGetResponse;
import br.gov.jfrj.siga.ex.bl.CurrentRequest;
import br.gov.jfrj.siga.ex.bl.RequestInfo;

@AcessoPrivadoOAuth
@CheckSecurity.Teste.PodeConsultar
public class TesteApiGet implements ITesteApiGet {

	@Override
	public void run(TesteApiGetRequest req, TesteApiGetResponse resp) throws Exception {
		
		CurrentRequest.set(new RequestInfo(null, SwaggerServlet.getHttpServletRequest(), SwaggerServlet.getHttpServletResponse()));
		try (ApiContext ctx = new ApiContext(false, false,this.getClass().getAnnotations())) {
			resp.mensagem = "Ok";
		}
	}

	@Override
	public String getContext() {
		// TODO Auto-generated method stub
		return null;
	}




}