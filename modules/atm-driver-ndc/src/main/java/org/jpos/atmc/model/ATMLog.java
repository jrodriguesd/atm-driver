package org.jpos.atmc.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.persistence.Lob;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.openpojo.business.BusinessIdentity;
import com.openpojo.business.annotation.BusinessKey ;


@Entity
@Table(name="atmlog")
public class ATMLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="atmlog_id", unique=true, nullable=false)
    @JsonProperty("atmlog_id")
    private Long id;    

	@Column(name="atmlog_amount", precision=10, scale=2)
    @JsonProperty("atmlog_amount")
	private BigDecimal amount;

	@Lob
	@Column(name="atmlog_atm_confirmation")
    @JsonProperty("atmlog_atm_confirmation")
	private String atmConfirmation;

	@Column(name="atmlog_atm_confirmation_dt")
    @JsonProperty("atmlog_atm_confirmation_dt")
	// @JsonSerialize(using = InstantSerializer.class)
	private Instant atmConfirmationDt;

	@Lob
	@Column(name="atmlog_atm_reply")
    @JsonProperty("atmlog_atm_reply")
	private String atmReply;

	@Column(name="atmlog_atm_reply_dt")
    @JsonProperty("atmlog_atm_reply_dt")
	private Instant atmReplyDt;

	@Lob
	@Column(name="atmlog_atm_request", nullable=false)
    @JsonProperty("atmlog_atm_request")
	private String atmRequest;

	@Column(name="atmlog_atm_request_dt", nullable=false)
    @JsonProperty("atmlog_atm_request_dt")
	private Instant atmRequestDt;

	@Column(name="atmlog_card", length=20)
    @JsonProperty("atmlog_card")
	private String card;

	@Column(name="atmlog_currency_code", length=3)
    @JsonProperty("atmlog_currency_code")
	private String currencyCode;

	@BusinessKey(caseSensitive = false, required = true)
	@Column(name="atmlog_ip", nullable=false, length=20)
    @JsonProperty("atmlog_ip")
	private String ip;

	@Lob
	@Column(name="atmlog_iso_confirmation_reply")
    @JsonProperty("atmlog_iso_confirmation_reply")
	private String isoConfirmationReply;

	@Column(name="atmlog_iso_confirmation_reply_dt")
    @JsonProperty("atmlog_iso_confirmation_reply_dt")
	private Instant isoConfirmationReplyDt;

	@Lob
	@Column(name="atmlog_iso_confirmation_request")
    @JsonProperty("atmlog_iso_confirmation_request")
	private String isoConfirmationRequest;

	@Column(name="atmlog_iso_confirmation_request_dt")
    @JsonProperty("atmlog_iso_confirmation_request_dt")
	private Instant isoConfirmationRequestDt;

	@Lob
	@Column(name="atmlog_iso_reply")
    @JsonProperty("atmlog_iso_reply")
	private String isoReply;

	@Column(name="atmlog_iso_reply_dt")
    @JsonProperty("atmlog_iso_reply_dt")
	private Instant isoReplyDt;

	@Lob
	@Column(name="atmlog_iso_request")
    @JsonProperty("atmlog_iso_request")
	private String isoRequest;

	@Column(name="atmlog_iso_request_dt")
    @JsonProperty("atmlog_iso_request_dt")
	private Instant isoRequestDt;

	@Column(name="atmlog_luno", nullable=false, length=9)
    @JsonProperty("atmlog_luno")
	private String luno;

	@Column(name="atmlog_message_class", nullable=false, length=2)
    @JsonProperty("atmlog_message_class")
	private String messageClass;

	@Column(name="atmlog_op_code", length=20)
    @JsonProperty("atmlog_op_code")
	private String opCode;

	@Column(name="atmlog_op_desc", length=20)
    @JsonProperty("atmlog_op_desc")
	private String opDescription;
	
	@Column(name="atmlog_timezone", length=20)
    @JsonProperty("atmlog_timezone")
	private String timezone;

	public ATMLog() {
	}
	
	private final static String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss.SSS a VV";
	
	private String instantToString(Instant instant)
	{
		if (instant != null)
		{
		    ZonedDateTime zonedDateTime = instant.atZone(ZoneId.of(this.getTimezone()));
		    return DateTimeFormatter.ofPattern(DATE_FORMAT).format(zonedDateTime);
		}
		return null;
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

	/**
	 * @return the atmConfirmation
	 */
	public String getAtmConfirmation() {
		return atmConfirmation;
	}

	/**
	 * @param atmConfirmation the atmConfirmation to set
	 */
	public void setAtmConfirmation(String atmConfirmation) {
		this.atmConfirmation = atmConfirmation;
	}

	/**
	 * @return the atmConfirmationDt
	 */
//	public Instant getAtmConfirmationDt() {
//		return atmConfirmationDt;
//	}
	
	public String getAtmConfirmationDt() {
		return instantToString(atmConfirmationDt);
	}

	/**
	 * @param atmConfirmationDt the atmConfirmationDt to set
	 */
	public void setAtmConfirmationDt(Instant atmConfirmationDt) {
		this.atmConfirmationDt = atmConfirmationDt;
	}

	/**
	 * @return the atmReply
	 */
	public String getAtmReply() {
		return atmReply;
	}

	/**
	 * @param atmReply the atmReply to set
	 */
	public void setAtmReply(String atmReply) {
		this.atmReply = atmReply;
	}

	/**
	 * @return the atmReplyDt
	 */
//	public Instant getAtmReplyDt() {
//		return atmReplyDt;
//	}

	public String getAtmReplyDt() {
		return instantToString(atmReplyDt);
	}
	
	/**
	 * @param atmReplyDt the atmReplyDt to set
	 */
	public void setAtmReplyDt(Instant atmReplyDt) {
		this.atmReplyDt = atmReplyDt;
	}

	/**
	 * @return the atmRequest
	 */
	public String getAtmRequest() {
		return atmRequest;
	}

	/**
	 * @param atmRequest the atmRequest to set
	 */
	public void setAtmRequest(String atmRequest) {
		this.atmRequest = atmRequest;
	}

	/**
	 * @return the atmRequestDt
	 */
//	public Instant getAtmRequestDt() {
//		return atmRequestDt;
//	}

	public String getAtmRequestDt() {
		return instantToString(atmRequestDt);
	}

	/**
	 * @param atmRequestDt the atmRequestDt to set
	 */
	public void setAtmRequestDt(Instant atmRequestDt) {
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
	 * @return the isoConfirmationReply
	 */
	public String getIsoConfirmationReply() {
		return isoConfirmationReply;
	}

	/**
	 * @param isoConfirmationReply the isoConfirmationReply to set
	 */
	public void setIsoConfirmationReply(String isoConfirmationReply) {
		this.isoConfirmationReply = isoConfirmationReply;
	}

	/**
	 * @return the isoConfirmationReplyDt
	 */
//	public Instant getIsoConfirmationReplyDt() {
//		return isoConfirmationReplyDt;
//	}

	public String getIsoConfirmationReplyDt() {
		return instantToString(isoConfirmationReplyDt);
	}

	/**
	 * @param isoConfirmationReplyDt the isoConfirmationReplyDt to set
	 */
	public void setIsoConfirmationReplyDt(Instant isoConfirmationReplyDt) {
		this.isoConfirmationReplyDt = isoConfirmationReplyDt;
	}

	/**
	 * @return the isoConfirmationRequest
	 */
	public String getIsoConfirmationRequest() {
		return isoConfirmationRequest;
	}

	/**
	 * @param isoConfirmationRequest the isoConfirmationRequest to set
	 */
	public void setIsoConfirmationRequest(String isoConfirmationRequest) {
		this.isoConfirmationRequest = isoConfirmationRequest;
	}

	/**
	 * @return the isoConfirmationRequestDt
	 */
//	public Instant getIsoConfirmationRequestDt() {
//		return isoConfirmationRequestDt;
//	}

	public String getIsoConfirmationRequestDt() {
		return instantToString(isoConfirmationRequestDt);
	}

	/**
	 * @param isoConfirmationRequestDt the isoConfirmationRequestDt to set
	 */
	public void setIsoConfirmationRequestDt(Instant isoConfirmationRequestDt) {
		this.isoConfirmationRequestDt = isoConfirmationRequestDt;
	}

	/**
	 * @return the isoReply
	 */
	public String getIsoReply() {
		return isoReply;
	}

	/**
	 * @param isoReply the isoReply to set
	 */
	public void setIsoReply(String isoReply) {
		this.isoReply = isoReply;
	}

	/**
	 * @return the isoReplyDt
	 */
//	public Instant getIsoReplyDt() {
//		return isoReplyDt;
//	}

	public String getIsoReplyDt() {
		return instantToString(isoReplyDt);
	}

	/**
	 * @param isoReplyDt the isoReplyDt to set
	 */
	public void setIsoReplyDt(Instant isoReplyDt) {
		this.isoReplyDt = isoReplyDt;
	}

	/**
	 * @return the isoRequest
	 */
	public String getIsoRequest() {
		return isoRequest;
	}

	/**
	 * @param isoRequest the isoRequest to set
	 */
	public void setIsoRequest(String isoRequest) {
		this.isoRequest = isoRequest;
	}

	/**
	 * @return the isoRequestDt
	 */
//	public Instant getIsoRequestDt() {
//		return isoRequestDt;
//	}

	public String getIsoRequestDt() {
		return instantToString(isoRequestDt);
	}

	/**
	 * @param isoRequestDt the isoRequestDt to set
	 */
	public void setIsoRequestDt(Instant isoRequestDt) {
		this.isoRequestDt = isoRequestDt;
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
	 * @return the opCode
	 */
	public String getOpCode() {
		return opCode;
	}

	/**
	 * @param opCode the opCode to set
	 */
	public void setOpCode(String opCode) {
		this.opCode = opCode;
	}

	/**
	 * @return the opDescription
	 */
	public String getOpDescription() {
		return opDescription;
	}

	/**
	 * @param opDescription the opDescription to set
	 */
	public void setOpDescription(String opDescription) {
		this.opDescription = opDescription;
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

}
