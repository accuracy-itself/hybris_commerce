package de.hybris.training.questions.populators;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.type.TypeService;
import de.hybris.training.questions.data.QuestionData;
import de.hybris.training.questions.model.QuestionModel;
import org.springframework.beans.factory.annotation.Required;

/**
 * Populates {@link QuestionData} with name and code.
 */
public class QuestionDataPopulator implements Populator<QuestionModel, QuestionData> {
    private TypeService typeService;

    protected TypeService getTypeService() {
        return typeService;
    }

    @Required
    public void setTypeService(final TypeService typeService) {
        this.typeService = typeService;
    }

    @Override
    public void populate(final QuestionModel source, final QuestionData target) {
        target.setCode(source.getCode());
        target.setQuestion(source.getQuestion());
        target.setQuestionCustomer(source.getQuestionCustomer().getName());
        target.setAnswer(source.getAnswer());
        target.setAnswerCustomer(source.getAnswerCustomer().getName());
        target.setProductCode(source.getProduct().getCode());
    }
}
