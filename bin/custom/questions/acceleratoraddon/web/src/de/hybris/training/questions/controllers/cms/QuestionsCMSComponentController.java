package de.hybris.training.questions.controllers.cms;

import de.hybris.platform.addonsupport.controllers.cms.AbstractCMSAddOnComponentController;
import de.hybris.platform.commercefacades.product.ProductFacade;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.training.storefront.controllers.ControllerConstants;
import de.hybris.platform.core.model.product.ProductModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.training.questions.model.QuestionsCMSComponentModel;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;


@Controller("QuestionsCMSComponentController")
@RequestMapping(value = ControllerConstants.Actions.Cms.QuestionsCMSComponent)
public class QuestionsCMSComponentController extends AbstractCMSAddOnComponentController<QuestionsCMSComponentModel> {
    protected static final List<ProductOption> PRODUCT_OPTIONS = Arrays.asList(ProductOption.BASIC, ProductOption.STARTING_BUNDLES);

    @Resource(name = "productFacade")
    private ProductFacade productFacade;

    @Override
    protected void fillModel(final HttpServletRequest request, final Model model, final QuestionsCMSComponentModel component) {
        final ProductModel currentProduct = getRequestContextData(request).getProduct();

        if (currentProduct != null) {
            final ProductData productData = getProductFacade().getProductForCodeAndOptions(currentProduct.getCode(), PRODUCT_OPTIONS);
            model.addAttribute("product", productData);
            String questions = productData.getQuestions().toString();
            String dfsdsf = productData.getQuestions().toString();

        }

        model.addAttribute("numberOfQuestionsToShow", component.getNumberOfQuestionsToShow());
        model.addAttribute("fontSize", component.getFontSize());
    }

    private ProductFacade getProductFacade() {
        return productFacade;
    }
}
