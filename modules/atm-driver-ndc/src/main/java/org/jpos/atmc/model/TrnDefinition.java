/*
 * This file is part of atm-driver.
 * Copyright (C) 2021-2022
 *
 * atm-driver is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 *
 * Bisq is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with atm-driver. If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * Returns days to New Year.
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

@Entity
@Table(name="trndefs")
public class TrnDefinition 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "trndef_id", updatable = false, nullable = false)
    private Long id;    

	@BusinessKey(caseSensitive = false, required = true)
	@Column(name = "trndef_config_id", updatable = true, nullable = false)
    private String configId;

	@BusinessKey(caseSensitive = false, required = true)
	@Column(name = "trndef_atm_trn_code", updatable = true, nullable = false)
	private String atmTrnCode;

	@Column(name = "trndef_description", updatable = true, nullable = false)
	private String  description;

	@Column(name = "trndef_language639", updatable = true, nullable = false)
    private String  language639;

	@Column(name = "trndef_language3166", updatable = true, nullable = false)
	private String  language3166;

	@Column(name = "trndef_mti", updatable = true, nullable = false)
	private String MTI;

	@Column(name = "trndef_procesingcode", updatable = true, nullable = false)
    private String procesingCode;

	@Column(name = "trndef_state", updatable = true, nullable = false)
    private String  state;

	@Column(name = "trndef_screen", updatable = true, nullable = false)
	private String screen;

	@Column(name = "trndef_screen_update", updatable = true, nullable = false)
	private String screenUpdate;

	@Column(name = "trndef_respMaskScr", updatable = true, nullable = false)
	private String respMaskScr;

	@Column(name = "trndef_receipt_code", updatable = true, nullable = false)
	private String receiptCode;
	
	/**
	 * 
	 */
	public TrnDefinition() 
	{
		super();
	}

	/**
	 * @return the id
	 */
	public Long getId() 
	{
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) 
	{
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
	 * @return the atmTrnCode
	 */
	public String getAtmTrnCode() {
		return atmTrnCode;
	}

	/**
	 * @param atmTrnCode the atmTrnCode to set
	 */
	public void setAtmTrnCode(String atmTrnCode) {
		this.atmTrnCode = atmTrnCode;
	}

	/**
	 * @return the description
	 */
	public String getDescription() 
	{
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) 
	{
		this.description = description;
	}

	/**
	 * @return the language639
	 */
	public String getLanguage639() 
	{
		return language639;
	}

	/**
	 * @param language639 the language639 to set
	 */
	public void setLanguage639(String language639) 
	{
		this.language639 = language639;
	}

	/**
	 * @return the language3166
	 */
	public String getLanguage3166() 
	{
		return language3166;
	}

	/**
	 * @param language3166 the language3166 to set
	 */
	public void setLanguage3166(String language3166) 
	{
		this.language3166 = language3166;
	}

	/**
	 * @return the mTI
	 */
	public String getMTI() 
	{
		return MTI;
	}

	/**
	 * @param mTI the mTI to set
	 */
	public void setMTI(String mTI) 
	{
		MTI = mTI;
	}

	/**
	 * @return the procesingCode
	 */
	public String getProcesingCode() 
	{
		return procesingCode;
	}

	/**
	 * @param procesingCode the procesingCode to set
	 */
	public void setProcesingCode(String procesingCode) 
	{
		this.procesingCode = procesingCode;
	}

	/**
	 * @return the state
	 */
	public String getState() 
	{
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) 
	{
		this.state = state;
	}
	
	/**
	 * @return the screen
	 */
	public String getScreen() 
	{
		return screen;
	}

	/**
	 * @param screen the screen to set
	 */
	public void setScreen(String screen) 
	{
		this.screen = screen;
	}

	/**
	 * @return the screenUpdate
	 */
	public String getScreenUpdate() 
	{
		return screenUpdate;
	}

	/**
	 * @param screenUpdate the screenUpdate to set
	 */
	public void setScreenUpdate(String screenUpdate) 
	{
		this.screenUpdate = screenUpdate;
	}

	/**
	 * @return the respMaskScr
	 */
	public String getRespMaskScr() {
		return respMaskScr;
	}

	/**
	 * @param respMaskScr the respMaskScr to set
	 */
	public void setRespMaskScr(String respMaskScr) {
		this.respMaskScr = respMaskScr;
	}

	/**
	 * @return the receiptCode
	 */
	public String getReceiptCode() {
		return receiptCode;
	}

	/**
	 * @param receiptCode the receiptCode to set
	 */
	public void setReceiptCode(String receiptCode) {
		this.receiptCode = receiptCode;
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
