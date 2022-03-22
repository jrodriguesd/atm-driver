package org.jpos.atmc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author User
 *
 */
@Entity
@Table(name="isoerr2atms")
public class IsoError2ATM 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "isoerr2atm_id", updatable = false, nullable = false)
    private Long id;    

	@Column(name = "isoerr2atm_configid", updatable = true, nullable = false)
	private String  configID;

	@Column(name = "isoerr2atm_isoresp", updatable = true, nullable = false)
	private int     isoResp;

	@Column(name = "isoerr2atm_description", updatable = true, nullable = false)
	private String  description;

	@Column(name = "isoerr2atm_language639", updatable = true, nullable = false)
    private String  language639;

	@Column(name = "isoerr2atm_language3166", updatable = true, nullable = false)
	private String  language3166;

	@Column(name = "isoerr2atm_state", updatable = true, nullable = false)
    private String  state;

	@Column(name = "isoerr2atm_screen_receipt", updatable = true, nullable = false)
    private String  screenReceipt;

	@Column(name = "isoerr2atm_screen_no_receipt", updatable = true, nullable = false)
    private String  screenNoReceipt;

	@Column(name = "isoerr2atm_screen_card_retained", updatable = true, nullable = false)
    private String  screenCardReteined;

	@Column(name = "isoerr2atm_screen_stmt_delivered", updatable = true, nullable = false)
    private String  screenStmtDelivered;
	
	/**
	 * 
	 */
	public IsoError2ATM() 
	{
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param configID
	 * @param isoResp
	 * @param description
	 * @param language639
	 * @param language3166
	 * @param state
	 * @param screenReceipt
	 */
	public IsoError2ATM(String configID,
    		            int    isoResp,
    		            String description, 
    		            String language639, 
    		            String language3166, 
    		            String state,
			            String screenReceipt) 
    {
		super();
		this.configID = configID;
		this.isoResp = isoResp;
        this.description = description;
		this.language639 = language639;
		this.language3166 = language3166;
		this.state = state;
		this.screenReceipt = screenReceipt;
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
	 * @return the isoResp
	 */
	public int getIsoResp() 
	{
		return isoResp;
	}

	/**
	 * @param isoResp the isoResp to set
	 */
	public void setIsoResp(int isoResp) 
	{
		this.isoResp = isoResp;
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
		return screenReceipt;
	}

	/**
	 * @param screenReceipt the screenReceipt to set
	 */
	public void setScreen(String screenReceipt) 
	{
		this.screenReceipt = screenReceipt;
	}
	
	/**
	 * @return the isoError2ATMs
	 */
	public static IsoError2ATM[] getIsoError2ATMs() 
	{
		return isoError2ATMs;
	}

	/**
	 * @param isoError2ATMs the isoError2ATMs to set
	 */
	public static void setIsoError2ATMs(IsoError2ATM[] isoError2ATMs) 
	{
		IsoError2ATM.isoError2ATMs = isoError2ATMs;
	}

	@Override
	public String toString() 
	{
		return "IsoError2ATM [isoResp=" + isoResp + ", configID=" + configID + ", language639=" + language639
				+ ", language3166=" + language3166 + ", state=" + state + ", screenReceipt=" + screenReceipt + "]";
	}

	public static IsoError2ATM getIsoError2ATM(int    isoRespCode, 
			                                   String configID, 
			                                   String language639) 
	{
        for (int i = 0; i < isoError2ATMs.length; i++) 
		{
			if ( (isoError2ATMs[i].getIsoResp() == isoRespCode ) &&
				 (isoError2ATMs[i].getConfigID().equals(configID) ) &&	
			     (isoError2ATMs[i].getLanguage639().equals(language639) ) )
			return isoError2ATMs[i];
        }		
		return null;
	}

	private static IsoError2ATM[] isoError2ATMs = 
    {
    	// Configuration_Orig.txt eng
        new IsoError2ATM("0870",   1, "Expired card",                "eng", "840", "000", "000"),
        new IsoError2ATM("0870",   6, "PIN retries exceeded",        "eng", "840", "000", "000"),
        new IsoError2ATM("0870",  11, "Invalid card number",         "eng", "840", "000", "000"),
        new IsoError2ATM("0870",  17, "Invalid PIN",                 "eng", "840", "350", "350"), 
        new IsoError2ATM("0870",  18, "No card record",              "eng", "840", "000", "000"),
        new IsoError2ATM("0870",  98, "Unknown Transaction",         "eng", "840", "000", "000"),
    	new IsoError2ATM("0870",  99, "Eror Processing Transaction", "eng", "840", "361", "361"),  
    	new IsoError2ATM("0870", 101, "Expired card",                "eng", "840", "352", "352"),  
        
        
    	// Configuration_Orig.txt ind
        new IsoError2ATM("0870",  6, "PIN retries exceeded",        "ind", "360", "361", "361"), 
        new IsoError2ATM("0870", 17, "Invalid PIN",                 "ind", "360", "350", "350"),
    	new IsoError2ATM("0870", 99, "Eror Processing Transaction", "ind", "360", "361", "361"),  
        
        // Configuration_NCR_EMV.txt
        new IsoError2ATM("0850", 99, "Eror Processing Transaction", "eng", "840", "050", "017"), 
        new IsoError2ATM("0850", 17, "Invalid PIN",                 "eng", "840", "235", "033"), 
    };
    
}
