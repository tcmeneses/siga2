<%@ page language="java" contentType="text/html; charset=UTF-8"
	buffer="64kb"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://localhost/jeetags" prefix="siga"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<script type="text/javascript">
	function validar() {
		sigaSpinner.mostrar();
		document.getElementById("btnOk").disabled = true;

		if(!document.getElementById('nome').value) {
			habilitarBotaoOk();
			sigaModal.alerta("Preencha o nome da Marcação");
			document.getElementById('nome').focus();
		} else if(!document.getElementById('cor').value) {
			habilitarBotaoOk();
			sigaModal.alerta("Preencha a cor da Marcação");
			document.getElementById('cor').focus();
		} else {
			frm.submit();
		}
	}

	function habilitarBotaoOk() {
		sigaSpinner.ocultar();
		document.getElementById("btnOk").disabled = false;
	}
</script>
<siga:pagina titulo="Cadastro de Lota&ccedil;&atilde;o">

	<!-- main content -->
	<div class="container-fluid">
		<div class="card bg-light mb-3">
			<div class="card-header">
				<h5>
					Dados da Marcação da
					<fmt:message key="usuario.lotacao" />
				</h5>
			</div>
			<div class="card-body">
				<form name="frm"
					action="${request.contextPath}/app/lotacao/marcador/gravar"
					method="POST">
					<input type="hidden" name="postback" value="1" /> <input
						type="hidden" name="id" value="${id}" />
					<div class="row">
						<div class="col-sm-4">
							<div class="form-group">
								<label for="nome">Nome</label> <input type="text" id="nome"
									name="nome" value="${nome}" maxlength="40" class="form-control" />
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-4">
							<div class="form-group">
								<label for="cor">Cor</label> 
								<input type="color" id="cor" title="Clique para alterar a cor"
									name="cor" value="${cor}" class="form-control" style="height: 40px;"/>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-2">
							<div class="form-group">
								<button type="button" id="btnOk"
									onclick="javascript: validar();" class="btn btn-primary">Ok</button>

								<button type="button" onclick="javascript:history.back();"
									class="btn btn-primary">Cancelar</button>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</siga:pagina>