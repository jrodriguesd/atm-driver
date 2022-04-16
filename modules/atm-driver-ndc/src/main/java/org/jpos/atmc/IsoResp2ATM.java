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

// No Est listo solo se Copio Necesita ser Arreglado

import java.util.Objects;

class IsoResp2ATM
{
    private int     isoResp;
    private String  language639;
    private String  language3166;
    private String  atmMsg;

    /*
     *     int    isoResp        ISO Resp Code
	 *     String language639    ISO 639-2 Language Name
	 *     String language3166   ISO 3166  
	 *     String atmMsg         Res Mesg
	 */
    public IsoResp2ATM(int    isoResp,
	                   String language639,
	                   String language3166,
	                   String atmMsg)
    {
		this.isoResp = isoResp;
		this.language639 = language639;
		this.language3166 = language3166;
		this.atmMsg = atmMsg;
    } 

    public int getIsoResp()
	{
		return this.isoResp;
	}

    public String getLanguage639()
	{
		return this.language639;
	}

    public String getLanguage3166()
	{
		return this.language3166;
	}

    public String getAtmMsg()
	{
		return this.atmMsg;
	}


    public static IsoResp2ATM getIsoResp2ATM(int isoResp, String language639)
    {
		for (IsoResp2ATM isoResp2ATM : isoResp2ATMs) {
			if ((isoResp2ATM.getIsoResp() == isoResp) &&
					(Objects.equals(isoResp2ATM.getLanguage639(), language639)))
				return isoResp2ATM;
		}
	    return null; 
    }

    // La Respuesta Va a Un Estado J
	// Se Usa para Mapear Mensajes de Error DE39 != 0
	// de los mensajes multilanguage se encarga el ATM. SIl Configuracion es Multi Lenguaje
	// Mejor Nombre ISOError2ATM
    // 
    // Campos
    //
    // Iso Resp DE39
    // ATM Config ID
    // ATM Operation Code
	// Description
    // State Numbre
    // Receipt Delivered Screen Number
    // No Receipt Delivered Screen Number
    // Card Retained Screen Number
    // Statement Delivered Screen Number
    // BNA Returned Screen Number

    private static IsoResp2ATM[] isoResp2ATMs = 
    {
        //
        //  Esta Clase Nada mas deberia ser para Errores
        //
        //  int    isoResp        ISO Resp Code
	    //  String language639    ISO 639-2 Language Name
	    //  String language3166   ISO 3166  
	    //  String ConfId         Configuration Id
	    //  String StateNumber    State Number
	    //  String ScreenNumber   Screen Number
        // new IsoResp2ATM(17, "eng", "840", "0870", "350", "350"),

                                        // @ABCDEFGHIJKLMNO0123456789:;<=>?
        new IsoResp2ATM(1, "eng", "840", "Expired card"),
        new IsoResp2ATM(6, "eng", "840", "PIN retries exceeded"),
        new IsoResp2ATM(11, "eng", "840", "Invalid card number"),
        new IsoResp2ATM(17, "eng", "840", "Invalid PIN"),
        new IsoResp2ATM(18, "eng", "840", "No card record"),
        new IsoResp2ATM(98, "eng", "840", "Unknown Transaction"),
        new IsoResp2ATM(99, "eng", "840", "Eror Processing Transaction"),

        new IsoResp2ATM(1, "esp", "724", "Tarjeta Vencida"),
        new IsoResp2ATM(6, "esp", "724", "Intentos PIN Excedido"),
        new IsoResp2ATM(11, "esp", "724", "Problemas con Tarjeta 117"),
        new IsoResp2ATM(17, "esp", "724", "PIN Incorrecto"),
        new IsoResp2ATM(18, "esp", "724", "Problemas con Tarjeta 118"),
        new IsoResp2ATM(98, "esp", "724", "Transaccion Desconocida"),
        new IsoResp2ATM(99, "esp", "724", "Error Procesando la Transaccion"),

        new IsoResp2ATM(1, "ind", "360", "Tarjeta Vencida"),
        new IsoResp2ATM(6, "ind", "360", "Intentos PIN Excedido"),
        new IsoResp2ATM(11, "ind", "360", "Problemas con Tarjeta 117"),
        new IsoResp2ATM(17, "ind", "360", "PIN Incorrecto"),
        new IsoResp2ATM(18, "ind", "360", "Problemas con Tarjeta 118"),
        new IsoResp2ATM(98, "ind", "360", "Transaccion Desconocida"),
        new IsoResp2ATM(99, "ind", "360", "Error Procesando la Transaccion"),



    };	
}
