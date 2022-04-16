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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.openpojo.business.BusinessIdentity;
import com.openpojo.business.annotation.BusinessKey ;

/**
 * @author User
 *
 */
@Entity
@Table(name="isoerr2atms")
public class IsoError2ATM 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "isoerr2atm_id", updatable = false, nullable = false)
    private Long id;    

	@BusinessKey(caseSensitive = false, required = true)
	@Column(name = "isoerr2atm_config_id", updatable = true, nullable = false)
    private String configId;

	@BusinessKey(caseSensitive = false, required = true)
	@Column(name = "isoerr2atm_isoresp", updatable = true, nullable = false)
	private Integer isoResp;

	@Column(name = "isoerr2atm_description", updatable = true, nullable = false)
	private String  description;

	@BusinessKey(caseSensitive = false, required = true)
	@Column(name = "isoerr2atm_language639", updatable = true, nullable = false)
    private String  language639;

	@Column(name = "isoerr2atm_language3166", updatable = true, nullable = false)
	private String  language3166;

	@Column(name = "isoerr2atm_state", updatable = true, nullable = false)
    private String  state;

	@Column(name = "isoerr2atm_screen_receipt", updatable = true, nullable = false)
    private String  screenReceipt;

	@Column(name = "isoerr2atm_screen_no_receipt", updatable = true, nullable = false)
    private String  screenNoReceipt;

	@Column(name = "isoerr2atm_screen_card_retained", updatable = true, nullable = false)
    private String  screenCardReteined;

	@Column(name = "isoerr2atm_screen_stmt_delivered", updatable = true, nullable = false)
    private String  screenStmtDelivered;
	
	/**
	 * 
	 */
	public IsoError2ATM() 
	{
		super();
		// TODO Auto-generated constructor stub
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
	 * @return the isoResp
	 */
	public Integer getIsoResp() {
		return isoResp;
	}

	/**
	 * @param isoResp the isoResp to set
	 */
	public void setIsoResp(Integer isoResp) {
		this.isoResp = isoResp;
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
	 * @return the language639
	 */
	public String getLanguage639() {
		return language639;
	}

	/**
	 * @param language639 the language639 to set
	 */
	public void setLanguage639(String language639) {
		this.language639 = language639;
	}

	/**
	 * @return the language3166
	 */
	public String getLanguage3166() {
		return language3166;
	}

	/**
	 * @param language3166 the language3166 to set
	 */
	public void setLanguage3166(String language3166) {
		this.language3166 = language3166;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the screenReceipt
	 */
	public String getScreenReceipt() {
		return screenReceipt;
	}

	/**
	 * @param screenReceipt the screenReceipt to set
	 */
	public void setScreenReceipt(String screenReceipt) {
		this.screenReceipt = screenReceipt;
	}

	/**
	 * @return the screenNoReceipt
	 */
	public String getScreenNoReceipt() {
		return screenNoReceipt;
	}

	/**
	 * @param screenNoReceipt the screenNoReceipt to set
	 */
	public void setScreenNoReceipt(String screenNoReceipt) {
		this.screenNoReceipt = screenNoReceipt;
	}

	/**
	 * @return the screenCardReteined
	 */
	public String getScreenCardReteined() {
		return screenCardReteined;
	}

	/**
	 * @param screenCardReteined the screenCardReteined to set
	 */
	public void setScreenCardReteined(String screenCardReteined) {
		this.screenCardReteined = screenCardReteined;
	}

	/**
	 * @return the screenStmtDelivered
	 */
	public String getScreenStmtDelivered() {
		return screenStmtDelivered;
	}

	/**
	 * @param screenStmtDelivered the screenStmtDelivered to set
	 */
	public void setScreenStmtDelivered(String screenStmtDelivered) {
		this.screenStmtDelivered = screenStmtDelivered;
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
