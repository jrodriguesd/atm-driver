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