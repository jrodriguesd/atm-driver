package org.jpos.atmc.model;

import java.io.Serializable;
import javax.persistence.*;

import com.openpojo.business.BusinessIdentity;
import com.openpojo.business.annotation.BusinessKey ;


/**
 * The persistent class for the receipts database table.
 * 
 */
@Entity
@Table(name="receipts")
public class Receipt implements Serializable 
{
	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rcp_id", updatable = false, nullable = false)
    private Long id;    

	@BusinessKey(caseSensitive = false, required = true)
	@Column(name="rcp_code")
	private String code;

	@BusinessKey(caseSensitive = false, required = true)
	@Column(name="rcp_config_id")
	private String configId;

	@Column(name="rcp_description")
	private String description;

	@Lob
	@Column(name="rcp_template")
	private String template;

	public Receipt() {
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the configId
	 */
	public String getConfigId() {
		return configId;
	}

	/**
	 * @param configId the configId to set
	 */
	public void setConfigId(String configId) {
		this.configId = configId;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the template
	 */
	public String getTemplate() {
		return template;
	}

	/**
	 * @param template the template to set
	 */
	public void setTemplate(String template) {
		this.template = template;
	}

	@Override
	public int hashCode() {
	    return BusinessIdentity.getHashCode(this);
	}

	@Override
	public boolean equals(final Object obj) {
	    return BusinessIdentity.areEqual(this, obj);
	}

	@Override
	public String toString() {
	    return BusinessIdentity.toString(this);
	}	

}