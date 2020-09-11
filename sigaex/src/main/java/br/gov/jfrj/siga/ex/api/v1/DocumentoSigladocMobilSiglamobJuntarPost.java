package br.gov.jfrj.siga.ex.api.v1;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import com.crivano.swaggerservlet.SwaggerException;
import br.gov.jfrj.siga.base.AplicacaoException;
import br.gov.jfrj.siga.ex.ExMobil;
import br.gov.jfrj.siga.ex.api.v1.IExApiV1.DocumentoSigladocMobilSiglamobJuntarPostResponse;
import br.gov.jfrj.siga.ex.api.v1.IExApiV1.DocumentoSigladocMobilSiglamobJuntarPostRequest;
import br.gov.jfrj.siga.ex.api.v1.IExApiV1.IDocumentoSigladocMobilSiglamobJuntarPost;
import br.gov.jfrj.siga.ex.bl.Ex;

@AcessoPublicoEPrivado
public class DocumentoSigladocMobilSiglamobJuntarPost extends ExWebservice implements IDocumentoSigladocMobilSiglamobJuntarPost {
	@Override
	public void run(DocumentoSigladocMobilSiglamobJuntarPostRequest req, 
			DocumentoSigladocMobilSiglamobJuntarPostResponse resp) throws Exception {
		try {
			inicializar(true);
			String siglaFilho = URLDecoder.decode(req.sigladoc + req.siglamob, StandardCharsets.UTF_8.toString());
			String siglaPai = req.siglapai;
			ExMobil mobFilho = 	Ex.getInstance()
									.getBL()
									.buscarMobil(siglaFilho);
			if (mobFilho == null)
				throw new SwaggerException(
						"Via ou volume não encontrado: " + req.sigladoc + req.siglamob, 
						404, new AplicacaoException(), req, resp, null);
			
			if (!podeAcessar(mobFilho, getTitular(), getLotaTitular()))
				throw new SwaggerException(
						"Acesso ao documento " + siglaFilho + " permitido somente a usuários autorizados. ("
								+ getTitular().getSigla() + "/" + getLotaTitular().getSiglaCompleta() + ")",
						403, null, req, resp, null);
			
			ExMobil mobPai = Ex.getInstance()
					.getBL()
					.buscarMobil(siglaPai);
			if (mobPai == null)
				throw new SwaggerException(
						"Via ou volume do documento pai não encontrado: " + siglaPai, 
						404, new AplicacaoException(), req, resp, null);

			if (!podeAcessar(mobPai, getTitular(), getLotaTitular()))
				throw new SwaggerException(
						"Acesso ao documento pai " + siglaPai + " permitido somente a usuários autorizados. ("
								+ getTitular().getSigla() + "/" + getLotaTitular().getSiglaCompleta() + ")",
						403, null, req, resp, null);

			Ex.getInstance()
					.getBL()
					.juntarDocumento(getCadastrante(),
							getCadastrante(),
							getLotaTitular(), null, mobFilho,
							mobPai, null, getCadastrante(),
							getCadastrante(), "1");
			getApiContext().close();
			return;
		} catch (Exception e) {
			e.printStackTrace(System.out);
			getApiContext().rollback(e);
			throw e;
		}
	}

	@Override
	public String getContext() {
		return "Junta vias";
	}
}