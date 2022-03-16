package org.jpos.rest;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.packager.GenericPackager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

import java.io.InputStream;

@Path("/getIsoEleNO")
public class UnpackISOMessageNO {
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes(MediaType.TEXT_PLAIN)
    @Path("/{IsoMsgFromGet}")

    //new main
    public Response echoGet(
            //@PathParam("name") String name,
            @PathParam("IsoMsgFromGet") String tMsgFromGet,
            String tMsgFromBody
    ) {
        //removed the
        UnpackISOMessageNO iso = new UnpackISOMessageNO();
        ISOMsg isoMsg = null;
        try {
            // use parameter from the body or the url GET tMsgFromGet/tMsgFromBody
            isoMsg = iso.parseISOMessage(tMsgFromGet);
            //iso.printISOMessage(isoMsg);
        } catch (Exception e) {
            e.printStackTrace();

            //display the error in json
            Map<String, Object> respError = new HashMap<>();
            respError.put("success", "false");
            respError.put("ErrorMessage", e);

            Response.ResponseBuilder rbError = Response.ok(respError,
                    MediaType.APPLICATION_JSON).status(Response.Status.OK);
            return rbError.header("Access-Control-Allow-Origin", "*").build();


        }

        //building the actual json response
        Map<String, Object> resp = new HashMap<>();

        //resp.put("ISO8583msg", tMsgFromBody);
        resp.put("success", "true");
        //resp.put("Name", name);

        //display the contents of the ISO Message
        try {
            System.out.printf("\"MTI\":\"%s\",%n", isoMsg.getMTI());
            for (int i = 1; i <= isoMsg.getMaxField(); i++) {
                if (isoMsg.hasField(i)) {
                    System.out.printf("\"%s\": \"%s\",%n", i, isoMsg.getString(i));


                    //THE ISO DATA ELEMENTS
                    resp.put("MTI",isoMsg.getMTI());

                    resp.put("1",isoMsg.getString(1));
                    resp.put("2",isoMsg.getString(2));
                    resp.put("3",isoMsg.getString(3));
                    resp.put("4",isoMsg.getString(4));
                    resp.put("5",isoMsg.getString(5));
                    resp.put("6",isoMsg.getString(6));
                    resp.put("7",isoMsg.getString(7));
                    resp.put("8",isoMsg.getString(8));
                    resp.put("9",isoMsg.getString(9));
                    resp.put("10",isoMsg.getString(10));
                    resp.put("11",isoMsg.getString(11));
                    resp.put("12",isoMsg.getString(12));
                    resp.put("13",isoMsg.getString(13));
                    resp.put("14",isoMsg.getString(14));
                    resp.put("15",isoMsg.getString(15));
                    resp.put("16",isoMsg.getString(16));
                    resp.put("17",isoMsg.getString(17));
                    resp.put("18",isoMsg.getString(18));
                    resp.put("19",isoMsg.getString(19));
                    resp.put("20",isoMsg.getString(20));
                    resp.put("21",isoMsg.getString(21));
                    resp.put("22",isoMsg.getString(22));
                    resp.put("23",isoMsg.getString(23));
                    resp.put("24",isoMsg.getString(24));
                    resp.put("25",isoMsg.getString(25));
                    resp.put("26",isoMsg.getString(26));
                    resp.put("27",isoMsg.getString(27));
                    resp.put("28",isoMsg.getString(28));
                    resp.put("29",isoMsg.getString(29));
                    resp.put("30",isoMsg.getString(30));
                    resp.put("31",isoMsg.getString(31));
                    resp.put("32",isoMsg.getString(32));
                    resp.put("33",isoMsg.getString(33));
                    resp.put("34",isoMsg.getString(34));
                    resp.put("35",isoMsg.getString(35));
                    resp.put("36",isoMsg.getString(36));
                    resp.put("37",isoMsg.getString(37));
                    resp.put("38",isoMsg.getString(38));
                    resp.put("39",isoMsg.getString(39));
                    resp.put("40",isoMsg.getString(40));
                    resp.put("41",isoMsg.getString(41));
                    resp.put("42",isoMsg.getString(42));
                    resp.put("43",isoMsg.getString(43));
                    resp.put("44",isoMsg.getString(44));
                    resp.put("45",isoMsg.getString(45));
                    resp.put("46",isoMsg.getString(46));
                    resp.put("47",isoMsg.getString(47));
                    resp.put("48",isoMsg.getString(48));
                    resp.put("49",isoMsg.getString(49));
                    resp.put("50",isoMsg.getString(50));
                    resp.put("51",isoMsg.getString(51));
                    resp.put("52",isoMsg.getString(52));
                    resp.put("53",isoMsg.getString(53));
                    resp.put("54",isoMsg.getString(54));
                    resp.put("55",isoMsg.getString(55));
                    resp.put("56",isoMsg.getString(56));
                    resp.put("57",isoMsg.getString(57));
                    resp.put("58",isoMsg.getString(58));
                    resp.put("59",isoMsg.getString(59));
                    resp.put("60",isoMsg.getString(60));
                    resp.put("61",isoMsg.getString(61));
                    resp.put("62",isoMsg.getString(62));
                    resp.put("63",isoMsg.getString(63));
                    resp.put("64",isoMsg.getString(64));
                    resp.put("65",isoMsg.getString(65));
                    resp.put("66",isoMsg.getString(66));
                    resp.put("67",isoMsg.getString(67));
                    resp.put("68",isoMsg.getString(68));
                    resp.put("69",isoMsg.getString(69));
                    resp.put("70",isoMsg.getString(70));
                    resp.put("71",isoMsg.getString(71));
                    resp.put("72",isoMsg.getString(72));
                    resp.put("73",isoMsg.getString(73));
                    resp.put("74",isoMsg.getString(74));
                    resp.put("75",isoMsg.getString(75));
                    resp.put("76",isoMsg.getString(76));
                    resp.put("77",isoMsg.getString(77));
                    resp.put("78",isoMsg.getString(78));
                    resp.put("79",isoMsg.getString(79));
                    resp.put("80",isoMsg.getString(80));
                    resp.put("81",isoMsg.getString(81));
                    resp.put("82",isoMsg.getString(82));
                    resp.put("83",isoMsg.getString(83));
                    resp.put("84",isoMsg.getString(84));
                    resp.put("85",isoMsg.getString(85));
                    resp.put("86",isoMsg.getString(86));
                    resp.put("87",isoMsg.getString(87));
                    resp.put("88",isoMsg.getString(88));
                    resp.put("89",isoMsg.getString(89));
                    resp.put("90",isoMsg.getString(90));
                    resp.put("91",isoMsg.getString(91));
                    resp.put("92",isoMsg.getString(92));
                    resp.put("93",isoMsg.getString(93));
                    resp.put("94",isoMsg.getString(94));
                    resp.put("95",isoMsg.getString(95));
                    resp.put("96",isoMsg.getString(96));
                    resp.put("97",isoMsg.getString(97));
                    resp.put("98",isoMsg.getString(98));
                    resp.put("99",isoMsg.getString(99));
                    resp.put("100",isoMsg.getString(100));
                    resp.put("101",isoMsg.getString(101));
                    resp.put("102",isoMsg.getString(102));
                    resp.put("103",isoMsg.getString(103));
                    resp.put("104",isoMsg.getString(104));
                    resp.put("105",isoMsg.getString(105));
                    resp.put("106",isoMsg.getString(106));
                    resp.put("107",isoMsg.getString(107));
                    resp.put("108",isoMsg.getString(108));
                    resp.put("109",isoMsg.getString(109));
                    resp.put("110",isoMsg.getString(110));
                    resp.put("111",isoMsg.getString(111));
                    resp.put("112",isoMsg.getString(112));
                    resp.put("113",isoMsg.getString(113));
                    resp.put("114",isoMsg.getString(114));
                    resp.put("115",isoMsg.getString(115));
                    resp.put("116",isoMsg.getString(116));
                    resp.put("117",isoMsg.getString(117));
                    resp.put("118",isoMsg.getString(118));
                    resp.put("119",isoMsg.getString(119));
                    resp.put("120",isoMsg.getString(120));
                    resp.put("121",isoMsg.getString(121));
                    resp.put("122",isoMsg.getString(122));
                    resp.put("123",isoMsg.getString(123));
                    resp.put("124",isoMsg.getString(124));
                    resp.put("125",isoMsg.getString(125));
                    resp.put("126",isoMsg.getString(126));
                    resp.put("127",isoMsg.getString(127));
                    resp.put("128",isoMsg.getString(128));


                }
            }
        } catch (ISOException e) {
            e.printStackTrace();
        }



        //resp.put("IsoMsgFromBody", isoMsg);

        //display the IsoMsg itself in the JSON
        //resp.put("IsoMsgFromGet", tMsgFromGet);

        Response.ResponseBuilder rb = Response.ok(resp,
                MediaType.APPLICATION_JSON).status(Response.Status.OK);
        return rb.header("Access-Control-Allow-Origin", "*").build();
    }




    public ISOMsg parseISOMessage(String message) throws Exception {
//        String message= "0200FABF469F29E0C12000000000040000221646603898000201580120000000000040000000000005770411101202714425000136931012000411220404110411605190200100C00000000C00000000C00000000C0000000006476117374660389800020158D22042011384200106210910110013693201TERMID01HCB_CODE       HEYZIEL                MAURITIUS      MU480936004151001000000012591313000000516310152001012000001040002682008040000000000VB12ISS     CALT24R15Snk013693013693CALVisaGrp                48000182218Postilion:MetaData270218Postilion:OBS:dCvv111214TRANSACTION_ID111220OriginalPosEntryMode111218Postilion:OBS:dCvv110214TRANSACTION_ID215309101367222006220OriginalPosEntryMode149020";

        //\"{string}" , {newline}
        System.out.printf("\"Message\": \"%s\",%n", message);
        try {
            // Load package from resources directory.
            InputStream is = getClass().getResourceAsStream("/fields.xml");
            GenericPackager packager = new GenericPackager(is);
            ISOMsg isoMsg = new ISOMsg();
            isoMsg.setPackager(packager);
            isoMsg.unpack(message.getBytes());
            return isoMsg;
        } catch (ISOException e) {
            throw new Exception(e);
        }
    }


}