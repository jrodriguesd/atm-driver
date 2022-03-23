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
import javax.persistence.*;


/**
 * The persistent class for the atmconfigs database table.
 * 
 */
@Entity
@Table(name="atmconfigs")
public class Atmconfig implements Serializable {
	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "atmcnf_id", updatable = false, nullable = false)
    private Long id;    

	@Column(name="atmcnf_configid")
	private String atmcnf_configid;

	@Column(name="atmcnf_description")
	private String atmcnf_description;

	@Column(name="atmcnf_language639")
	private String atmcnf_language639;

	@Column(name="atmcnf_languageatm")
	private String atmcnf_languageatm;

	@Column(name="atmcnf_languageindex")
	private byte atmcnf_languageindex;

	@Column(name="atmcnf_screengroupbase")
	private short atmcnf_screengroupbase;

	public Atmconfig() {
	}

	/**
	 * @return the atmcnf_configid
	 */
	public String getAtmcnf_configid() {
		return atmcnf_configid;
	}

	/**
	 * @param atmcnf_configid the atmcnf_configid to set
	 */
	public void setAtmcnf_configid(String atmcnf_configid) {
		this.atmcnf_configid = atmcnf_configid;
	}

	/**
	 * @return the atmcnf_description
	 */
	public String getAtmcnf_description() {
		return atmcnf_description;
	}

	/**
	 * @param atmcnf_description the atmcnf_description to set
	 */
	public void setAtmcnf_description(String atmcnf_description) {
		this.atmcnf_description = atmcnf_description;
	}

	/**
	 * @return the atmcnf_language639
	 */
	public String getAtmcnf_language639() {
		return atmcnf_language639;
	}

	/**
	 * @param atmcnf_language639 the atmcnf_language639 to set
	 */
	public void setAtmcnf_language639(String atmcnf_language639) {
		this.atmcnf_language639 = atmcnf_language639;
	}

	/**
	 * @return the atmcnf_languageatm
	 */
	public String getAtmcnf_languageatm() {
		return atmcnf_languageatm;
	}

	/**
	 * @param atmcnf_languageatm the atmcnf_languageatm to set
	 */
	public void setAtmcnf_languageatm(String atmcnf_languageatm) {
		this.atmcnf_languageatm = atmcnf_languageatm;
	}

	/**
	 * @return the atmcnf_languageindex
	 */
	public byte getAtmcnf_languageindex() {
		return atmcnf_languageindex;
	}

	/**
	 * @param atmcnf_languageindex the atmcnf_languageindex to set
	 */
	public void setAtmcnf_languageindex(byte atmcnf_languageindex) {
		this.atmcnf_languageindex = atmcnf_languageindex;
	}

	/**
	 * @return the atmcnf_screengroupbase
	 */
	public short getAtmcnf_screengroupbase() {
		return atmcnf_screengroupbase;
	}

	/**
	 * @param atmcnf_screengroupbase the atmcnf_screengroupbase to set
	 */
	public void setAtmcnf_screengroupbase(short atmcnf_screengroupbase) {
		this.atmcnf_screengroupbase = atmcnf_screengroupbase;
	}

	@Override
	public String toString() {
		return "Atmconfig [id=" + id + ", atmcnf_configid=" + atmcnf_configid + ", atmcnf_description="
				+ atmcnf_description + ", atmcnf_language639=" + atmcnf_language639 + ", atmcnf_languageatm="
				+ atmcnf_languageatm + ", atmcnf_languageindex=" + atmcnf_languageindex + ", atmcnf_screengroupbase="
				+ atmcnf_screengroupbase + "]";
	}

}