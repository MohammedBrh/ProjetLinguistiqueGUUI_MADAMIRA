package Traitement;

import edu.columbia.ccls.madamira.MADAMIRAWrapper;
import edu.columbia.ccls.madamira.configuration.MadamiraInput;
import edu.columbia.ccls.madamira.configuration.MadamiraOutput;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 * An example class that shows how MADAMIRA can be called through its API.
 *
 */
public class APIExampleUse {

    // MADAMIRA namespace as defined by its XML schema
    private static final String MADAMIRA_NS = "edu.columbia.ccls.madamira.configuration";
    private static final String INPUT_FILE = "src\\Ressource\\SampleInputFile.xml";
    private static final String OUTPUT_FILE = "src\\Ressource\\sampleOutputFile.xml";

    public static List<String> trait(String motaTrait) throws JAXBException, ExecutionException, InterruptedException, IOException, SAXException, ParserConfigurationException {
//        public void APExampleUse(String FileInput,String OUTPUT_FILE) throws JAXBException, ExecutionException, InterruptedException{
        final MADAMIRAWrapper wrapper = new MADAMIRAWrapper();
        JAXBContext jc = null;
        TraitementXml.CreeInput(motaTrait, INPUT_FILE);
            jc = JAXBContext.newInstance(MADAMIRA_NS);
            Unmarshaller unmarshaller = jc.createUnmarshaller();

            // The structure of the MadamiraInput object is exactly similar to the
            // madamira_input element in the XML
            final MadamiraInput input = (MadamiraInput)unmarshaller.unmarshal(new File( INPUT_FILE ) );            
                int numSents = input.getInDoc().getInSeg().size();
                String outputAnalysis = input.getMadamiraConfiguration().getOverallVars().getOutputAnalyses();
                String outputEncoding = input.getMadamiraConfiguration().getOverallVars().getOutputEncoding();

                System.out.println("processing " + numSents +
                        " sentences for analysis type = " + outputAnalysis +
                        " and output encoding = " + outputEncoding);
            

            // The structure of the MadamiraOutput object is exactly similar to the
            // madamira_output element in the XML
            final MadamiraOutput output = wrapper.processString(input);
            
                int numSentstt = output.getOutDoc().getOutSeg().size();
            
                System.out.println("processed output contains "+numSentstt+" sentences...");
            


            jc.createMarshaller().marshal(output, new File(OUTPUT_FILE));


        wrapper.shutdown();
        return TraitementXml.OooutPut(OUTPUT_FILE);
    }
}