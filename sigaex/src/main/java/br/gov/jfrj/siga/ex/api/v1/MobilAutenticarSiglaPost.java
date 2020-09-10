/**
 * 
 */
package br.gov.jfrj.siga.ex.api.v1;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.crivano.swaggerservlet.SwaggerAuthorizationException;
import com.crivano.swaggerservlet.SwaggerException;
import com.crivano.swaggerservlet.SwaggerServlet;

import br.gov.jfrj.siga.base.AplicacaoException;
import br.gov.jfrj.siga.ex.ExMobil;
import br.gov.jfrj.siga.ex.ExTipoMovimentacao;
import br.gov.jfrj.siga.ex.api.v1.IExApiV1.IMobilAutenticarSiglaPost;
import br.gov.jfrj.siga.ex.api.v1.IExApiV1.MobilAutenticarSiglaPostRequest;
import br.gov.jfrj.siga.ex.api.v1.IExApiV1.MobilAutenticarSiglaPostResponse;
import br.gov.jfrj.siga.ex.bl.Ex;
import br.gov.jfrj.siga.hibernate.ExDao;
import br.gov.jfrj.siga.model.ContextoPersistencia;
import br.gov.jfrj.siga.persistencia.ExMobilDaoFiltro;
import br.gov.jfrj.siga.vraptor.SigaObjects;

/**
 * @author 811197
 *
 */
@AcessoPublicoEPrivado
public class MobilAutenticarSiglaPost implements IMobilAutenticarSiglaPost {

	private SigaObjects so;

	private void inicializar() throws SwaggerAuthorizationException, Exception {
		String usuario = ContextoPersistencia.getUserPrincipal();
		if (usuario == null)
			throw new SwaggerAuthorizationException("Usuário não está logado");
		HttpServletRequest request = SwaggerServlet.getHttpServletRequest();
		so = new SigaObjects(request);
		assertAcesso("");
	}

	protected void assertAcesso(String pathServico) throws AplicacaoException {
		so.assertAcesso("DOC:Módulo de Documentos;" + pathServico);
	}

	private ExMobil buscarMobil(final String sigla, MobilAutenticarSiglaPostRequest req,
			MobilAutenticarSiglaPostResponse resp) throws Exception {
		final ExMobilDaoFiltro filter = new ExMobilDaoFiltro();
		filter.setSigla(sigla);
		ExMobil mob = (ExMobil) ExDao.getInstance().consultarPorSigla(filter);
		if (mob == null) {
			throw new SwaggerException("Número do Documento não existe no SPSP", 404, null, req, resp, null);
		}

		verificarNivelAcesso(mob, req, resp);
		return mob;
	}

	private void verificarNivelAcesso(ExMobil mob, MobilAutenticarSiglaPostRequest req, MobilAutenticarSiglaPostResponse resp)
			throws SwaggerException {
		if (!Ex.getInstance().getComp().podeAcessarDocumento(so.getTitular(), so.getLotaTitular(), mob))
			throw new SwaggerException(
					"Acesso ao documento " + mob.getSigla() + " permitido somente a usuários autorizados. ("
							+ so.getTitular().getSigla() + "/" + so.getLotaTitular().getSiglaCompleta() + ")",
					403, null, req, resp, null);
	}

	@Override
	public String getContext() {
		return "Autenticar Documento";
	}

	@Override
	public void run(MobilAutenticarSiglaPostRequest req, MobilAutenticarSiglaPostResponse resp) throws Exception {
		req.sigla = URLDecoder.decode(req.sigla, StandardCharsets.UTF_8.toString());
		ApiContext ctx = new ApiContext(true);
		try {
			inicializar();

			ExMobil mob = buscarMobil(req.sigla, req, resp);
			Date dt = ExDao.getInstance().consultarDataEHoraDoServidor(); // Essa data?

			Ex.getInstance().getBL().assinarMovimentacao(
					so.getCadastrante(), so.getLotaTitular(),
					mob.getUltimaMovimentacao(), // Será ???????
					dt, 
					null, 
					null, 
					ExTipoMovimentacao.TIPO_MOVIMENTACAO_ASSINATURA_DIGITAL_MOVIMENTACAO);

			ctx.close();
			return;
		} catch (Exception e) {
			e.printStackTrace(System.out);
			ctx.rollback(e);
			throw e;
		}
	}

}
