package br.gov.jfrj.siga.dp;

import java.util.Date;
import java.util.Objects;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.gov.jfrj.siga.cp.CpIdentidade;
import br.gov.jfrj.siga.cp.model.HistoricoAuditavel;
import br.gov.jfrj.siga.model.Assemelhavel;
import br.gov.jfrj.siga.sinc.lib.SincronizavelSuporte;

public class DpMarcadorLotacao extends CpMarcador implements HistoricoAuditavel {

	private static final long serialVersionUID = -4041778777498104976L;

	private String cor;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "HIS_IDC_INI")
	private CpIdentidade hisIdcIni;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "HIS_IDC_FIM")
	private CpIdentidade hisIdcFim;

	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name = "DATA_FIM_LOT", length = 19)
//	@Desconsiderar
	private Date dataFimMarcadorLotacao;

	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name = "DATA_INI_LOT", nullable = false, length = 19)
//	@Desconsiderar
	private Date dataInicioMarcadorLotacao;

	private DpLotacao lotacao;

	// CONSTRUTORES - INÍCIO

	public DpMarcadorLotacao() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Construtor temporário.
	 * 
	 * @param id
	 * @param nome
	 * @param lotacao
	 * @param cor
	 * @param dtFim
	 */
	@Deprecated
	public DpMarcadorLotacao(Long id, String nome, DpLotacao lotacao, String cor, Date dtFim) {
		super.setDescrMarcador(nome);
		super.setIdMarcador(id);
		this.lotacao = lotacao;
		this.cor = cor;
		this.setHisDtFim(dtFim);
	}

	// CONSTRUTORES - FINAL

	// GETTERS & SETTERS - INÍCIO

	@Override
	public Long getIdInicial() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getId() {
		return super.getIdMarcador();
	}

	@Override
	public void setId(Long id) {
		super.setIdMarcador(id);
	}

	@Override
	public Long getHisIdIni() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setHisIdIni(Long hisIdIni) {
		// TODO Auto-generated method stub

	}

	@Override
	public Date getHisDtIni() {
		return getDataInicioMarcadorLotacao();
	}

	@Override
	public void setHisDtIni(Date hisDtIni) {
		setDataInicioMarcadorLotacao(hisDtIni);
	}

	@Override
	public Date getHisDtFim() {
		return getDataFimMarcadorLotacao();
	}

	@Override
	public void setHisDtFim(Date hisDtFim) {
		setDataFimMarcadorLotacao(hisDtFim);
	}

	@Override
	public Integer getHisAtivo() {
		return Objects.isNull(getDataFimMarcadorLotacao()) ? 1 : 0;
	}

	@Override
	public void setHisAtivo(Integer hisAtivo) {
		// TODO Auto-generated method stub

	}

	public CpIdentidade getHisIdcIni() {
		return hisIdcIni;
	}

	public void setHisIdcIni(CpIdentidade hisIdcIni) {
		this.hisIdcIni = hisIdcIni;
	}

	public CpIdentidade getHisIdcFim() {
		return hisIdcFim;
	}

	public void setHisIdcFim(CpIdentidade hisIdcFim) {
		this.hisIdcFim = hisIdcFim;
	}

	public Date getDataFimMarcadorLotacao() {
		return dataFimMarcadorLotacao;
	}

	public void setDataFimMarcadorLotacao(Date dataFimMarcadorLotacao) {
		this.dataFimMarcadorLotacao = dataFimMarcadorLotacao;
	}

	public Date getDataInicioMarcadorLotacao() {
		return dataInicioMarcadorLotacao;
	}

	public void setDataInicioMarcadorLotacao(Date dataInicioMarcadorLotacao) {
		this.dataInicioMarcadorLotacao = dataInicioMarcadorLotacao;
	}

	public DpLotacao getLotacao() {
		return lotacao;
	}

	public void setLotacao(DpLotacao lotacao) {
		this.lotacao = lotacao;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	// GETTERS & SETTERS - FINAL

	@Override
	public boolean equivale(Object other) {
		if (other == null)
			return false;
		return this.getIdInicial().longValue() == ((DpMarcadorLotacao) other).getIdInicial().longValue();
	}

	@Override
	public boolean semelhante(Assemelhavel obj, int profundidade) {
		return SincronizavelSuporte.semelhante(this, obj, profundidade);
	}

	
	
}
