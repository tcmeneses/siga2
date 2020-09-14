package br.gov.jfrj.siga.ex.api.v1;

import java.util.Date;

import br.gov.jfrj.siga.ex.ExMobil;
import br.gov.jfrj.siga.ex.ExTipoMovimentacao;
import br.gov.jfrj.siga.ex.api.v1.IExApiV1.IMobilAutenticarSiglaPost;
import br.gov.jfrj.siga.ex.api.v1.IExApiV1.MobilAutenticarSiglaPostRequest;
import br.gov.jfrj.siga.ex.api.v1.IExApiV1.MobilAutenticarSiglaPostResponse;
import br.gov.jfrj.siga.ex.bl.Ex;
import br.gov.jfrj.siga.hibernate.ExDao;
import br.gov.jfrj.siga.vraptor.SigaObjects;

/**
 * @author 811197
 *
 */
@AcessoPublicoEPrivado
public class MobilAutenticarSiglaPost implements IMobilAutenticarSiglaPost {

	@Override
	public String getContext() {
		return "Autenticar Documento";
	}

	@Override
	public void run(MobilAutenticarSiglaPostRequest req, MobilAutenticarSiglaPostResponse resp) throws Exception {
		req.sigla = SwaggerHelper.decodePathParam(req.sigla);
		ApiContext ctx = new ApiContext(true);
		try {
			SwaggerHelper.buscarEValidarUsuarioLogado();

			ExMobil mob = SwaggerHelper.buscarEValidarMobil(req.sigla, req, resp);
			Date dt = ExDao.getInstance().consultarDataEHoraDoServidor(); // Essa data?

			SigaObjects so = SwaggerHelper.getSigaObjects();

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
