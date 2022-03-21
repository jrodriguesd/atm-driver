package org.jpos.atmc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/*
DROP TABLE IF EXISTS atmconfigs;
CREATE TABLE atmconfigs
(
  atmcnf_id              INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  atmcnf_configid        VARCHAR(250) NOT NULL,
  atmcnf_description     VARCHAR(250) NOT NULL,
  atmcnf_languageindex   TINYINT      NOT NULL,
  atmcnf_languageatm     CHAR(1)      NOT NULL,
  atmcnf_language639     VARCHAR(250) NOT NULL,
  atmcnf_screengroupbase SMALLINT     NOT NULL,
  PRIMARY KEY (atmcnf_id),
  UNIQUE KEY atmcnf_uniq (atmcnf_configid, atmcnf_languageatm)
);

INSERT  INTO atmconfigs VALUES 
(1,'0850','NDC Configuration with EMV English',      0,'A','eng',0),
(2,'0870','NDC Configuration without EMV English',   1,'A','eng',0),
(3,'0870','NDC Configuration without EMV Indonesian',1,'B','ind',400);
 */

@Entity
@Table(name="atmconfigs")
public class ATMConfig 
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "atmcnf_id", updatable = false, nullable = false)
    private Long id;    

    @Column(name="atmcnf_configid")
    private String  configID;

    @Column(name="atmcnf_description")
    private String  description;

    @Column(name="atmcnf_languageindex")
    private int     languageIndex;
    
    @Column(name="atmcnf_languageatm")
    private char    languageATM;
    
    @Column(name="atmcnf_language639")
    private String  language639;
    
    @Column(name="atmcnf_screengroupbase")
    private int     screenGroupBase;
    
	public ATMConfig() 
	{
		super();
	}
	
	public ATMConfig(String configID,
			         String description,
			         int    languageIndex, 
			         char   languageATM, 
			         String language639,
			         int    screenGroupBase) 
	{
		super();
		this.configID = configID;
		this.description = description;
		this.languageIndex = languageIndex;
		this.languageATM = languageATM;
		this.language639 = language639;
		this.screenGroupBase = screenGroupBase;
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
	 * @return the languageIndex
	 */
	public int getLanguageIndex() 
	{
		return languageIndex;
	}

	/**
	 * @param languageIndex the languageIndex to set
	 */
	public void setLanguageIndex(int languageIndex) 
	{
		this.languageIndex = languageIndex;
	}

	/**
	 * @return the languageATM
	 */
	public char getLanguageATM() {
		return languageATM;
	}

	/**
	 * @param languageATM the languageATM to set
	 */
	public void setLanguageATM(char languageATM) {
		this.languageATM = languageATM;
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
	 * @return the screenGroupBase
	 */
	public int getScreenGroupBase() 
	{
		return screenGroupBase;
	}

	/**
	 * @param screenGroupBase the screenGroupBase to set
	 */
	public void setScreenGroupBase(int screenGroupBase) 
	{
		this.screenGroupBase = screenGroupBase;
	}

	/**
	 * @return the atmConfigs
	 */
	public static ATMConfig[] getAtmConfigs() 
	{
		return atmConfigs;
	}

	/**
	 * @param atmConfigs the atmConfigs to set
	 */
	public static void setAtmConfigs(ATMConfig[] atmConfigs) 
	{
		ATMConfig.atmConfigs = atmConfigs;
	}
	
	@Override
	public String toString() 
	{
		return "ATMConfig [configID=" + configID + ", languageIndex=" + languageIndex + ", languageATM="
				+ languageATM + ", language639=" + language639 + ", screenGroupBase=" + screenGroupBase + "]";
	}

    /*
     * SELECT atmcnf_language639 FROM atmconfigs WHERE (atmcnf_configid = '0870')
     * if solo hay un Registro (MonoLenguage) 
     *     Devolver Ese Registro
     * else
     *     Hay Varios Risregistros (MultiLenguage)
     * SELECT atmcnf_language639 FROM atmconfigs 
     *     WHERE (atmcnf_configid = '0870' AND 
     *     SUBSTRING("AIA     ", atmcnf_languageindex, 1) = atmcnf_languageatm)	
     */
	public static ATMConfig getATMConfig(String configID, String atmOperationCode) 
	{
        for (int i = 0; i < atmConfigs.length; i++) 
		{
			if ( atmConfigs[i].getConfigID().equals(configID) )
			{
				if (atmConfigs[i].getLanguageIndex() < 0 )
				{
		        	/* MonoLanguage Configuration */
					return atmConfigs[i];
				}
				else
				{
		        	/* MultiLanguage Configuration */
				    char languageATM = atmOperationCode.charAt(atmConfigs[i].getLanguageIndex());
					if (atmConfigs[i].getLanguageATM() == languageATM )
					{
					    return atmConfigs[i];
					}
				}
			}
        }		
		return null;
	}

	private static ATMConfig[] atmConfigs = 
	{
	    new ATMConfig("0850", "NDC Configuration with EMV English",       -1, 'A', "eng",   0),
	    new ATMConfig("0870", "NDC Configuration without EMV English",     0, 'A', "eng",   0),
		new ATMConfig("0870", "NDC Configuration without EMV Indonesian",  0, 'B', "ind", 400),
	};

}
