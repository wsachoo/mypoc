//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0.5-b02-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.30 at 06:15:54 PM EST 
//


package com.att.edb.accessquote;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://edb.att.com/accessQuote}accessQuoteAlternateRequest"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "accessQuoteAlternateRequest"
})
@XmlRootElement(name = "getAccessQuoteAlternate")
public class GetAccessQuoteAlternate {

    @XmlElement(required = true)
    protected AccessQuoteAlternateRequest accessQuoteAlternateRequest;

    /**
     * Gets the value of the accessQuoteAlternateRequest property.
     * 
     * @return
     *     possible object is
     *     {@link AccessQuoteAlternateRequest }
     *     
     */
    public AccessQuoteAlternateRequest getAccessQuoteAlternateRequest() {
        return accessQuoteAlternateRequest;
    }

    /**
     * Sets the value of the accessQuoteAlternateRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccessQuoteAlternateRequest }
     *     
     */
    public void setAccessQuoteAlternateRequest(AccessQuoteAlternateRequest value) {
        this.accessQuoteAlternateRequest = value;
    }

}
