/*
 * This file is part of atm-driver.
 * Copyright (C) 2021-2022
 *
 * atm-driver is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 *
 * atm-driver is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with atm-driver. If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * @author <a href="mailto:j@rodriguesd.org">Jose Rodrigues D.</a>
 */
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