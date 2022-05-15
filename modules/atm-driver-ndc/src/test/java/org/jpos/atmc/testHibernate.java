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
package org.jpos.atmc;

import org.hibernate.*;

import org.hibernate.criterion.Restrictions;

import org.jpos.ee.DB;

import org.jpos.atmc.model.ATM;

import org.jpos.atmc.util.Util;

class testHibernate
{
    public static void main(String[] args) 
	{
		/*
		// TODO Auto-generated method stub
		Configuration conf = new Configuration();
		conf.configure("hibernate.cfg.xml");
		
		// Alternativa a <mapping class="jfrd.ATM"/> en hibernate.cfg.xml
		// Inicio
		conf.addPackage("jfrd");
		conf.addAnnotatedClass(ATM.class);
		// Fin

		SessionFactory db = conf.buildSessionFactory();

		Session session = db.openSession();
		*/

        DB db = new DB();
		Session session = db.open();

		ATM atm = new ATM();
		atm.setId(1L); 
		System.out.println("Lectura del Registro con Id " + atm.getId());
		
		session.beginTransaction();
		ATM insATM = session.get(ATM.class, atm.getId());
		System.out.println("Registro Leido  " + insATM);
		session.getTransaction().commit();

        String ip = "127.0.0.1";
		session.beginTransaction();
        Criteria criteria = session.createCriteria (ATM.class)
                .add(Restrictions.eq("ip", ip));			

	    System.out.println("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " " + criteria.toString() );

		atm = (ATM) criteria.uniqueResult();
		session.getTransaction().commit();
		
		if (atm != null)
		{
	        System.out.println("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " Encontro en ATM en la Tabla" );
		    System.out.println("Registro Leido  " + insATM);
		}
		else
		{
	        System.out.println("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " ATM == null" );
		}

        // Employee e1 = new Employee();    
        // e1.setId(101);    
        // e1.setFirstName("Gaurav");    
        // e1.setLastName("Chawla");    
        //     
        // session.save(e1);  
        // t.commit();
		
        System.out.println("successfully saved");    
        session.close();   
		db.close();
    }

}
