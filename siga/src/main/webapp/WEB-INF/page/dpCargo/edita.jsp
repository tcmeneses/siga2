<%@ page language="java" contentType="text/html; charset=UTF-8"
	buffer="64kb"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://localhost/jeetags" prefix="siga"%>


<siga:pagina titulo="Cadastro de Cargo">

<script type="text/javascript">
	function validar() {
		var nmCargo = document.getElementsByName('nmCargo')[0].value;	
		var id = document.getElementsByName('id')[0].value;	
		if (nmCargo==null || nmCargo=="") {			
			mensagemAlerta("Preencha o nome do Cargo.");
			document.getElementById('nmCargo').focus();		
		}else {
			frm.submit();
		}			
	}

	function mensagemAlerta(mensagem) {
		$('#alertaModal').find('.mensagem-Modal').text(mensagem);
		$('#alertaModal').modal();
	}
</script>

<div class="container-fluid">
	<div class="card bg-light mb-3" >
		<form name="frm" action="${request.contextPath}/app/cargo/gravar" method="POST">
			<input type="hidden" name="postback" value="1" />
			<input type="hidden" name="id" value="${id}" />
			<div class="card-header"><h5>Cadastro de Cargo</h5></div>
				<div class="card-body">
					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<label>Órgão</label>
								<c:choose>
									<c:when test="${empty id || podeAlterarOrgao}">
										<select name="idOrgaoUsu" value="${idOrgaoUsu}" class="form-control">
											<c:forEach items="${orgaosUsu}" var="item">
												<option value="${item.idOrgaoUsu}"
													${item.idOrgaoUsu == idOrgaoUsu ? 'selected' : ''}>
													${item.nmOrgaoUsu}</option>
											</c:forEach>
										</select>
									</c:when>
									<c:otherwise>
										${nmOrgaousu}
										<input type="hidden" name="idOrgaoUsu" value="${idOrgaoUsu}" class="form-control"/>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
						<div class="col-sm-6">
							<div class="form-group">
								<label>Nome</label>
								<input type="text" id="nmCargo" name="nmCargo" value="${nmCargo}" maxlength="100" size="100" class="form-control"/>
							</div>
						</div>
					</div>
					<c:if test="${empty id}">
						<div class="row">
							<div class="col-sm-6">
								<div class="form-group">
									<label>Carregar planilha para inserir múltiplos registros:</label>
									<input type="button" value="Carregar planilha" onclick="javascript:location.href='/siga/app/cargo/carregarExcel';" class="btn btn-primary" />
								</div>
							</div>
						</div>
					</c:if>
					<div class="row">
						<div class="col-sm-6">
							<div class="form-group">
								<input type="button" value="Ok" onclick="javascript: validar();" class="btn btn-primary" /> 
								<input type="button" value="Cancelar" onclick="javascript:location.href='/siga/app/cargo/listar';" class="btn btn-primary" />
							</div>
						</div>
					</div>
				</div>
			</div>
		</form>
		<!-- Modal -->
		<div class="modal fade" id="alertaModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
			<div class="modal-dialog" role="document">
		    	<div class="modal-content">
		      		<div class="modal-header">
				        <h5 class="modal-title" id="alertaModalLabel">Alerta</h5>
				        <button type="button" class="close" data-dismiss="modal" aria-label="Fechar">
				          <span aria-hidden="true">&times;</span>
				    	</button>
				    </div>
			      	<div class="modal-body">
			        	<p class="mensagem-Modal"></p>
			      	</div>
					<div class="modal-footer">
					  <button type="button" class="btn btn-primary" data-dismiss="modal">Fechar</button>
					</div>
		    	</div>
		  	</div>
		</div>				
		<!--Fim Modal -->
	</div>
</div>
</siga:pagina>