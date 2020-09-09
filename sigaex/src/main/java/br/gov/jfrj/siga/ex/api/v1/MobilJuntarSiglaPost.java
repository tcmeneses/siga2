package br.gov.jfrj.siga.ex.api.v1;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;

import com.crivano.swaggerservlet.SwaggerAuthorizationException;
import com.crivano.swaggerservlet.SwaggerException;
import com.crivano.swaggerservlet.SwaggerServlet;

import br.gov.jfrj.siga.base.AplicacaoException;
import br.gov.jfrj.siga.ex.ExMobil;
import br.gov.jfrj.siga.ex.api.v1.IExApiV1.IMobilJuntarSiglaPost;
import br.gov.jfrj.siga.ex.api.v1.IExApiV1.MobilJuntarSiglaPostRequest;
import br.gov.jfrj.siga.ex.api.v1.IExApiV1.MobilJuntarSiglaPostResponse;
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
			
			ExMobil mobFilho = buscarMobil(URLDecoder.decode(req.sigla, StandardCharsets.UTF_8.toString()), "Secundário", req, resp);
			ExMobil mobPai = buscarMobil(req.siglaPai, "Principal", req, resp);

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

	private ExMobil buscarMobil (final String sigla, String tipo, MobilJuntarSiglaPostRequest req, MobilJuntarSiglaPostResponse resp) throws Exception {
		final ExMobilDaoFiltro filter = new ExMobilDaoFiltro();
		filter.setSigla(sigla);
		ExMobil mob = (ExMobil) ExDao.getInstance().consultarPorSigla(filter);
		if (mob == null) {
			throw new SwaggerException("Número do Documento " + tipo
					+ " não existe no SPSP", 404, null, req, resp, null);
		}

		verificarNivelAcesso(mob, req, resp);
		return mob;
	}

	private void verificarNivelAcesso(ExMobil mob, MobilJuntarSiglaPostRequest req, MobilJuntarSiglaPostResponse resp)
			throws SwaggerException {
		if (!Ex.getInstance().getComp().podeAcessarDocumento(so.getTitular(), so.getLotaTitular(), mob))
			throw new SwaggerException(
					"Acesso ao documento " + mob.getSigla() + " permitido somente a usuários autorizados. ("
							+ so.getTitular().getSigla() + "/" + so.getLotaTitular().getSiglaCompleta() + ")",
					403, null, req, resp, null);
	}

	protected void assertAcesso(String pathServico) throws AplicacaoException {
		so.assertAcesso("DOC:Módulo de Documentos;" + pathServico);
	}
}