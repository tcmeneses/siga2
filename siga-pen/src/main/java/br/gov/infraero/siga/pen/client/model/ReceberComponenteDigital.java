
package br.gov.infraero.siga.pen.client.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="parametrosParaRecebimentoDeComponenteDigital" type="{http://pen.planejamento.gov.br/interoperabilidade/soap/v2/tramite}parametrosParaRecebimentoDeComponenteDigital"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "parametrosParaRecebimentoDeComponenteDigital"
})
@XmlRootElement(name = "receberComponenteDigital")
public class ReceberComponenteDigital {

    @XmlElement(required = true)
    protected ParametrosParaRecebimentoDeComponenteDigital parametrosParaRecebimentoDeComponenteDigital;

    /**
     * Gets the value of the parametrosParaRecebimentoDeComponenteDigital property.
     * 
     * @return
     *     possible object is
     *     {@link ParametrosParaRecebimentoDeComponenteDigital }
     *     
     */
    public ParametrosParaRecebimentoDeComponenteDigital getParametrosParaRecebimentoDeComponenteDigital() {
        return parametrosParaRecebimentoDeComponenteDigital;
    }

    /**
     * Sets the value of the parametrosParaRecebimentoDeComponenteDigital property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParametrosParaRecebimentoDeComponenteDigital }
     *     
     */
    public void setParametrosParaRecebimentoDeComponenteDigital(ParametrosParaRecebimentoDeComponenteDigital value) {
        this.parametrosParaRecebimentoDeComponenteDigital = value;
    }

}