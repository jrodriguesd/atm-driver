package org.jpos.atmc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="trndefs")
public class TrnDefinition 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "trndef_id", updatable = false, nullable = false)
    private Long id;    

	@Column(name = "trndef_configid", updatable = true, nullable = false)
	private String  configID;

	@Column(name = "trndef_atmcode", updatable = true, nullable = false)
	private String atmCode;

	@Column(name = "trndef_description", updatable = true, nullable = false)
	private String  description;

	@Column(name = "trndef_language639", updatable = true, nullable = false)
    private String  language639;

	@Column(name = "trndef_language3166", updatable = true, nullable = false)
	private String  language3166;

	@Column(name = "trndef_mti", updatable = true, nullable = false)
	private String MTI;

	@Column(name = "trndef_procesingcode", updatable = true, nullable = false)
    private String procesingCode;

	@Column(name = "trndef_state", updatable = true, nullable = false)
    private String  state;

	@Column(name = "trndef_screen", updatable = true, nullable = false)
	private String screen;

	@Column(name = "trndef_screen_update", updatable = true, nullable = false)
	private String screenUpdate;

	@Column(name = "trndef_respMaskScr", updatable = true, nullable = false)
	private String respMaskScr;

	@Column(name = "trndef_receipt_code", updatable = true, nullable = false)
	private String receiptCode;
	
	/**
	 * 
	 */
	public TrnDefinition() 
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
	 * @return the configID
	 */
	public String getConfigID() 
	{
		return configID;
	}

	/**
	 * @param configID the configID to set
	 */
	public void setConfigID(String configID) 
	{
		this.configID = configID;
	}

	/**
	 * @return the atmCode
	 */
	public String getAtmCode() 
	{
		return atmCode;
	}

	/**
	 * @param atmCode the atmCode to set
	 */
	public void setAtmCode(String atmCode) 
	{
		this.atmCode = atmCode;
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
	 * @return the language3166
	 */
	public String getLanguage3166() 
	{
		return language3166;
	}

	/**
	 * @param language3166 the language3166 to set
	 */
	public void setLanguage3166(String language3166) 
	{
		this.language3166 = language3166;
	}

	/**
	 * @return the mTI
	 */
	public String getMTI() 
	{
		return MTI;
	}

	/**
	 * @param mTI the mTI to set
	 */
	public void setMTI(String mTI) 
	{
		MTI = mTI;
	}

	/**
	 * @return the procesingCode
	 */
	public String getProcesingCode() 
	{
		return procesingCode;
	}

	/**
	 * @param procesingCode the procesingCode to set
	 */
	public void setProcesingCode(String procesingCode) 
	{
		this.procesingCode = procesingCode;
	}

	/**
	 * @return the state
	 */
	public String getState() 
	{
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) 
	{
		this.state = state;
	}
	
	/**
	 * @return the screen
	 */
	public String getScreen() 
	{
		return screen;
	}

	/**
	 * @param screen the screen to set
	 */
	public void setScreen(String screen) 
	{
		this.screen = screen;
	}

	/**
	 * @return the screenUpdate
	 */
	public String getScreenUpdate() 
	{
		return screenUpdate;
	}

	/**
	 * @param screenUpdate the screenUpdate to set
	 */
	public void setScreenUpdate(String screenUpdate) 
	{
		this.screenUpdate = screenUpdate;
	}

	/**
	 * @return the respMaskScr
	 */
	public String getRespMaskScr() {
		return respMaskScr;
	}

	/**
	 * @param respMaskScr the respMaskScr to set
	 */
	public void setRespMaskScr(String respMaskScr) {
		this.respMaskScr = respMaskScr;
	}

	/**
	 * @return the receiptCode
	 */
	public String getReceiptCode() {
		return receiptCode;
	}

	/**
	 * @param receiptCode the receiptCode to set
	 */
	public void setReceiptCode(String receiptCode) {
		this.receiptCode = receiptCode;
	}

}
