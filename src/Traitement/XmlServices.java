/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Traitement;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;





/**
 * this class use it for conroll xml file
 * @author essakhy
 */
public class XmlServices {


    public static void main(String[] args) {
        String tt="اللُّغَةِ";
        for(int i=0;i<tt.length();i++){
            System.out.println(tt.charAt(i));
        }
        System.out.println(tt);
    }
    public XmlServices() {
    }
    
    
        public static Document getDucument(String DataXmlPath){
        Document d=null;
        try{
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            d=db.parse(DataXmlPath);
        }catch( Exception ex){
            System.err.println("[take look at line 31 ]: "+ex.getMessage());
        }
        return d;
        }
        
        
        
        public static String getXMLContent(Document d){
            String out ="";
            try{
            TransformerFactory tff = TransformerFactory.newInstance();
            Transformer tf = tff.newTransformer();
            tf.setOutputProperty(OutputKeys.INDENT,"yes");
            StringWriter sw = new StringWriter();
            StreamResult sr= new StreamResult(sw);
            DOMSource source = new DOMSource(d);
            tf.transform(source, sr);
            out=sw.toString();
            }catch(Exception ex){
             System.err.println("[take look at line 51 ]: "+ex.getMessage());
            }
        return out;
        }
        
        public static void saveXmlContent(Document d,String DataXmlPath){
            try {
               TransformerFactory tff = TransformerFactory.newInstance();
               Transformer tf = tff.newTransformer();
               tf.setOutputProperty(OutputKeys.INDENT, "yes");
               DOMSource ds = new DOMSource(d);
               StreamResult sr = new StreamResult(DataXmlPath);
               tf.transform(ds, sr);
           } catch (Exception e) {
                System.err.println("[take look at line 65 ]: "+e.getMessage());
            }
        }
        
     //// CRUD USER
        
 
}