/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Traitement;

/**
 *
 * @author Mohammed
 */
import java.io.BufferedWriter;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class TraitementXml {
//
//    public static void main(String argv[]) throws IOException, JAXBException, InterruptedException, ExecutionException {
//        CreeInput("رحب الرئيس الأميركي المنتخب جو بايدن بقرار الرئيس دونالد ترامب عدم رغبته بحضور مراسم التنصيب، ووصف ترامب بأنه غير مؤهل للسلطة، محملا إياه مسؤولية اقتحام مبنى الكونغرس، ومؤكدا استعداده لاستلام الرئاسة");
//    }

    public static void main(String[] args) {
        try {
            //        try {
//            Trrait();
//        } catch (JAXBException ex) {
//            Logger.getLogger(TraitementXml.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(TraitementXml.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ExecutionException ex) {
//            Logger.getLogger(TraitementXml.class.getName()).log(Level.SEVERE, null, ex);
//        }
            Trrait();
            List<String> res = OooutPut("C:\\ProjectIL\\ResourceDemo\\SampleOutputFile.xml");
            for (String r : res) {
                System.out.println(r);
            }
        } catch (IOException ex) {
            Logger.getLogger(TraitementXml.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JAXBException ex) {
            Logger.getLogger(TraitementXml.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(TraitementXml.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(TraitementXml.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(TraitementXml.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(TraitementXml.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void Trrait() throws JAXBException, InterruptedException, ExecutionException {
        APIExampleUse p = new APIExampleUse();
//        p.APExampleUse("G:\\wwssservice\\MIni_Projet_IL\\web\\SampleInputFile.xml", "G:\\wwssservice\\MIni_Projet_IL\\web\\SampleOutputFile.xml");
    }
//    public TraitementXml( ) throws IOException {}

    public static List<String> OooutPut(String fileOUt) throws IOException, JAXBException, InterruptedException, ExecutionException, SAXException, ParserConfigurationException {
//        CreeInput(MotTrait, myFile);

        List<String> diaclitise = new ArrayList();
        List<String> Ssstem = new ArrayList();
        diaclitise.add("َ");
        diaclitise.add("ً");
        diaclitise.add("ُ");
        diaclitise.add("ٌ");
        diaclitise.add("ّ");
        diaclitise.add("ْ");
        diaclitise.add("ِ");
        diaclitise.add("ٍ");

//creating a constructor of file class and parsing an XML file  
        List<String> ListStopWord = lireStoprWord();
        File file = new File(fileOUt);
//an instance of factory that gives a document builder  
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//an instance of builder to parse the specified xml file  
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(file);
        doc.getDocumentElement().normalize();
        System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
        NodeList nodeList = doc.getElementsByTagName("word");
// nodeList is not iterable, so we are using for loop  
        for (int itr = 0; itr < nodeList.getLength(); itr++) {
            Node node = nodeList.item(itr);

            String Enclitique = "", Proclitique = "";
            System.out.println("\nNode Name :" + node.getNodeName());
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;

                String motAttrait = eElement.getAttribute("word");
//                if (ListStopWord.contains(motAttrait)) {
//                    Ssstem.add(motAttrait);
//                    continue;
//                }
                NodeList nodeListSE = eElement.getElementsByTagName("morph_feature_set");
                NodeList nodeListTok = eElement.getElementsByTagName("tokenized");
                Element eElementDi = (Element) nodeListSE.item(0);

                Node NOdeTook = nodeListTok.item(3);
                Element eElementTok = (Element) NOdeTook;
                NodeList nodeListTokFor = eElementTok.getElementsByTagName("tok");

                String MotDiaclitise = normalize(eElementDi.getAttribute("diac")).replaceAll("ّ", "");
                if (MotDiaclitise.isEmpty() || diaclitise.contains(MotDiaclitise) || ListStopWord.contains(MotDiaclitise)) {

                    Ssstem.add(motAttrait);
                    continue;
                }
                char[] caracterDiac = MotDiaclitise.toCharArray();

                System.out.println("Diaclitise       :" + MotDiaclitise);

                if (!diaclitise.contains(MotDiaclitise)) {
                    String MotDiaclitiseTrait = MotDiaclitise;
                    String Stem = "";
                    if (nodeListTokFor.getLength() <= 1) {
                        char[] caracterMot = MotDiaclitise.toCharArray();
                        List<String> carrMot = ConvertToList(caracterMot);
                        int LastDiaclitise = 0;
                        if (diaclitise.contains(carrMot.get(carrMot.size() - 1))) {
                            LastDiaclitise++;
                        }
                        if (carrMot.size() > 2) {
                            if (diaclitise.contains(carrMot.get(carrMot.size() - 1)) && diaclitise.contains(carrMot.get(carrMot.size() - 2))) {
                                LastDiaclitise++;
                            }
                        }

                        for (int i = 0; i < carrMot.size() - LastDiaclitise; i++) {
                            Stem = Stem.concat(carrMot.get(i) + "");
                        }

                    } else {
                        String StemNDiac = "";
                        for (int itrTok = 0; itrTok < nodeListTokFor.getLength(); itrTok++) {

                            Node nodeTok = nodeListTokFor.item(itrTok);
                            Element eElementTokK = (Element) nodeTok;
                            String Formm = eElementTokK.getAttribute("form0");
                            char[] caracterFormm = Formm.toCharArray();
                            List<String> carr = ConvertToList(caracterFormm);

                            if (carr.get(0).equals("+")) {
                                MotDiaclitiseTrait = RemoveEnclitique(Formm, MotDiaclitiseTrait, diaclitise);
                                Enclitique += Formm;
                            } else if (carr.get(carr.size() - 1).equals("+")) {
                                MotDiaclitiseTrait = RemoveProclitique(Formm, MotDiaclitiseTrait, diaclitise);
                                Proclitique += Formm;
                            } else {
                                StemNDiac = Formm;
                            }
                        }

                        Stem = MotDiaclitiseTrait;
                        char[] stemDiac = MotDiaclitiseTrait.toCharArray();
                        char[] stemNDiacCC = StemNDiac.toCharArray();
                        String test = "";

                        List<String> StemNoDia = ConvertToList(stemNDiacCC);

                        List<String> StemDia = ConvertToList(stemDiac);
                        List<String> StemDiaF = ConvertToList(stemDiac);
                        StemDia.removeAll(diaclitise);
                        for (String s : StemDiaF) {
                            if (diaclitise.contains(s) && !s.equals("")) {
                                test += s;
                            } else {
                                test += StemNoDia.get(0);
                                StemNoDia.remove(0);
                            }
                        }
                        if (!StemNoDia.isEmpty()) {

                            test += StemNoDia.get(0);
                            StemNoDia.remove(0);
                        }
                        Stem = test;
                    }
                    Stem = RemoveLastDiacr(Stem, diaclitise);
                    System.out.println(motAttrait + " Steem == " + Stem);

                    Ssstem.add(Proclitique + Stem + Enclitique);
                }
            }
        }

        return Ssstem;
    }

    public static String normalize(String linee) {

        String line = linee.replaceAll("\\s+", " ");
        line = line.replaceAll("\\p{Punct}|[a-zA-Z]|[0-9]", "");
        line = line.replaceAll("((.)\\2{2})\\2+", "$1$2");
        return line;
    }

    public static String RemoveProclitique(String Procl, String Mot, List<String> diaclitise) {
        char[] caracterProc = Procl.toCharArray();
        char[] caracterMot = Mot.toCharArray();

        List<String> carrProc = ConvertToList(caracterProc);
        List<String> carrMot = ConvertToList(caracterMot);
        carrProc.remove("+");

        while (!carrProc.isEmpty()) {
            String mm = carrProc.get(0);
            int index = Mot.indexOf(mm);

            if (mm.equals("ا")) {
                index = 0;
            }
            carrProc.remove(0);
            for (int i = 0; i <= index; i++) {
                carrMot.remove(0);
            }

            String Res = "";
            for (int i = 0; i < carrMot.size(); i++) {
                Res = Res.concat(carrMot.get(i) + "");
            }
            Mot = Res;
        }

        return Mot;

    }

    public static String RemoveEnclitique(String Enc, String Mot, List<String> diaclitise) {
        char[] caracterProc = Enc.toCharArray();
        char[] caracterMot = Mot.toCharArray();

        List<String> carrEnc = ConvertToList(caracterProc);
        List<String> carrMot = ConvertToList(caracterMot);
        carrEnc.remove("+");
        while (!carrEnc.isEmpty()) {
            int kInd = carrEnc.size() - 1;
            String mm = carrEnc.get(kInd);
            int index = Mot.lastIndexOf(mm);
            if (index <= 0) {
                int j = 1;
                while (true) {
                    index = Mot.lastIndexOf(Mot.length() - j);
                    if (diaclitise.contains(Mot.charAt(Mot.length() - j))) {
                        j++;
                    } else {
                        break;
                    }
                }
            }
            carrEnc.remove(kInd);
            for (int i = carrMot.size() - 1; i >= index; i--) {
                carrMot.remove(i);
            }

            String Res = "";
            for (int i = 0; i < carrMot.size(); i++) {
                Res = Res.concat(carrMot.get(i) + "");
            }
        }
        String Res = "";
        int LastDiaclitise = 0;
        if (diaclitise.contains(carrMot.get(carrMot.size() - 1))) {
            LastDiaclitise++;
        }
        if (diaclitise.contains(carrMot.get(carrMot.size() - 1)) && diaclitise.contains(carrMot.get(carrMot.size() - 2))) {
            LastDiaclitise++;
        }

        for (int i = 0; i < carrMot.size() - LastDiaclitise; i++) {
            Res = Res.concat(carrMot.get(i) + "");
        }
        return Res;

    }

    public static void CreeInput(String inn, String myFile) throws FileNotFoundException, IOException {
        String Header = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<!--\n"
                + "  ~ Copyright (c) 2013. The Trustees of Columbia University in the City of New York.\n"
                + "  ~ The copyright owner has no objection to the reproduction of this work by anyone for\n"
                + "  ~ non-commercial use, but otherwise reserves all rights whatsoever.  For avoidance of\n"
                + "  ~ doubt, this work may not be reproduced, or modified, in whole or in part, for commercial\n"
                + "  ~ use without the prior written consent of the copyright owner.\n"
                + "  -->\n"
                + "\n"
                + "<madamira_input xmlns=\"urn:edu.columbia.ccls.madamira.configuration:0.1\">\n"
                + "    <madamira_configuration>\n"
                + "        <preprocessing sentence_ids=\"false\" separate_punct=\"true\" input_encoding=\"UTF8\"/>\n"
                + "        <overall_vars output_encoding=\"UTF8\" dialect=\"MSA\" output_analyses=\"TOP\" morph_backoff=\"NONE\"/>\n"
                + "        <requested_output>\n"
                + "            <req_variable name=\"PREPROCESSED\" value=\"true\" />\n"
                + "            <req_variable name=\"STEM\" value=\"true\" />\n"
                + "            <req_variable name=\"GLOSS\" value=\"false\" />\n"
                + "            <req_variable name=\"LEMMA\" value=\"false\" />\n"
                + "            <req_variable name=\"DIAC\" value=\"true\" />\n"
                + "            <req_variable name=\"ASP\" value=\"true\" />\n"
                + "            <req_variable name=\"CAS\" value=\"true\" />\n"
                + "            <req_variable name=\"ENC0\" value=\"true\" />\n"
                + "            <req_variable name=\"ENC1\" value=\"false\" />\n"
                + "            <req_variable name=\"ENC2\" value=\"false\" />\n"
                + "            <req_variable name=\"GEN\" value=\"false\" />\n"
                + "            <req_variable   name=\"MOD\" value=\"true\" />\n"
                + "            <req_variable name=\"NUM\" value=\"true\" />\n"
                + "            <req_variable name=\"PER\" value=\"true\" />\n"
                + "            <req_variable name=\"POS\" value=\"true\" />\n"
                + "            <req_variable name=\"PRC0\" value=\"true\" />\n"
                + "            <req_variable name=\"PRC1\" value=\"true\" />\n"
                + "            <req_variable name=\"PRC2\" value=\"true\" />\n"
                + "            <req_variable name=\"PRC3\" value=\"true\" />\n"
                + "            <req_variable name=\"STT\" value=\"true\" />\n"
                + "            <req_variable name=\"VOX\" value=\"true\" />\n"
                + "            <req_variable name=\"BW\" value=\"false\" />\n"
                + "            <req_variable name=\"SOURCE\" value=\"false\" />\n"
                + "			<req_variable name=\"NER\" value=\"true\" />\n"
                + "			<req_variable name=\"BPC\" value=\"true\" />\n"
                + "        </requested_output>\n"
                + "        <tokenization>\n"
                + "            <scheme alias=\"ATB\" />\n"
                + "			<scheme alias=\"D3_BWPOS\" /> <!-- Required for NER -->\n"
                + "            <scheme alias=\"ATB4MT\" />\n"
                + "            <scheme alias=\"MyD3\">\n"
                + "				<!-- Same as D3 -->\n"
                + "				<scheme_override alias=\"MyD3\"\n"
                + "								 form_delimiter=\"\\u00B7\"\n"
                + "								 include_non_arabic=\"true\"\n"
                + "								 mark_no_analysis=\"false\"\n"
                + "								 token_delimiter=\" \"\n"
                + "								 tokenize_from_BW=\"false\">\n"
                + "					<split_term_spec term=\"PRC3\"/>\n"
                + "					<split_term_spec term=\"PRC2\"/>\n"
                + "					<split_term_spec term=\"PART\"/>\n"
                + "					<split_term_spec term=\"PRC0\"/>\n"
                + "					<split_term_spec term=\"REST\"/>\n"
                + "					<split_term_spec term=\"ENC0\"/>\n"
                + "					<token_form_spec enclitic_mark=\"+\"\n"
                + "									 proclitic_mark=\"+\"\n"
                + "									 token_form_base=\"WORD\"\n"
                + "									 transliteration=\"UTF8\">\n"
                + "						<normalization type=\"ALEF\"/>\n"
                + "						<normalization type=\"YAA\"/>\n"
                + "						<normalization type=\"DIAC\"/>\n"
                + "						<normalization type=\"LEFTPAREN\"/>\n"
                + "						<normalization type=\"RIGHTPAREN\"/>\n"
                + "					</token_form_spec>\n"
                + "				</scheme_override>\n"
                + "			</scheme>\n"
                + "        </tokenization>\n"
                + "    </madamira_configuration>\n"
                + "\n"
                + "\n"
                + "    <in_doc id=\"ExampleDocument\">\n";

        String footer = "        \n"
                + "    </in_doc>\n"
                + "\n"
                + "</madamira_input>";

        //            FileInputStream fstream =                 new FileInputStream(getServletContext().getRealPath("/database.txt"));
        //            FileWriter fw = new FileWriter(getServletContext().getRealPath("\\src\\java\\Ressource")+"\\SampleInputFile.xml");
        //                    FileWriter myWriter = new FileWriter("MIni_Projet_IL\\src\\java\\Traitement\\TraitementXml.java")) {
        //                myWriter.write("Files in Java might be tricky, but it is fun enough!");
        ////                    FileWriter fw = new FileWriter("web\\SampleOutputFile.xml")) {
        //                myWriter.write(Header);
        //                myWriter.write(inn);
        //                myWriter.write(footer);
        //                myWriter.close();
        BufferedWriter lecteurAvecBuffer = new BufferedWriter(new FileWriter(myFile));

        lecteurAvecBuffer.write(Header);
        lecteurAvecBuffer.write("        <in_seg id=\"SENT1\">");

        lecteurAvecBuffer.write(inn);

        lecteurAvecBuffer.write("</in_seg>\n");
        lecteurAvecBuffer.write(footer);
        lecteurAvecBuffer.close();

    }

    public static List ConvertToList(char[] caracterProc) {
        List<String> res = new ArrayList();
        for (int i = 0; i < caracterProc.length; i++) {
            res.add(caracterProc[i] + "");
        }
        return res;
    }

    public static String RemoveLastDiacr(String carMot, List<String> diaclitise) {
        String Res = "";

        char[] caracterMot = carMot.toCharArray();
        List<String> carrMot = ConvertToList(caracterMot);
        int LastDiaclitise = 0, DepuSDiaclitise = 0;
//        System.out.println("Diac :"+carrMot.size()+" erreur :"+carrMot.get(0));
//        for(int i=0;i<carrMot.size();i++)
//            System.out.println(carrMot.get(i)+" + ");
//        System.out.println(diaclitise);
//        System.out.println(diaclitise.contains(carrMot.get(0)));
        if (diaclitise.contains(carrMot.get(0))) {
            DepuSDiaclitise++;
        }
        if (diaclitise.contains(carrMot.get(0)) && diaclitise.contains(carrMot.get(1))) {
            DepuSDiaclitise++;
        }
        if (diaclitise.contains(carrMot.get(carrMot.size() - 1))) {
            LastDiaclitise++;
        }
        if (diaclitise.contains(carrMot.get(carrMot.size() - 1)) && diaclitise.contains(carrMot.get(carrMot.size() - 2))) {
            LastDiaclitise++;
        }
        for (int i = DepuSDiaclitise; i < carrMot.size() - LastDiaclitise; i++) {
            Res = Res.concat(carrMot.get(i) + "");
        }
        return Res;
    }

    public static List<String> lireStoprWord() {
        List<String> resList = new ArrayList();
        try {
            File myObj = new File("src\\Ressource\\stop-words.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                resList.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return resList;
    }

}
