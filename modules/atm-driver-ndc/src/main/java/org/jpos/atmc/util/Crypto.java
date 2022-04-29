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
package org.jpos.atmc.util;

import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.Security;
 
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.jpos.iso.ISOUtil;

/**
 * Crypto Operations Class
 *
 * @author
 */
public class Crypto
{
	public static final byte[] ZERO_IVC = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0 };

	public static String encypt(String strKey, String strData)
	{
        // convert hex string to byte array
        byte[] keyBytes = ISOUtil.hex2byte(strKey);
        byte[] input    = ISOUtil.hex2byte(strData);

        // Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        
        SecretKeySpec key = new SecretKeySpec(keyBytes, "3DES");
		byte[] cipherText = null;
		int ctLength = 0;
		try
		{
            Cipher cipher = Cipher.getInstance("DESede/ECB/NoPadding", "BC");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            cipherText = new byte[cipher.getOutputSize(input.length)];
            ctLength = cipher.update(input, 0, input.length, cipherText, 0);
            ctLength += cipher.doFinal(cipherText, ctLength);
		}
		catch (Exception e) 
		{
	        System.out.println("JFRD " + Util.fileName() + " Line " + Util.lineNumber() + " " + Util.methodName() + " exception: " + e.getMessage());
			e.printStackTrace(Log.out);
        }

		return ISOUtil.byte2hex(cipherText);
	}

	/*************************************************************************************************************************/
	/* https://github.com/xiaokukuo/sportsbet/tree/master/sport-parent/sport-common/src/main/java/com/sport/bet/common/utils */
	/*************************************************************************************************************************/

	/**
	 * Generar clave
	 * @return 16 bytes 3des key
	 * @throws GeneralSecurityException
	 */
	public static byte[] create3DESKey() throws GeneralSecurityException 
	{ 
		KeyGenerator kg = KeyGenerator.getInstance("DESede");
		kg.init(112);//must be equal to 112 or 168
		byte[] key24 =  kg.generateKey().getEncoded();
		byte[] result = new byte[16];
		System.arraycopy(key24, 0, result, 0, 16);
		return result;
	}

	/**
	 * Modo cbc encriptado 3DES
	 * @param datos de contenido que se cifraran
	 * @param key clave secreta
	 * @param vector ivb
	 * @ devolver resultado cifrado
	 * @throws GeneralSecurityException
	 */
	public static byte[] encryptBy3DesCbc(byte[] content, byte[] key, byte[] ivb) throws GeneralSecurityException 
	{ 
		byte[] _3deskey = new byte[24];
		System.arraycopy(key, 0, _3deskey, 0, 16);
		System.arraycopy(key, 0, _3deskey, 16, 8);

        Cipher cipher = Cipher.getInstance("DESede/CBC/NoPadding");  
        SecretKey secureKey = new SecretKeySpec(_3deskey, "DESede");  
        IvParameterSpec iv = new IvParameterSpec(ivb);
        cipher.init(Cipher.ENCRYPT_MODE, secureKey, iv);  
        return cipher.doFinal(content);  
    }

	/**
	 * 3DES descifra el modo cbc
	 * Los datos de contenido de @param se descifraran
	 * @param key clave secreta
	 * @param vector ivb
	 * @return descifra el resultado
	 * @throws GeneralSecurityException
	 */
	public static byte[] decryptBy3DesCbc(byte[] content, byte[] key, byte[] ivb) throws GeneralSecurityException 
	{ 
		byte[] _3deskey = new byte[24];
		System.arraycopy(key, 0, _3deskey, 0, 16);
		System.arraycopy(key, 0, _3deskey, 16, 8);

		Cipher cipher = Cipher.getInstance("DESede/CBC/NoPadding");  
		SecretKey secureKey = new SecretKeySpec(_3deskey, "DESede");  
		IvParameterSpec iv = new IvParameterSpec(ivb);
		cipher.init(Cipher.DECRYPT_MODE, secureKey, iv);  
		return cipher.doFinal(content);  
	}
	/**
	 * Modo cbc encriptado 3DES, vector predeterminado
	 * @param datos de contenido que se cifraran
	 * @param key clave secreta
	 * @ devolver resultado cifrado
	 * @throws GeneralSecurityException
	 */
	public static byte[] encryptBy3DesCbc(byte[] content, byte[] key) throws GeneralSecurityException 
	{ 
		return encryptBy3DesCbc(content, key, ZERO_IVC);
	}

	/**
	 * 3DES descifra el modo cbc, vector predeterminado
	 * @param contenido con datos descifrados
	 * @param key clave secreta
	 * @return descifra el resultado
	 * @throws GeneralSecurityException
	 */
	public static byte[] decryptBy3DesCbc(byte[] content, byte[] key) throws GeneralSecurityException 
	{
		return decryptBy3DesCbc(content, key, ZERO_IVC);
	}

	/**
	 * Modo Ecb encriptado 3DES
	 * @param datos de contenido que se cifraran
	 * Clave de cifrado de clave @param
	 * @ devolver resultado cifrado
	 * @throws GeneralSecurityException
	 */
	public static byte[] encryptBy3DesEcb(byte[] content, byte[] key) throws GeneralSecurityException 
	{
		byte[] _3deskey = new byte[24];
		System.arraycopy(key, 0, _3deskey, 0, 16);
		System.arraycopy(key, 0, _3deskey, 16, 8);

		Cipher cipher = Cipher.getInstance("DESede/ECB/NoPadding");  
		SecretKey secureKey = new SecretKeySpec(_3deskey, "DESede");  
		cipher.init(Cipher.ENCRYPT_MODE, secureKey);  
		return cipher.doFinal(content);  
	}

	/**
	 * 3DES descifra el modo Ecb
	 * Los datos de contenido de @param se descifraran
	 * @param key clave secreta
	 * @return descifra el resultado
	 * @throws GeneralSecurityException
	 */
	public static byte[] decryptBy3DesEcb(byte[] content, byte[] key) throws GeneralSecurityException 
	{
		byte[] _3deskey = new byte[24];
		System.arraycopy(key, 0, _3deskey, 0, 16);
		System.arraycopy(key, 0, _3deskey, 16, 8);

		Cipher cipher = Cipher.getInstance("DESede/ECB/NoPadding");  
		SecretKey secureKey = new SecretKeySpec(_3deskey, "DESede");  
		cipher.init(Cipher.DECRYPT_MODE, secureKey);  
		return cipher.doFinal(content);  
	}

	/**
	 * algoritmo de cifrado en modo des cbc
	 * @param datos de contenido que se cifraran
	 * @param key
	 * @ devolver resultado cifrado
	 * @throws GeneralSecurityException
	 */
	public static byte[] encryptByDesCbc(byte[] content, byte[] key) throws GeneralSecurityException 
	{
		return encryptByDesCbc(content, key, ZERO_IVC);
	}

	/**
	 * Algoritmo de descifrado del modo cbc
	 * Los datos de contenido de @param se descifraran
	 * @param key
	 * @return descifra el resultado
	 * @throws GeneralSecurityException
	 */
	public static byte[] decryptByDesCbc(byte[] content, byte[] key) throws GeneralSecurityException 
	{
		return decryptByDesCbc(content, key, ZERO_IVC);
	}

	/**
	 * algoritmo de cifrado en modo des cbc
	 * @param datos de contenido que se cifraran
	 * Clave de cifrado de clave @param
	 * @ devolver resultado cifrado
	 * @throws GeneralSecurityException
	 */
	public static byte[] encryptByDesCbc(byte[] content, byte[] key, byte[] icv) throws GeneralSecurityException 
	{
		SecureRandom sr = new SecureRandom();
		DESKeySpec dks = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding");
		IvParameterSpec iv = new IvParameterSpec(icv);

		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv, sr);

		return cipher.doFinal(content);
	}

	/**
	 * Algoritmo de descifrado del modo cbc
	 * Los datos de contenido de @param se descifraran
	 * @param key
	 * @return descifra el resultado
	 * @throws GeneralSecurityException
	 */
	public static byte[] decryptByDesCbc(byte[] content, byte[] key, byte[] icv) throws GeneralSecurityException 
	{
		SecureRandom sr = new SecureRandom();
		DESKeySpec dks = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance("DES/CBC/NoPadding");
		IvParameterSpec iv = new IvParameterSpec(icv);

		cipher.init(Cipher.DECRYPT_MODE, secretKey, iv, sr);

		return cipher.doFinal(content);
	}

	/**
	 * algoritmo de cifrado des, modo ECB, modo NoPadding, el byte de datos debe ser un multiplo entero de 8
	 * @param key
	 * El byte de datos de datos @param debe ser un multiplo entero de 8
	 * @ devolver resultado cifrado
	 * @throws GeneralSecurityException
	 */
	public static byte[] encryptByDesEcb(byte[] content, byte[] key) throws GeneralSecurityException 
	{ 
        Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");  
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(new DESKeySpec(key));
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);  
        return cipher.doFinal(content);  
    }

	/**
	 * algoritmo de descifrado, modo ECB, modo NoPadding, el byte de datos debe ser un multiplo entero de 8
	 * @param key clave secreta
	 * El byte de datos de datos @param debe ser un multiplo entero de 8
	 * @throws GeneralSecurityException 
	 * @return
	 */
	public static byte[] decryptByDesEcb(byte[] content, byte[] key) throws GeneralSecurityException 
	{ 
		Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(new DESKeySpec(key));
		cipher.init(Cipher.DECRYPT_MODE, secretKey);  
		return cipher.doFinal(content);  
	}

	/**
	 * Se utiliza para generar codigo de autenticacion de dispositivo externo y codigo de autenticacion de tarjeta de verificacion
	 *  (canal seguro SCP02 de la especificacion GP de Java Card) (criptograma de tarjeta)
	 * B.1.2.1  Full Triple DES MAC 
     * The full triple DES MAC is as defined in [ISO 9797-1] as MAC Algorithm 1 with output transformation 3, 
     * without truncation, and with triple DES taking the place of the block cipher. 
	 * @param datos de contenido que se cifraran
	 * Clave de cifrado de clave @param
	 * @ devuelve 8 bytes despues del resultado cifrado
	 * @throws Exception
	 */
	public static byte[] encryptBy3DesCbcLast8Mac(byte[] content, byte[] key) throws GeneralSecurityException 
	{ 
		byte[] edata = encryptBy3DesCbc(content, key);

		byte[] result = new byte[8];
		System.arraycopy(edata, edata.length - 8, result, 0, 8);
		return result;
	}

	/**
	 * Xor b1 y b2, y luego regresar
	 * @param b1
	 * @param b2
	 * @return XOR result
	 */
	public static byte[] xOr(byte[] b1, byte[] b2) 
	{
		byte[] tXor = new byte[Math.min(b1.length, b2.length)];
		for (int i = 0; i < tXor.length; i++)
			tXor [i] = (byte) (b1 [i] ^ b2 [i]); // XOR (Xor)
		return tXor;
	}

    /**
          * Forma a byte
          * @param n valor entero
          * @param buf result byte array
          * @param posicion de inicio de relleno offset
     */
    public static void int2byte(int n, byte buf[], int offset)
    {
        buf[offset] = (byte)(n >> 24);
        buf[offset + 1] = (byte)(n >> 16);
        buf[offset + 2] = (byte)(n >> 8);
        buf[offset + 3] = (byte)n;
    }

    /**
          * Entero largo al byte
          * @param n valor entero largo 
          * @param buf result byte array
          * @param posicion de inicio de relleno offset
     */
    public static void long2byte(long n, byte buf[], int offset)
    {
        buf[offset] = (byte)(int)(n >> 56);
        buf[offset + 1] = (byte)(int)(n >> 48);
        buf[offset + 2] = (byte)(int)(n >> 40);
        buf[offset + 3] = (byte)(int)(n >> 32);
        buf[offset + 4] = (byte)(int)(n >> 24);
        buf[offset + 5] = (byte)(int)(n >> 16);
        buf[offset + 6] = (byte)(int)(n >> 8);
        buf[offset + 7] = (byte)(int)n;
    }

	/**
	 * Algoritmo ANSI X9.9MAC <br/>
	 * (1) El algoritmo ANSI X9.9MAC solo usa una sola clave larga. <br/>
	 * (2) Los datos MAC se agrupan primero en 8 bytes, expresados como D0 .. Dn, si Dn es inferior a 8 bytes, la cola se llena con el byte 00. <br/>
	 * (3) Use la tecla MAC para encriptar D0, y el resultado del XOR con D1 se usa como la siguiente entrada. <br/>
	 * (4) XOR el resultado de cifrado del paso anterior con el siguiente paquete, y luego cifrar con la clave MAC. <br/>
	 * (5) Hasta el final de todos los paquetes, la mitad izquierda del resultado final se toma como MAC. <br/>
	 * Calcular MAC usando el algoritmo x9.9 (Count MAC by ANSI-x9.9).
	 * 
	 * @param key Datos de clave de 8 bytes
	 * @param buffer de datos a calcular
	 * @throws GeneralSecurityException 
	 */
	public static byte[] calculateANSIX9_9MAC(byte[] key, byte[] data) throws GeneralSecurityException 
	{

		final int dataLength = data.length;
		final int lastLength = dataLength % 8;
		final int lastBlockLength = lastLength == 0 ? 8 : lastLength;
		final int blockCount = dataLength / 8 + (lastLength > 0 ? 1 : 0);

		// Datos divididos (bloque de 8 bytes / bloque)
		byte[][] dataBlock = new byte[blockCount][8];
		for (int i = 0; i < blockCount; i++) {
			int copyLength = i == blockCount - 1 ? lastBlockLength : 8;
			System.arraycopy(data, i * 8, dataBlock[i], 0, copyLength);
		}

		byte[] desXor = new byte[8];
		for (int i = 0; i < blockCount; i++) {
			byte[] tXor = Crypto.xOr(desXor, dataBlock[i]);
			desXor = Crypto.encryptByDesEcb (tXor, key); // cifrado DES
		}
		return desXor;
	}

	/**
	 * Use el algoritmo ANSI x9.19 para calcular MAC (Count MAC by ANSI-x9.19). <br/>
	 * Calcule los resultados de ANSI X9.9 de la siguiente manera <br/>
	 * (6) Descifra el resultado de (5) con la mitad derecha de la tecla MAC. <br/>
	 * (7) El resultado de cifrar (6) con la mitad izquierda de la clave MAC. <br/>
	 * (8) Tome la mitad izquierda del resultado de (7) como MAC. <br/>
	 * @param key Datos de clave de 16 bytes
	 * @param buffer de datos a calcular
	 * @throws GeneralSecurityException 
	 */
	public static byte[] calculateANSIX9_19MAC(byte[] key, byte[] data) throws GeneralSecurityException 
	{
		if (key == null || data == null)
			return null;

		if (key.length != 16) {
			throw new RuntimeException ("Longitud de clave incorrecta");
		}

		byte[] keyLeft = new byte[8];
		byte[] keyRight = new byte[8];
		System.arraycopy(key, 0, keyLeft, 0, 8);
		System.arraycopy(key, 8, keyRight, 0, 8);

		byte[] result99 = calculateANSIX9_9MAC(keyLeft, data);

		byte[] resultTemp = Crypto.decryptByDesEcb(result99, keyRight);
		return Crypto.encryptByDesEcb(resultTemp, keyLeft);
	}

	public static byte[] calculateANSIX9_MAC(byte[] key, byte[] data) throws GeneralSecurityException
	{
		switch (key.length)
		{
		    case 8:
		    	return calculateANSIX9_9MAC(key, data);
		    case 16:
		    	return calculateANSIX9_19MAC(key, data);
		    default:
		    	throw new GeneralSecurityException();
		}
	}
}
