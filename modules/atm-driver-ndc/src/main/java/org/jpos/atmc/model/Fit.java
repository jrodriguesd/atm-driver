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

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.openpojo.business.BusinessIdentity;
import com.openpojo.business.annotation.BusinessKey;

/**
 * The persistent class for the fits database table.
 * 
 */
@Entity
@Table(name="fits")
public class Fit implements Serializable 
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="fit_id", unique=true, nullable=false)
    @JsonProperty("fit_id")
    private Long id;    

	@Column(name="fit_algoidx", nullable=false, length=2)
    @JsonProperty("fit_algoidx")
	private String algoidx;

	@Column(name="fit_bin_prefix", nullable=false, length=10)
    @JsonProperty("fit_bin_prefix")
	private String binPrefix;

	@BusinessKey(caseSensitive = false, required = true)
	@Column(name="fit_config_id", nullable=false, length=4)
    @JsonProperty("fit_config_id")
	private String configId;

	@Column(name="fit_decimaltab", nullable=false, length=16)
    @JsonProperty("fit_decimaltab")
	private String decimaltab;

	@Column(name="fit_desc", nullable=false, length=250)
    @JsonProperty("fit_desc")
	private String desc;

	@Column(name="fit_encpinkey", nullable=false, length=16)
    @JsonProperty("fit_encpinkey")
	private String encpinkey;

	@Column(name="fit_idxrefpoints", nullable=false, length=6)
    @JsonProperty("fit_idxrefpoints")
	private String idxrefpoints;

	@Column(name="fit_indirectstateidx", nullable=false, length=2)
    @JsonProperty("fit_indirectstateidx")
	private String indirectstateidx;

	@Column(name="fit_langcodeidx", nullable=false, length=2)
    @JsonProperty("fit_langcodeidx")
	private String langcodeidx;

	@Column(name="fit_localpinchecklen", nullable=false, length=2)
    @JsonProperty("fit_localpinchecklen")
	private String localpinchecklen;

	@Column(name="fit_maxpinlen", nullable=false, length=2)
    @JsonProperty("fit_maxpinlen")
	private String maxpinlen;

	@BusinessKey(caseSensitive = false, required = true)
	@Column(name="fit_number", nullable=false, length=3)
    @JsonProperty("fit_number")
	private String number;

	@Column(name="fit_panlen", nullable=false, length=2)
    @JsonProperty("fit_panlen")
	private String panlen;

	@Column(name="fit_panlocidx", nullable=false, length=2)
    @JsonProperty("fit_panlocidx")
	private String panlocidx;

	@Column(name="fit_panpad", nullable=false, length=2)
    @JsonProperty("fit_panpad")
	private String panpad;

	@Column(name="fit_pinblkformat", length=2)
    @JsonProperty("fit_pinblkformat")
	private String pinblkformat;

	@Column(name="fit_pinoffsetidx", nullable=false, length=2)
    @JsonProperty("fit_pinoffsetidx")
	private String pinoffsetidx;

	@Column(name="fit_pinpad", nullable=false, length=2)
    @JsonProperty("fit_pinpad")
	private String pinpad;

	@Column(name="fit_pinretrycount", nullable=false, length=2)
    @JsonProperty("fit_pinretrycount")
	private String pinretrycount;

	public Fit() {
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
	 * @return the algoidx
	 */
	public String getAlgoidx() {
		return algoidx;
	}

	/**
	 * @param algoidx the algoidx to set
	 */
	public void setAlgoidx(String algoidx) {
		this.algoidx = algoidx;
	}

	/**
	 * @return the binPrefix
	 */
	public String getBinPrefix() {
		return binPrefix;
	}

	/**
	 * @param binPrefix the binPrefix to set
	 */
	public void setBinPrefix(String binPrefix) {
		this.binPrefix = binPrefix;
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
	 * @return the decimaltab
	 */
	public String getDecimaltab() {
		return decimaltab;
	}

	/**
	 * @param decimaltab the decimaltab to set
	 */
	public void setDecimaltab(String decimaltab) {
		this.decimaltab = decimaltab;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * @return the encpinkey
	 */
	public String getEncpinkey() {
		return encpinkey;
	}

	/**
	 * @param encpinkey the encpinkey to set
	 */
	public void setEncpinkey(String encpinkey) {
		this.encpinkey = encpinkey;
	}

	/**
	 * @return the idxrefpoints
	 */
	public String getIdxrefpoints() {
		return idxrefpoints;
	}

	/**
	 * @param idxrefpoints the idxrefpoints to set
	 */
	public void setIdxrefpoints(String idxrefpoints) {
		this.idxrefpoints = idxrefpoints;
	}

	/**
	 * @return the indirectstateidx
	 */
	public String getIndirectstateidx() {
		return indirectstateidx;
	}

	/**
	 * @param indirectstateidx the indirectstateidx to set
	 */
	public void setIndirectstateidx(String indirectstateidx) {
		this.indirectstateidx = indirectstateidx;
	}

	/**
	 * @return the langcodeidx
	 */
	public String getLangcodeidx() {
		return langcodeidx;
	}

	/**
	 * @param langcodeidx the langcodeidx to set
	 */
	public void setLangcodeidx(String langcodeidx) {
		this.langcodeidx = langcodeidx;
	}

	/**
	 * @return the localpinchecklen
	 */
	public String getLocalpinchecklen() {
		return localpinchecklen;
	}

	/**
	 * @param localpinchecklen the localpinchecklen to set
	 */
	public void setLocalpinchecklen(String localpinchecklen) {
		this.localpinchecklen = localpinchecklen;
	}

	/**
	 * @return the maxpinlen
	 */
	public String getMaxpinlen() {
		return maxpinlen;
	}

	/**
	 * @param maxpinlen the maxpinlen to set
	 */
	public void setMaxpinlen(String maxpinlen) {
		this.maxpinlen = maxpinlen;
	}

	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * @return the panlen
	 */
	public String getPanlen() {
		return panlen;
	}

	/**
	 * @param panlen the panlen to set
	 */
	public void setPanlen(String panlen) {
		this.panlen = panlen;
	}

	/**
	 * @return the panlocidx
	 */
	public String getPanlocidx() {
		return panlocidx;
	}

	/**
	 * @param panlocidx the panlocidx to set
	 */
	public void setPanlocidx(String panlocidx) {
		this.panlocidx = panlocidx;
	}

	/**
	 * @return the panpad
	 */
	public String getPanpad() {
		return panpad;
	}

	/**
	 * @param panpad the panpad to set
	 */
	public void setPanpad(String panpad) {
		this.panpad = panpad;
	}

	/**
	 * @return the pinblkformat
	 */
	public String getPinblkformat() {
		return pinblkformat;
	}

	/**
	 * @param pinblkformat the pinblkformat to set
	 */
	public void setPinblkformat(String pinblkformat) {
		this.pinblkformat = pinblkformat;
	}

	/**
	 * @return the pinoffsetidx
	 */
	public String getPinoffsetidx() {
		return pinoffsetidx;
	}

	/**
	 * @param pinoffsetidx the pinoffsetidx to set
	 */
	public void setPinoffsetidx(String pinoffsetidx) {
		this.pinoffsetidx = pinoffsetidx;
	}

	/**
	 * @return the pinpad
	 */
	public String getPinpad() {
		return pinpad;
	}

	/**
	 * @param pinpad the pinpad to set
	 */
	public void setPinpad(String pinpad) {
		this.pinpad = pinpad;
	}

	/**
	 * @return the pinretrycount
	 */
	public String getPinretrycount() {
		return pinretrycount;
	}

	/**
	 * @param pinretrycount the pinretrycount to set
	 */
	public void setPinretrycount(String pinretrycount) {
		this.pinretrycount = pinretrycount;
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
