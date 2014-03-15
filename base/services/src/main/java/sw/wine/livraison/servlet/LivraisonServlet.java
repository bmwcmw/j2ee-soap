package sw.wine.livraison.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.StringTokenizer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.xml.messaging.JAXMServlet;
//import javax.xml.messaging.ReqRespListener;
import javax.xml.soap.*;

import org.apache.commons.io.IOUtils;

public class LivraisonServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	static MessageFactory messageFactory = null;

	static {
		try {
			messageFactory = MessageFactory.newInstance();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	};

	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);
	}

	@SuppressWarnings("rawtypes")
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("doPost - SOAP requête reçu...");
		SOAPMessage mySoapMsg;
		try{
			ServletInputStream myInputStream = req.getInputStream();
			MimeHeaders mimeHeaders = getHeaders(req);
			mySoapMsg = messageFactory.createMessage(mimeHeaders,myInputStream);
			StringWriter writer = new StringWriter();
			IOUtils.copy(myInputStream, writer);
			String theString = writer.toString();
			System.out.println(theString);
			System.out.println("doPost - SOAP requête créé et en traitement...");
			
			System.out.println("doPost - Headers : " );
			Iterator it = mimeHeaders.getAllHeaders();
            while ( it.hasNext() ) {
                MimeHeader head = (MimeHeader) it.next();
                System.out.println( "\t"+head.getName() + " " + head.getValue() );
            }
            mySoapMsg.writeTo( System.out );
            System.out.println();
			
			/* Création de SOAP message depuis input stream. */			
            //SOAPMessage reply=this.onMessage( mySoapMsg );
            SOAPMessage reply = JAXBTools.traiterSOAPMessage(messageFactory,mySoapMsg);
			/* Return modified SOAP message. */
			System.out.println("doPost - SOAP réponse envoyée...");
			reply.saveChanges();
			OutputStream myOutputStream = resp.getOutputStream();
			reply.writeTo(myOutputStream);
			myOutputStream.flush();
		} catch (Exception e){
			throw new ServletException("doPost - Erreur : " + e.getMessage());
		}
	}


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@SuppressWarnings("rawtypes")
	public static MimeHeaders getHeaders(HttpServletRequest req) {

		Enumeration headerNames = req.getHeaderNames();
		MimeHeaders headers = new MimeHeaders();

		while (headerNames.hasMoreElements()) {
			String headerName = (String) headerNames.nextElement();
			String headerValue = req.getHeader(headerName);

			StringTokenizer values = new StringTokenizer(headerValue, ",");
			while (values.hasMoreTokens()) {
				headers.addHeader(headerName, values.nextToken().trim());
			}
		}
		return headers;
	}

	@SuppressWarnings("rawtypes")
	public static void putHeaders(MimeHeaders headers, HttpServletResponse res) {

		Iterator it = headers.getAllHeaders();
		while (it.hasNext()) {
			MimeHeader header = (MimeHeader) it.next();

			String[] values = headers.getHeader(header.getName());
			if (values.length == 1)
				res.setHeader(header.getName(), header.getValue());
			else {
				StringBuffer concat = new StringBuffer();
				int i = 0;
				while (i < values.length) {
					if (i != 0) {
						concat.append(",");
					}
					concat.append(values[i++]);
				}
				res.setHeader(header.getName(), concat.toString());
			}
		}
	}

//	@Override
//	public SOAPMessage onMessage(SOAPMessage arg0) {
//		try {
//			System.out.println("doPost - onMessage...");
//			SOAPMessage reply=JAXBTools.traiterSOAPMessage(messageFactory,arg0);
//			return reply;
//		} catch (SOAPException e) {
//			return null;
//		}
//	}

}

//import java.io.*; 
//import java.util.*; 
//import javax.servlet.*; 
//import javax.servlet.http.*;

//@SuppressWarnings("serial")
//public class LivraisonServlet extends HttpServlet {// Treat GET requests as errors.
//	public void doGet(HttpServletRequest request, HttpServletResponse response) 
//			throws IOException, ServletException {
//		System.out.println("Received GET request"); 
//		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//		doPost(request, response);
//	}// Our SOAP requests are going to be received as HTTP POSTS
//	
//	@SuppressWarnings("rawtypes")
//	public void doPost(HttpServletRequest request, HttpServletResponse response)
//			throws IOException, ServletException {// Traverse the HTTP headers and show them on the screen
//		for(Enumeration enume = request.getHeaderNames(); enume.hasMoreElements(); ) {
//			String header = (String)enume.nextElement();
//			String value = request.getHeader(header);
//			System.out.println(" " + header + " = " + value);
//		}// If there is anything in the body of the message, dump it to the screen as well
//		if(request.getContentLength() > 0) {
//			try{
//				java.io.BufferedReader reader = request.getReader();
//				String line = null;while((line = reader.readLine()) != null) { 
//					System.out.println(line);
//				}
//			}catch(Exception e) { 
//				System.out.println(e); 
//			}
//		} 
//		response.setContentType("text/xml"); // Need this to prevent Apache SOAP from gacking
//	}
//}