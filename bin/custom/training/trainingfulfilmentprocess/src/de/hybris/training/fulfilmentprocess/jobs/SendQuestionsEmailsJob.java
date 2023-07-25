package de.hybris.training.fulfilmentprocess.jobs;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.processengine.model.BusinessProcessModel;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.training.fulfilmentprocess.constants.TrainingFulfilmentProcessConstants;
import de.hybris.training.questions.model.SendNewQuestionsEmailProcessModel;
import org.springframework.beans.factory.annotation.Required;
import java.util.List;

public class SendQuestionsEmailsJob extends AbstractJobPerformable<CronJobModel>
{
    private static final String EMAIL_ADDRESS = "stankevich.lenkaa@mail.ru";
    private static final String WEBSITE_ID = "apparelUK"; ////////////////////////////////////////////////////////////////////////
    private BusinessProcessService businessProcessService;
    private ModelService modelService;
    private BaseSiteService baseSiteService;

    protected BusinessProcessService getBusinessProcessService()
    {
        return businessProcessService;
    }
    protected ModelService getModelService()
    {
        return modelService;
    }

    @Required
    public void setBusinessProcessService(final ModelService modelService)
    {
        this.modelService = modelService;
    }

    @Required
    public void setBusinessProcessService(final BusinessProcessService businessProcessService)
    {
        this.businessProcessService = businessProcessService;
    }

    @Override
    public PerformResult perform(final CronJobModel cronJob)
    {
        final SendNewQuestionsEmailProcessModel sendNewQuestionsEmailProcessModel = (SendNewQuestionsEmailProcessModel)getBusinessProcessService()
            .createProcess("sendNewQuestionsEmailProcess-" + System.currentTimeMillis(),
                    "sendNewQuestionsEmailProcess");
        //sendNewQuestionsEmailProcessModel.setSite(baseSiteService.getBaseSiteForUID(WEBSITE_ID));
        sendNewQuestionsEmailProcessModel.setEndMessage("very good");
        getModelService().save(sendNewQuestionsEmailProcessModel);
        getBusinessProcessService().startProcess(sendNewQuestionsEmailProcessModel);
       /* final SendNew forgottenPasswordProcessModel = (ForgottenPasswordProcessModel) getBusinessProcessService()
                .createProcess("forgottenPassword-" + event.getCustomer().getUid() + "-" + System.currentTimeMillis(),
                        "forgottenPasswordEmailProcess");
        forgottenPasswordProcessModel.setSite(event.getSite());
        forgottenPasswordProcessModel.setCustomer(event.getCustomer());
        forgottenPasswordProcessModel.setToken(event.getToken());
        forgottenPasswordProcessModel.setLanguage(event.getLanguage());
        forgottenPasswordProcessModel.setCurrency(event.getCurrency());
        forgottenPasswordProcessModel.setStore(event.getBaseStore());
        getModelService().save(forgottenPasswordProcessModel);
        getBusinessProcessService().startProcess(forgottenPasswordProcessModel);*/

        return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
    }

    private void sendEmail() {

    }
}
