/*
 *  
 * Copyright (c) 2023 SAP SE or an SAP affiliate company. All rights reserved.
 */
package de.hybris.training.questions.jalo;

import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.extension.ExtensionManager;
import de.hybris.training.questions.constants.QuestionsConstants;
import org.apache.log4j.Logger;

public class QuestionsManager extends GeneratedQuestionsManager
{
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger( QuestionsManager.class.getName() );
	
	public static final QuestionsManager getInstance()
	{
		ExtensionManager em = JaloSession.getCurrentSession().getExtensionManager();
		return (QuestionsManager) em.getExtension(QuestionsConstants.EXTENSIONNAME);
	}
	
}
