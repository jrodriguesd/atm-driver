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
 * The persistent class for the states database table.
 * 
 */
@Entity
@Table(name="states")
@NamedQuery(name="State.findAll", query="SELECT s FROM State s")
public class State implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="std_id")
    @JsonProperty("std_id")
    private Long id;    

	@BusinessKey(caseSensitive = false, required = true)
	@Column(name="std_config_id")
    @JsonProperty("std_config_id")
	private String configId;

	@Column(name="std_desc")
    @JsonProperty("std_desc")
	private String desc;

	@BusinessKey(caseSensitive = false, required = true)
	@Column(name="std_number")
    @JsonProperty("std_number")
	private String number;

	@Column(name="std_s1")
    @JsonProperty("std_s1")
	private String s1;

	@Column(name="std_s2")
    @JsonProperty("std_s2")
	private String s2;

	@Column(name="std_s3")
    @JsonProperty("std_s3")
	private String s3;

	@Column(name="std_s4")
    @JsonProperty("std_s4")
	private String s4;

	@Column(name="std_s5")
    @JsonProperty("std_s5")
	private String s5;

	@Column(name="std_s6")
    @JsonProperty("std_s6")
	private String s6;

	@Column(name="std_s7")
    @JsonProperty("std_s7")
	private String s7;

	@Column(name="std_s8")
    @JsonProperty("std_s8")
	private String s8;

	@Column(name="std_type")
    @JsonProperty("std_type")
	private String type;

	public State() {
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
	 * @return the s1
	 */
	public String getS1() {
		return s1;
	}

	/**
	 * @param s1 the s1 to set
	 */
	public void setS1(String s1) {
		this.s1 = s1;
	}

	/**
	 * @return the s2
	 */
	public String getS2() {
		return s2;
	}

	/**
	 * @param s2 the s2 to set
	 */
	public void setS2(String s2) {
		this.s2 = s2;
	}

	/**
	 * @return the s3
	 */
	public String getS3() {
		return s3;
	}

	/**
	 * @param s3 the s3 to set
	 */
	public void setS3(String s3) {
		this.s3 = s3;
	}

	/**
	 * @return the s4
	 */
	public String getS4() {
		return s4;
	}

	/**
	 * @param s4 the s4 to set
	 */
	public void setS4(String s4) {
		this.s4 = s4;
	}

	/**
	 * @return the s5
	 */
	public String getS5() {
		return s5;
	}

	/**
	 * @param s5 the s5 to set
	 */
	public void setS5(String s5) {
		this.s5 = s5;
	}

	/**
	 * @return the s6
	 */
	public String getS6() {
		return s6;
	}

	/**
	 * @param s6 the s6 to set
	 */
	public void setS6(String s6) {
		this.s6 = s6;
	}

	/**
	 * @return the s7
	 */
	public String getS7() {
		return s7;
	}

	/**
	 * @param s7 the s7 to set
	 */
	public void setS7(String s7) {
		this.s7 = s7;
	}

	/**
	 * @return the s8
	 */
	public String getS8() {
		return s8;
	}

	/**
	 * @param s8 the s8 to set
	 */
	public void setS8(String s8) {
		this.s8 = s8;
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