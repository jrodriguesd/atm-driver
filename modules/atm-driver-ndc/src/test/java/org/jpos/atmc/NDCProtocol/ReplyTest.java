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
 * Returns days to New Year.
 * @author <a href="mailto:j@rodriguesd.org">Jose Rodrigues D.</a>
 */
package org.jpos.atmc.NDCProtocol;

import org.jdom2.JDOMException;
import org.jpos.atmc.util.Crypto;
import org.jpos.atmc.util.NDCFSDMsg;
import org.jpos.atmc.util.Util;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOUtil;
import org.junit.Before;
import org.jpos.util.FSDMsg;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;

import org.junit.Test;
import static org.junit.Assert.*;

public class ReplyTest
{
	private static final String HOSTNAME = "127.0.0.1";
	private static final int    PORT = 8000;
	
    private Socket socket;
    private InputStream input;
    private OutputStream output;
    private PrintWriter out;
    private PrintStream ps;

	private String key = "0123456789ABCDEF";
	private byte bKey[];

	@Before
	public void setup()  throws IOException 
	{
        this.socket = new Socket(HOSTNAME, PORT);
		this.input  = this.socket.getInputStream();
		this.output = this.socket.getOutputStream();

		FileOutputStream fout = new FileOutputStream("LogPeriquete.txt", true); 
		this.ps = new PrintStream(fout);
		this.out = new PrintWriter(ps);
	  	this.bKey = ISOUtil.hex2byte(this.key);
	}

    /**
     * Send cmd to Server
     * @throws GeneralSecurityException 
     *
     */
    public void sendMessage(byte [] cmd) throws IOException
    {
 	    println("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
    	byte[] buffer = new byte[4096];

		buffer[0] = (byte) (cmd.length >> 8);
		buffer[1] = (byte) cmd.length;
        System.arraycopy(cmd, 0, buffer, 2, cmd.length);
		
		String dumpString = Util.formatHexDump(buffer, 0, cmd.length + 2);
 	    println("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
		println(">> " + (cmd.length + 2) + " bytes sent:");
		println(dumpString);
		
        output.write(buffer, 0, cmd.length + 2);
    }

    /**
     * Receive msg from Server
     *
     */
    public byte [] receiveMessage() throws IOException
    {
        byte[] buffer = new byte[4096];
		int count;

		count = input.read(buffer);
        if (count < 0)
            return null;

        byte msg[] = new byte[count - 2];
        System.arraycopy(buffer, 2, msg, 0, count - 2);

		String dumpString = Util.formatHexDump(msg, 0, msg.length);
 	    println("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
		println(">> " + msg.length + " bytes received:");
		println(dumpString);

		return msg;
	}

    private NDCFSDMsg InterchangeMsgsWithATMC(NDCFSDMsg request, boolean generateBadMAC) throws IOException, JDOMException, ISOException, GeneralSecurityException
    {
 		request.set("mac", null);
 	    this.out.println("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " pack " + request.pack() );
  		byte bRequest[] = request.packToBytes();
  		
	    String dumpString = Util.formatHexDump(bRequest, 0, bRequest.length);
 	    println("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
	    println(dumpString);

		byte calculatedMAC[] = Crypto.calculateANSIX9_9MAC(this.bKey, bRequest);

 	    println("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " calculatedMAC " + ISOUtil.byte2hex(calculatedMAC) );

 		if (generateBadMAC)
 		{
 	 		calculatedMAC[2] = 0x33;
 	 		calculatedMAC[3] = 0x33;
 	 		calculatedMAC[4] = 0x33;
 		}

 		String strMAC = Util.byteDecode(calculatedMAC);
        byte[] bytes = strMAC.getBytes(StandardCharsets.ISO_8859_1);

		dumpString = Util.formatHexDump(bytes, 0, bytes.length);
 	    println("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
		println(">> " + (bytes.length) + " bytes sent:");
		println(dumpString);

 		request.set("mac", strMAC);
 		bRequest = request.packToBytes();

    	sendMessage( bRequest );

    	byte bReply[] = receiveMessage();

    	NDCFSDMsg reply = new NDCFSDMsg( request.getBasePath() );
        reply.unpack(bReply);
    	return reply;
    }
    
    private void println(String str)
    {
 	    this.out.println(str);
 		this.out.flush();
    }

	@Test
	public void testUnsolicitedStatus() throws IOException, JDOMException, ISOException, GeneralSecurityException 
	{
		String strUnsolicitedStatus = "12000B0870";

		String Schema = "file:src/dist/cfg/ndc/ndc-";
		NDCFSDMsg request = new NDCFSDMsg(Schema);

  		InputStream byteInputStream = new ByteArrayInputStream(strUnsolicitedStatus.getBytes());
  		request.unpack(byteInputStream);

  		NDCFSDMsg reply = InterchangeMsgsWithATMC(request, false);

		String replyData = reply.get("data");
 	    println("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " replyData>" + replyData + "<" );

  		assertNotEquals(replyData, "");	
	}

	@Test
	public void testReplyWithTimeVariantNumber() throws IOException, JDOMException, ISOException, GeneralSecurityException 
	{
 	    println("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
		String balanceInquiryRq = "1100001A2E16615;41073741454145=251210110000232?AIA     000000000000>354619447=;078?";

		String Schema = "file:src/dist/cfg/ndc/ndc-";
		NDCFSDMsg request = new NDCFSDMsg(Schema);

  		InputStream byteInputStream = new ByteArrayInputStream(balanceInquiryRq.getBytes());
  		request.unpack(byteInputStream);

		String RequestTimeVariantNumber = request.get("time-variant-number");

  		NDCFSDMsg reply = InterchangeMsgsWithATMC(request, false);
  		
		String ReplyTimeVariantNumber = reply.get("time-variant-number");

 		assertEquals(RequestTimeVariantNumber, ReplyTimeVariantNumber);	
	}

	@Test
	public void testReplyWithoutTimeVariantNumber() throws IOException, JDOMException, ISOException, GeneralSecurityException 
	{
 	    println("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
		String balanceInquiryRq = "1100001A2E16615;41073741454145=251210110000232?AIA     000000000000>354619447=;078?";

		String Schema = "file:src/dist/cfg/ndc/ndc-";
		NDCFSDMsg request = new NDCFSDMsg(Schema);

  		InputStream byteInputStream = new ByteArrayInputStream(balanceInquiryRq.getBytes());
  		request.unpack(byteInputStream);
		request.set("time-variant-number", null);

 		NDCFSDMsg reply = InterchangeMsgsWithATMC(request, false);

  		String ReplyTimeVariantNumber = reply.get("time-variant-number");

  		assertNull(ReplyTimeVariantNumber);	
	}

	@Test
	public void testRequestWithGoodMAC() throws IOException, JDOMException, ISOException, GeneralSecurityException 
	{
 	    println("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
		String balanceInquiryRq = "1100100000101A2E16612;41073741454145=251210110000232?AIA     000000000000>354619447=;078?21639100000000000000000000U5CAM00008C159F02069F03069F1A0295055F2A029A039C019F37049F02060000000000009F0306000000000000820218005A095022654000890000925F3401019F360209B79F2608B2746052B28B64139F34030200009F2701809F1E0830303030303030319F100706010A03A0A0009F090200969F33036040E89F1A0206089F35011495058000040000570F502265400089000092D2708620951F5F2A0206089F080200969A031810019F4104000038169B0260009C01309F3704039690459F53015A9F0607A000000635101050105068696C697070696E652044656269745F201A4544204241524741444F2F2020202020202020202020202020205F24032708313FF7AA85";

		String Schema = "file:src/dist/cfg/ndc/ndc-";
		NDCFSDMsg request = new NDCFSDMsg(Schema);

  		InputStream byteInputStream = new ByteArrayInputStream(balanceInquiryRq.getBytes());
  		request.unpack(byteInputStream);

  		NDCFSDMsg reply = InterchangeMsgsWithATMC(request, false);

  		String replyScreenDisplayUpdate = reply.get("screen-display-update");

  		assertNotEquals(replyScreenDisplayUpdate, "");	
	}
	
	@Test
	public void testRequestBadMAC() throws IOException, JDOMException, ISOException, GeneralSecurityException 
	{
 	    println("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
		String balanceInquiryRq = "1100100000101A2E1661241073741454145=211210110000232AIA     000000000000>354619447=;078?21639100000000000000000000U5CAM00008C159F02069F03069F1A0295055F2A029A039C019F37049F02060000000000009F0306000000000000820218005A095022654000890000925F3401019F360209B79F2608B2746052B28B64139F34030200009F2701809F1E0830303030303030319F100706010A03A0A0009F090200969F33036040E89F1A0206089F35011495058000040000570F502265400089000092D2708620951F5F2A0206089F080200969A031810019F4104000038169B0260009C01309F3704039690459F53015A9F0607A000000635101050105068696C697070696E652044656269745F201A4544204241524741444F2F2020202020202020202020202020205F24032708313FF7AA85";

		String Schema = "file:src/dist/cfg/ndc/ndc-";
		NDCFSDMsg request = new NDCFSDMsg(Schema);

  		InputStream byteInputStream = new ByteArrayInputStream(balanceInquiryRq.getBytes());
  		request.unpack(byteInputStream);

  		NDCFSDMsg reply = InterchangeMsgsWithATMC(request, true);

  		String replyScreenDisplayUpdate = reply.get("screen-display-update");
 	    println("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " replyScreenDisplayUpdate>" + replyScreenDisplayUpdate + "<" );

  		assertNull(replyScreenDisplayUpdate);	
	}

	@Test
	public void testReplyWithGoodMAC() throws IOException, JDOMException, ISOException, GeneralSecurityException 
	{
 	    println("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() );
		String balanceInquiryRq = "1100100000101A2E16612;41073741454145=251210110000232?AIA     000000000000>354619447=;078?21639100000000000000000000U5CAM00008C159F02069F03069F1A0295055F2A029A039C019F37049F02060000000000009F0306000000000000820218005A095022654000890000925F3401019F360209B79F2608B2746052B28B64139F34030200009F2701809F1E0830303030303030319F100706010A03A0A0009F090200969F33036040E89F1A0206089F35011495058000040000570F502265400089000092D2708620951F5F2A0206089F080200969A031810019F4104000038169B0260009C01309F3704039690459F53015A9F0607A000000635101050105068696C697070696E652044656269745F201A4544204241524741444F2F2020202020202020202020202020205F24032708313FF7AA85";

		String Schema = "file:src/dist/cfg/ndc/ndc-";
		NDCFSDMsg request = new NDCFSDMsg(Schema);

  		InputStream byteInputStream = new ByteArrayInputStream(balanceInquiryRq.getBytes());
  		request.unpack(byteInputStream);

  		NDCFSDMsg reply = InterchangeMsgsWithATMC(request, false);

  		String replyMAC = reply.get("mac");
		byte bmsgMAC[] = null;
		if (replyMAC != null)
		    bmsgMAC = replyMAC.getBytes(StandardCharsets.ISO_8859_1);

        /*******************************************************************/
        /* Calcular MAC - (Inicio)                                         */
        /*******************************************************************/
 		reply.set("mac", null);
  		byte bReply[] = reply.packToBytes();

		byte calculatedMAC[] = Crypto.calculateANSIX9_9MAC(this.bKey, bReply);
        /*******************************************************************/
        /* Calcular MAC - (Fin)                                            */
        /*******************************************************************/

  		assertArrayEquals(bmsgMAC, calculatedMAC);
	}
	
}