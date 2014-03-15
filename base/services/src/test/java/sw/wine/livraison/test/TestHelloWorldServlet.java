//package sw.wine.livraison.test;
//
//import java.io.File;
//
//import sw.wine.livraison.servlet.HelloWorldServlet;
//
//import com.meterware.httpunit.GetMethodWebRequest;
//import com.meterware.httpunit.WebRequest;
//import com.meterware.httpunit.WebResponse;
//import com.meterware.servletunit.InvocationContext;
//import com.meterware.servletunit.ServletRunner;
//import com.meterware.servletunit.ServletUnitClient;
//import junit.framework.Assert;
//import junit.framework.TestCase;
//
//public class TestHelloWorldServlet extends TestCase {
//
//	protected void setUp() throws Exception {
//		super.setUp();
//	}
//
//	protected void tearDown() throws Exception {
//		super.tearDown();
//	}
//
//	public void testHelloWorld() {
//
//		try {
//			File webXml = new File("WebContent/WEB-INF/web.xml");
//			
//			ServletRunner sr = new ServletRunner(webXml,"");
//
//			sr.registerServlet("HelloWorldServlet",
//					HelloWorldServlet.class.getName());
//
//			ServletUnitClient sc = sr.newClient();
//
//			WebRequest request = new GetMethodWebRequest(
//					"http://localhost:8080/services/helloworld");
//			String username = "testuser";
//			request.setParameter("username", username);
//
//			InvocationContext ic = sc.newInvocation(request);
//
//			HelloWorldServlet is = (HelloWorldServlet) ic.getServlet();
//
//			Assert.assertTrue(is.authenticate());
//
//			WebResponse response = sc.getResponse(request);
//
//			Assert.assertTrue(response.getText().equals(username + ":Hello World!"));
//			
//			sr.shutDown();
//
//		} catch (Exception e) {
//
//			e.printStackTrace();
//			Assert.fail();
//
//		}
//
//	}
//
//}
