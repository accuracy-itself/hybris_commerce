package de.hybris.training.core.daos.impl;

import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.training.core.daos.QuestionsDAO;
import de.hybris.training.questions.model.QuestionModel;
import org.springframework.beans.factory.annotation.Required;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DefaultQuestionsDAO implements QuestionsDAO {
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private FlexibleSearchService flexibleSearchService;

    @Override
    public List<QuestionModel> getQuestionsAfterDate(Date date) {
        final String questionsDate = new SimpleDateFormat(DATE_FORMAT).format(date);

        final String queryString = //
                "SELECT {p:" + QuestionModel.PK + "} "//
                        + "FROM {" + QuestionModel._TYPECODE + " AS p} "
                        + "WHERE {MODIFIEDTIME} >= DATE \'" + questionsDate + "\' ";
        final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);

        return flexibleSearchService.<QuestionModel>search(query).getResult();
    }

    @Override
    public boolean checkAmountByDate(Date date) {
        boolean areQuestions = getQuestionsAfterDate(date).size() > 0;

        return areQuestions;
    }

    @Required
    public void setFlexibleSearchService(final FlexibleSearchService flexibleSearchService) {
        this.flexibleSearchService = flexibleSearchService;
    }
}
