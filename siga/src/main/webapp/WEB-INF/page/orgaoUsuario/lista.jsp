<%@ page language="java" contentType="text/html; charset=UTF-8"
	buffer="64kb"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://localhost/jeetags" prefix="siga"%>

<siga:pagina titulo="Lista Orgão Usuário">
<script type="text/javascript" language="Javascript1.1">
function sbmt(offset) {
	if (offset==null) {
		offset=0;
	}
	frm.elements['offset'].value=offset;
	frm.submit();
}
</script>
<form name="frm" action="listar" class="form" method="POST">
<input type="hidden" name="offset" value="0" />
	<div class="container-fluid">
		<div class="card bg-light mb-3" >		
			<div class="card-header"><h5>Cadastro de &Oacute;rg&atilde;o</h5></div>
				<div class="card-body">
					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label>Nome</label>
								<input type="text" id="nome" name="nome" value="${nome}" maxlength="100" size="30" class="form-control"/>
							</div>						
						</div>
					</div>
				
					<div class="row">
						<div class="col-sm-6">
							<input type="submit" value="Pesquisar" class="btn btn-primary"/>
						</div>
					</div>
				</div>
			</div>
			
			<!-- main content -->
			<h5>&Oacute;rg&atilde;os cadastrados</h5>
				<table border="0" class="table table-sm table-striped">
					<thead class="thead-dark">
						<tr>
							<th algin="center">ID</th>
							<th align="left">Nome</th>
							<th align="center">Sigla</th>
							<th colspan="2" align="center">Op&ccedil;&otilde;es</th>					
						</tr>
					</thead>
					
					<tbody>
						<siga:paginador maxItens="15" maxIndices="10" totalItens="${tamanho}"
							itens="${itens}" var="orgaoUsuario">

							<tr>
								<td align="left">${orgaoUsuario.id}</td>
								<td align="left">${orgaoUsuario.descricao}</td>
								<td align="left">${orgaoUsuario.sigla}</td>
								<td align="left">
									<c:url var="url" value="/app/orgaoUsuario/editar">
										<c:param name="id" value="${orgaoUsuario.id}"></c:param>
									</c:url>
									<c:if test="${empty orgaoUsuarioSiglaLogado || orgaoUsuarioSiglaLogado eq orgaoUsuario.sigla}">
									<input type="button" value="Alterar"
										onclick="javascript:window.location.href='${url}'"
										class="btn btn-primary">
									</c:if>					
								</td>
							<%--	<td align="left">									
	 			 					<a href="javascript:if (confirm('Deseja excluir o orgão?')) location.href='/siga/app/orgao/excluir?id=${orgao.idOrgao}';">
										<img style="display: inline;"
										src="/siga/css/famfamfam/icons/cancel_gray.png" title="Excluir orgão"							
										onmouseover="this.src='/siga/css/famfamfam/icons/cancel.png';" 
										onmouseout="this.src='/siga/css/famfamfam/icons/cancel_gray.png';"/>
									</a>															
								</td>
							 --%>							
							</tr>
						</siga:paginador>
					</tbody>
				</table>				
			
			<c:if test="${empty orgaoUsuarioSiglaLogado}">
			<div class="gt-table-buttons">
					<c:url var="url" value="/app/orgaoUsuario/editar"></c:url>
					<input type="button" value="Incluir"
						onclick="javascript:window.location.href='${url}'"
						class="btn btn-primary">
				</div>	
			</c:if>			
		</div>

</form>
</siga:pagina>