package com.att.salesexpress.webapp.service.igloo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.att.cio.commonheader.v3.WSContext;
import com.att.cio.commonheader.v3.WSContext.WSNameValue;
import com.att.cio.commonheader.v3.WSHeader;
import com.att.cio.commonheader.v3.WSMessageData;
import com.att.edb.accessquote.AccessQuoteRequest;
import com.att.edb.accessquote.AccessQuoteRequest.AccessQuoteBody;
import com.att.edb.accessquote.GetAccessQuote;
import com.att.edb.accessquote.GetAccessQuoteResponse;
import com.att.salesexpress.igloo.consumer.service.IglooWSConsumerService;
import com.att.salesexpress.webapp.entity.SalesSite;
import com.att.salesexpress.webapp.pojos.UserDesignSelectionDO;
import com.att.salesexpress.webapp.util.Constants;

/**
 * 
 * @author sw088d Sachin Wadhankar
 *
 */
@Service
public class IglooConsumerServiceImpl implements IglooConsumerService {

	@Autowired
	private IglooWSConsumerService iglooWSConsumerService;

	@Override
	public boolean call(UserDesignSelectionDO objUserDesignSelectionDO, List<SalesSite> objSalesSite) {
		GetAccessQuote quote = createAccessQuoteRequestObject(objUserDesignSelectionDO, objSalesSite);
		GetAccessQuoteResponse resp = iglooWSConsumerService.getAccessQuote(quote);
		String data = resp.getAccessQuoteResponse().getAccessQuoteBody().getCustCountry();
		return true;
	}

	private GetAccessQuote createAccessQuoteRequestObject(UserDesignSelectionDO objUserDesignSelectionDO, List<SalesSite> objSalesSite) {
		objUserDesignSelectionDO.getSiteDesignList();
		
		AccessQuoteBody bd = new AccessQuoteBody();
		bd.setCountry(Constants.IGLOO_WEBSERVICE_DEFAULT_COUNTRY);
		bd.setService(Constants.IGLOO_WEBSERVICE_DEFAULT_SERVICE_ID);
		bd.setAccessType(Constants.IGLOO_WEBSERVICE_DEFAULT_ACCESS_TYPE);// default - came to know after conversion with Vikas
		bd.setAccessTransport(Constants.IGLOO_WEBSERVICE_DEFAULT_ACCESS_TRANSPORT);// default - came to know after conversion with Vikas
		bd.setEGRFlag(Constants.IGLOO_WEBSERVICE_DEFAULT_ERG_FLAG);// default - came to know after conversion with Vikas
		bd.setHouseNo(Constants.IGLOO_WEBSERVICE_DEFAULT_HOUSE_NO);
		bd.setQuoteRequestType(Constants.IGLOO_WEBSERVICE_DEFAULT_QUOTE_REQUEST_TYPE);
		bd.setAccesArrangement(Constants.IGLOO_WEBSERVICE_DEFAULT_ACCESS_ARRANGEMENT);
		bd.setContractTerm(Constants.IGLOO_WEBSERVICE_DEFAULT_CONTRACT_TERM);
		bd.setDiscountPercentage(Constants.IGLOO_WEBSERVICE_DEFAULT_DISCOUNT_PERCENTAGE);
		bd.setOnNetCheck(Constants.IGLOO_WEBSERVICE_DEFAULT_ON_NET_CHECK);
		bd.setMisPNT(Constants.IGLOO_WEBSERVICE_DEFAULT_MISPNT);

		bd.setAccessBandwidth(10000000);
		bd.setCustAddr1("3735 W 26th St");
		bd.setCity("Chicago");
		bd.setState("IL");
		bd.setPostalCode("60623");// Zip code of use site
		
		bd.setTelephoneCode("773257");//npanxx

		bd.setTailTechnology(7);//
		bd.setAccessInterconnect(3);//
		bd.setAccessArch("1");//
		bd.setPhysicalInterface("1011");// 1010

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
