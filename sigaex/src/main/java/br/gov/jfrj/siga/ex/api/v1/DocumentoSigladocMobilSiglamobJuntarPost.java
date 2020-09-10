package br.gov.jfrj.siga.ex.api.v1;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import javax.servlet.http.HttpServletRequest;

import com.crivano.swaggerservlet.SwaggerAuthorizationException;
import com.crivano.swaggerservlet.SwaggerException;
import com.crivano.swaggerservlet.SwaggerServlet;
import br.gov.jfrj.siga.base.AplicacaoException;
import br.gov.jfrj.siga.dp.DpLotacao;
import br.gov.jfrj.siga.dp.DpPessoa;
import br.gov.jfrj.siga.ex.ExMobil;
import br.gov.jfrj.siga.ex.api.v1.IExApiV1.DocumentoSigladocMobilSiglamobJuntarPostResponse;
import br.gov.jfrj.siga.ex.api.v1.IExApiV1.DocumentoSigladocMobilSiglamobJuntarPostRequest;
import br.gov.jfrj.siga.ex.api.v1.IExApiV1.IDocumentoSigladocMobilSiglamobJuntarPost;
import br.gov.jfrj.siga.ex.bl.Ex;
import br.gov.jfrj.siga.model.ContextoPersistencia;
import br.gov.jfrj.siga.vraptor.SigaObjects;

@AcessoPublicoEPrivado
public class DocumentoSigladocMobilSiglamobJuntarPost implements IDocumentoSigladocMobilSiglamobJuntarPost {
	private SigaObjects so;
	@Override
	public void run(DocumentoSigladocMobilSiglamobJuntarPostRequest req, 
			DocumentoSigladocMobilSiglamobJuntarPostResponse resp) throws Exception {
		ApiContext ctx = new ApiContext(true);
		try {
			inicializar();
			String siglaFilho = URLDecoder.decode(req.sigladoc + req.siglamob, StandardCharsets.UTF_8.toString());
			String siglaPai = req.siglapai;
			ExMobil mobFilho = 	Ex.getInstance()
									.getBL()
									.buscarMobil(siglaFilho);
			if (mobFilho == null)
				throw new SwaggerException(
						"Via ou volume não encontrado: " + req.sigladoc + req.siglamob, 
						404, new AplicacaoException(), req, resp, null);
			
			if (!podeAcessar(mobFilho, so.getTitular(), so.getLotaTitular()))
				throw new SwaggerException(
						"Acesso ao documento " + siglaFilho + " permitido somente a usuários autorizados. ("
								+ so.getTitular().getSigla() + "/" + so.getLotaTitular().getSiglaCompleta() + ")",
						403, null, req, resp, null);
			
			ExMobil mobPai = Ex.getInstance()
					.getBL()
					.buscarMobil(siglaPai);
			if (mobPai == null)
				throw new SwaggerException(
						"Via ou volume do documento pai não encontrado: " + siglaPai, 
						404, new AplicacaoException(), req, resp, null);

			if (!podeAcessar(mobPai, so.getTitular(), so.getLotaTitular()))
				throw new SwaggerException(
						"Acesso ao documento pai " + siglaPai + " permitido somente a usuários autorizados. ("
								+ so.getTitular().getSigla() + "/" + so.getLotaTitular().getSiglaCompleta() + ")",
						403, null, req, resp, null);

			Ex.getInstance()
					.getBL()
					.juntarDocumento(so.getCadastrante(),
							so.getCadastrante(),
							so.getLotaTitular(), null, mobFilho,
							mobPai, null, so.getCadastrante(),
							so.getCadastrante(), "1");
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

	private boolean podeAcessar(ExMobil mob, DpPessoa titular, DpLotacao lotacao) {
		if (!Ex.getInstance().getComp().podeAcessarDocumento(titular, lotacao, mob)) 
			return false;
		return true;
	}

	protected void assertAcesso(String pathServico) throws AplicacaoException {
		so.assertAcesso("DOC:Módulo de Documentos;" + pathServico);
	}
}