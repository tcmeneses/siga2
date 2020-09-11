package br.gov.jfrj.siga.ex.api.v1;

import java.util.Date;

import br.gov.jfrj.siga.ex.ExMobil;
import br.gov.jfrj.siga.ex.api.v1.IExApiV1.DocumentoSigladocMobilSiglamobJuntarPostRequest;
import br.gov.jfrj.siga.ex.api.v1.IExApiV1.DocumentoSigladocMobilSiglamobJuntarPostResponse;
import br.gov.jfrj.siga.ex.api.v1.IExApiV1.IDocumentoSigladocMobilSiglamobJuntarPost;
import br.gov.jfrj.siga.ex.bl.Ex;
import br.gov.jfrj.siga.hibernate.ExDao;
import br.gov.jfrj.siga.vraptor.SigaObjects;

@AcessoPublicoEPrivado
public class DocumentoSigladocMobilSiglamobJuntarPost implements IDocumentoSigladocMobilSiglamobJuntarPost {

	@Override
	public void run(DocumentoSigladocMobilSiglamobJuntarPostRequest req,
			DocumentoSigladocMobilSiglamobJuntarPostResponse resp) throws Exception {
		req.sigladoc = SwaggerHelper.decodePathParam(req.sigladoc);
		req.siglamob = SwaggerHelper.decodePathParam(req.siglamob);
		ApiContext apiContext = new ApiContext(true);
		try {
			SwaggerHelper.buscarEValidarUsuarioLogado();

			String siglaFilho = req.sigladoc + req.siglamob;
			SigaObjects so = SwaggerHelper.getSigaObjects();
			so.assertAcesso("DOC:Módulo de Documentos;" + "");

			ExMobil mobFilho = SwaggerHelper.buscarEValidarMobil(siglaFilho, so, req, resp, "Documento Secundário");
			ExMobil mobPai = SwaggerHelper.buscarEValidarMobil(req.siglapai, so, req, resp, "Documento Principal");
			Date dt = ExDao.getInstance().consultarDataEHoraDoServidor(); 

			Ex.getInstance().getBL().juntarDocumento(so.getCadastrante(), so.getCadastrante(), so.getLotaTitular(),
					null, mobFilho, mobPai, dt, so.getCadastrante(), so.getCadastrante(), "1");
			apiContext.close();
			return;
		} catch (Exception e) {
			e.printStackTrace(System.out);
			apiContext.rollback(e);
			throw e;
		}
	}

	@Override
	public String getContext() {
		return "Junta vias";
	}
}