package org.jfrd.webapp.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AtmLogGridEntry {

    @JsonProperty("atmlog_id")
    private Long id;    

    @JsonProperty("atmlog_ip")
	private String ip;

    @JsonProperty("atmlog_luno")
	private String luno;

    @JsonProperty("atmlog_message_class")
	private String messageClass;

    @JsonProperty("atmlog_atm_request_dt")
	private String atmRequestDt;

    @JsonProperty("atmlog_card")
	private String card;

    @JsonProperty("atmlog_amount")
	private BigDecimal amount;

	public AtmLogGridEntry() {
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
	 * @return the messageClass
	 */
	public String getMessageClass() {
		return messageClass;
	}

	/**
	 * @param messageClass the messageClass to set
	 */
	public void setMessageClass(String messageClass) {
		this.messageClass = messageClass;
	}

	/**
	 * @return the atmRequestDt
	 */
	public String getAtmRequestDt() {
		return atmRequestDt;
	}

	/**
	 * @param atmRequestDt the atmRequestDt to set
	 */
	public void setAtmRequestDt(String atmRequestDt) {
		this.atmRequestDt = atmRequestDt;
	}

	/**
	 * @return the card
	 */
	public String getCard() {
		return card;
	}

	/**
	 * @param card the card to set
	 */
	public void setCard(String card) {
		this.card = card;
	}

	/**
	 * @return the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
