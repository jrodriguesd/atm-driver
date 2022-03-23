package org.jpos.atmc.model;

import java.io.Serializable;
import javax.persistence.*;

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

	@Column(name="atm_aceptor_id")
	private String aceptorId;

	@Column(name="atm_active")
	private byte active;

	@Column(name="atm_address1")
	private String address1;

	@Column(name="atm_address2")
	private String address2;

	@Column(name="atm_city")
	private String city;

	@Column(name="atm_communications_key")
	private String communicationsKey;

	@Column(name="atm_config_id")
	private String configId;

	@Column(name="atm_contact")
	private String contact;

	@Column(name="atm_country")
	private String country;

	@Column(name="atm_currency_code")
	private String currencyCode;

	@Column(name="atm_institution_code")
	private String institutionCode;

	@Column(name="atm_ip")
	private String ip;

	@Column(name="atm_luno")
	private String luno;

	@Column(name="atm_marca")
	private String marca;

	@Column(name="atm_master_key")
	private String masterKey;

	@Column(name="atm_merch_type")
	private String merchType;

	@Column(name="atm_model")
	private String model;

	@Column(name="atm_name")
	private String name;

	@Column(name="atm_network_data")
	private String networkData;

	@Column(name="atm_phone")
	private String phone;

	@Column(name="atm_point_serv_data")
	private String pointServData;

	@Column(name="atm_pos_entry_mode")
	private String posEntryMode;

	@Column(name="atm_protocol")
	private String protocol;

	@Column(name="atm_province")
	private String province;

	@Column(name="atm_state")
	private String state;

	@Column(name="atm_status")
	private String status;

	@Column(name="atm_terminal_id")
	private String terminalId;

	@Column(name="atm_trn_ser_num")
	private short trnSerNum;

	@Column(name="atm_zip")
	private String zip;

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
	 * @return the active
	 */
	public byte getActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(byte active) {
		this.active = active;
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
	 * @return the currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * @param currencyCode the currencyCode to set
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
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
	 * @return the marca
	 */
	public String getMarca() {
		return marca;
	}

	/**
	 * @param marca the marca to set
	 */
	public void setMarca(String marca) {
		this.marca = marca;
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
	public short getTrnSerNum() {
		return trnSerNum;
	}

	/**
	 * @param trnSerNum the trnSerNum to set
	 */
	public void setTrnSerNum(short trnSerNum) {
		this.trnSerNum = trnSerNum;
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
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
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