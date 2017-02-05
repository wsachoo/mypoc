package com.att.salesexpress.webapp.service.igloo;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ws.soap.SoapFault;
import org.springframework.ws.soap.SoapFaultDetail;
import org.springframework.ws.soap.SoapFaultDetailElement;
import org.springframework.ws.soap.client.SoapFaultClientException;

import com.att.cio.commonheader.v3.WSContext;
import com.att.cio.commonheader.v3.WSContext.WSNameValue;
import com.att.cio.commonheader.v3.WSException;
import com.att.cio.commonheader.v3.WSHeader;
import com.att.cio.commonheader.v3.WSMessageData;
import com.att.edb.accessquote.AccessQuoteRequest;
import com.att.edb.accessquote.AccessQuoteRequest.AccessQuoteBody;
import com.att.edb.accessquote.GetAccessQuote;
import com.att.edb.accessquote.GetAccessQuoteResponse;
import com.att.salesexpress.igloo.consumer.service.IglooWSConsumerService;
import com.att.salesexpress.webapp.entity.SalesSite;
import com.att.salesexpress.webapp.pojos.UserDesignSelectionDO;
import com.att.salesexpress.webapp.pojos.UserSiteDesignDO;
import com.att.salesexpress.webapp.util.Constants;

/**
 * 
 * @author sw088d Sachin Wadhankar
 *
 */
@Service
public class IglooConsumerServiceImpl implements IglooConsumerService {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IglooWSConsumerService iglooWSConsumerService;

	@Override
	public List<Object> call(UserDesignSelectionDO objUserDesignSelectionDO, List<SalesSite> objSalesSite) {
		List<Object> iglooResponseList = new ArrayList<>();

		Map<String, UserSiteDesignDO> mapSiteDesign = objUserDesignSelectionDO.getSiteDesignList();
		Collection<UserSiteDesignDO> objUserSiteDesignDOList = mapSiteDesign.values();

		for (UserSiteDesignDO userSiteDesignDO : objUserSiteDesignDOList) {
			GetAccessQuote quote = createAccessQuoteRequestObject(userSiteDesignDO, objSalesSite);
			try {
				GetAccessQuoteResponse resp = iglooWSConsumerService.getAccessQuote(quote);
				iglooResponseList.add(resp);
			} catch (SoapFaultClientException e) {
				try {
					List<WSException> faultDetailMsgs = extractSOAPFaultMessageXML(e);
					for (WSException faultDetailMsg : faultDetailMsgs) {
						logger.info("The fault detail message is: {}", faultDetailMsg.getMessage());
					}
					iglooResponseList.add(faultDetailMsgs);
				} catch (TransformerException | TransformerFactoryConfigurationError | JAXBException e1) {
					logger.info("Error while extracting fault detail: {}", ExceptionUtils.getStackTrace(e1));
					iglooResponseList.add("EXCEPTION: IGLOO call resulted in SOAP Fault for site id: "
							+ userSiteDesignDO.getSiteId());
				}
			}
		}

		return iglooResponseList;
	}

	private List<WSException> extractSOAPFaultMessageXML(SoapFaultClientException e)
			throws TransformerFactoryConfigurationError, TransformerException, JAXBException {
		List<WSException> faultMsgList = new ArrayList<>();
		final SoapFault soapFault = e.getSoapFault();
		final SoapFaultDetail faultDetail = soapFault.getFaultDetail();

		for (final Iterator<SoapFaultDetailElement> detailEntryItr = faultDetail.getDetailEntries(); detailEntryItr
				.hasNext();) {
			final SoapFaultDetailElement detailEntry = detailEntryItr.next();
			final Source source = detailEntry.getSource();
/*			StringWriter writer = new StringWriter();
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			transformer.transform(source, new StreamResult(writer));
			String xml = writer.toString();*/
			JAXBContext jaxbContext = JAXBContext.newInstance(WSException.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			WSException objWSException = (WSException)jaxbUnmarshaller.unmarshal(source);

			faultMsgList.add(objWSException);
		}

		return faultMsgList;
	}

	private GetAccessQuote createAccessQuoteRequestObject(UserSiteDesignDO userSiteDesignDO,
			List<SalesSite> objSalesSiteList) {
		SalesSite objSalesSite = null;
		for (SalesSite salesSite : objSalesSiteList) {
			if (salesSite.getSiteId() == userSiteDesignDO.getSiteId()) {
				objSalesSite = salesSite;
				break;
			}
		}

		AccessQuoteBody bd = new AccessQuoteBody();
		bd.setCountry(Constants.IGLOO_WEBSERVICE_DEFAULT_COUNTRY);
		bd.setService(Constants.IGLOO_WEBSERVICE_DEFAULT_SERVICE_ID);
		bd.setAccessType(Constants.IGLOO_WEBSERVICE_DEFAULT_ACCESS_TYPE);
		bd.setAccessTransport(Constants.IGLOO_WEBSERVICE_DEFAULT_ACCESS_TRANSPORT);
		bd.setEGRFlag(Constants.IGLOO_WEBSERVICE_DEFAULT_ERG_FLAG);
		bd.setHouseNo(Constants.IGLOO_WEBSERVICE_DEFAULT_HOUSE_NO);
		bd.setQuoteRequestType(Constants.IGLOO_WEBSERVICE_DEFAULT_QUOTE_REQUEST_TYPE);
		bd.setAccesArrangement(Constants.IGLOO_WEBSERVICE_DEFAULT_ACCESS_ARRANGEMENT);
		bd.setContractTerm(Constants.IGLOO_WEBSERVICE_DEFAULT_CONTRACT_TERM);
		bd.setDiscountPercentage(Constants.IGLOO_WEBSERVICE_DEFAULT_DISCOUNT_PERCENTAGE);
		bd.setOnNetCheck(Constants.IGLOO_WEBSERVICE_DEFAULT_ON_NET_CHECK);
		bd.setMisPNT(Constants.IGLOO_WEBSERVICE_DEFAULT_MISPNT);

		bd.setCustAddr1(objSalesSite.getAddressName());
		bd.setCity(objSalesSite.getCity());
		bd.setState(objSalesSite.getState());
		bd.setPostalCode(objSalesSite.getZip());
		bd.setTelephoneCode(objSalesSite.getNpanxx().toString());

		bd.setTailTechnology(userSiteDesignDO.getAccessConfigDesign().getAccessTailTechnology());
		bd.setAccessInterconnect(userSiteDesignDO.getAccessConfigDesign().getAccessInterconnectTechnology());
		bd.setAccessArch(userSiteDesignDO.getAccessConfigDesign().getAccessArchitecture());
		bd.setPhysicalInterface(userSiteDesignDO.getAccessConfigDesign().getPhysicalInterferenceOptions());
		bd.setAccessBandwidth(userSiteDesignDO.getAccessConfigDesign().getSliderSpeedValue());

		WSNameValue wsNV = new WSNameValue();
		wsNV.setName(Constants.IGLOO_WEBSERVICE_DEFAULT_DEFAULT_WS_NAME);
		wsNV.setValue(Constants.IGLOO_WEBSERVICE_DEFAULT_WS_VALUE);
		WSContext wsCtx = new WSContext();
		wsCtx.getWSNameValue().add(wsNV);

		WSMessageData wsMsgData = new WSMessageData();
		wsMsgData.setMessageId(Constants.IGLOO_WEBSERVICE_DEFAULT_MESSAGE_ID_PREFIX + System.currentTimeMillis());

		WSHeader wsHeader = new WSHeader();
		wsHeader.setWSContext(wsCtx);
		wsHeader.setWSMessageData(wsMsgData);

		AccessQuoteRequest req = new AccessQuoteRequest();
		req.setAccessQuoteBody(bd);
		req.setWSHeader(wsHeader);

		GetAccessQuote quote = new GetAccessQuote();
		quote.setAccessQuoteRequest(req);
		return quote;
	}

}
