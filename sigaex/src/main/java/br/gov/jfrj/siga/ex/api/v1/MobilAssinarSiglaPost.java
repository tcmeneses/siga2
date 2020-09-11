package br.gov.jfrj.siga.ex.api.v1;

import java.util.Date;

import br.gov.jfrj.siga.ex.ExMobil;
import br.gov.jfrj.siga.ex.ExTipoMovimentacao;
import br.gov.jfrj.siga.ex.api.v1.IExApiV1.IMobilAssinarSiglaPost;
import br.gov.jfrj.siga.ex.api.v1.IExApiV1.MobilAssinarSiglaPostRequest;
import br.gov.jfrj.siga.ex.api.v1.IExApiV1.MobilAssinarSiglaPostResponse;
import br.gov.jfrj.siga.ex.bl.Ex;
import br.gov.jfrj.siga.hibernate.ExDao;
import br.gov.jfrj.siga.vraptor.SigaObjects;

/**
 * @author 811197
 *
 */
@AcessoPublicoEPrivado
public class MobilAssinarSiglaPost implements IMobilAssinarSiglaPost {

	@Override
	public String getContext() {
		return "Assinar Documento";
	}

	@Override
	public void run(MobilAssinarSiglaPostRequest req, MobilAssinarSiglaPostResponse resp) throws Exception {
		req.sigla = SwaggerHelper.decodePathParam(req.sigla);
		ApiContext ctx = new ApiContext(true);
		try {
			SwaggerHelper.buscarEValidarUsuarioLogado();

			ExMobil mob = SwaggerHelper.buscarEValidarMobil(req.sigla, req, resp);
			Date dt = ExDao.getInstance().consultarDataEHoraDoServidor(); // Essa data?

			SigaObjects so = SwaggerHelper.getSigaObjects();
			so.assertAcesso("DOC:Módulo de Documentos;" + "");

			Ex.getInstance().getBL().assinarDocumento(so.getCadastrante(), so.getLotaTitular(), mob.getDoc(), dt, 
					so.getIdentidadeCadastrante().getDscSenhaIdentidadeCripto().getBytes(), // pkcs7
					null, // certificado
					ExTipoMovimentacao.TIPO_MOVIMENTACAO_ASSINATURA_DIGITAL_DOCUMENTO, // tpMovAssinatura
					false, false);
			ctx.close();
			return;
		} catch (Exception e) {
			e.printStackTrace(System.out);
			ctx.rollback(e);
			throw e;
		}
	}

}
