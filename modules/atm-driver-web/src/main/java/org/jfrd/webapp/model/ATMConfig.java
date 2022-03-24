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
package org.jfrd.webapp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="atmconfigs")
public class ATMConfig implements Serializable 
{
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "atmcnf_id", updatable = false, nullable = false)
    @JsonProperty("atmcnf_id")
    private Long id;    

    @Column(name="atmcnf_configid")
    @JsonProperty("atmcnf_configid")
    private String  configId;

    @Column(name="atmcnf_description")
    @JsonProperty("atmcnf_description")
    private String  description;

    @Column(name="atmcnf_languageindex")
    @JsonProperty("atmcnf_languageindex")
    private int     languageIndex;
    
    @Column(name="atmcnf_languageatm")
    @JsonProperty("atmcnf_languageatm")
    private char    languageATM;
    
    @Column(name="atmcnf_language639")
    @JsonProperty("atmcnf_language639")
    private String  language639;
    
    @Column(name="atmcnf_screengroupbase")
    @JsonProperty("atmcnf_screengroupbase")
    private int     screenGroupBase;
    
	public ATMConfig() 
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
	public String getConfigID() 
	{
		return configId;
	}

	/**
	 * @param configId the configId to set
	 */
	public void setConfigID(String configId) 
	{
		this.configId = configId;
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
	 * @return the languageIndex
	 */
	public int getLanguageIndex() 
	{
		return languageIndex;
	}

	/**
	 * @param languageIndex the languageIndex to set
	 */
	public void setLanguageIndex(int languageIndex) 
	{
		this.languageIndex = languageIndex;
	}

	/**
	 * @return the languageATM
	 */
	public char getLanguageATM() {
		return languageATM;
	}

	/**
	 * @param languageATM the languageATM to set
	 */
	public void setLanguageATM(char languageATM) {
		this.languageATM = languageATM;
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
	 * @return the screenGroupBase
	 */
	public int getScreenGroupBase() 
	{
		return screenGroupBase;
	}

	/**
	 * @param screenGroupBase the screenGroupBase to set
	 */
	public void setScreenGroupBase(int screenGroupBase) 
	{
		this.screenGroupBase = screenGroupBase;
	}

	@Override
	public String toString() 
	{
		return "ATMConfig [configID=" + configId + ", languageIndex=" + languageIndex + ", languageATM="
				+ languageATM + ", language639=" + language639 + ", screenGroupBase=" + screenGroupBase + "]";
	}

}
