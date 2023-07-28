package de.hybris.training.core.daos;

import de.hybris.training.questions.model.QuestionModel;

import java.util.Date;
import java.util.List;

public interface QuestionsDAO {
    List<QuestionModel> getQuestionsAfterDate(Date date);
    boolean checkAmountByDate(Date date);
}
