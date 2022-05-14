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

import com.openpojo.business.BusinessIdentity;
import com.openpojo.business.annotation.BusinessKey ;

/**
 * The persistent class for the atms database table.
 * 
 */
@Entity
@Table(name="atms")
public class ATM implements Serializable {
	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "atm_id", updatable = false, nullable = false)
    private Long id;    

	@Column(name="atm_last_trn_log_id")
	private Long lastTrnLogId;

	@BusinessKey(caseSensitive = false, required = true)
	@Column(name="atm_ip", length=40)
	private String ip;

	@Column(name="atm_luno", length=9)
	private String luno;

	@Column(name="atm_name", length=255)
	private String name;

	@Column(name="atm_protocol", length=10)
	private String protocol;

	@Column(name="atm_aceptor_id", length=15)
	private String aceptorId;

	@Column(name="atm_status", length=20)
	private String status;

	@Column(name="atm_active")
	private Byte active;

	@Column(name="atm_group", length=255)
	private String group;

	@Column(name="atm_region", length=255)
	private String region;

	@Column(name="atm_brand", length=20)
	private String brand;
	
	@Column(name="atm_model", length=255)
	private String model;

	@Column(name="atm_address1", length=255)
	private String address1;

	@Column(name="atm_address2", length=255)
	private String address2;

	@Column(name="atm_city", length=255)
	private String city;

	@Column(name="atm_state", length=255)
	private String state;

	@Column(name="atm_province", length=255)
	private String province;

	@Column(name="atm_country", length=3)
	private String country;

	@Column(name="atm_zip", length=15)
	private String zip;

	@Column(name="atm_contact", length=255)
	private String contact;

	@Column(name="atm_phone", length=20)
	private String phone;

	@Column(name="atm_master_key", length=64)
	private String masterKey;

	@Column(name="atm_communications_key", length=64)
	private String communicationsKey;

	@Column(name="atm_mac_key", length=64)
	private String macKey;

	@Column(name="atm_config_id", length=4)
	private String configId;

	@Column(name="atm_institution_code", length=11)
	private String institutionCode;

	@Column(name="atm_merch_type", length=4)
	private String merchType;

	@Column(name="atm_network_data", length=255)
	private String networkData;

	@Column(name="atm_point_serv_data", length=255)
	private String pointServData;

	@Column(name="atm_pos_entry_mode", length=3)
	private String posEntryMode;

	@Column(name="atm_terminal_id", length=8)
	private String terminalId;

	@Column(name="atm_timezone", length=20)
	private String timezone;

	@Column(name="atm_trn_ser_num")
	private Short trnSerNum;

	public ATM() {
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
	 * @return the lastTrnLogId
	 */
	public Long getLastTrnLogId() {
		return lastTrnLogId;
	}

	/**
	 * @param lastTrnLogId the lastTrnLogId to set
	 */
	public void setLastTrnLogId(Long lastTrnLogId) {
		this.lastTrnLogId = lastTrnLogId;
	}

	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the protocol
	 */
	public String getProtocol() {
		return protocol;
	}

	/**
	 * @param protocol the protocol to set
	 */
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	/**
	 * @return the aceptorId
	 */
	public String getAceptorId() {
		return aceptorId;
	}

	/**
	 * @param aceptorId the aceptorId to set
	 */
	public void setAceptorId(String aceptorId) {
		this.aceptorId = aceptorId;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the active
	 */
	public Byte getActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(Byte active) {
		this.active = active;
	}

	/**
	 * @return the group
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * @param group the group to set
	 */
	public void setGroup(String group) {
		this.group = group;
	}

	/**
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * @param region the region to set
	 */
	public void setRegion(String region) {
		this.region = region;
	}

	/**
	 * @return the brand
	 */
	public String getBrand() {
		return brand;
	}

	/**
	 * @param brand the brand to set
	 */
	public void setBrand(String brand) {
		this.brand = brand;
	}

	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * @return the address1
	 */
	public String getAddress1() {
		return address1;
	}

	/**
	 * @param address1 the address1 to set
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	/**
	 * @return the address2
	 */
	public String getAddress2() {
		return address2;
	}

	/**
	 * @param address2 the address2 to set
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
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
	 * @return the province
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * @param province the province to set
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the zip
	 */
	public String getZip() {
		return zip;
	}

	/**
	 * @param zip the zip to set
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}

	/**
	 * @return the contact
	 */
	public String getContact() {
		return contact;
	}

	/**
	 * @param contact the contact to set
	 */
	public void setContact(String contact) {
		this.contact = contact;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the masterKey
	 */
	public String getMasterKey() {
		return masterKey;
	}

	/**
	 * @param masterKey the masterKey to set
	 */
	public void setMasterKey(String masterKey) {
		this.masterKey = masterKey;
	}

	/**
	 * @return the communicationsKey
	 */
	public String getCommunicationsKey() {
		return communicationsKey;
	}

	/**
	 * @param communicationsKey the communicationsKey to set
	 */
	public void setCommunicationsKey(String communicationsKey) {
		this.communicationsKey = communicationsKey;
	}

	/**
	 * @return the macKey
	 */
	public String getMacKey() {
		return macKey;
	}

	/**
	 * @param macKey the macKey to set
	 */
	public void setMacKey(String macKey) {
		this.macKey = macKey;
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
	 * @return the institutionCode
	 */
	public String getInstitutionCode() {
		return institutionCode;
	}

	/**
	 * @param institutionCode the institutionCode to set
	 */
	public void setInstitutionCode(String institutionCode) {
		this.institutionCode = institutionCode;
	}

	/**
	 * @return the merchType
	 */
	public String getMerchType() {
		return merchType;
	}

	/**
	 * @param merchType the merchType to set
	 */
	public void setMerchType(String merchType) {
		this.merchType = merchType;
	}

	/**
	 * @return the networkData
	 */
	public String getNetworkData() {
		return networkData;
	}

	/**
	 * @param networkData the networkData to set
	 */
	public void setNetworkData(String networkData) {
		this.networkData = networkData;
	}

	/**
	 * @return the pointServData
	 */
	public String getPointServData() {
		return pointServData;
	}

	/**
	 * @param pointServData the pointServData to set
	 */
	public void setPointServData(String pointServData) {
		this.pointServData = pointServData;
	}

	/**
	 * @return the posEntryMode
	 */
	public String getPosEntryMode() {
		return posEntryMode;
	}

	/**
	 * @param posEntryMode the posEntryMode to set
	 */
	public void setPosEntryMode(String posEntryMode) {
		this.posEntryMode = posEntryMode;
	}

	/**
	 * @return the terminalId
	 */
	public String getTerminalId() {
		return terminalId;
	}

	/**
	 * @param terminalId the terminalId to set
	 */
	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	/**
	 * @return the trnSerNum
	 */
	public Short getTrnSerNum() {
		return trnSerNum;
	}

	/**
	 * @param trnSerNum the trnSerNum to set
	 */
	public void setTrnSerNum(Short trnSerNum) {
		this.trnSerNum = trnSerNum;
	}

	/**
	 * @return the timezone
	 */
	public String getTimezone() {
		return timezone;
	}

	/**
	 * @param timezone the timezone to set
	 */
	public void setTimezone(String timezone) {
		this.timezone = timezone;
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

	public String getAddress()
	{
		final int ADDRESS1_LENGTH = 22;
		final int CITY_LENGTH = 9;
		
		String spaces = "                         ";   
		StringBuffer sb = new StringBuffer();

		if (this.address1.length() < ADDRESS1_LENGTH)
		{
			sb.append(this.address1);
   		    sb.append(spaces.substring(0, ADDRESS1_LENGTH - this.address1.length() + 1 ) );
		}
		else
		{
			sb.append(this.address1.substring(0, ADDRESS1_LENGTH) );
			sb.append(" ");
		}

		if (this.city.length() < CITY_LENGTH)
		{
			sb.append(this.city);
   		    sb.append(spaces.substring(0, CITY_LENGTH - this.city.length() + 1 ) );
		}
		else
		{
			sb.append(this.city.substring(0, CITY_LENGTH) );
			sb.append(" ");
		}
			

		sb.append(this.country);

		return sb.toString();
	}

}