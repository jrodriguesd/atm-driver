package org.jpos.rest;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOUtil;
import org.jpos.iso.packager.GenericPackager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

import java.io.InputStream;


@Path("/packIsoEle")
public class PackISOMessage {

        @GET
        @Produces({MediaType.APPLICATION_JSON})
        @Consumes(MediaType.TEXT_PLAIN)
        @Path("/mti/{mti}/f2/{f2}/f3/{f3}/f4/{f4}/f5/{f5}")
                //"/f6/{f6}/f7/{f7}/f8/{f8}/f9/{f9}/f10/{f10}/f11/{f11}/f12/{f12}/f13/{f13}/f14/{f14}/f15/{f15}/f16/{f16}/f17/{f17}/f18/{f18}/f19/{f19}/f20/{f20}/f21/{f21}/f22/{f22}/f23/{f23}/f24/{f24}/f25/{f25}/f26/{f26}/f27/{f27}/f28/{f28}/f29/{f29}/f30/{f30}/f31/{f31}/f32/{f32}/f33/{f33}/f34/{f34}/f35/{f35}/f36/{f36}/f37/{f37}/f38/{f38}/f39/{f39}/f40/{f40}/f41/{f41}/f42/{f42}/f43/{f43}/f44/{f44}/f45/{f45}/f46/{f46}/f47/{f47}/f48/{f48}/f49/{f49}/f50/{f50}/f51/{f51}/f52/{f52}/f53/{f53}/f54/{f54}/f55/{f55}/f56/{f56}/f57/{f57}/f58/{f58}/f59/{f59}/f60/{f60}/f61/{f61}/f62/{f62}/f63/{f63}/f64/{f64}/f65/{f65}/f66/{f66}/f67/{f67}/f68/{f68}/f69/{f69}/f70/{f70}/f71/{f71}/f72/{f72}/f73/{f73}/f74/{f74}/f75/{f75}/f76/{f76}/f77/{f77}/f78/{f78}/f79/{f79}/f80/{f80}/f81/{f81}/f82/{f82}/f83/{f83}/f84/{f84}/f85/{f85}/f86/{f86}/f87/{f87}/f88/{f88}/f89/{f89}/f90/{f90}/f91/{f91}/f92/{f92}/f93/{f93}/f94/{f94}/f95/{f95}/f96/{f96}/f97/{f97}/f98/{f98}/f99/{f99}/f100/{f100}/f101/{f101}/f102/{f102}/f103/{f103}/f104/{f104}/f105/{f105}/f106/{f106}/f107/{f107}/f108/{f108}/f109/{f109}/f110/{f110}/f111/{f111}/f112/{f112}/f113/{f113}/f114/{f114}/f115/{f115}/f116/{f116}/f117/{f117}/f118/{f118}/f119/{f119}/f120/{f120}/f121/{f121}/f122/{f122}/f123/{f123}/f124/{f124}/f125/{f125}/f126/{f126}/f127/{f127}/f128/{f128}")
        //@Path("/{name}/{family}")


        public Response echoGet(
                //@PathParam("name") String name,
                @PathParam("mti") String mti,
                //f1 is for the bit map , no need to specify, auto generated to selected the fields
                @PathParam("f2") String f2, @PathParam("f3") String f3, @PathParam("f4") String f4, @PathParam("f5") String f5,
                //@PathParam("f6") String f6, @PathParam("f7") String f7, @PathParam("f8") String f8, @PathParam("f9") String f9, @PathParam("f10") String f10, @PathParam("f11") String f11, @PathParam("f12") String f12, @PathParam("f13") String f13, @PathParam("f14") String f14, @PathParam("f15") String f15, @PathParam("f16") String f16, @PathParam("f17") String f17, @PathParam("f18") String f18, @PathParam("f19") String f19, @PathParam("f20") String f20, @PathParam("f21") String f21, @PathParam("f22") String f22, @PathParam("f23") String f23, @PathParam("f24") String f24, @PathParam("f25") String f25, @PathParam("f26") String f26, @PathParam("f27") String f27, @PathParam("f28") String f28, @PathParam("f29") String f29, @PathParam("f30") String f30, @PathParam("f31") String f31, @PathParam("f32") String f32, @PathParam("f33") String f33, @PathParam("f34") String f34, @PathParam("f35") String f35, @PathParam("f36") String f36, @PathParam("f37") String f37, @PathParam("f38") String f38, @PathParam("f39") String f39, @PathParam("f40") String f40, @PathParam("f41") String f41, @PathParam("f42") String f42, @PathParam("f43") String f43, @PathParam("f44") String f44, @PathParam("f45") String f45, @PathParam("f46") String f46, @PathParam("f47") String f47, @PathParam("f48") String f48, @PathParam("f49") String f49, @PathParam("f50") String f50, @PathParam("f51") String f51, @PathParam("f52") String f52, @PathParam("f53") String f53, @PathParam("f54") String f54, @PathParam("f55") String f55, @PathParam("f56") String f56, @PathParam("f57") String f57, @PathParam("f58") String f58, @PathParam("f59") String f59, @PathParam("f60") String f60, @PathParam("f61") String f61, @PathParam("f62") String f62, @PathParam("f63") String f63, @PathParam("f64") String  f64, @PathParam("f65") String  f65, @PathParam("f66") String f66, @PathParam("f67") String f67, @PathParam("f68") String f68, @PathParam("f69") String f69, @PathParam("f70") String f70, @PathParam("f71") String f71, @PathParam("f72") String f72, @PathParam("f73") String f73, @PathParam("f74") String f74, @PathParam("f75") String f75, @PathParam("f76") String f76, @PathParam("f77") String f77, @PathParam("f78") String f78, @PathParam("f79") String f79, @PathParam("f80") String f80, @PathParam("f81") String f81, @PathParam("f82") String f82, @PathParam("f83") String f83, @PathParam("f84") String f84, @PathParam("f85") String f85, @PathParam("f86") String f86, @PathParam("f87") String f87, @PathParam("f88") String f88, @PathParam("f89") String f89, @PathParam("f90") String f90, @PathParam("f91") String f91, @PathParam("f92") String f92, @PathParam("f93") String f93, @PathParam("f94") String f94, @PathParam("f95") String f95, @PathParam("f96") String f96, @PathParam("f97") String f97, @PathParam("f98") String f98, @PathParam("f99") String f99, @PathParam("f100") String f100, @PathParam("f101") String f101, @PathParam("f102") String f102, @PathParam("f103") String f103, @PathParam("f104") String f104, @PathParam("f105") String f105, @PathParam("f106") String f106, @PathParam("f107") String f107, @PathParam("f108") String f108, @PathParam("f109") String f109, @PathParam("f110") String f110, @PathParam("f111") String f111, @PathParam("f112") String f112, @PathParam("f113") String f113, @PathParam("f114") String f114, @PathParam("f115") String f115, @PathParam("f116") String f116, @PathParam("f117") String f117, @PathParam("f118") String f118, @PathParam("f119") String f119, @PathParam("f120") String f120, @PathParam("f121") String f121, @PathParam("f122") String f122, @PathParam("f123") String f123, @PathParam("f124") String f124, @PathParam("f125") String f125, @PathParam("f126") String f126, @PathParam("f127") String f127, @PathParam("f128") String f128,
                String tMsgFromBody
        ) {
            //convert strings to byte
            //f64 = ISOUtil.hex2byte("00000000");
            PackISOMessage iso = new PackISOMessage();
            String message = null;
            try {
                //message = iso.buildISOMessage();
                try {
                    // Load package from resources directory.
                    InputStream is = getClass().getResourceAsStream("/fields.xml");
                    GenericPackager packager = new GenericPackager(is);

                    ISOMsg isoMsg = new ISOMsg();
                    isoMsg.setPackager(packager);
                    isoMsg.setMTI(mti);
                    //set all the fields
                   isoMsg.set(2, f2 ); isoMsg.set(3, f3 ); isoMsg.set(4, f4 ); isoMsg.set(5, f5 );
                    //isoMsg.set(6, f6 ); isoMsg.set(7, f7 ); isoMsg.set(8, f8 ); isoMsg.set(9, f9 ); isoMsg.set(10, f10 ); isoMsg.set(11, f11 ); isoMsg.set(12, f12 ); isoMsg.set(13, f13 ); isoMsg.set(14, f14 ); isoMsg.set(15, f15 ); isoMsg.set(16, f16 ); isoMsg.set(17, f17 ); isoMsg.set(18, f18 ); isoMsg.set(19, f19 ); isoMsg.set(20, f20 ); isoMsg.set(21, f21 ); isoMsg.set(22, f22 ); isoMsg.set(23, f23 ); isoMsg.set(24, f24 ); isoMsg.set(25, f25 ); isoMsg.set(26, f26 ); isoMsg.set(27, f27 ); isoMsg.set(28, f28 ); isoMsg.set(29, f29 ); isoMsg.set(30, f30 ); isoMsg.set(31, f31 ); isoMsg.set(32, f32 ); isoMsg.set(33, f33 ); isoMsg.set(34, f34 ); isoMsg.set(35, f35 ); isoMsg.set(36, f36 ); isoMsg.set(37, f37 ); isoMsg.set(38, f38 ); isoMsg.set(39, f39 ); isoMsg.set(40, f40 ); isoMsg.set(41, f41 ); isoMsg.set(42, f42 ); isoMsg.set(43, f43 ); isoMsg.set(44, f44 ); isoMsg.set(45, f45 ); isoMsg.set(46, f46 ); isoMsg.set(47, f47 ); isoMsg.set(48, f48 ); isoMsg.set(49, f49 ); isoMsg.set(50, f50 ); isoMsg.set(51, f51 ); isoMsg.set(52, f52 ); isoMsg.set(53, f53 ); isoMsg.set(54, f54 ); isoMsg.set(55, f55 ); isoMsg.set(56, f56 ); isoMsg.set(57, f57 ); isoMsg.set(58, f58 ); isoMsg.set(59, f59 ); isoMsg.set(60, f60 ); isoMsg.set(61, f61 ); isoMsg.set(62, f62 ); isoMsg.set(63, f63 ); isoMsg.set(64, f64 ); isoMsg.set(65, f65 ); isoMsg.set(66, f66 ); isoMsg.set(67, f67 ); isoMsg.set(68, f68 ); isoMsg.set(69, f69 ); isoMsg.set(70, f70 ); isoMsg.set(71, f71 ); isoMsg.set(72, f72 ); isoMsg.set(73, f73 ); isoMsg.set(74, f74 ); isoMsg.set(75, f75 ); isoMsg.set(76, f76 ); isoMsg.set(77, f77 ); isoMsg.set(78, f78 ); isoMsg.set(79, f79 ); isoMsg.set(80, f80 ); isoMsg.set(81, f81 ); isoMsg.set(82, f82 ); isoMsg.set(83, f83 ); isoMsg.set(84, f84 ); isoMsg.set(85, f85 ); isoMsg.set(86, f86 ); isoMsg.set(87, f87 ); isoMsg.set(88, f88 ); isoMsg.set(89, f89 ); isoMsg.set(90, f90 ); isoMsg.set(91, f91 ); isoMsg.set(92, f92 ); isoMsg.set(93, f93 ); isoMsg.set(94, f94 ); isoMsg.set(95, f95 ); isoMsg.set(96, f96 ); isoMsg.set(97, f97 ); isoMsg.set(98, f98 ); isoMsg.set(99, f99 ); isoMsg.set(100, f100 ); isoMsg.set(101, f101 ); isoMsg.set(102, f102 ); isoMsg.set(103, f103 ); isoMsg.set(104, f104 ); isoMsg.set(105, f105 ); isoMsg.set(106, f106 ); isoMsg.set(107, f107 ); isoMsg.set(108, f108 ); isoMsg.set(109, f109 ); isoMsg.set(110, f110 ); isoMsg.set(111, f111 ); isoMsg.set(112, f112 ); isoMsg.set(113, f113 ); isoMsg.set(114, f114 ); isoMsg.set(115, f115 ); isoMsg.set(116, f116 ); isoMsg.set(117, f117 ); isoMsg.set(118, f118 ); isoMsg.set(119, f119 ); isoMsg.set(120, f120 ); isoMsg.set(121, f121 ); isoMsg.set(122, f122 ); isoMsg.set(123, f123 ); isoMsg.set(124, f124 ); isoMsg.set(125, f125 ); isoMsg.set(126, f126 ); isoMsg.set(127, f127 ); isoMsg.set(128, f128 );
//                    isoMsg.set(3, "000010");
//                    isoMsg.set(4, "1500");
//                    isoMsg.set(7, "1206041200");
//                    isoMsg.set(11, "000001");
//                    isoMsg.set(41, "12340001");
//                    isoMsg.set(49, "840");
                    printISOMessage(isoMsg);

                    byte[] result = isoMsg.pack();
                    message = new String(result);
                } catch (ISOException e) {
                    throw new Exception(e);
                }


                System.out.printf("\"Message\": \"%s\",%n", message);
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


            Map<String, Object> resp = new HashMap<>();
            resp.put("success", "true");
            //resp.put("Name", name);
            resp.put("PackedIsoMsg", message);
            //resp.put("IsoMsgFromBody", tMsgFromBody);
            Response.ResponseBuilder rb = Response.ok(resp,
                    MediaType.APPLICATION_JSON).status(Response.Status.OK);
            return rb.build();
        }






        private void printISOMessage(ISOMsg isoMsg) {
            try {
                System.out.printf("\"MTI\":\"%s\",%n", isoMsg.getMTI());
                for (int i = 1; i <= isoMsg.getMaxField(); i++) {
                    if (isoMsg.hasField(i)) {
                        System.out.printf("\"%s\": \"%s\",%n", i, isoMsg.getString(i));
                    }
                }
            } catch (ISOException e) {
                e.printStackTrace();
            }
        }
    }