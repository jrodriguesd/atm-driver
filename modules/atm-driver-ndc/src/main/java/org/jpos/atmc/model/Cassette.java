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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.openpojo.business.BusinessIdentity;
import com.openpojo.business.annotation.BusinessKey;

@Entity
@Table(name="cassettes")
public class Cassette implements Serializable 
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="cass_id", unique=true, nullable=false)
    private Long id;    

	@Column(name="cass_begin", nullable=false)
	private Integer begin;

	@Column(name="cass_curr_number", nullable=false, length=3)
	private String number;

	@Column(name="cass_curr_type", nullable=false, length=2)
	private String type;

	@Column(name="cass_denomination", nullable=false)
	private Integer denomination;

	@Column(name="cass_dispensed", nullable=false)
	private Integer dispensed;

	@Column(name="cass_rejected", nullable=false)
	private Integer rejected;

	@BusinessKey(caseSensitive = false, required = true)
	@Column(name="cass_instaled", nullable=false, length=1)
	private Boolean isInstaled;

	@BusinessKey(caseSensitive = false, required = true)
	@Column(name="cass_luno", nullable=false, length=9)
	private String luno;

	@BusinessKey(caseSensitive = false, required = true)
	@Column(name="cass_position", nullable=false, length=1)
	private Integer position;

	public Cassette() 
	{
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
	 * @return the begin
	 */
	public Integer getBegin() {
		return begin;
	}

	/**
	 * @param begin the begin to set
	 */
	public void setBegin(Integer begin) {
		this.begin = begin;
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
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the denomination
	 */
	public Integer getDenomination() {
		return denomination;
	}

	/**
	 * @param denomination the denomination to set
	 */
	public void setDenomination(Integer denomination) {
		this.denomination = denomination;
	}

	/**
	 * @return the dispensed
	 */
	public Integer getDispensed() {
		return dispensed;
	}

	/**
	 * @param dispensed the dispensed to set
	 */
	public void setDispensed(Integer dispensed) {
		this.dispensed = dispensed;
	}

	/**
	 * @return the rejected
	 */
	public Integer getRejected() {
		return rejected;
	}

	/**
	 * @param rejected the rejected to set
	 */
	public void setRejected(Integer rejected) {
		this.rejected = rejected;
	}

	/**
	 * @return the isInstaled
	 */
	public Boolean getIsInstaled() {
		return isInstaled;
	}

	/**
	 * @param isInstaled the isInstaled to set
	 */
	public void setIsInstaled(Boolean isInstaled) {
		this.isInstaled = isInstaled;
	}

	/**
	 * @return the luno
	 */
	public String getLuno() {
		return luno;
	}

	/**
	 * @param luno the luno to set
	 */
	public void setLuno(String luno) {
		this.luno = luno;
	}

	/**
	 * @return the position
	 */
	public Integer getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Integer position) {
		this.position = position;
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
