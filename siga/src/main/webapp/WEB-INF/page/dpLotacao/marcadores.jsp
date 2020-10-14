<%@ page language="java" contentType="text/html; charset=UTF-8"
	buffer="64kb"%>
<%@ taglib uri="http://localhost/jeetags" prefix="siga"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<siga:pagina titulo="Marcadores">
	<style>
.cor-marcador {
	border: 1px solid black;
	width: 100px;
	display: inline-block;
}
</style>
	<div class="container-fluid">
		<form name="frm" action="listar" id="listar" class="form100"
			method="GET">
			<div class="card bg-light mb-3">
				<div class="card-header">
					<h5>
						Marcadores da
						<fmt:message key="usuario.lotacao" />
						${lotacao.nomeLotacao}
					</h5>
				</div>
			</div>

			<h3 class="gt-table-head">Marcadores cadastrados</h3>
			<table border="0" class="table table-sm table-striped">
				<thead class="${thead_color}">
					<tr>
						<th align="left">Nome</th>
						<th align="left">Cor</th>
						<th colspan="2" align="center">Op&ccedil;&otilde;es</th>
					</tr>
				</thead>

				<tbody>
					<siga:paginador maxItens="10" maxIndices="10"
						totalItens="${tamanho}" itens="${itens}" var="marcador">
						<tr>
							<td align="left">${marcador.descrMarcador}</td>
							<td align="left"><span class="cor-marcador"
								style="background-color: ${marcador.cor}"
								title="${marcador.cor}">&nbsp;</span></td>

							<td align="left"><c:url var="urlEditar"
									value="/app/lotacao/marcador/editar">
									<c:param name="id" value="${marcador.id}"></c:param>
								</c:url> <%-- ********************************** --%> <c:url
									var="urlAtivarInativar"
									value="/app/lotacao/marcador/ativarInativar">
									<c:param name="id" value="${marcador.id}"></c:param>
								</c:url> <%-- ********************************** --%> <a
								href="${urlEditar}" role="button" aria-pressed="true"
								class="btn btn-primary">Alterar</a> <%-- ********************************** --%>

								<a href="${urlAtivarInativar}" role="button" aria-pressed="true"
								class="btn btn-primary"> ${empty marcador.dataFimMarcadorLotacao? 'Inativar': 'Ativar'}
							</a> <%-- ********************************** --%></td>
						</tr>
					</siga:paginador>
				</tbody>
			</table>
			<div class="gt-table-buttons">
				<c:url var="url" value="/app/lotacao/marcador/editar"></c:url>
				<input type="button" value="Incluir"
					onclick="javascript:window.location.href='${url}'"
					class="btn btn-primary">
			</div>
		</form>
	</div>

</siga:pagina>