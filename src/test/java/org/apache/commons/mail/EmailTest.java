package org.apache.commons.mail;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import javax.mail.Session;
import javax.mail.internet.MimeMultipart;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Test.None;

public class EmailTest {

	private static final String[] TEST_EMAILS = { "abc@def", "ryan@gmail", "dearborn@hdc.com" };  // array of 3 emails

	/* Concrete Email Class for testing */
	private EmailConcrete email;
	

	@Before
	public void setUpEmailTest() throws Exception {
		email = new EmailConcrete();
	}

	@After
	public void tearDownEmailTest() {

	}

	/*
	 * Test addBcc(String... emails) function
	 */

	@Test
	public void addBccTest() throws Exception {

		email.addBcc(TEST_EMAILS);  // add 3 BCC recipients to email
		assertEquals(3, email.getBccAddresses().size());  // verify BCC list has 3 recipients

	}

	/*
	 * Test addCc(String email) function
	 */

	@Test
	public void addCCTest() throws Exception {
		email.addCc(TEST_EMAILS);  // add 3 CC recipients to email
		assertEquals("ryan@gmail", email.ccList.get(1).toString());  // verify 2nd on CC list is ryan@gmail
	}

	/*
	 * Test addHeader(String name, String value) function
	 */

	@Test
	public void addHeaderTest() throws Exception {
		email.addHeader("Ryan", "happy");  
		assertEquals("happy", email.headers.get("Ryan")); // verify value of header w/ name "Ryan" matches
	}

	/*
	 * Test addHeader(String name, String value) function - for an exception
	 */

	@Test(expected = IllegalArgumentException.class)
	public void addHeaderExceptionTest() throws Exception {
		email.addHeader("", "happy");   // should throw expected exception since name is empty
	}
	
	/*
	 * Test addReplyTo(String email, String name) function
	 */

	@Test
	public void addReplyToTest() throws Exception {
		email.addReplyTo("cis@376", "Jimbo");  // adds email and name to reply list
		assertEquals("cis@376", email.replyList.get(0).getAddress()); // verify first email in reply list matches
	}
	
	/*
	 * Test buildMimeMessage() function - exception b/c no from address
	*/
	
	@Test(expected = EmailException.class)
	public void buildMimeMessageFromAddressExceptionTest() throws Exception {
		email.setHostName("Billy123");
		email.buildMimeMessage();  // should throw email exception since no from address
	}
	
	/*
	 * Test buildMimeMessage() function - exception b/c no receiver
	*/
	
	@Test(expected = EmailException.class)
	public void buildMimeMessageNoReceiverExceptionTest() throws Exception {
		email.setHostName("Billy123");
		email.setFrom("ryan@gmail");
		
		email.buildMimeMessage();  // should throw email exception since no receiver
	}
	
	/*
	 * Test buildMimeMessage() function - exception b/c no store connect
	*/
	
	@Test(expected = EmailException.class)
	public void buildMimeMessageStoreConnectExceptionTest() throws Exception {
		email.setHostName("Billy123");
		email.setFrom("ryan@gmail");
		
		email.addTo("adam@com");  // add receiver email so doesn't throw that exception
		
		email.setPopBeforeSmtp(true, null, null, null);
		
		email.buildMimeMessage();  // should exception since store cannot connect (no host, username, password)
	}
	
	/*
	 * Test buildMimeMessage() function -  no exceptions
	*/
	
	@Test(expected = None.class)
	public void buildMimeMessageNoExceptionTest() throws Exception {
		email.setHostName("Billy123");
		
		Object content = new Object();
		email.setContent(content, "Hello neighbor");  // set email body
		
		email.setFrom("ryan@gmail");
		
		email.addTo("adam@com");  // add receiver emails so doesn't throw that exception
		email.addCc("abc@xyz");
		email.addBcc(TEST_EMAILS);
		email.addReplyTo("cis@376", "Jimbo");
		
		email.addHeader("Ryan", "happy");
		
		email.setPopBeforeSmtp(false, null, null, null);  // don't log in to pop3 server before sending mail
		                                                  // skips store connect
		
		email.buildMimeMessage();  // should throw no exceptions
	}
	
	/*
	 * Test getHostName() function
	*/
	
	@Test
	public void getHostNameTest() throws Exception {
		email.setHostName("Jackson123");  // sets host name of outgoing mail server
		assertEquals("Jackson123",email.getHostName());  // verify host name matches when call getHostName()
	}
	
	/*
	 * Test getHostName() function - when no session and empty
	*/
	
	@Test
	public void getHostNameNullTest() throws Exception {
		assertEquals(null,email.getHostName());  // verify there is no host name when call getHostName() w/o setting it
	}
	
	/*
	 * Test getMailSession() function
	*/
	
	@Test 
	public void getMailSessionTest() throws Exception {
		email.setHostName("123");
		email.setAuthentication("Ryan", "Len5");
		assertNotNull(email.getMailSession());  // verify a valid session is returned
	}
	
	/*
	 * Test getMailSession() function - for an exception
	*/
	
	@Test(expected = EmailException.class)
	public void getMailSessionExceptionTest() throws Exception {
		email.getMailSession();  // should throw expected exception since no host name
	}
	
	/*
	 * Test getSentDate() function
	*/
	
	@Test
	public void getSentDateTest() throws Exception {
		assertEquals(new Date(), email.getSentDate()); // verify current date matches date of getSentDate() request
	}
	
	/*
	 * Test getSentDate() function - after setting date
	*/
	
	@Test
	public void getSentDateAfterSetTest() throws Exception {
		Date date = new Date();   // generates new date
		email.setSentDate(date);
		assertEquals(date, email.getSentDate());   // verify previously generated date matches returned value
	}
	
	/*
	 * Test getSocketConnectionTimeout() function
	*/
	
	@Test
	public void getSocketConnectionTimeoutTest() throws Exception {
		email.setSocketConnectionTimeout(15);  
		assertEquals(15, email.getSocketConnectionTimeout());  // verify previously set timeout matches returned value
	}
	
	/*
	 * Test setFrom(String email) function
	*/
	
	@Test
	public void setFromTest() throws Exception {
		
		email.setFrom("ryan@gmail");
		assertEquals("ryan@gmail", email.getFromAddress().toString());  // verifies previously set from email matches returned value
		
	}
	
	

}
