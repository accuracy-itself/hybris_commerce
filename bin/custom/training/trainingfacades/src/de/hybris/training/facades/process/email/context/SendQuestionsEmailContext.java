/*
 * Copyright (c) 2021 SAP SE or an SAP affiliate company. All rights reserved.
 */
package de.hybris.training.facades.process.email.context;

import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel;
import de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commerceservices.model.process.StoreFrontCustomerProcessModel;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.training.questions.model.SendNewQuestionsEmailProcessModel;
import org.springframework.beans.factory.annotation.Required;


/**
 * Velocity context for a customer email.
 */
public class SendQuestionsEmailContext extends AbstractEmailContext<SendNewQuestionsEmailProcessModel>
{
	@Override
	public void init(final SendNewQuestionsEmailProcessModel sendNewQuestionsEmailProcessModel, final EmailPageModel emailPageModel)
	{
		super.init(sendNewQuestionsEmailProcessModel, emailPageModel);
		put(EMAIL, "alena.stankevich.off@gmail.com");
		put(DISPLAY_NAME, "some name");
	}

	@Override
	protected BaseSiteModel getSite(SendNewQuestionsEmailProcessModel businessProcessModel) {
		return null;
	}

	@Override
	protected CustomerModel getCustomer(SendNewQuestionsEmailProcessModel businessProcessModel) {
		return null;
	}

	@Override
	protected LanguageModel getEmailLanguage(SendNewQuestionsEmailProcessModel businessProcessModel) {
		return null;
	}
}
