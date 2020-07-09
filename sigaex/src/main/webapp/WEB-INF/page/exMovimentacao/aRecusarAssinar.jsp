<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	buffer="64kb" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://localhost/jeetags" prefix="siga"%>
<%@ taglib uri="http://localhost/functiontag" prefix="f"%>
<%@ taglib uri="http://localhost/customtag" prefix="tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<siga:pagina titulo="Documento" compatibilidade="IE=EmulateIE9">
	<script type="text/javascript" language="Javascript1.1">
		function sbmt() {
			let motivo = document.getElementById("descrMotivo").value.trim();

			if (!motivo) {
				alert("Descrição do motivo é obrigatória");
				return false;
			}

			if (!confirm("A operação Recusar Assinatura não poderá ser desfeita. Confirma operação?")) {
				history.back();
				return false;
			}

			return true;
		}

		function tamanho() {
			var i = tamanho2();
			if (i < 0) {
				i = 0
			}
			;
			document.getElementById("Qtd").innerText = 'Restam ' + i
					+ ' caracteres';
		}

		function tamanho2() {
			nota = new String();
			nota = this.frm.descrMotivo.value;
			var i = 500 - nota.length;
			return i;
		}

		function corrige() {
			if (tamanho2() < 0) {
				alert('Descrição com mais de 500 caracteres');
				nota = new String();
				nota = document.getElementById("descrMotivo").value;
				document.getElementById("descrMotivo").value = nota.substring(
						0, 500);
			}
		}
	</script>

	<!-- main content bootstrap -->
	<div class="container-fluid">
		<div class="card bg-light mb-3">
			<div class="card-header">
				<h5>Recusar Assinatura ${sigla}</h5>
			</div>
			<div class="card-body">
				<form name="frm" action="recusar_assinatura_gravar" method="post"
					onsubmit="return sbmt();">
					<input type="hidden" name="postback" value="1" /> <input
						type="hidden" name="sigla" value="${sigla}" />
					<div class="row">
						<div class="col-sm">
							<div class="form-group">
								<label for="descrMotivo">Motivo</label>
								<textarea class="form-control" name="descrMotivo" id="descrMotivo"
									value="${descrMotivo}" cols="60" rows="5"
									onkeydown="corrige();tamanho();" maxlength="500"
									onblur="tamanho();" onclick="tamanho();"></textarea>
								<small class="form-text text-muted" id="Qtd">Restam&nbsp;500&nbsp;caracteres</small>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-sm">
							<input type="submit" value="Ok" class="btn btn-primary" /> <input
								type="button" value="Cancela"
								onclick="javascript:history.back();" class="btn btn-cancel ml-2" />
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</siga:pagina>