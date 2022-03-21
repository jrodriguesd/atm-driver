package org.jpos.atmc.dao;

import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.jpos.atmc.model.Receipt;
import org.jpos.atmc.util.Log;
import org.jpos.atmc.util.Util;
import org.jpos.ee.DB;
import org.jpos.ee.DBManager;

public class ReceiptManager extends DBManager<Receipt> 
{

	public ReceiptManager(DB db) 
	{
		super(db, Receipt.class);
	}

	public Receipt findById(Long id) 
	{
		return getItemByParam("id", id);
	}

	@SuppressWarnings("unchecked")
	public Receipt getReceipt(String configID, String receiptCode) 
	{
        try 
        {
            Query<Receipt> query = db.session().createQuery 
            (
                "from atmconfigs in class org.jpos.atmc.model.Receipt WHERE rcp_config_id = :configID AND rcp_code = :receiptCode"
            );
            query.setParameter ("configID", configID);
            query.setParameter ("receiptCode", receiptCode);
            List<Receipt> l = query.list();
            Iterator<Receipt> iter = l.iterator();
            if (l.size() == 1)
                return (Receipt) iter.next();
            else if (l.size() < 1)
    			Log.staticPrintln("JFRD "  + Util.fileName() + 
    					    " Line " + Util.lineNumber() + 
    					    " "      + Util.methodName() 
    					    + " No se Encontro Receipt " 
    					    + "configID " + configID 
    					    + "receiptCode " + receiptCode);
            else if (l.size() > 1)
    			Log.staticPrintln("JFRD "  + Util.fileName() + 
    				    " Line " + Util.lineNumber() + 
    				    " "      + Util.methodName() 
    				    + " Se Encontro mas de un Receipt " 
					    + "configID " + configID 
					    + "receiptCode " + receiptCode);
            return null;
        } 
        catch (HibernateException e) 
        {
            db.getLog().warn (e);
        }
        return null;
    }
	
}
