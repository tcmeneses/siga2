<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	buffer="64kb"%>
<%@ taglib tagdir="/WEB-INF/tags/mod" prefix="mod"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="siga"%>
<%@ taglib uri="http://localhost/functiontag" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="esconderTexto" value="sim" scope="request" />

<mod:modelo >
	<mod:entrevista>
		<mod:grupo titulo="DADOS DO JUIZ">
			<mod:pessoa titulo="JUIZ" var="juiz"/>
		</mod:grupo>
		<mod:grupo>
			<mod:texto titulo="numero do processo" var="numP"/>
		</mod:grupo>
		<mod:grupo>
			<mod:texto titulo="Juizo de conhecimento" var="juizoCon"/>
		</mod:grupo>
		<mod:grupo>
			<mod:texto titulo="Juizo da execução penal" var="juizoPen"/>
		</mod:grupo>
		
		<mod:grupo titulo="IDENTIFICAÇÃO DO CONDENADO">
			<mod:grupo>
				<mod:texto titulo="Nome" var="nome"/>	
			</mod:grupo>
			<mod:grupo>
				<mod:texto titulo="Filiação" var="filiacao"/>	
			</mod:grupo>
			<mod:grupo>
				<mod:texto titulo="Naturalidade" var="natural"/>
				<mod:texto titulo="Data de nascimento" var="dtnasc"/>
			</mod:grupo>
			<mod:grupo>
				<mod:texto titulo="Profissão" var="prof"/>	
			</mod:grupo>
			<mod:grupo>
				<mod:texto titulo="Grau de instrução" var="grau"/>	
				<mod:texto titulo="Estado civil" var="estCivil"/>	
			</mod:grupo>
			<mod:grupo>
				<mod:texto titulo="Documento(s)" var="docum"/>	
			</mod:grupo>
			<mod:grupo>
				<mod:texto titulo="Alcunha(s)" var="alc"/>	
			</mod:grupo>
			<mod:grupo>
				<mod:texto titulo="Outro(s) Nome(s)" var="outro"/>	
			</mod:grupo>
			<mod:grupo>
				<mod:texto titulo="Endereço(s) Completo(s)" var="end"/>	
			</mod:grupo>			
		</mod:grupo>
		
		
		<mod:grupo titulo="DADOS DO PROCESSO CRIMINAL">
			<mod:grupo>
				<mod:texto titulo="Numero do processo de origem" var="num"/>
				<mod:texto titulo="Orgão de Origem" var="org"/>	
			</mod:grupo>
			<mod:grupo>
				<mod:texto titulo="Local de Ocorrência do delito" var="loc"/>	
			</mod:grupo>
			<mod:grupo>
				<mod:texto titulo="Tipificação Penal" var="tppenal"/>	
			</mod:grupo>
			<mod:grupo>
				<mod:texto titulo="Data do fato" var="dtfato"/>
				<mod:texto titulo="recebimento da denúncia ou queixa" var="recDen"/>
				<mod:texto titulo="Data da publicação da pronúncia" var="dtPron"/>	
			</mod:grupo>
			<mod:grupo>
				<mod:texto titulo="Data de publicação da sentença" var="dtSent"/>
				<mod:texto titulo="Data de publicaçao do acordão" var="dtAcordao"/>
				<mod:texto titulo="Órgão do Tribunal" var="orgTrib"/>	
			</mod:grupo>
			<mod:grupo>
				<mod:texto titulo="Data do trânsito em julgado" var="dttransDef"/>
				<mod:texto titulo="Data do trânsito em julgamento para o Ministerio publico" var="dttransMP"/>
			</mod:grupo>
			<mod:grupo>
				<mod:texto titulo="Suspenção pelo artigo 366 do CPP" var="suspensao"/>
			</mod:grupo>
		</mod:grupo>
				
		<mod:grupo titulo= "PENAS IMPOSTAS NO PROCESSO">	
			<mod:grupo titulo="CRIME COMUM - reclusão">
				<mod:texto titulo="ano(s)" var="anoReclusao" />
				<mod:texto titulo="mês(es)" var="mesReclusao" />
				<mod:texto titulo="dia(s)" var="diaReclusao" />
			</mod:grupo>
			<mod:grupo titulo="CRIME COMUM - Detenção">
				<mod:texto titulo="ano(s)" var="anoDetencao" />
				<mod:texto titulo="mês(es)" var="mesDetencao" />
				<mod:texto titulo="dia(s)" var="diaDetencao" />
			</mod:grupo>
			<mod:grupo titulo="CRIME HEDIONDO">
				<mod:texto titulo="ano(s)" var="anoHediondo" />
				<mod:texto titulo="mês(es)" var="mesHediondo" />
				<mod:texto titulo="dia(s)" var="diaHediondo" />
			</mod:grupo>
			<mod:grupo titulo="REINCIDÊNCIA">
				<mod:texto titulo="COMUM" var="reinComum" />
				<mod:texto titulo="HEDIONDA" var="reinHedio" />
				<mod:texto titulo="GENERICA" var="reinGene" />
			</mod:grupo>
			<mod:grupo>
				<mod:texto titulo="DIAS-MULTA" var="diasmulta" />
			</mod:grupo>	
		</mod:grupo>
		
		<mod:grupo titulo="DADOS PARA DETRAÇAO PENAL">
				<mod:memo var="editor" colunas="80" linhas="3"/>
		</mod:grupo>

	</mod:entrevista>
	
	<mod:documento>
	<c:set var="juiz" value ="${f:pessoa(requestScope['juiz_pessoaSel.id'])}" />
		<head>
		<style type="text/css">
@page {
	margin-left: 3cm;
	margin-right: 2cm;
	margin-top: 1cm;
	margin-bottom: 2cm;
}
</style>
		</head>
		<body>

		<c:if test="${empty tl}">
			<c:set var="tl" value="7pt"></c:set>
		</c:if>

		<!-- INICIO PRIMEIRO CABECALHO
		<table width="100%" border="0" bgcolor="#FFFFFF"><tr><td>
		<c:import url="/paginas/expediente/modelos/inc_cabecalhoCentralizadoPrimeiraPagina.jsp" />
		</td></tr>
		
		<tr><td><br/>&nbsp;<br/></td></tr>
			<tr bgcolor="#FFFFFF">
				<td width="100%">
					<table width="100%">
						<tr>
							<td align="center"><p style="font-family:Arial;font-weight:bold;font-size:11pt;">GUIA DE EXECU&Ccedil;&Atilde;O PROVIS&Oacute;RIO ${doc.codigo} DE ${doc.dtExtensoMaiusculasSemLocalidade}</p></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		FIM PRIMEIRO CABECALHO -->

		<!-- INICIO CABECALHO
		<c:import url="/paginas/expediente/modelos/inc_cabecalhoEsquerda.jsp" />
		FIM CABECALHO -->

		<mod:letra tamanho="7pt">

			<span style="font-size: align=right"> ${texto_ato} </span>
			
			<p>&nbsp;</p>
			O(a) Doutor(a) ${juiz.descricao} , Desembargador(a) Federal do Tribunal Regional Federal da 2a. região,
			na forma da lei, e no uso de suas atribuições, FAZ SABER ao Exmº Dr. Juiz de Direito da Vara de 
			Execuções Penais do Estado do Rio de Janeiro, que perante este Tribunal tramitam os autos da Apelação 
			Criminal número ,${numP} contra o(a) apenado(a) abaixo identificado(a), o(a) qual foi 
			condenado(a) às sanções adiante especificadas, estando o(a) apenado(a) preso(a), passo o mesmo à 
			disposição de V.Exª, a fim de que faça executar a condenação, consoante dados a seguir indicados:
			<br/>
			JUIZO DE CONHECIMENTO: ${juizpCon}<br/>
			JUÍZO DA EXECUÇÃO PENAL: ${juizoPen }<br/>

			<p align="center">IDENTIFICAÇÃO DO CONDENADO   <br/>
			 
				<table border="1" width="100%" bordercolor="#000000" bgcolor="#FFFFFF" align="center">
		             <tr> 
			           <td  bgcolor="#FFFFFF"  align="center"><b>Nome</b><br/></td>
		             </tr>
		             <tr> <td>	
			            <table border="0" width="100%" bordercolor="#FFFFFF" bgcolor="#000000" align="center">
			            	<tr><td  bgcolor="#FFFFFF" width="100%" align="center"><b> ${nome}</b><br/></td><tr/>
			        	</table></td>	
					</tr>
		        </table>
		        
				<table border="1" width="100%" bordercolor="#000000" bgcolor="#FFFFFF" align="center">
		             <tr> 
			           <td  bgcolor="#FFFFFF" width="100%" align="center"><b>Filiação</b><br/></td>
		             </tr>
		             <tr> <td>	
			            <table border="0" width=100% bordercolor="#FFFFFF" bgcolor="#000000" align="center">
			            	<tr><td  bgcolor="#FFFFFF" width="100%" align="center"><b> ${filiacao}</b><br/></td><tr/>
			            </table></td>	
		             </tr>
		        </table>
		        
				<table border="1" width="100%" bordercolor="#000000" bgcolor="#FFFFFF" align="center">
		             <tr > 
			           	<td width= "48%"  bgcolor="#FFFFFF"  align="center">
							<table border="1" width="100%" bordercolor="#000000" bgcolor="#FFFFFF" align="center">
			            	<tr>
			            		<td bgcolor="#FFFFFF" width="100%" align="center"><b>Naturalidade</b><br/></td>
			            	</tr>
			            	<tr>
			            		<td>
			            			<table border="1" width="100%" bordercolor="#000000" bgcolor="#000000" align="center">
			            				<tr><td  bgcolor="#FFFFFF" width="100%" align="center"><b> ${dtnasc}</b><br/></td><tr/>
			            			</table>
			            		</td>
			            	</tr>
			            </table>
			           
			            </td>
			           
			            <td width= "4%"></td>
			           
			            <td width= "48%"  bgcolor="#FFFFFF"  align="center">
							<table border="1" width="100%" bordercolor="#000000" bgcolor="#FFFFFF" align="center">
			            		<tr>
			            			<td bgcolor="#FFFFFF"  align="center"><b>Data de Nascimento</b><br/></td>
			            		</tr>
			            		<tr>
			            			<td>
			            				<table border="1" width="100%" bordercolor="#000000" bgcolor="#000000" align="center">
			            					<tr><td  bgcolor="#FFFFFF" align="center"><b> ${filiacao}</b><br/></td><tr/>
			            				</table>
			            			</td>
			            		</tr>
			            	</table>       
			            </td>
		        </table>
		        
		        <table border="1" width="100%" bordercolor="#000000" bgcolor="#FFFFFF" align="center">
		             <tr> 
			           <td  bgcolor="#FFFFFF"  align="center"><b>Profissão</b><br/></td>
		             </tr>
		             <tr> <td>	
			            <table border="1" width="100%" bordercolor="#000000" bgcolor="#000000" align="center">
			            	<tr><td  bgcolor="#FFFFFF" align="center"><b> ${prof}</b><br/></td><tr/>
			            </table></td>	
		             </tr>
		        </table> 
		        
			 	<table border="1" width="100%" bordercolor="#FFFFFF" bgcolor="#FFFFFF" align="center">
		             <tr > 
			           	<td width= "48%"  bgcolor="#FFFFFF"  align="center">
							<table border="1" width="100%" bordercolor="#000000" bgcolor="#FFFFFF" align="center">
			            	<tr>
			            		<td bgcolor="#FFFFFF"  align="center"><b>Grau de Instrução</b><br/></td>
			            	</tr>
			            	<tr>
			            		<td>
			            			<table border="1" width="100%" bordercolor="#000000" bgcolor="#000000" align="center">
			            				<tr><td  bgcolor="#FFFFFF" align="center"><b> ${grau}</b><br/></td><tr/>
			            			</table>
			            		</td>
			            	</tr>
			            </table>
			           
			            </td>
			           
			            <td width= "4%"></td>
			           
			            <td width= "48%"  bgcolor="#FFFFFF"  align="center">
							<table border="1" width="100%" bordercolor="#000000" bgcolor="#FFFFFF" align="center">
			            		<tr>
			            			<td bgcolor="#FFFFFF"  align="center"><b>Estado Civil</b><br/></td>
			            		</tr>
			            		<tr>
			            			<td>
			            				<table border="1" width="100%" bordercolor="#000000" bgcolor="#000000" align="center">
			            					<tr><td  bgcolor="#FFFFFF" align="center"><b> ${estCivil}</b><br/></td><tr/>
			            				</table>
			            			</td>
			            		</tr>
			            	</table>       
			            </td>
		        </table>
		        
		        <table border="1" width="100%" bordercolor="#000000" bgcolor="#FFFFFF" align="center">
		             <tr> 
			         	<td  bgcolor="#FFFFFF"  align="center"><b>Documentos</b><br/></td>
		             </tr>
		             <tr> <td>	
			         	<table border="1" width="100%" bordercolor="#000000" bgcolor="#000000" align="center">
			            	<tr><td  bgcolor="#FFFFFF"" align="center"><b> ${docum}</b><br/></td><tr/>
			            </table></td>	
		             </tr>
		        </table>
		        
		        <table border="1" width="100%" bordercolor="#000000" bgcolor="#FFFFFF" align="center">
		             <tr> 
			           <td  bgcolor="#FFFFFF"  align="center"><b>Alcunha(s)</b><br/></td>
		             </tr>
		             <tr> <td>	
			            <table border="1" width="100%" bordercolor="#000000" bgcolor="#000000" align="center">
			            	<tr><td border="0" width="100%" bgcolor="#FFFFFF" align="center"><br/>${alc}</td><tr/>
			            </table></td>	
		             </tr>
		        </table>
		        
		        <table border="1" width="100%" bordercolor="#000000" bgcolor="#FFFFFF" align="center">
		             <tr> 
			           <td  bgcolor="#FFFFFF"  align="center"><b>Outro(s) Nome(s)</b><br/></td>
		             </tr>
		             <tr> <td>	
			            <table border="1" width="100%" bordercolor="#000000" bgcolor="#000000" align="center">
			            	<tr><td border="0" width="100%" bgcolor="#FFFFFF" align="center"><br/>${outro}</td><tr/>
			            </table></td>	
		             </tr>
		        </table>
		        
		        <table border="1" width="100%" bordercolor="#000000" bgcolor="#FFFFFF" align="center">
		             <tr> 
			           <td  bgcolor="#FFFFFF"  align="center"><b>Endereço Completo</b><br/></td>
		             </tr>
		             <tr> <td>	
			            <table border="0" width="100%" bordercolor="#000000" bgcolor="#000000" align="center">
			            	<tr><td border="0" width="100%" bgcolor="#FFFFFF" align="center"><br/>${end }</td><tr/>
			            </table></td>	
		             </tr>
		        </table>			 
			 <p/>
			 
			 <p align="center">DADOS DO PROCESSO CRIMINAL  <br/>
			 
			 <table border="1" width="100%" bordercolor="#FFFFFF" bgcolor="#FFFFFF" align="center">
		             <tr > 
			           	<td width= "40%"  bgcolor="#FFFFFF"  align="center">
							<table border="1" width="100%" bordercolor="#000000" bgcolor="#FFFFFF" align="center">
			            	<tr>
			            		<td bgcolor="#FFFFFF"  align="center"><b>Numero do processo de origem</b><br/></td>
			            	</tr>
			            	<tr>
			            		<td>
			            			<table border="1" width="100%" bordercolor="#000000" bgcolor="#000000" align="center">
			            				<tr><td  bgcolor="#FFFFFF"" align="center"><b> ${num}</b><br/></td><tr/>
			            			</table>
			            		</td>
			            	</tr>
			            </table>
			           
			            </td>
			           
			            <td width= "4%"></td>
			           
			            <td width= "56%"  bgcolor="#FFFFFF"  align="center">
							<table border="1" width="100%" bordercolor="#000000" bgcolor="#FFFFFF" align="center">
			            		<tr>
			            			<td bgcolor="#FFFFFF"  align="center"><b>Òrgão de origem</b><br/></td>
			            		</tr>
			            		<tr>
			            			<td>
			            				<table border="1" width="100%" bordercolor="#000000" bgcolor="#000000" align="center">
			            					<tr><td  bgcolor="#FFFFFF" align="center"><b> ${org}</b><br/></td><tr/>
			            				</table>
			            			</td>
			            		</tr>
			            	</table>       
			            </td>
		        </table>
		        
			 	<table border="1" width="100%" bordercolor="#000000" bgcolor="#FFFFFF" align="center">
		             <tr> 
			           <td  bgcolor="#FFFFFF"  align="center"><b>Local de Ocorrência do delito</b><br/></td>
		             </tr>
		             <tr> <td>	
			            <table border="1" width="100%" bordercolor="#000000" bgcolor="#000000" align="center">
			            	<tr><td  bgcolor="#FFFFFF" align="center"><b> ${loc}</b><br/></td><tr/>
			            </table></td>	
		             </tr>
		        </table>
		        
		        <table border="1" width="100%" bordercolor="#000000" bgcolor="#FFFFFF" align="center">
		             <tr> 
			           <td  bgcolor="#FFFFFF"  align="center"><b>Tipificão Penal</b><br/></td>
		             </tr>
		             <tr> <td>	
			            <table border="1" width="100%" bordercolor="#000000" bgcolor="#000000" align="center">
			            	<tr><td  bgcolor="#FFFFFF" align="center"><b> ${tppenal}</b><br/></td><tr/>
			            </table></td>	
		             </tr>
		        </table>
		        
			 	<table border="1" width="100%" bordercolor="#FFFFFF" bgcolor="#FFFFFF" align="center">
		             <tr > 
			           	<td width= "31%"  bgcolor="#FFFFFF"  align="center">
							<table border="1" width="100%" bordercolor="#000000" bgcolor="#FFFFFF" align="center">
			            	<tr>
			            		<td bgcolor="#FFFFFF"  align="center"><b>Data do fato</b><br/></td>
			            	</tr>
			            	<tr>
			            		<td>
			            			<table border="1" width="100%" bordercolor="#000000" bgcolor="#000000" align="center">
			            				<tr><td  bgcolor="#FFFFFF" align="center"><b> ${dtFato}</b><br/></td><tr/>
			            			</table>
			            		</td>
			            	</tr>
			            </table>
			           
			            </td>
			           
			            <td width= "4%"></td>
			           
			            <td width= "30%"  bgcolor="#FFFFFF"  align="center">
							<table border="1" width="100%" bordercolor="#000000" bgcolor="#FFFFFF" align="center">
			            		<tr>
			            			<td bgcolor="#FFFFFF"  align="center"><b>Recebimento da denúncia ou queixa</b><br/></td>
			            		</tr>
			            		<tr>
			            			<td>
			            				<table border="1" width="100%" bordercolor="#000000" bgcolor="#000000" align="center">
			            					<tr><td  bgcolor="#FFFFFF" align="center"><b> ${recDen}</b><br/></td><tr/>
			            				</table>
			            			</td>
			            		</tr>
			            	</table>       
			            </td>
			            <td width= "4%"></td>
			            <td width= "31%"  bgcolor="#FFFFFF"  align="center">
							<table border="1" width="100%" bordercolor="#000000" bgcolor="#FFFFFF" align="center">
			            		<tr>
			            			<td bgcolor="#FFFFFF"  align="center"><b>Data da publicação da pronuncia</b><br/></td>
			            		</tr>
			            		<tr>
			            			<td>
			            				<table border="1" width="100%" bordercolor="#000000" bgcolor="#000000" align="center">
			            					<tr><td  bgcolor="#FFFFFF" align="center"><b> ${dtPron}</b><br/></td><tr/>
			            				</table>
			            			</td>
			            		</tr>
			            	</table>       
			            </td>
					</tr>
		        </table>
		        <table border="1" width="100%" bordercolor="#FFFFFF" bgcolor="#FFFFFF" align="center">
		             <tr > 
			           	<td width= "31%"  bgcolor="#FFFFFF"  align="center">
							<table border="1" width=100% bordercolor="#000000" bgcolor="#FFFFFF" align="center">
			            	<tr>
			            		<td bgcolor="#FFFFFF"  align="center"><b>Data da publicação da sentença</b><br/></td>
			            	</tr>
			            	<tr>
			            		<td>
			            			<table border="1" width="100%" bordercolor="#000000" bgcolor="#000000" align="center">
			            				<tr><td  bgcolor="#FFFFFF" align="center"><b> ${dtSend}</b><br/></td><tr/>
			            			</table>
			            		</td>
			            	</tr>
			            </table>
			           
			            </td>
			           
			            <td width= "4%"></td>
			           
			            <td width= "30%"  bgcolor="#FFFFFF"  align="center">
							<table border="1" width="100%" bordercolor="#000000" bgcolor="#FFFFFF" align="center">
			            		<tr>
			            			<td bgcolor="#FFFFFF"  align="center"><b>Data da publicação do Ácordão</b><br/></td>
			            		</tr>
			            		<tr>
			            			<td>
			            				<table border="1" width="100%" bordercolor="#000000" bgcolor="#000000" align="center">
			            					<tr><td  bgcolor="#FFFFFF" align="center"><b> ${dtAcordao}</b><br/></td><tr/>
			            				</table>
			            			</td>
			            		</tr>
			            	</table>       
			            </td>
			            <td width= "4%"></td>
			            <td width= "31%"  bgcolor="#FFFFFF"  align="center">
							<table border="1" width="100%" bordercolor="#000000" bgcolor="#FFFFFF" align="center">
			            		<tr>
			            			<td bgcolor="#FFFFFF"  align="center"><b>Órgão do Tribunal</b><br/></td>
			            		</tr>
			            		<tr>
			            			<td>
			            				<table border="1" width="100%" bordercolor="#000000" bgcolor="#000000" align="center">
			            					<tr><td  bgcolor="#FFFFFF" align="center"><b> ${dttransDef}</b><br/></td><tr/>
			            				</table>
			            			</td>
			            		</tr>
			            	</table>       
			            </td>
					</tr>
		        </table>
		        	        
				<table border="1" width="100%" bordercolor="#FFFFFF" bgcolor="#FFFFFF" align="center">
		             <tr > 
			           	<td width= "36%"  bgcolor="#FFFFFF"  align="center">
							<table border="1" width="100%" bordercolor="#000000" bgcolor="#FFFFFF" align="center">
			            	<tr>
			            		<td bgcolor="#FFFFFF"  align="center"><b>Data do transito em julgamento para Defesa</b><br/></td>
			            	</tr>
			            	<tr>
			            		<td>
			            			<table border="1" width="100%" bordercolor="#000000" bgcolor="#000000" align="center">
			            				<tr><td  bgcolor="#FFFFFF" align="center"><b> ${dttransDef}</b><br/></td><tr/>
			            			</table>
			            		</td>
			            	</tr>
			            </table>
			           
			            </td>
			           
			            <td width= "8%"></td>
			           
			            <td width= "46%"  bgcolor="#FFFFFF"  align="center">
							<table border="1" width="100%" bordercolor="#000000" bgcolor="#FFFFFF" align="center">
			            		<tr>
			            			<td bgcolor="#FFFFFF"  align="center"><b><b>Data do transito em julgado para o Ministério Publico</b><br/></td>
			            		</tr>
			            		<tr>
			            			<td>
			            				<table border="1" width="100%" bordercolor="#000000" bgcolor="#000000" align="right">
			            					<tr><td  bgcolor="#FFFFFF" align="center"><b> ${dttransMP}</b><br/></td><tr/>
			            				</table>
			            			</td>
			            		</tr>
			            	</table>       
			            </td>     
					</tr>
		        </table>
			 	<table border="1" width="100%" bordercolor="#000000" bgcolor="#FFFFFF" align="center">
		             <tr> 
			           <td  bgcolor="#FFFFFF" width="100%" align="center"><b>Suspensão pelo artigo 366 do CPP</b><br/></td>
		             </tr>
		             <tr> <td>	
			            <table border="0" width="100%" bordercolor="#FFFFFF" bgcolor="#000000" align="center">
			            	<tr><td bgcolor="#FFFFFF" align="center"><b> ${suspensao}</b><br/></td><tr/>
			        	</table></td>	
					</tr>
		        </table>
		        
		        <p align="center">DADOS PARA DETRAÇÃO PENAL  <br/>
		        
		        <table border="1" width="100%" bordercolor="#FFFFFF" bgcolor="#000000" align="center">
			       	<tr><td bgcolor="#FFFFFF" align="center"><b> ${editor}</b><br/><br/><br/></td><tr/>
			    </table><br/>
			    
			    <p align="center">PENAS IMPOSTAS NO PROCESSO  <br/>
		        
		        <table border="1" width="100%" bordercolor="#FFFFFF" bgcolor="#000000" align="center">
			       	<tr>
			       		<td width= "28%" bgcolor="#FFFFFF" align="center"><b>CRIME COMUM - reclusão</b><br/></td>
			       		<td width= "12%" bgcolor="#FFFFFF" align="center"><b>ANO(S)</b><br/></td>
			       		<td width= "12%" bgcolor="#FFFFFF" align="center">${anoReclusao }<br/></td>
			       		<td width= "12%" bgcolor="#FFFFFF" align="center"><b>MES(ES)</b><br/></td>
			       		<td width= "12%" bgcolor="#FFFFFF" align="center">${mesReclusao }<br/></td>
			       		<td width= "12%" bgcolor="#FFFFFF" align="center"><b>DIA(S)</b><br/></td>
			       		<td width= "12%" bgcolor="#FFFFFF" align="center">${diaReclusao }<br/></td>
			       	<tr/>
			       	<tr>
			       		<td width= "28%" bgcolor="#FFFFFF" align="center"><b>CRIME COMUM - detenção</b><br/></td>
			       		<td width= "12%" bgcolor="#FFFFFF" align="center"><b>ANO(S)</b><br/></td>
			       		<td width= "12%" bgcolor="#FFFFFF" align="center">${anoDetencao}<br/></td>
			       		<td width= "12%" bgcolor="#FFFFFF" align="center"><b>MES(ES)</b><br/></td>
			       		<td width= "12%" bgcolor="#FFFFFF" align="center">${mesDetencao}<br/></td>
			       		<td width= "12%" bgcolor="#FFFFFF" align="center"><b>DIA(S)</b><br/></td>
			       		<td width= "12%" bgcolor="#FFFFFF" align="center">${diaDetencao}<br/></td>
			       	<tr/>
			       	<tr>
			       		<td width= "28%" bgcolor="#FFFFFF" align="center"><b>CRIME HEDIONDO</b><br/></td>
			       		<td width= "12%" bgcolor="#FFFFFF" align="center"><b>ANO(S)</b><br/></td>
			       		<td width= "12%" bgcolor="#FFFFFF" align="center">${anoHediondo }<br/></td>
			       		<td width= "12%" bgcolor="#FFFFFF" align="center"><b>MES(ES)</b><br/></td>
			       		<td width= "12%" bgcolor="#FFFFFF" align="center">${mesHediondo }<br/></td>
			       		<td width= "12%" bgcolor="#FFFFFF" align="center"><b>DIA(S)</b><br/></td>
			       		<td width= "12%" bgcolor="#FFFFFF" align="center">${diaHediondo }<br/></td>
			       	<tr/>
			       	<tr>
			       		<td width= "28%" bgcolor="#FFFFFF" align="center"><b>REINCIDENCIA</b><br/></td>
			       		<td width= "12%" bgcolor="#FFFFFF" align="center"><b>COMUM</b><br/></td>
			       		<td width= "12%" bgcolor="#FFFFFF" align="center">${reinComum}<br/></td>
			       		<td width= "12%" bgcolor="#FFFFFF" align="center"><b>HEDIONDO</b><br/></td>
			       		<td width= "12%" bgcolor="#FFFFFF" align="center">${reinHedion}<br/></td>
			       		<td width= "12%" bgcolor="#FFFFFF" align="center"><b>GENERICA</b><br/></td>
			       		<td width= "12%" bgcolor="#FFFFFF" align="center">${reinGene }<br/></td>
			       	<tr/>
			       	<tr >
			       		<td width= "28%" bgcolor="#FFFFFF" align="center"><b>DIAS-MULTA</b></td>
			       		<td width= "12%" bgcolor="#FFFFFF" align="center">${editor}<br/></td>
			       		
			       	<tr/>

			    </table><br/><br/>
				
			 
			 
			<c:import url="/paginas/expediente/modelos/inc_assinatura.jsp" />

		</mod:letra>

		<!-- INICIO PRIMEIRO RODAPE
		<c:import url="/paginas/expediente/modelos/inc_rodapeClassificacaoDocumental.jsp" />
		FIM PRIMEIRO RODAPE -->

		<!-- INICIO RODAPE
		<c:import url="/paginas/expediente/modelos/inc_rodapeNumeracaoADireita.jsp" />
		FIM RODAPE -->

		</body>

	</mod:documento>

</mod:modelo>