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
 * @author <a href="mailto:j@rodriguesd.org">Jose Rodrigues D.</a>
 */
package jfrd;

class Card
{
    private String pan;
    private String exp;
    private String serviceCode;
    private String cvv;
    private String encryptedPIN;

    public Card(String pan,
                String exp,
                String serviceCode,
                String cvv,				 
	            String encryptedPIN)
    {
		this.pan = pan;
		this.exp = exp;
		this.serviceCode = serviceCode;
		this.cvv = cvv;
		this.encryptedPIN = encryptedPIN;
    } 

    public String getPAN()
	{
		return this.pan;
	}

    public String getExp()
	{
		return this.exp;
	}

    public String getServiceCode()
	{
		return this.serviceCode;
	}

    public String getCVV()
	{
		return this.cvv;
	}

    public String getEncryptedPIN()
	{
		return this.encryptedPIN;
	}

 
    public static Card getCard(String pan)
    {
        for (int i = 0; i < cards.length; i++) 
		{
			if (cards[i].getPAN().equals(pan)) return cards[i];
        }		
	    return null; 
    }

    private static Card[] cards = 
    {
        new Card("41073741454145", "2512", "101", "232", "E354619447DB078F"),
    };	
}