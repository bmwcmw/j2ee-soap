///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package sw.wine.model;
//
//import java.io.IOException;
//import java.util.Calendar;
//
//import javax.xml.XMLConstants;
//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.JAXBException;
//import javax.xml.bind.util.JAXBSource;
//import javax.xml.validation.Schema;
//import javax.xml.validation.SchemaFactory;
//import javax.xml.validation.Validator;
//
//import junit.framework.TestCase;
//
//import org.xml.sax.SAXException;
//
//import sw.wine.itf.WineColor;
//import sw.wine.itf.WineType;
//
///**
// *
// * @author Famille
// */
//public class JAXBTest extends TestCase {
//    public void testXSD() throws JAXBException, SAXException, IOException {  
//        Location loc = new Location();
//        WineCollection coll = new WineCollection();
//        coll.addLocation(loc);
//        long id = 0;
//        loc.setId(++id);
//        loc.setRegion("Rhone-Alpes");
//        loc.setCountry("France");
//        loc.setSubRegion("Rhone");
//        Wine wine = new Wine();
//        wine.setAppellation("Courante");
//        wine.setColor(WineColor.Red);
//        wine.setFbId("azertyuiop");
//        wine.setLocation(loc);
//        wine.setPercentage(0.0);
//        wine.setProducer("Ernest");
//        wine.setVineyard("Chateau la pompe");
//        Calendar annee = Calendar.getInstance();
//        annee.clear();
//        annee.set(2013, 1, 1);
//        wine.setVintage(annee.getTime());
//        wine.setWinestyle("Eau courante");
//        wine.setWinetype(WineType.Ice);
//        wine.addVariety("Eau",100);
//        wine.newBottle().setId(++id);
//        wine.newBottle().setId(++id);
//        wine.newBottle().setId(++id);
//        wine = new Wine();
//        wine.setAppellation("Courante");
//        wine.setColor(WineColor.Red);
//        wine.setFbId("qsdfghjk");
//        wine.setLocation(loc);
//        wine.setPercentage(14.0);
//        wine.setProducer("Ernest");
//        wine.setVineyard("Chateau gaillard");
//        annee.clear();
//        annee.set(2012, 1, 1);
//        wine.setVintage(annee.getTime());
//        wine.setWinestyle("Eau courante");
//        wine.setWinetype(WineType.Fortified);
//        wine.addVariety("Eau",100);
//        wine.newBottle().setId(++id);
//        wine.newBottle().setId(++id);
//        loc = new Location();
//        loc.setCountry("USA");
//        loc.setRegion("California");
//        loc.setSubRegion("none");
//        loc.setId(++id);
//        coll.addLocation(loc);
//        wine = new Wine();
//        wine.setAppellation("California Wines");
//        wine.setColor(WineColor.White);
//        wine.setFbId("wxcvbn");
//        wine.setLocation(loc);
//        wine.setPercentage(30.0);
//        wine.setProducer("Ernest");
//        wine.setVineyard("Extra Strong Wine");
//        annee.clear();
//        annee.set(2012, 1, 1);
//        wine.setVintage(annee.getTime());
//        wine.setWinestyle("Special Brewing");
//        wine.setWinetype(WineType.Fortified);
//        wine.addVariety("Poisoneous",100);
//        wine.newBottle().setId(++id);
//        JAXBContext ctx = JAXBContext.newInstance(WineCollection.class);
//        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
//        Schema sch = factory.newSchema(WineCollection.class.getResource("/wine.xsd"));
//        Validator validator = sch.newValidator();
//        validator.validate(new JAXBSource(ctx, coll));
//    }
//}
