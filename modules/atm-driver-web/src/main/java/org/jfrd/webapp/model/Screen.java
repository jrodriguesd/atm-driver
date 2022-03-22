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
 * The persistent class for the screens database table.
 * 
 */
@Entity
@Table(name="screens")
public class Screen implements Serializable 
{
	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "scr_id", updatable = false, nullable = false)
    private Long id;    

	@Column(name="scr_config_id")
	private String scr_config_id;

	@Lob
	@Column(name="scr_data")
	private String scr_data;

	@Column(name="scr_desc")
	private String scr_desc;

	@Column(name="scr_number")
	private String scr_number;

	public Screen() 
	{
	}

	/**
	 * @return the scr_config_id
	 */
	public String getScr_config_id() {
		return scr_config_id;
	}

	/**
	 * @param scr_config_id the scr_config_id to set
	 */
	public void setScr_config_id(String scr_config_id) {
		this.scr_config_id = scr_config_id;
	}

	/**
	 * @return the scr_data
	 */
	public String getScr_data() {
		return scr_data;
	}

	/**
	 * @param scr_data the scr_data to set
	 */
	public void setScr_data(String scr_data) {
		this.scr_data = scr_data;
	}

	/**
	 * @return the scr_desc
	 */
	public String getScr_desc() {
		return scr_desc;
	}

	/**
	 * @param scr_desc the scr_desc to set
	 */
	public void setScr_desc(String scr_desc) {
		this.scr_desc = scr_desc;
	}

	/**
	 * @return the scr_number
	 */
	public String getScr_number() {
		return scr_number;
	}

	/**
	 * @param scr_number the scr_number to set
	 */
	public void setScr_number(String scr_number) {
		this.scr_number = scr_number;
	}

	@Override
	public String toString() 
	{
		return "Screen [scr_config_id=" + scr_config_id + ", scr_data=" + scr_data + ", scr_desc=" + scr_desc
				+ ", scr_number=" + scr_number + "]";
	}

}
