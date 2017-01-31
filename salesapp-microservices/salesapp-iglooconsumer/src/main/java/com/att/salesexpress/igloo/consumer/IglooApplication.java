package com.att.salesexpress.igloo.consumer;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.att.cio.commonheader.v3.WSContext;
import com.att.cio.commonheader.v3.WSContext.WSNameValue;
import com.att.cio.commonheader.v3.WSHeader;
import com.att.cio.commonheader.v3.WSMessageData;
import com.att.edb.accessquote.AccessQuoteRequest;
import com.att.edb.accessquote.AccessQuoteRequest.AccessQuoteBody;
import com.att.edb.accessquote.GetAccessQuote;
import com.att.edb.accessquote.GetAccessQuoteResponse;
import com.att.salesexpress.igloo.consumer.config.IglooConfig;
import com.att.salesexpress.igloo.consumer.service.IglooWSConsumerService;

public class IglooApplication {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(IglooConfig.class);
		IglooWSConsumerService wsclient = (IglooWSConsumerService) context
				.getBean(IglooWSConsumerService.class);

		GetAccessQuote quote = getTestAccessQuote();
		GetAccessQuoteResponse resp = wsclient.getAccessQuote(quote);
		String data = resp.getAccessQuoteResponse().toString();
		System.out.println(data);
	}

	private static GetAccessQuote getTestAccessQuote() {
		AccessQuoteBody bd = new AccessQuoteBody();
		bd.setDQID("ETH3469324");
		bd.setCountry("US");
		bd.setService(0);
		bd.setAccessType(0);
		bd.setAccessTransport(0);
		bd.setAccessBandwidth(0);
		bd.setEGRFlag(0);
		bd.setHouseNo(0);
		bd.setAccessTransport(0);
		bd.setAccessInterconnect(0);
		bd.setQueryType("Get Previous Results");
		bd.setQuoteRequestType("1");

		WSNameValue wsNV = new WSNameValue();
		wsNV.setName("Application Name");
		wsNV.setValue("ADOPT");
		WSContext wsCtx = new WSContext();
		wsCtx.getWSNameValue().add(wsNV);

		WSMessageData wsMsgData = new WSMessageData();
		wsMsgData.setMessageId("ADOPT_43798472909");

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