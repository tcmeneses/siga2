package br.gov.jfrj.siga.feature.converter.entity.vraptor;

import java.io.Serializable;

public interface ConvertableEntity<T extends Serializable> extends Serializable {

	public abstract T getId();
	
	public void setId(T id);

}
