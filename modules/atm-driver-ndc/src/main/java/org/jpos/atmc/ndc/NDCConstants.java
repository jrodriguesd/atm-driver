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
package org.jpos.atmc.ndc;

import java.util.Date;
import java.util.Locale;
import java.math.BigDecimal;
import java.text.Format;
import java.text.NumberFormat;

import org.jpos.iso.*;
import org.jpos.util.FSDMsg;
import org.jpos.util.NameRegistrar;

import org.jpos.iso.packager.ISO87APackager;

import org.jpos.q2.iso.QMUX;

import org.jpos.atmc.util.Log;
import org.jpos.atmc.util.Util;

public class NDCConstants
{
    /**
	 *
	 * Transaction Reply Command Function Identifier
	 *
	 */
	public static final String DEPOSIT_AND_PRINT                                              = "1";
	public static final String DISPENSE_AND_PRINT                                             = "2";
	public static final String DISPLAY_AND_PRINT                                              = "3";
	public static final String PRINT_IMMEDIATE                                                = "4";
	public static final String NEXT_STATE_AND_PRINT                                           = "5";
	public static final String NIGHT_SAFE_DEPOSIT_AND_PRINT                                   = "6";
	public static final String EJECT_CARD_DISPENSE_AND_PRINT                                  = "A";
	public static final String PARALLEL_DISPENSE_PRINT_AND_CARD_EJECT                         = "B";
	public static final String CARD_BEFORE_PARALLEL_DISPENSE_PRINT                            = "F";
	public static final String PRINT_STATEMENT_AND_WAIT                                       = "P";
	public static final String PRINT_STATEMENT_AND_NEXT_STATE                                 = "Q";
	public static final String REFUND_BNA_DEPOSITED_MONEY_AND_NEXT_STATE                      = "*";
	public static final String ENCASH_BNA_DEPOSITED_MONEY_RECEIPT_IF_REQUESTED_AND_NEXT_STATE = "-";
	public static final String ENCASH_BNA_DEPOSITED_MONEY_AND_WAIT_ANOTHER_REPLY              = "’";
	public static final String PROCESS_CPM_CHEQUE                                             = "-";

    /**
	 *
	 * Transaction Reply Card Return/Retain Flag
	 *
	 */
	public static final String RETURN_CARD                                                    = "0";
	public static final String RETAIN_CARD                                                    = "1";

    /**
	 *
	 * Transaction Reply Printer Flas
	 *
	 */
	public static final String DO_NOT_PRINT                                                   = "0";
	public static final String JOURNAL_PRINTER_ONLY                                           = "1";
	public static final String RECEIPT_PRINTER_ONLY                                           = "2";
	public static final String RECEIPT_AND_JOURNAL_PRINTER                                    = "3";

}
