package br.gov.jfrj.siga.ex.api.v1;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;

public @interface CheckSecurity {

	public @interface Teste {
		

		@PreAuthorize(securityClass="br.gov.jfrj.siga.ex.api.v1.SigaSecurity",method="podeConsultarTeste")
		@Retention(RUNTIME)
		public @interface PodeConsultar { }
		
	}

}
