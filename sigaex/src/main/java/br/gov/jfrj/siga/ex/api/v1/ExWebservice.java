package br.gov.jfrj.siga.ex.api.v1;

import javax.servlet.http.HttpServletRequest;

import com.crivano.swaggerservlet.SwaggerAuthorizationException;
import com.crivano.swaggerservlet.SwaggerServlet;

import br.gov.jfrj.siga.base.AplicacaoException;
import br.gov.jfrj.siga.dp.DpLotacao;
import br.gov.jfrj.siga.dp.DpPessoa;
import br.gov.jfrj.siga.ex.ExDocumento;
import br.gov.jfrj.siga.ex.ExMobil;
import br.gov.jfrj.siga.ex.bl.Ex;
import br.gov.jfrj.siga.model.ContextoPersistencia;
import br.gov.jfrj.siga.vraptor.SigaObjects;

public class ExWebservice {
	private ApiContext apiContext;
	private SigaObjects so;

	public void inicializar(boolean transacional) throws Exception {
		apiContext = new ApiContext(transacional);
		String usuario = ContextoPersistencia.getUserPrincipal();
		if (usuario == null)
			throw new SwaggerAuthorizationException("Usuário não está logado");
		HttpServletRequest request = SwaggerServlet.getHttpServletRequest();
		so = new SigaObjects(request);
		assertAcesso("");		
	}

	public boolean podeAcessar(ExDocumento doc, DpPessoa titular, DpLotacao lotacao) {
		return podeAcessar(doc.getMobilGeral(), titular, lotacao);
	}
	public boolean podeAcessar(ExMobil mob, DpPessoa titular, DpLotacao lotacao) {
		if (!Ex.getInstance().getComp().podeAcessarDocumento(titular, lotacao, mob)) 
			return false;
		return true;
	}
	public void assertAcesso(String pathServico) throws AplicacaoException {
		so.assertAcesso("DOC:Módulo de Documentos;" + pathServico);
	}
	public ApiContext getApiContext() {
		return apiContext;
	}
	public SigaObjects getSigaObjects() {
		return so;
	}
	public DpPessoa getTitular() {
		return so.getTitular();
	}
	public DpPessoa getCadastrante() {
		return so.getCadastrante();
	}
	public DpLotacao getLotaTitular() {
		return so.getLotaTitular();
	}
}