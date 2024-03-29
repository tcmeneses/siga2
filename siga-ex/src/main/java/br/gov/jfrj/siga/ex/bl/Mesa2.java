package br.gov.jfrj.siga.ex.bl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.crivano.swaggerservlet.ISwaggerModel;

import br.gov.jfrj.siga.base.Data;
import br.gov.jfrj.siga.base.Prop;
import br.gov.jfrj.siga.base.SigaMessages;
import br.gov.jfrj.siga.dp.CpMarcador;
import br.gov.jfrj.siga.dp.DpLotacao;
import br.gov.jfrj.siga.dp.DpPessoa;
import br.gov.jfrj.siga.ex.ExMarca;
import br.gov.jfrj.siga.ex.ExMobil;
import br.gov.jfrj.siga.ex.ExMovimentacao;
import br.gov.jfrj.siga.ex.ExTipoMovimentacao;
import br.gov.jfrj.siga.hibernate.ExDao;

public class Mesa2 {
	private static List<GrupoItem> gruposBase;
	
	public static class SelGrupo implements ISwaggerModel {
		public String grupoOrdem;
		public Long grupoQtd;
		public Long grupoQtdPag;
		public boolean grupoCollapsed;
		public boolean grupoHide;
	}
	
	public static class GrupoItem implements ISwaggerModel {
		public String grupo;
		public String grupoNome;
		public String grupoIcone;
		public Long grupoCounterUser;
		public Long grupoCounterLota;
		public Long grupoCounterAtivo;
		public Long grupoQtd;
		public Long grupoQtdPag;
		public String grupoOrdem;
		public boolean grupoCollapsed;
		public boolean grupoHide;
		public List<MesaItem> grupoDocs;
		public List<Integer> grupoMarcadores;
	}
	
	public static class MesaItem implements ISwaggerModel {
		public String grupoOrdem;
		public String tipo;
		public Date datahora;
		public String datahoraDDMMYYYHHMM;
		public String tempoRelativo;
		public String codigo;
		public String sigla;
		public String descr;
		public String origem;
		public String anotacao;
		public String dataDevolucao;
		public String tipoDoc;
		public List<Marca> list;
	}

	public static class Marca implements ISwaggerModel {
		public String pessoa;
		public String lotacao;
		public String nome;
		public String icone;
		public String titulo;
		public Date inicio;
		public Date termino;
		public String ref1;
		public String ref2;
		public Boolean daPessoa;
		public Boolean deOutraPessoa;
		public Boolean daLotacao;
		public String cor;
	}

	public enum TipoDePainelEnum {
		PESSOAL, UNIDADE;
	}

	public enum GrupoDeMarcadorEnum {
		PRONTO_PARA_ASSINAR(1, "Pronto para Assinar", "fas fa-inbox", true, true, false),
		//
		ALERTA(2, "Alertas", "fas fa-hourglass-end", true, false, false),
		//
		A_REVISAR(3, "Pendente de Revisão", "fas fa-glasses", true, true, false),
		//
		A_ASSINAR(4, "Pendente de Assinatura", "fas fa-key", true, false, false),
		//
		CAIXA_DE_ENTRADA(5, "Caixa de Entrada", "fas fa-inbox", true, false, false),
		//
		EM_ELABORACAO(6, "Em Elaboração", "fas fa-lightbulb", true, false, false),
		//
		AGUARDANDO_ANDAMENTO(7, "Aguardando Andamento", "fas fa-clock", true, true, false),
		//
		CAIXA_DE_SAIDA(8, "Caixa de Saída", "fas fa-inbox", false, true, true),
		//
		ACOMPANHANDO(9, "Acompanhando", "fas fa-tags", true, true, false),
		//
		MONITORANDO(10, "Monitorando", "fas fa-hourglass-half", true, true, false),
		//
		AGUARDANDO_ACAO_DE_TEMPORALIDADE(11, "Aguardando Ação de Temporalidade",
				"fas fa-hourglass-half", true, true, true),
		//
		OUTROS(12, "Outros", "fas fa-inbox", true, true, false),
		//
		QUALQUER(13, "Qualquer", "fas fa-inbox", false, true, true),
		//
		NENHUM(14, "Nenhum", "fas fa-inbox", false, true, true);

		private final Integer id;
		private final String nome;
		private final String icone;
		private final boolean visible;
		private final boolean collapsed;
		private final boolean hide;

		private GrupoDeMarcadorEnum(Integer id, String nome, String icone, boolean visible, boolean collapsed, boolean hide) {
			this.id = id;
			this.nome = nome;
			this.icone = icone;
			this.visible = visible;
			this.collapsed = collapsed;
			this.hide = hide;
		}
		public String getNome() {
			return this.nome;
		}
		public Integer getId() {
			return this.id;
		}
		public static GrupoDeMarcadorEnum getByNome(String nome) {
			for (GrupoDeMarcadorEnum i : GrupoDeMarcadorEnum.values()) {
				if (i.nome.equals(nome))
					return i;
			}
			return null;
		}
		public static GrupoDeMarcadorEnum getById(Integer id) {
			for (GrupoDeMarcadorEnum i : GrupoDeMarcadorEnum.values()) {
				if (id.equals(i.id))
					return i;
			}
			return null;
		}
		public static List<String> getIdList() {
			List<String> idList = new ArrayList<String>();
			for (GrupoDeMarcadorEnum i : GrupoDeMarcadorEnum.values()) {
				idList.add((i.id).toString());
			}
			return idList;
		}
	}

	public enum MarcadorEnum {
		//
		EM_ELABORACAO(1, "Em Elaboração", "fas fa-lightbulb", "",
				GrupoDeMarcadorEnum.EM_ELABORACAO),
		//
		EM_ANDAMENTO(2, "Aguardando Andamento", "fas fa-clock", "",
				GrupoDeMarcadorEnum.AGUARDANDO_ANDAMENTO),
		//
		A_RECEBER(3, "A Receber", "fas fa-inbox", "",
				GrupoDeMarcadorEnum.CAIXA_DE_ENTRADA),
		//
		EXTRAVIADO(4, "Extraviado", "fas fa-inbox", "", GrupoDeMarcadorEnum.ALERTA),
		//
		A_ARQUIVAR(5, "A Arquivar", "fas fa-inbox", "", GrupoDeMarcadorEnum.OUTROS),
		//
		ARQUIVADO_CORRENTE(6, "Arquivado Corrente", "fas fa-inbox", "",
				GrupoDeMarcadorEnum.OUTROS),
		//
		A_ELIMINAR(7, "A Eliminar", "fas fa-inbox", "",
				GrupoDeMarcadorEnum.AGUARDANDO_ACAO_DE_TEMPORALIDADE),
		//
		ELIMINADO(8, "Eliminado", "fas fa-power-off", "", GrupoDeMarcadorEnum.OUTROS),
		//
		JUNTADO(9, "Juntado", "fas fa-lock", "", GrupoDeMarcadorEnum.OUTROS),
		//
		JUNTADO_EXTERNO(16, "Juntado Externo", "fas fa-lock", "",
				GrupoDeMarcadorEnum.OUTROS),
		//
		CANCELADO(10, SigaMessages.getMessage("marcador.cancelado.label"), "fas fa-ban", "", GrupoDeMarcadorEnum.OUTROS),
		//
		TRANSFERIDO_A_ORGAO_EXTERNO(11, "Tranferido a Órgão Externo", "fas fa-paper-plane",
				"", GrupoDeMarcadorEnum.OUTROS),

		//
		ARQUIVADO_INTERMEDIARIO(12, "Arquivado Intermediário", "fas fa-inbox", "",
				GrupoDeMarcadorEnum.OUTROS),
		//
		CAIXA_DE_ENTRADA(14, "A Receber", "fas fa-inbox", "",
				GrupoDeMarcadorEnum.CAIXA_DE_ENTRADA),
		//
		ARQUIVADO_PERMANENTE(13, "Arquivado Permanente", "fas fa-inbox", "",
				GrupoDeMarcadorEnum.OUTROS),
		//
		PENDENTE_DE_ASSINATURA(15, "Pendente de Assinatura", "fas fa-key", "",
				GrupoDeMarcadorEnum.AGUARDANDO_ANDAMENTO),
		//
		JUNTADO_A_DOCUMENTO_EXTERNO(16, "Juntado a Documento Externo", "fas fa-inbox",
				"", GrupoDeMarcadorEnum.OUTROS),
		//
		A_REMETER_PARA_PUBLICACAO(17, "A Remeter para Publicação", "fas fa-scroll", "",
				GrupoDeMarcadorEnum.AGUARDANDO_ANDAMENTO),

		//
		REMETIDO_PARA_PUBLICACAO(18, "Remetido para Publicação", "fas fa-scroll", "",
				GrupoDeMarcadorEnum.OUTROS),
		//
		A_REMETER_MANUALMENTE(19, "A Remeter Manualmente", "fas fa-scroll", "",
				GrupoDeMarcadorEnum.OUTROS),
		//
		PUBLICADO(20, "Publicado", "fas fa-scroll", "", GrupoDeMarcadorEnum.OUTROS),
		//
		PUBLICACAO_SOLICITADA(21, "Publicação Solicitada", "fas fa-scroll", "",
				GrupoDeMarcadorEnum.OUTROS),
		//
		DISPONIBILIZADO(22, "Disponibilizado", "fas fa-scroll", "",
				GrupoDeMarcadorEnum.OUTROS),

		//
		EM_TRANSITO(23, "Em Trâmite Físico", "fas fa-truck", "",
				GrupoDeMarcadorEnum.CAIXA_DE_SAIDA),
		//
		EM_TRANSITO_ELETRONICO(24, "Em Trâmite", "fas fa-shipping-fast", "",
				GrupoDeMarcadorEnum.AGUARDANDO_ANDAMENTO),
		//
		COMO_SUBSCRITOR(25, "Como Subscritor", "fas fa-key", "",
				GrupoDeMarcadorEnum.A_ASSINAR),
		//
		APENSADO(26, "Apensado", "fas fa-compress-arrows-alt", "",
				GrupoDeMarcadorEnum.AGUARDANDO_ANDAMENTO),
		//
		MARCADOR_COMO_GESTOR(27, "Gestor", "fas fa-tag", "",
				GrupoDeMarcadorEnum.ACOMPANHANDO),

		//
		MARCADOR_COMO_INTERESSADO(28, "Interessado", "fas fa-tag", "",
				GrupoDeMarcadorEnum.ACOMPANHANDO),
		//
		DESPACHO_PENDENTE_DE_ASSINATURA(29, "Despacho Pendente de Assinatura",
				"fas fa-key", "", GrupoDeMarcadorEnum.ALERTA),
		//
		ANEXO_PENDENTE_DE_ASSINATURA(30, "Anexo Pendente de Assinatura", "fas fa-key",
				"", GrupoDeMarcadorEnum.ALERTA),
		//
		SOBRESTADO(31, "Sobrestado", "fas fa-hourglass-start", "",
				GrupoDeMarcadorEnum.ACOMPANHANDO),
		//
		SEM_EFEITO(32, SigaMessages.getMessage("marcador.semEfeito.label"), "fas fa-power-off", "",
				GrupoDeMarcadorEnum.NENHUM),

		//
		ATIVO(36, "Ativo", "inbox", "",
				GrupoDeMarcadorEnum.AGUARDANDO_ANDAMENTO),
		//
		NOVO(37, "Novo", "inbox", "", GrupoDeMarcadorEnum.AGUARDANDO_ANDAMENTO),
		//
		POPULAR(38, "Popular", "inbox", "", GrupoDeMarcadorEnum.ALERTA),
		//
		REVISAR(39, "A Revisar", "fas fa-glasses", "",
				GrupoDeMarcadorEnum.AGUARDANDO_ANDAMENTO),
		//
		TOMAR_CIENCIA(40, "Tomar Ciência", "inbox", "",
				GrupoDeMarcadorEnum.AGUARDANDO_ANDAMENTO),

		//
		SOLICITACAO_A_RECEBER(41, "A Receber", "inbox", "",
				GrupoDeMarcadorEnum.CAIXA_DE_ENTRADA),
		//
		SOLICITACAO_EM_ANDAMENTO(42, "Em Andamento", "inbox", "",
				GrupoDeMarcadorEnum.AGUARDANDO_ANDAMENTO),
		//
		SOLICITACAO_FECHADO(43, "Fechado", "inbox", "",
				GrupoDeMarcadorEnum.OUTROS),
		//
		SOLICITACAO_PENDENTE(44, "Pendente", "inbox", "",
				GrupoDeMarcadorEnum.OUTROS),
		//
		SOLICITACAO_CANCELADO(45, "Cancelado", "inbox", "",
				GrupoDeMarcadorEnum.NENHUM),

		//
		SOLICITACAO_PRE_ATENDIMENTO(46, "Pré-atendimento", "inbox", "",
				GrupoDeMarcadorEnum.AGUARDANDO_ANDAMENTO),
		//
		SOLICITACAO_POS_ATENDIMENTO(47, "Pós-atendimento", "inbox", "",
				GrupoDeMarcadorEnum.AGUARDANDO_ANDAMENTO),
		//
		SOLICITACAO_COMO_CADASTRANTE(48, "Cadastrante", "inbox", "",
				GrupoDeMarcadorEnum.AGUARDANDO_ANDAMENTO),
		//
		SOLICITACAO_COMO_SOLICITANTE(49, "Solicitante", "inbox", "",
				GrupoDeMarcadorEnum.AGUARDANDO_ANDAMENTO),
		//
		RECOLHER_PARA_ARQUIVO_PERMANENTE(50, "Recolher Arq. Permante", "fas fa-inbox",
				"", GrupoDeMarcadorEnum.AGUARDANDO_ACAO_DE_TEMPORALIDADE),

		//
		TRANSFERIR_PARA_ARQUIVO_INTERMEDIARIO(51,
				"Transferir Arq. Intermediário", "fas fa-inbox", "",
				GrupoDeMarcadorEnum.AGUARDANDO_ACAO_DE_TEMPORALIDADE),
		//
		EM_EDITAL_DE_ELIMINACAO(52, "Em Edital de Eliminação", "fas fa-inbox", "",
				GrupoDeMarcadorEnum.AGUARDANDO_ANDAMENTO),
		//
		SOLICITACAO_FECHADO_PARCIAL(53, "Fechado Parcial", "inbox", "",
				GrupoDeMarcadorEnum.AGUARDANDO_ANDAMENTO),
		//
		SOLICITACAO_EM_CONTROLE_QUALIDADE(54, "Em Controle de Qualidade",
				"inbox", "", GrupoDeMarcadorEnum.AGUARDANDO_ANDAMENTO),
		//
		A_DEVOLVER(56, "A Devolver", "fas fa-exchange-alt", "",
				GrupoDeMarcadorEnum.AGUARDANDO_ANDAMENTO),

		//
		AGUARDANDO(57, "Aguardando", "fas fa-clock", "",
				GrupoDeMarcadorEnum.AGUARDANDO_ANDAMENTO),
		//
		A_DEVOLVER_FORA_DO_PRAZO(58, "A Devolver Fora do Prazo", "fas fa-exchange-alt", "",
				GrupoDeMarcadorEnum.ALERTA),
		//
		AGUARDANDO_DEVOLUCAO_FORA_DO_PRAZO(59,
				"Aguardando Devolução Fora Do Prazo", "fas fa-exchange-alt", "",
				GrupoDeMarcadorEnum.ALERTA),
		//
		PENDENTE_DE_ANEXACAO(60, "Pendente de Anexação", "fas fa-arrow-alt-circle-up", "",
				GrupoDeMarcadorEnum.ALERTA),
		//
		SOLICITACAO_EM_ELABORACAO(61, "Em Elaboração", "inbox", "",
				GrupoDeMarcadorEnum.AGUARDANDO_ANDAMENTO),

		//
		DOCUMENTO_ASSINADO_COM_SENHA(62, "Assinado com Senha", "fas fa-key", "",
				GrupoDeMarcadorEnum.NENHUM),
		//
		MOVIMENTACAO_ASSINADA_COM_SENHA(63, "Movimentação Ass. com Senha",
				"fas fa-key", "", GrupoDeMarcadorEnum.NENHUM),
		//
		MOVIMENTACAO_CONFERIDA_COM_SENHA(64,
				"Movimentação Autenticada com Senha", "fas fa-key", "",
				GrupoDeMarcadorEnum.NENHUM),
		//
		SOLICITACAO_FORA_DO_PRAZO(65, "Fora do Prazo", "inbox", "",
				GrupoDeMarcadorEnum.ALERTA),
		//
		SOLICITACAO_ATIVO(66, "Ativo", "inbox", "",
				GrupoDeMarcadorEnum.AGUARDANDO_ANDAMENTO),

		//
		PENDENTE_DE_COLABORACAO(67, "Pendente de Colaboração", "fas fa-users-cog", "",
				GrupoDeMarcadorEnum.CAIXA_DE_ENTRADA),
		//
		FINALIZAR_DOCUMENTO_COLABORATIVO(68,
				"Finalizar Documento Colaborativo", "fas fa-users-cog", "",
				GrupoDeMarcadorEnum.AGUARDANDO_ANDAMENTO),
		//
		SOLICITACAO_NECESSITA_PROVIDENCIA(69, "Necessita Providência", "inbox",
				"", GrupoDeMarcadorEnum.ALERTA),
		//
		COMO_EXECUTOR(70, "Executor", "inbox", "",
				GrupoDeMarcadorEnum.ACOMPANHANDO),
		//
		MARCADOR_PRONTO_PARA_ASSINAR(71, "Pronto para Assinar", "fas fa-check-circle", "",
				GrupoDeMarcadorEnum.PRONTO_PARA_ASSINAR),
		//
		MARCADOR_COMO_REVISOR(72, "Como Revisor", "fas fa-glasses", "",
				GrupoDeMarcadorEnum.A_REVISAR),
		//
		MARCADOR_PORTAL_TRANSPARENCIA(73, "Portal da Transparência", "fas fa-globe", "",
				GrupoDeMarcadorEnum.NENHUM),
		//
		URGENTE(1000, "Urgente", "fas fa-bomb", "", GrupoDeMarcadorEnum.ALERTA),

		//
		IDOSO(1001, "Idoso", "fas fa-user-tag", "", GrupoDeMarcadorEnum.ALERTA),

		//
		RETENCAO_INSS(1002, "Retenção de INSS", "fas fa-tag", "",
				GrupoDeMarcadorEnum.ALERTA),
		//
		PRIORITARIO(1003, "Prioritário", "fas fa-star", "", GrupoDeMarcadorEnum.ALERTA),
		//		
		RESTRICAO_ACESSO(1004, "Restrição de Acesso", "fas fa-user-secret", "", GrupoDeMarcadorEnum.ALERTA),
		//
		DOCUMENTO_ANALISADO(1005, "Documento Analisado", "fas fa-book-reader", "",
				GrupoDeMarcadorEnum.AGUARDANDO_ANDAMENTO),
		//
		COVID_19(1006, "COVID-19", "fas fa-tag", "",
				GrupoDeMarcadorEnum.NENHUM),
		//
		NOTA_EMPENHO(1007, "Nota de Empenho", "fas fa-tag", "",
				GrupoDeMarcadorEnum.NENHUM),
		//
		DEMANDA_JUDICIAL_BAIXA(1008, "Demanda Judicial Prioridade Baixa", "fas fa-tag", "",
                GrupoDeMarcadorEnum.ALERTA),
		//
		DEMANDA_JUDICIAL_MEDIA(1009, "Demanda Judicial Prioridade Média", "fas fa-tag", "",
                GrupoDeMarcadorEnum.ALERTA),
		//
		DEMANDA_JUDICIAL_ALTA(1010, "Demanda Judicial Prioridade Alta", "fas fa-tag", "",
                GrupoDeMarcadorEnum.ALERTA);

		private MarcadorEnum(int id, String nome, String icone,
				String descricao, GrupoDeMarcadorEnum grupo) {
			this.id = id;
			this.nome = nome;
			this.icone = icone;
			this.descricao = descricao;
			this.grupo = grupo;
		}

		public static MarcadorEnum getById(int id) {
			for (MarcadorEnum i : MarcadorEnum.values()) {
				if (i.id == id)
					return i;
			}
			return null;
		}
		public static List<Integer> getListIdByGrupo(String nomegrupo) {
			List<Integer> listMar = new ArrayList<Integer>();
			for(MarcadorEnum mar : MarcadorEnum.values()) {
				if (mar.grupo.nome.equals(nomegrupo)) {
						listMar.add(mar.id);
				}
			}
			return listMar;
		}
		
		public String getIcone() {
			return icone;
		}

		public int getId() {
			return id;
		}
		
		public String getNome() {
			if (SigaMessages.isSigaSP() && nome.equals("Como Subscritor")) {
				return "Responsável pela Assinatura";
			} else {
				return nome;
			}
		}

		private int id;
		private String nome;
		private String icone;
		private String descricao;
		private GrupoDeMarcadorEnum grupo;

	}
	
	public static class DocDados {
		List<MeM> listMeM;
		Date movUltimaDtIniMov;
		Date movUltimaDtFimMov;
		String movTramiteSiglaOrgao;
		String movTramiteSiglaLotacao;
		String movAnotacaoDescrMov;
		boolean isComposto;
	}

	public static class MeM {
		ExMarca marca;
		CpMarcador marcador;
		Date dtRef1;
		Date dtRef2;
	}

	private static List<MesaItem> listarReferencias(TipoDePainelEnum tipo,
			Map<ExMobil, DocDados> references, DpPessoa pessoa,
			DpLotacao unidade, Date currentDate, String grupoOrdem, boolean trazerAnotacoes, boolean ordemCrescenteData,
			List<Integer> marcasAIgnorar) {
		List<MesaItem> l = new ArrayList<>();
		final SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

		for (ExMobil mobil : references.keySet()) {
			MesaItem r = new MesaItem();
			r.tipo = "Documento";

			Date datahora = null;
			if (references.get(mobil).movUltimaDtIniMov != null)
				datahora = references.get(mobil).movUltimaDtIniMov;
			else
				datahora = mobil.getDoc().getDtAltDoc();
			r.datahora = datahora;
			r.datahoraDDMMYYYHHMM = df.format(datahora);
			r.tempoRelativo = Data.calcularTempoRelativo(datahora);

			r.codigo = mobil.getCodigoCompacto();
			r.sigla = mobil.getSigla();
			r.descr = mobil.doc().getDescrCurta(255).replace("\r", " ").replace("\f", " ").replace("\n", " ");
			if (references.get(mobil).isComposto) {
				r.tipoDoc = "Composto";
			} else {
				r.tipoDoc = "Avulso";
			}

			if (mobil.doc().getSubscritor() != null
					&& mobil.doc().getSubscritor().getLotacao() != null)

				if (SigaMessages.isSigaSP()) {
					if (references.get(mobil).movTramiteSiglaLotacao != null) {
						r.origem = references.get(mobil).movTramiteSiglaOrgao + " / "
								+ references.get(mobil).movTramiteSiglaLotacao;
					} else {
						r.origem = mobil.doc().getSubscritor().getLotacao()
								.getOrgaoUsuario() + " / "
								+ mobil.doc().getSubscritor().getLotacao()
										.getSigla();
					}
				} else {
					r.origem = mobil.doc().getSubscritor().getLotacao()
							.getSigla();
				}

			r.dataDevolucao = "ocultar";

			if (references.get(mobil).movUltimaDtFimMov != null
					&& references.get(mobil).movUltimaDtFimMov != null) {
				Date dataMovimentacao;
				dataMovimentacao = references.get(mobil).movUltimaDtFimMov;

				Date dataHoje = new Timestamp(System.currentTimeMillis());

				int dias;
				if (dataHoje.compareTo(dataMovimentacao) <= 0) {
					dias = Integer
							.parseInt(((dataMovimentacao.getTime() - dataHoje.getTime() - +3600000L) / 86400000L)
									+ "");

					String qtdDias = Prop.get("/siga.devolucao.dias");
					
					if(qtdDias == null){
						qtdDias = "5";
					}
					
					if (dias <= Integer.parseInt(qtdDias)) {
						r.dataDevolucao = "alerta";
					}

				} else {
					r.dataDevolucao = "atrasado";
				}

			}

			r.grupoOrdem = grupoOrdem;
			
			if (trazerAnotacoes && mobil.getDnmUltimaAnotacao() != null && !mobil.getDnmUltimaAnotacao().replace(" ", "").equals("")) { 
				r.anotacao = mobil.getDnmUltimaAnotacao().replace("\r\f", "<br/>").replace("\n", "<br/>");
			}

			r.list = new ArrayList<Marca>();

			for (MeM tag : references.get(mobil).listMeM) {
				if (tag.marca.getDtIniMarca() != null
						&& tag.marca.getDtIniMarca().getTime() > currentDate
								.getTime())
					continue;
				if (tag.marca.getDtFimMarca() != null
						&& tag.marca.getDtFimMarca().getTime() < currentDate
								.getTime())
					continue;

				Marca t = new Marca();
				MarcadorEnum mar = MarcadorEnum.getById(tag.marcador
						.getIdInicial().intValue());
				if (mar != null) {
					t.nome = mar.getNome();
					t.icone = mar.getIcone();
				} else {
					t.nome = tag.marcador.getDescrMarcador();
					t.icone = tag.marcador.getIdIcone().getCodigoFontAwesome();
					t.cor = tag.marcador.getIdCor().getDescricao();
				}
				t.titulo = Data
						.calcularTempoRelativo(tag.marca.getDtIniMarca());

				if (tag.marca.getDpPessoaIni() != null) {
					t.pessoa = tag.marca.getDpPessoaIni().getIdInicial().toString();

				}
				if (tag.marca.getDpLotacaoIni() != null) {
					t.lotacao = tag.marca.getDpLotacaoIni().getIdInicial().toString();
				}
				
				t.inicio = tag.marca.getDtIniMarca();
				t.termino = tag.marca.getDtFimMarca();
				if (tag.dtRef1 != null)
					t.ref1 = Data.calcularTempoRelativoEmDias(tag.dtRef1);
				if (tag.dtRef2 != null)
					t.ref2 = Data.calcularTempoRelativoEmDias(tag.dtRef2);
				if(tag.marca.getCpMarcador().isDemandaJudicial()) {
					t.nome += " até " + tag.marca.getExMobil().getDoc().getMobilGeral()
							.getExMovimentacaoSet().stream() //
							.filter(mov -> mov.getExTipoMovimentacao().getId()
									.equals(ExTipoMovimentacao.TIPO_MOVIMENTACAO_MARCACAO))
							.filter(mov -> !mov.isCancelada()) //
							.filter(mov -> tag.marca.getCpMarcador().equals(mov.getMarcador())) //
							.map(ExMovimentacao::getDtFimMovDDMMYY) //
							.findFirst().orElse("[indeterminado]");
				}

				r.list.add(t);
				if (pessoa != null && tag.marca.getDpPessoaIni() != null) {
					if (pessoa.getIdInicial().equals(
							tag.marca.getDpPessoaIni().getId()))
						t.daPessoa = true;
					else
						t.deOutraPessoa = tag.marca.getDpPessoaIni() != null;
				}
				if (unidade != null
						&& tag.marca.getDpLotacaoIni() != null
						&& unidade.getId().equals(
								tag.marca.getDpLotacaoIni().getId()))
					t.daLotacao = true;
			}
			l.add(r);
		}

		Collections.sort(l, new Comparator<MesaItem>() {
			@Override
			public int compare(MesaItem o1, MesaItem o2) {
				int i;
				if (ordemCrescenteData) {
					i = o1.datahora.compareTo(o2.datahora);
				} else {
					i = o2.datahora.compareTo(o1.datahora);
				}
				if (i != 0)
					return i;
				return 0;
			}
		});
		return l;
	}

	public static List<GrupoItem> getContadores(ExDao dao, DpPessoa titular, DpLotacao lotaTitular, 
			Map<String, SelGrupo> selGrupos, boolean exibeLotacao, 
			List<Integer> marcasAIgnorar) throws Exception {
		List<GrupoItem> gruposMesa = new ArrayList<GrupoItem>();
		gruposMesa = montaGruposUsuario(selGrupos);
		List<Object[]> l = dao.consultarTotaisPorMarcador(titular, lotaTitular, gruposMesa, 
				exibeLotacao, marcasAIgnorar);
		if (l == null)
			return gruposMesa;
		
		for (GrupoItem gItem : gruposMesa) {
			gItem.grupoCounterUser = 0L;
			gItem.grupoCounterLota = 0L;
			for (Object[] reference : l) {
				if (gItem.grupoOrdem.equals(reference[0].toString())) {
					if (reference[1] != null)  
						gItem.grupoCounterUser = Long.valueOf(reference[1].toString());
					if (reference[2] != null)  
						gItem.grupoCounterLota = Long.valueOf(reference[2].toString());
				}
			}
			if (exibeLotacao) {
				gItem.grupoCounterAtivo = gItem.grupoCounterLota;
			} else {
				gItem.grupoCounterAtivo = gItem.grupoCounterUser;
			}
		}
		return gruposMesa;
	}

	public static List<GrupoItem> getMesa(ExDao dao, DpPessoa titular,
			DpLotacao lotaTitular, Map<String, SelGrupo> selGrupos, List<Mesa2.GrupoItem> gruposMesa, 
			boolean exibeLotacao, boolean trazerAnotacoes, boolean trazerComposto, boolean ordemCrescenteData,
			List<Integer> marcasAIgnorar) throws Exception {
//		long tempoIni = System.nanoTime();
		Date dtNow = dao.consultarDataEHoraDoServidor();

		List<Object[]> l = dao.listarMobilsPorMarcas(titular,
				lotaTitular, exibeLotacao, ordemCrescenteData, marcasAIgnorar);

		Map<ExMobil, DocDados> map = new HashMap<>();
		// Cria hashmap para pesquisa do mobil nos grupos
		Map<Long, List<Long>> hashMobGrp = l.stream()
		        .collect(Collectors.groupingBy(k -> (Long.valueOf(((ExMobil) k[2]).getIdMobil())), 
		                Collectors.mapping(v -> (Long.valueOf(((CpMarcador) v[1]).getGrupoMarcador())), 
		                		Collectors.toList())));
		
		List<Long> listIdMobil = new ArrayList<Long>();
		Long idMob = 0L;

		if (l != null && l.size() > 0) {
			// Para cada grupo da mesa, pesquisa no resultado da query
			for (GrupoItem gItem : gruposMesa) {
				Object[] reference = l.get(0);
				ExMarca marca = (ExMarca) reference[0];
				CpMarcador marcador = (CpMarcador) reference[1];
				ExMobil mobil = (ExMobil) reference[2];
				idMob = mobil.getIdMobil();
				
				for (Integer i = 0; i < l.size(); i++) {
					reference = l.get(i);
					// Inclui o mobil no grupo da mesa
					mobil = (ExMobil) reference[2];
					idMob = mobil.getIdMobil();
					// Se for TMP e o grupo nao for Em Elaboracao, nao deve mostrar no grupo (só GOVSP).
					if (SigaMessages.isSigaSP()
							&& reference[4] == null 
							&& !gItem.grupoNome.equals(GrupoDeMarcadorEnum.EM_ELABORACAO.getNome())) 
						continue;
					// Se for do grupo Aguardando Andamento e tiver marcador da caixa de entrada, nao inclui
					if (gItem.grupoNome.equals(GrupoDeMarcadorEnum.AGUARDANDO_ANDAMENTO.getNome())
							&& temMarcador(hashMobGrp, idMob, Integer.valueOf(GrupoDeMarcadorEnum.CAIXA_DE_ENTRADA.id))) 
						continue;
					
					if (temMarcador(hashMobGrp, idMob, Integer.valueOf(gItem.grupoOrdem)) && !map.containsKey(mobil)) {
						// Se o mobil possui um marcador do grupo e ele ainda nao foi incluido,
						// inclui junto com as outras marcas que estao no resultado da query
						for (Integer i2 = 0; i2 < l.size(); i2++) {  
							reference = l.get(i2);
							mobil = (ExMobil) reference[2];
							if (mobil.getIdMobil() == idMob) {
								marca = (ExMarca) reference[0];
								marcador = (CpMarcador) reference[1];
								if (!map.containsKey(mobil)) {
									// Mobil ainda nao foi incluido no grupo, inclui
									if (listIdMobil.size() < gItem.grupoQtd) {
										DocDados docDados = new DocDados();
										MeM mm = new MeM();
										mm.marca = marca;
										mm.marcador = marcador;
										mm.dtRef1 = (Date) reference[5];
										mm.dtRef2 = (Date) reference[6];
										docDados.listMeM = new ArrayList<MeM>();
										docDados.listMeM.add(mm);
										map.put(mobil, docDados);
										listIdMobil.add(mobil.getId());
									} else {
										break;
									} 
								} else {
									// Mobil ja foi incluido no grupo, inclui so a marca no mobil
									MeM mm = new MeM();
									mm.marca = marca;
									mm.marcador = marcador;
									mm.dtRef1 = (Date) reference[5];
									mm.dtRef2 = (Date) reference[6];
									map.get(mobil).listMeM.add(mm);
								}
							}
						}
					}
				}
				if (!map.isEmpty()) {
					Integer iMobs = 0;
					while (iMobs < listIdMobil.size()) {
						Integer iMobsFim = listIdMobil.size();
						if ( iMobs + 1000 < iMobsFim )
							iMobsFim = iMobs + 1000;
						List<Object[]> refs = dao.listarMovimentacoesMesa(
								listIdMobil.subList(iMobs, iMobsFim), trazerComposto);
			
						for (Object[] ref : refs) {
							incluiMovimentacoesMesa(map, ref);
						}
						iMobs = iMobsFim;
					}
					gItem.grupoDocs = Mesa2.listarReferencias(TipoDePainelEnum.UNIDADE, map, titular,
							titular.getLotacao(), dtNow, gItem.grupoOrdem, trazerAnotacoes, ordemCrescenteData, marcasAIgnorar);
					map = new HashMap<>();
					listIdMobil = new ArrayList<Long>();
				}
			}
		}
//		long tempoTotal = System.nanoTime() - tempoIni;
//		System.out.println("getMesa: " + tempoTotal
//		/ 1000000 + " ms ==> ");

		return gruposMesa;
	}

	private static void incluiMovimentacoesMesa(Map<ExMobil, DocDados> map, Object[] ref) {
		ExMobil mob = (ExMobil) ref[0];
		ExMovimentacao movUltima = null;
		ExMovimentacao movTramite = null;
		if (ref[2] != null)
			movUltima = (ExMovimentacao) ref[2];
		if (ref[3] != null)
			movTramite = (ExMovimentacao) ref[3];
		if (map.containsKey(mob)) {
			DocDados docDados = map.get(mob); 
			if (ref[1] != null) {
				docDados.isComposto = (((Integer) ref[1]) == 1);
			}
			if (movUltima != null) {
				docDados.movUltimaDtIniMov = movUltima.getDtIniMov();
				docDados.movUltimaDtFimMov = movUltima.getDtFimMov();
			}
			if (movTramite != null) {
				docDados.movTramiteSiglaLotacao = movTramite.getLotacao().getSigla();
				docDados.movTramiteSiglaOrgao = movTramite.getLotacao().getOrgaoUsuario().getSigla();
			}
		}
	}

	private static boolean temMarcador(Map<Long, List<Long>> hashMobGrp, Long idMobil, Integer grupoId) {
		// Pesquisa na lista retornada pela query se um determinado mobil (idMobil) tem
		// algum marcador do grupoId informado. Devolve true se existir.
		return ((List<Long>) hashMobGrp
				.get(idMobil))
				.contains(grupoId.longValue());
	}
	public static void carregaGruposBase() {
		if (gruposBase == null) {
			gruposBase = new ArrayList<GrupoItem>();
			for (GrupoDeMarcadorEnum gEnum : GrupoDeMarcadorEnum.values()) {
				if (gEnum.visible) {
					GrupoItem grpItem = new GrupoItem();
					grpItem.grupo = gEnum.name();
					grpItem.grupoOrdem = Integer.toString(gEnum.id);
					grpItem.grupoNome = gEnum.nome;
					grpItem.grupoIcone = gEnum.icone;
					grpItem.grupoCollapsed = gEnum.collapsed;
					if (gEnum.collapsed) {
						grpItem.grupoQtd = 0L;
					} else {
						grpItem.grupoQtd = 15L;
					}
					grpItem.grupoQtdPag = 15L;
					grpItem.grupoHide = gEnum.hide;
					grpItem.grupoMarcadores = MarcadorEnum.getListIdByGrupo(gEnum.nome);
					gruposBase.add(grpItem);
				}
			}
		}
	}
	
	public static List<GrupoItem> montaGruposUsuario(Map<String, SelGrupo> selGrupos) {
		carregaGruposBase();
		List<String> ordemGrupos = new ArrayList<String>(); 
		List<GrupoItem> lGrupo = new ArrayList<GrupoItem>();
		if (selGrupos != null && selGrupos.size() > 0) {
			for (Map.Entry<String, SelGrupo> selGrupo : selGrupos.entrySet())
				ordemGrupos.add(GrupoDeMarcadorEnum.getByNome(selGrupo.getKey()).id.toString());
		} else {
			ordemGrupos = GrupoDeMarcadorEnum.getIdList();
		}
		for (GrupoItem grp : gruposBase) {
			for (String ordemGrupo : ordemGrupos) {
				if (grp.grupoOrdem.equals(ordemGrupo)) {
					GrupoItem grpItem = new GrupoItem();
					grpItem.grupo = grp.grupo;
					grpItem.grupoOrdem = grp.grupoOrdem;
					grpItem.grupoNome = grp.grupoNome;
					grpItem.grupoIcone = grp.grupoIcone;
					if (selGrupos != null && selGrupos.size() > 0) {
						grpItem.grupoCollapsed = selGrupos.get(grp.grupoNome).grupoCollapsed;
						grpItem.grupoHide = selGrupos.get(grp.grupoNome).grupoHide;
						grpItem.grupoQtdPag = selGrupos.get(grp.grupoNome).grupoQtdPag;
						grpItem.grupoQtd = selGrupos.get(grp.grupoNome).grupoQtd;
					} else {
						grpItem.grupoCollapsed = grp.grupoCollapsed;
						grpItem.grupoHide = grp.grupoHide;
						grpItem.grupoQtdPag = grp.grupoQtdPag;
						grpItem.grupoQtd = grp.grupoQtd;
					}
					grpItem.grupoMarcadores = grp.grupoMarcadores;
					lGrupo.add(grpItem);
				}
			}
		}
		return lGrupo;
	}
}
