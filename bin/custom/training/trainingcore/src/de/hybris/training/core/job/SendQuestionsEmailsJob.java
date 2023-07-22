package de.hybris.training.core.job;

import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobHistoryModel;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.CronJobHistoryService;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.training.core.model.SendNewQuestionsEmailProcessModel;
import org.springframework.beans.factory.annotation.Required;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class SendQuestionsEmailsJob extends AbstractJobPerformable<CronJobModel> {
    private static final String WEBSITE_ID = "apparel-uk";
    private static final String TARGET_EMAIL = "alena.stankevich.off@gmail.com";
    private static final String DISPLAY_NAME = "some name";
    private static final String DATE_PATTERN = "YYYY-MM-DD";

    private BusinessProcessService businessProcessService;
    private ModelService modelService;
    private BaseSiteService baseSiteService;
    private CronJobHistoryService cronJobHistoryService;

    protected BusinessProcessService getBusinessProcessService() {
        return businessProcessService;
    }

    protected ModelService getModelService() {
        return modelService;
    }

    @Required
    public void setModelService(final ModelService modelService) {
        this.modelService = modelService;
    }

    @Required
    public void setBusinessProcessService(final ModelService modelService) {
        this.modelService = modelService;
    }

    @Required
    public void setBusinessProcessService(final BusinessProcessService businessProcessService) {
        this.businessProcessService = businessProcessService;
    }

    @Required
    public BaseSiteService getBaseSiteService() {
        return baseSiteService;
    }

    @Required
    public void setBaseSiteService(final BaseSiteService baseSiteService) {
        this.baseSiteService = baseSiteService;
    }

    @Required
    public void setCronJobHistoryService(final CronJobHistoryService cronJobHistoryService) {
        this.cronJobHistoryService = cronJobHistoryService;
    }

    @Override
    public PerformResult perform(final CronJobModel cronJob) {
        final SendNewQuestionsEmailProcessModel sendNewQuestionsEmailProcessModel =
                (SendNewQuestionsEmailProcessModel) getBusinessProcessService()
                .createProcess("sendNewQuestionsEmailProcess-" + System.currentTimeMillis(),
                        "sendNewQuestionsEmailProcess");
        BaseSiteModel baseSiteModel = baseSiteService.getBaseSiteForUID(WEBSITE_ID);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN);
        Date questionsDate;
        List<CronJobHistoryModel> histories = cronJobHistoryService.getCronJobHistoryBy(cronJob.getCode());
        CronJobHistoryModel lastSuccessHistory = histories.stream()
                .filter(history -> history.getStatus() == CronJobStatus.FINISHED)
                .max(Comparator.comparing(CronJobHistoryModel::getStartTime))
                .orElse(null);

        if (lastSuccessHistory != null) {
            questionsDate = lastSuccessHistory.getEndTime();
            // Use the lastSuccessTime as needed
        } else {
            questionsDate = new Date();
        }

        sendNewQuestionsEmailProcessModel.setSite(baseSiteModel);
        sendNewQuestionsEmailProcessModel.setLanguage(baseSiteModel.getDefaultLanguage());
        sendNewQuestionsEmailProcessModel.setStore(baseSiteModel.getStores().get(0));
        sendNewQuestionsEmailProcessModel.setEmail(TARGET_EMAIL);
        sendNewQuestionsEmailProcessModel.setDisplayName(DISPLAY_NAME);
        //sendNewQuestionsEmailProcessModel.setQuestionsDate(questionsDate);
        sendNewQuestionsEmailProcessModel.setQuestionsDate(simpleDateFormat.format(questionsDate));

        getModelService().save(sendNewQuestionsEmailProcessModel);
        getBusinessProcessService().startProcess(sendNewQuestionsEmailProcessModel);
        return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
    }
}
