package sw.wine.livraison.test;

import java.io.ByteArrayOutputStream;

import javax.xml.soap.SOAPMessage;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

public class SaajHelper {
   public static String getSOAPMessageAsString(SOAPMessage soapMessage) {
      try {

         TransformerFactory tff = TransformerFactory.newInstance();
         Transformer tf = tff.newTransformer();

         // Set formatting
         tf.setOutputProperty(OutputKeys.INDENT, "yes");
         tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
         
         Source sc = soapMessage.getSOAPPart().getContent();

         ByteArrayOutputStream streamOut = new ByteArrayOutputStream();
         StreamResult result = new StreamResult(streamOut);
         tf.transform(sc, result);

         String strMessage = streamOut.toString();
         return strMessage;
      } catch (Exception e) {
         System.out.println("Exception in SaajHelper.getSOAPMessageAsString "+ e.getMessage());
         return null;
      }

   }
}