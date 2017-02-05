package com.att.salesexpress.webapp.service.igloo;

import java.util.List;

import com.att.salesexpress.webapp.entity.SalesSite;
import com.att.salesexpress.webapp.pojos.UserDesignSelectionDO;

public interface IglooConsumerService {
	public static final String KEY_PARAM_IGLOO_CALL_REQUIRED = "iglooCallRequired";

	List<Object> call(UserDesignSelectionDO objUserDesignSelectionDO, List<SalesSite> objSalesSite);

}