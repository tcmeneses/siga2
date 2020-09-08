package br.gov.jfrj.siga.ex.api.v1;

import javax.servlet.http.HttpServletRequest;

import com.crivano.swaggerservlet.PresentableUnloggedException;
import com.crivano.swaggerservlet.SwaggerAuthorizationException;
import com.crivano.swaggerservlet.SwaggerServlet;

import br.gov.jfrj.siga.base.AplicacaoException;
import br.gov.jfrj.siga.ex.ExMobil;
import br.gov.jfrj.siga.ex.api.v1.IExApiV1.MobilJuntarSiglaPostRequest;
import br.gov.jfrj.siga.ex.api.v1.IExApiV1.MobilJuntarSiglaPostResponse;
import br.gov.jfrj.siga.ex.api.v1.IExApiV1.IMobilJuntarSiglaPost;
import br.gov.jfrj.siga.ex.bl.Ex;
import br.gov.jfrj.siga.hibernate.ExDao;
import br.gov.jfrj.siga.model.ContextoPersistencia;
import br.gov.jfrj.siga.persistencia.ExMobilDaoFiltro;
import br.gov.jfrj.siga.vraptor.SigaObjects;

@AcessoPublicoEPrivado
public class MobilJuntarSiglaPost implements IMobilJuntarSiglaPost {
	private SigaObjects so;
	@Override
	public void run(MobilJuntarSiglaPostRequest req, MobilJuntarSiglaPostResponse resp) throws Exception {
		ApiContext ctx = new ApiContext(true);
		try {
			inicializar();

			ExMobil mobFilho = buscarMobil(req.sigla);
			ExMobil mobPai = buscarMobil(req.siglaPai);

			Ex.getInstance()
					.getBL()
					.juntarDocumento(so.getCadastrante(),
							so.getCadastrante(),
							so.getLotaTitular(), null, mobFilho,
							mobPai, null, so.getCadastrante(),
							so.getCadastrante(), "1");
			resp.siglaPai = req.siglaPai;
			ctx.close();
			return;
		} catch (Exception e) {
			e.printStackTrace(System.out);
			ctx.rollback(e);
			throw e;
		}
	}

	private void inicializar() throws SwaggerAuthorizationException, Exception {
		String usuario = ContextoPersistencia.getUserPrincipal();
		if (usuario == null)
			throw new SwaggerAuthorizationException("Usuário não está logado");
		HttpServletRequest request = SwaggerServlet.getHttpServletRequest();
		so = new SigaObjects(request);
		assertAcesso("");
	}

	@Override
	public String getContext() {
		return "Junta vias";
	}

	private ExMobil buscarMobil (final String sigla) throws Exception {
		final ExMobilDaoFiltro filter = new ExMobilDaoFiltro();
		filter.setSigla(sigla);
		ExMobil mob = (ExMobil) ExDao.getInstance().consultarPorSigla(filter);
		if (mob == null)
			throw new PresentableUnloggedException(
					"Não foi possível encontrar um documento a partir da sigla fornecida");

		verificarNivelAcesso(mob);
		return mob;
	}

	private void verificarNivelAcesso(ExMobil mob) {
		if (!Ex.getInstance().getComp().podeAcessarDocumento(so.getTitular(), so.getLotaTitular(), mob))
			throw new AplicacaoException(
					"Acesso ao documento " + mob.getSigla() + " permitido somente a usuários autorizados. ("
							+ so.getTitular().getSigla() + "/" + so.getLotaTitular().getSiglaCompleta() + ")");
	}

	protected void assertAcesso(String pathServico) throws AplicacaoException {
		so.assertAcesso("DOC:Módulo de Documentos;" + pathServico);
	}
}