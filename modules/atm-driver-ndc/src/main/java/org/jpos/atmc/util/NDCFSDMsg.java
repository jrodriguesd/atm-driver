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

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOUtil;
import org.jpos.space.Space;
import org.jpos.space.SpaceFactory;
import org.jpos.util.FSDMsg;
import org.jpos.util.Loggeable;

/**
 * General purpose, Field Separator delimited message.
 * 
 * <h1>How to use</h1>
 * <p>
 * The message format (or schema) is defined in xml files containing a schema element, with an optional id attribute, and multiple
 * field elements. A field element is made up of the following attributes:
 * <dl>
 * <dt>id</dt>
 * <dd>The name of the field. This is used in calls to {@link FSDMsg#set(String, String)}. It should be unique amongst the fields in an FSDMsg.</dd>
 * <dt>length</dt>
 * <dd>The maximum length of the data allowed in this field. Fixed length fields will be padded to this length. A zero length is allowed, and can
 * be useful to define extra separator characters in the message.</dd>
 * <dt>type</dt>
 * <dd>The type of the included data, including an optional separator for marking the end of the field and the beginning of the next one. The data type
 * is defined by the first char of the type, and the separator is defined by the following chars. If a field separator is specified, then no
 * padding is done on values for this field.
 * </dd>
 * <dt>key</dt>
 * <dd>If this optional attribute has a value of "true", then fields from another schema, specified by the value, are appended to this schema.</dd>
 * <dt>separator</dt>
 * <dd>An optional attribute containing the separator for the field. This is the preferred method of specifying the separator. See the list of optional</dd>
 * </dl>
 * <p>
 * Possible types are:
 * <dl>
 * <dt>A</dt><dd>Alphanumeric. Padding if any is done with spaces to the right.</dd>
 * <dt>B</dt><dd>Binary. Padding, if any, is done with zeros to the left.</dd>
 * <dt>K</dt><dd>Constant. The value is specified by the field content. No padding is done.</dd>
 * <dt>N</dt><dd>Numeric. Padding, if any, is done with zeros to the left.</dd>
 * </dl>
 * </p>
 * <p>
 * Supported field separators are:
 * <dl>
 * <dt>FS</dt><dd>Field separator using '034' as the separator.</dd>
 * <dt>US</dt><dd>Field separator using '037' as the separator.</dd>
 * <dt>GS</dt><dd>Group separator using '035' as the separator.</dd>
 * <dt>RS</dt><dd>Row separator using '036' as the separator.</dd>
 * <dt>PIPE</dt><dd>Field separator using '|' as the separator.</dd>
 * <dt>EOF</dt><dd>End of File - no separator character is emitted, but also no padding is done. Also if the end of file is reached
 * parsing a message, then no exception is thrown.</dd>
 * <dt>DS</dt><dd>A dummy separator. This is similar to EOF, but the message stream must not end before it is allowed.</dd>
 * <dt>EOM</dt><dd>End of message separator. This reads all bytes available in the stream.  
 * </dl>
 * </p>
 * <p>
 * Key fields allow you to specify a tree of possible message formats. The key fields are the fork points of the tree.
 * Multiple key fields are supported. It is also possible to have more key fields specified in appended schemas.
 * </p>
 * 
 * @author Alejandro Revila
 * @author Mark Salter
 * @author Dave Bergert
 * @since 1.4.7
 */
@SuppressWarnings("unchecked")
public class NDCFSDMsg implements Loggeable, Cloneable  
{
    public static char FS = '\034';
    public static char US = '\037';
    public static char GS = '\035';
    public static char RS = '\036';
    public static char EOF = '\000';
    public static char PIPE = '\u007C';
    public static char EOM = '\000';
    
    private static final Set<String> DUMMY_SEPARATORS = new HashSet<String>(Arrays.asList("DS", "EOM"));
    private static final String EOM_SEPARATOR = "EOM";
    private static final int READ_BUFFER = 8192;
    
    Map<String,String> fields;
    Map<String, Character> separators;

    String baseSchema;
    String basePath;
    byte[] header;
    Charset charset;
    int readCount;

    /**
     * Creates a FSDMsg with a specific base path for the message format schema.
     * @param basePath   schema path, for example: "file:src/data/NDC-" looks for a file src/data/NDC-base.xml
     */
    public NDCFSDMsg (String basePath) {
        this (basePath, "base");
    }
    
    /**
     * Creates a FSDMsg with a specific base path for the message format schema, and a base schema name. For instance,
     * FSDMsg("file:src/data/NDC-", "root") will look for a file: src/data/NDC-root.xml
     * @param basePath   schema path
     * @param baseSchema schema name
     */
    public NDCFSDMsg (String basePath, String baseSchema) {
        fields = new LinkedHashMap<String,String>();
        separators = new LinkedHashMap<String,Character>();
        this.basePath   = basePath;
        this.baseSchema = baseSchema;
        charset = ISOUtil.CHARSET;
        readCount = 0;
        
        setSeparator("FS", FS);
        setSeparator("US", US);
        setSeparator("GS", GS);
        setSeparator("RS", RS);
        setSeparator("EOF", EOF);
        setSeparator("PIPE", PIPE);
    }
    public String getBasePath() {
        return basePath;
    }
    public String getBaseSchema() {
        return baseSchema;
    }
   
    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    /*
     * add a new or override an existing separator type/char pair.
     * 
     *  @param separatorName   string of type used in definition (FS, US etc)
     *  @param separator       char representing type
     */
    public void setSeparator(String separatorName, char separator) {
        separators.put(separatorName, separator);
    }
    
    /*
     * add a new or override an existing separator type/char pair.
     * 
     *  @param separatorName   string of type used in definition (FS, US etc)
     *  @param separator       char representing type
     */
    public void unsetSeparator(String separatorName) {
        if (!separators.containsKey(separatorName))
            throw new IllegalArgumentException("unsetSeparator was attempted for "+
                      separatorName+" which was not previously defined.");

        separators.remove(separatorName);
    }
    
    /**
     * parse message. If the stream ends before the message is completely read, then the method adds an EOF field.
     *
     * @param is input stream
     *
     * @throws IOException
     * @throws JDOMException
     */
    public void unpack (InputStream is) 
        throws IOException, JDOMException {
        try {
            if (is.markSupported())
                is.mark(READ_BUFFER);
            unpack (new InputStreamReader(is, charset), getSchema (baseSchema));
            if (is.markSupported()) {
                is.reset();
                is.skip (readCount);
                readCount = 0;
            }
        } catch (EOFException e) {
            fields.put ("EOF", "true");
        }
    }
    /**
     * parse message. If the stream ends before the message is completely read, then the method adds an EOF field.
     *
     * @param b message image
     *
     * @throws IOException
     * @throws JDOMException
     */
    public void unpack (byte[] b) 
        throws IOException, JDOMException {
        unpack (new ByteArrayInputStream (b));
    }

    /**
     * @return message string
     * @throws org.jdom2.JDOMException
     * @throws java.io.IOException
     * @throws ISOException 
     */
    public String pack () 
        throws JDOMException, IOException, ISOException
    {
        StringBuilder sb = new StringBuilder ();
        pack (getSchema (baseSchema), sb);
        return sb.toString ();
    }
    public byte[] packToBytes () 
        throws JDOMException, IOException, ISOException
    {
        return pack().getBytes(charset);
    }

    protected String get (String id, String type, int length, String defValue, String separator) 
        throws ISOException
    {
        String value = fields.get (id);
        if (value == null)
            value = defValue == null ? "" : defValue;

        type   = type.toUpperCase ();

        switch (type.charAt (0)) {
            case 'N':
                if (!isSeparated(separator)) {
                    value = ISOUtil.zeropad (value, length);
                } // else Leave value unpadded.
                break;
            case 'A':
            	/*
                if (!isSeparated(separator)) {
                    value = ISOUtil.strpad (value, length);
                } // else Leave value unpadded.
                if (value.length() > length)
                    value = value.substring(0,length);
                */
                break;
            case 'K':
                if (defValue != null)
                    value = defValue;
                break;
            case 'B':
                if (length << 1 < value.length())
                    throw new IllegalArgumentException("field content=" + value
                            + " is too long to fit in field " + id
                            + " whose length is " + length);

                if (isSeparated(separator)) {
                    // Convert but do not pad if this field ends with a
                    // separator
                    value = new String(ISOUtil.hex2byte(value), charset);
                } else {
                    value = new String(ISOUtil.hex2byte(ISOUtil.zeropad(
                            value, length << 1).substring(0, length << 1)), charset);
                }
                break;
        }

        if (!isSeparated(separator) || isBinary(type) || EOM_SEPARATOR.equals(separator))
          return value;

        return ISOUtil.blankUnPad(value);
    }
    
    private boolean isSeparated(String separator) {
        /*
         * if type's last two characters appear in our Map of separators,
         * return true
         */
        if (separator == null)
            return false;
        else if (separators.containsKey (separator))
            return true;
        else if (isDummySeparator (separator))
            return true;
        else
            try {
                if (Character.isDefined(Integer.parseInt(separator,16))) {
                    setSeparator(separator, (char)Long.parseLong(separator,16));
                    return true;
                }
            } catch (NumberFormatException ignored) {
                throw new IllegalArgumentException("Invalid separator '"+ separator + "'");
            }
        throw new IllegalArgumentException("isSeparated called on separator="+
                      separator+" which was not previously defined.");
    }

    private boolean isDummySeparator(String separator) {
        return DUMMY_SEPARATORS.contains(separator);
    }
    
    private boolean isBinary(String type) {
        /*
         * if type's first digit is a 'B' return true
         */
        return type.startsWith("B");
    }
    
    public boolean isSeparator(byte b) {
        return separators.containsValue((char) b);
    }
    
    private String getSeparatorType(String type) {
        if (type.length() > 2) {
            return type.substring(1);
        }
        return null;
    }
    
    private char getSeparator(String separator) {
        if (separators.containsKey(separator))
            return separators.get(separator);
        else if (isDummySeparator (separator)) {
            // Dummy separator type, return 0 to indicate nothing to add.
            return 0;
        }

        throw new IllegalArgumentException("getSeparator called on separator="+
                      separator+" which was not previously defined.");
    }

    protected void pack (Element schema, StringBuilder sb)
        throws JDOMException, IOException, ISOException
    {
        String keyOff = "";
        String defaultKey = "";
		List<Element> childList = (List<Element>)schema.getChildren();
		int childListSize = childList.size();
        for (int i = 0; i < childListSize; i++)
		{
        	Element elem = childList.get(i);
			String name = elem.getName();
            String separator = elem.getAttributeValue ("separator");

			if (name.equals("field_group") )
			{
				inFieldGroup = true;
				groupSeparator = separator;
				pack (elem, sb);
				continue;
			}

            String id    = elem.getAttributeValue ("id");
            int length   = Integer.parseInt (elem.getAttributeValue ("length"));
            String type  = elem.getAttributeValue ("type");
            // For backward compatibility, look for a separator at the end of the type attribute, if no separator has been defined.
            if (type != null && separator == null) {
            	separator = getSeparatorType (type);
            }
            boolean key  = "true".equals (elem.getAttributeValue ("key"));
            Map properties = key ? loadProperties(elem) : Collections.EMPTY_MAP;
            String defValue = elem.getText();
            // If properties were specified, then the defValue contains lots of \n and \t in it. It should just be set to the empty string, or null.
            if (!properties.isEmpty()) {
            	defValue = defValue.replace("\n", "").replace("\t", "").replace("\r", "");
            }

            String value;
			if (inFieldGroup)
			{
		        type = type.substring(0, 1) + groupSeparator;
                value = get (id, type, length, defValue, groupSeparator);
				
				if ( i == (childListSize - 1) ) // Ultimo Elemento del field_group
                    separator = groupSeparator;
				else
                    separator = null;
			}
			else
                value = get (id, type, length, defValue, separator);

            sb.append (value);

            if (isSeparated(separator)) {
                char c = getSeparator(separator);
                if (c > 0)
                    sb.append(c);
            }
            if (key) {
                String v = isBinary(type) ? ISOUtil.hexString(value.getBytes(charset)) : value;
                keyOff = keyOff + normalizeKeyValue(v, properties);
                defaultKey += elem.getAttributeValue ("default-key");
            }
        }
        if (keyOff.length() > 0) 
            pack (getSchema (getId (schema), keyOff, defaultKey), sb);
		if (inFieldGroup) inFieldGroup = false;
    }

    private Map loadProperties(Element elem) {
    	Map props = new HashMap ();
        for (Element prop : (List<Element>)elem.getChildren ("property")) {
    		String name = prop.getAttributeValue ("name");
    		String value = prop.getAttributeValue ("value");
    		props.put (name, value);
    	}
	    return props;
    }

	private String normalizeKeyValue(String value, Map<?,String> properties) {
    	if (properties.containsKey(value)) {
            return properties.get(value);
    	}
    	return ISOUtil.normalize(value);
    }

    private boolean inFieldGroup = false;
    private String  groupSeparator;
    protected void unpack (InputStreamReader r, Element schema)
        throws IOException, JDOMException {

        String keyOff = "";
        String defaultKey = "";
		List<Element> childList = (List<Element>)schema.getChildren();
		int childListSize = childList.size();
        // for (Element elem : childList) 
        for (int i = 0; i < childListSize; i++)
		{
        	Element elem = childList.get(i);
			String name = elem.getName();
            String separator = elem.getAttributeValue ("separator");

			if (name.equals("field_group") )
			{
				inFieldGroup = true;
				groupSeparator = separator;
				unpack (r, elem);
				continue;
			}

            String id    = elem.getAttributeValue ("id");
            int length   = Integer.parseInt (elem.getAttributeValue ("length"));
            String type  = elem.getAttributeValue ("type").toUpperCase();
            if (type != null && separator == null) {
            	separator = getSeparatorType (type);
            }
            boolean key  = "true".equals (elem.getAttributeValue ("key"));
            Map properties = key ? loadProperties(elem) : Collections.EMPTY_MAP;
			String value;
			if (inFieldGroup)
			{   
		        type = type.substring(0, 1) + groupSeparator;
				if ( i == (childListSize - 1) ) // Ultimo Elemento del field_group
                    value = readField(r, id, length, type, groupSeparator, true);
				else
                    value = readField(r, id, length, type, groupSeparator, false);

				if (value.length() < length)
				{
				    inFieldGroup = false;
					return;
				}
			}
			else
                value = readField(r, id, length, type, separator, true);
            
            if (key) {
                keyOff = keyOff + normalizeKeyValue(value, properties);
                defaultKey += elem.getAttributeValue ("default-key");
            }
            if ("K".equals(type) && !value.equals (elem.getText()))
                throw new IllegalArgumentException (
                    "Field "+id 
                       + " value='"     +value
                       + "' expected='" + elem.getText () + "'"
                );
        }
        if (keyOff.length() > 0) {
            unpack(r, getSchema (getId (schema), keyOff, defaultKey));
        }
		if (inFieldGroup) inFieldGroup = false;
    }
    private String getId (Element e) {
        String s = e.getAttributeValue ("id");
        return s == null ? "" : s;
    }
    protected String read (InputStreamReader r, int len, String type, String separator, boolean skip)
        throws IOException 
    {
        StringBuilder sb = new StringBuilder();
        char[] c = new char[1];
        boolean expectSeparator = isSeparated(separator);
        boolean separated = expectSeparator;

        if (EOM_SEPARATOR.equals(separator)) {
            // Grab what's left.
            char[] rest = new char[32];
            int con = 0;
            while ((con = r.read(rest, 0, rest.length)) >= 0) {
              if (rest.length == con)
                sb.append(rest);
              else
                sb.append(Arrays.copyOf(rest, con));
            }
        } else if (isDummySeparator(separator)) {
            /*
             * No need to look for a seperator, that is not there! Try and take
             * len bytes from the is.
             */
            for (int i = 0; i < len; i++) {
                if (r.read(c) < 0) {
                    break; // end of stream indicates end of field?
                }
                sb.append(c[0]);
            }
        } 
		else 
		{
			int rc = 0;
            for (int i = 0; i < len; i++) 
			{
				rc = r.read(c);
                if (rc < 0) 
				{
                    if (!"EOF".equals(separator))
                        throw new EOFException();
                    else {
                        separated = false;
                        break;
                    }
                }
                if (expectSeparator && c[0] == getSeparator(separator)) 
				{
                    separated = false;
                    break;
                }
                sb.append(c[0]);
            }

            if ( separated && skip )
			{
				while ( ! (expectSeparator && c[0] == getSeparator(separator)) )
				{
				    rc = r.read(c);
                    if (rc < 0) 
				    {
                        if (!"EOF".equals(separator))
                            throw new EOFException();
                        else {
                            separated = false;
                            break;
                        }
                    }

				}
			}

            if (separated && !"EOF".equals(separator) && rc < 0) 
			{
                throw new EOFException();
            }
        }
        readCount += sb.length();
        return sb.toString();
    }
    protected String readField (InputStreamReader r, String fieldName, int len,
        String type, String separator, boolean skip) throws IOException
    {
        String fieldValue = read (r, len, type, separator, skip);
        
        if (isBinary(type))
            fieldValue = ISOUtil.hexString (fieldValue.getBytes (charset));
		
		if (fieldValue.length() > 0)
            fields.put (fieldName, fieldValue);
        // System.out.println ("++++ "+fieldName + ":>" + fieldValue + "< " + type + "," + isBinary(type));
        return fieldValue;
    }
    public void set (String name, String value) {
        if (value != null)
            fields.put (name, value);
        else
            fields.remove (name);
    }
    public void setHeader (byte[] h) {
        this.header = h;
    }
    public byte[] getHeader () {
        return header;
    }
    public String getHexHeader () {
        return header != null ? ISOUtil.hexString (header).substring (2) : "";
    }
    public String get (String fieldName) {
        return fields.get (fieldName);
    }
    public String get (String fieldName, String def) {
        String s = fields.get (fieldName);
        return s != null ? s : def;
    }
    public void copy (String fieldName, NDCFSDMsg msg) {
        fields.put (fieldName, msg.get (fieldName));
    }
    public byte[] getHexBytes (String name) {
        String s = get (name);
        return s == null ? null : ISOUtil.hex2byte (s);
    }
    @SuppressWarnings("PMD.EmptyCatchBlock")
    public int getInt (String name) {
        int i = 0;
        try {
            i = Integer.parseInt (get (name));
        } catch (Exception ignored) { }
        return i;
    }
    @SuppressWarnings("PMD.EmptyCatchBlock")
    public int getInt (String name, int def) {
        int i = def;
        try {
            i = Integer.parseInt (get (name));
        } catch (Exception ignored) { }
        return i;
    }
    public Element toXML () {
        Element e = new Element ("message");
        if (header != null) {
            e.addContent (
                new Element ("header")
                    .setText (getHexHeader ())
            );
        }
        for (String fieldName :fields.keySet()) {
            Element inner = new Element (fieldName);
            inner.addContent (ISOUtil.normalize (fields.get (fieldName)));
            e.addContent (inner);
        }
        return e;
    }
    protected Element getSchema () 
        throws JDOMException, IOException {
        return getSchema (baseSchema);
    }
    protected Element getSchema (String message) 
        throws JDOMException, IOException {
        return getSchema (message, "", null);
    }
    protected Element getSchema (String prefix, String suffix, String defSuffix)
        throws JDOMException, IOException {
        StringBuilder sb = new StringBuilder (basePath);
        sb.append (prefix);
        prefix = sb.toString(); // little hack, we'll reuse later with defSuffix
        sb.append (suffix);
        sb.append (".xml");
        String uri = sb.toString ();

        Space sp = SpaceFactory.getSpace();
        Element schema = (Element) sp.rdp (uri);
        if (schema == null) {
            schema = loadSchema(uri, defSuffix == null);
            if (schema == null && defSuffix != null) {
                sb = new StringBuilder (prefix);
                sb.append (defSuffix);
                sb.append (".xml");
                schema = loadSchema(sb.toString(), true);
            }
            sp.out (uri, schema);
        }
        return schema;
    }

    protected Element loadSchema(String uri, boolean throwex)
        throws JDOMException, IOException {
        SAXBuilder builder = new SAXBuilder();
        if (uri.startsWith("jar:") && uri.length()>4) {
            InputStream is = schemaResouceInputStream(uri.substring(4));
            if (is == null && throwex)
                throw new FileNotFoundException(uri + " not found");
            else if (is != null)
                return builder.build(is).getRootElement();
            else
                return null;
        }

        URL url = new URL(uri);
        try {
            return builder.build(url).getRootElement();
        } catch (FileNotFoundException ex) {
            if (throwex)
                throw ex;
            return null;
        }
    }

    protected InputStream schemaResouceInputStream(String resource)
        throws JDOMException, IOException {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        cl = cl==null ? ClassLoader.getSystemClassLoader() : cl;
        return cl.getResourceAsStream(resource);
    }

    /**
     * @return message's Map
     */
    public Map getMap () {
        return fields;
    }
    public void setMap (Map fields) {
        this.fields = fields;
    }

    @Override
    public void dump (PrintStream p, String indent) {
        String inner = indent + "  ";
        p.println (indent + "<fsdmsg schema='" + basePath + baseSchema  + "'>");
        if (header != null) {
            append (p, "header", getHexHeader(), inner);
        }
        for (String f :fields.keySet())
            append (p, f, fields.get (f), inner);
        p.println (indent + "</fsdmsg>");
    }
    private void append (PrintStream p, String f, String v, String indent) {
        p.println (indent + f + ": '" + v + "'");
    }
    public boolean hasField(String fieldName) {
        return fields.containsKey(fieldName);
    }

    @Override
    public Object clone() {
        try {              
        	NDCFSDMsg m = (NDCFSDMsg) super.clone();
            m.fields = (Map) ((LinkedHashMap) fields).clone();
            return m;
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
    }
    public void merge (NDCFSDMsg m) {
        for (Entry<String,String> entry: m.fields.entrySet())
             set (entry.getKey(), entry.getValue());
    }
}
