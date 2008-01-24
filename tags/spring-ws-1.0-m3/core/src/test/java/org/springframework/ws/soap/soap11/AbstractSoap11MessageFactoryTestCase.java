/*
 * Copyright 2006 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.ws.soap.soap11;

import java.io.InputStream;
import java.util.Properties;

import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.soap.AbstractSoapMessageFactoryTestCase;
import org.springframework.ws.soap.Attachment;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.soap.SoapVersion;
import org.springframework.ws.transport.StubTransportInputStream;
import org.springframework.ws.transport.TransportInputStream;

public abstract class AbstractSoap11MessageFactoryTestCase extends AbstractSoapMessageFactoryTestCase {

    public void testCreateEmptyMessage() throws Exception {
        WebServiceMessage message = messageFactory.createWebServiceMessage();
        assertTrue("Not a SoapMessage", message instanceof SoapMessage);
        SoapMessage soapMessage = (SoapMessage) message;
        assertEquals("Invalid soap version", SoapVersion.SOAP_11, soapMessage.getVersion());
    }

    public void testCreateSoapMessageNoAttachment() throws Exception {
        InputStream is = AbstractSoap11MessageFactoryTestCase.class.getResourceAsStream("soap11.xml");
        final Properties headers = new Properties();
        headers.setProperty("Content-Type", "text/xml");
        String soapAction = "http://springframework.org/spring-ws/Action";
        headers.setProperty("SOAPAction", soapAction);
        TransportInputStream tis = new StubTransportInputStream(is, headers);

        WebServiceMessage message = messageFactory.createWebServiceMessage(tis);
        assertTrue("Not a SoapMessage", message instanceof SoapMessage);
        SoapMessage soapMessage = (SoapMessage) message;
        assertEquals("Invalid soap version", SoapVersion.SOAP_11, soapMessage.getVersion());
        assertEquals("Invalid soap action", soapAction, soapMessage.getSoapAction());
    }

    public void testCreateSoapMessageAttachment() throws Exception {
        InputStream is = AbstractSoap11MessageFactoryTestCase.class.getResourceAsStream("soap11-attachment.bin");
        Properties headers = new Properties();
        headers.setProperty("Content-Type",
                "multipart/related; type=\"text/xml\"; boundary=\"----=_Part_0_11416420.1149699787554\"");
        TransportInputStream tis = new StubTransportInputStream(is, headers);

        WebServiceMessage message = messageFactory.createWebServiceMessage(tis);
        assertTrue("Not a SoapMessage", message instanceof SoapMessage);
        SoapMessage soapMessage = (SoapMessage) message;
        assertEquals("Invalid soap version", SoapVersion.SOAP_11, soapMessage.getVersion());
        Attachment attachment = soapMessage.getAttachment("interface21");
        assertNotNull("No attachment read", attachment);
    }


}