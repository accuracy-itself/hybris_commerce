/*
 * Copyright (c) 2021 SAP SE or an SAP affiliate company. All rights reserved.
 */
package de.hybris.training.facades.process.email.context;

import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel;
import de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.training.core.daos.QuestionsDAO;
import de.hybris.training.core.model.SendNewQuestionsEmailProcessModel;
import de.hybris.training.questions.data.QuestionData;
import de.hybris.training.questions.model.QuestionModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Velocity context for a customer email.
 */
public class SendQuestionsEmailContext extends AbstractEmailContext<SendNewQuestionsEmailProcessModel> {
    private static final String DATE_PATTERN = "YYYY-MM-DD";
    private ModelService modelService;
    private QuestionsDAO questionsDAO;
    public List<QuestionData> questions;
    private Converter<QuestionModel, QuestionData> questionConverter;

    public void setModelService(final ModelService modelService) {
        this.modelService = modelService;
    }

    public void setQuestionsDAO(final QuestionsDAO questionsDAO) {
        this.questionsDAO = questionsDAO;
    }

    public void setQuestionConverter(final Converter<QuestionModel, QuestionData> questionConverter) {
        this.questionConverter = questionConverter;
    }

    @Override
    public void init(final SendNewQuestionsEmailProcessModel sendNewQuestionsEmailProcessModel, final EmailPageModel emailPageModel) {
        super.init(sendNewQuestionsEmailProcessModel, emailPageModel);
        questions = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN);
        Date questionsDate;
        try {
            questionsDate = simpleDateFormat.parse(sendNewQuestionsEmailProcessModel.getQuestionsDate());
        } catch (ParseException e) {
            questionsDate = new Date();
        }
        questionsDAO.getQuestions(questionsDate)
                .forEach(questionModel -> questions.add(questionConverter.convert(questionModel)));
        put(EMAIL, sendNewQuestionsEmailProcessModel.getEmail());
        put(DISPLAY_NAME, sendNewQuestionsEmailProcessModel.getDisplayName());
    }

    @Override
    protected BaseSiteModel getSite(SendNewQuestionsEmailProcessModel sendNewQuestionsEmailProcessModel) {
        return sendNewQuestionsEmailProcessModel.getSite();
    }

    @Override
    protected CustomerModel getCustomer(SendNewQuestionsEmailProcessModel businessProcessModel) {
        return null;
    }

    @Override
    protected LanguageModel getEmailLanguage(SendNewQuestionsEmailProcessModel businessProcessModel) {
        return businessProcessModel.getLanguage();
    }

    public List<QuestionData> getQuestions() {
        return questions;
    }
}
