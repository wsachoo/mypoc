//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0.5-b02-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.01.31 at 11:55:55 AM EST 
//


package com.att.cio.commonheader.v3;

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
 *         &lt;element name="URL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element ref="{http://cio.att.com/commonheader/v3}WSCorrelationId" minOccurs="0"/>
 *         &lt;element name="FaultURL" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
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
    "url",
    "wsCorrelationId",
    "faultURL"
})
@XmlRootElement(name = "WSCallback")
public class WSCallback {

    @XmlElement(name = "URL")
    protected String url;
    @XmlElement(name = "WSCorrelationId")
    protected String wsCorrelationId;
    @XmlElement(name = "FaultURL")
    protected String faultURL;

    /**
     * Gets the value of the url property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getURL() {
        return url;
    }

    /**
     * Sets the value of the url property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setURL(String value) {
        this.url = value;
    }

    /**
     * Gets the value of the wsCorrelationId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWSCorrelationId() {
        return wsCorrelationId;
    }

    /**
     * Sets the value of the wsCorrelationId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWSCorrelationId(String value) {
        this.wsCorrelationId = value;
    }

    /**
     * Gets the value of the faultURL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFaultURL() {
        return faultURL;
    }

    /**
     * Sets the value of the faultURL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFaultURL(String value) {
        this.faultURL = value;
    }

}
