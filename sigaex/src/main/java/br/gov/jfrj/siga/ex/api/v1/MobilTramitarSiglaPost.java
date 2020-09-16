package br.gov.jfrj.siga.ex.api.v1;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import br.gov.jfrj.siga.ex.ExMobil;
import br.gov.jfrj.siga.ex.api.v1.IExApiV1.IMobilTramitarSiglaPost;
import br.gov.jfrj.siga.ex.api.v1.IExApiV1.MobilTramitarSiglaPostRequest;
import br.gov.jfrj.siga.ex.api.v1.IExApiV1.MobilTramitarSiglaPostResponse;
import br.gov.jfrj.siga.hibernate.ExDao;
import br.gov.jfrj.siga.vraptor.SigaObjects;

public class MobilTramitarSiglaPost implements IMobilTramitarSiglaPost {

	@Override
	public String getContext() {
		return "Tramitar Documento";
	}

	@Override
	public void run(MobilTramitarSiglaPostRequest req, MobilTramitarSiglaPostResponse resp) throws Exception {
		req.sigla = SwaggerHelper.decodePathParam(req.sigla);
		System.out.println("MobilTramitarSiglaPost.run(): " + ToStringBuilder.reflectionToString(req, ToStringStyle.SHORT_PREFIX_STYLE));

		ApiContext apiContext = new ApiContext(true);
		try {
			SwaggerHelper.buscarEValidarUsuarioLogado();

			SigaObjects so = SwaggerHelper.getSigaObjects();

			ExMobil mob = SwaggerHelper.buscarEValidarMobil(req.sigla, req, resp);
			System.out.println("MobilTramitarSiglaPost.run(): " + mob);
			
			Date dt = ExDao.getInstance().consultarDataEHoraDoServidor();

			apiContext.close();
			return;
		} catch (Exception e) {
			e.printStackTrace(System.out);
			apiContext.rollback(e);
			throw e;
		}
	}

}
