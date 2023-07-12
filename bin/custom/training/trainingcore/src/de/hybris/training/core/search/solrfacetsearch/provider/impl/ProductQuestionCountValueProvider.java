package de.hybris.training.core.search.solrfacetsearch.provider.impl;

import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldNameProvider;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;
import de.hybris.platform.solrfacetsearch.provider.FieldValueProvider;
import de.hybris.training.questions.model.QuestionModel;
import org.springframework.beans.factory.annotation.Required;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class ProductQuestionCountValueProvider implements FieldValueProvider, Serializable {
    private FieldNameProvider fieldNameProvider;

    @Override
    public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty, final Object model) throws FieldValueProviderException {
        if (model instanceof ProductModel) {
            final ProductModel product = (ProductModel) model;
            final Collection<FieldValue> fieldValues = new ArrayList<FieldValue>();

            // case of the indexed property is localized
            if (indexedProperty.isLocalized()) {
                // retrieve and iterate over all the configured languages
                final Collection<LanguageModel> languages = indexConfig.getLanguages();
                for (final LanguageModel language : languages) {
                    fieldValues.addAll(createFieldValue(product, language, indexedProperty));
                }
            }
            // case of the indexed property is not localized
            else {
                fieldValues.addAll(createFieldValue(product, null, indexedProperty));
            }
            return fieldValues;
        }

        throw new FieldValueProviderException("Error: item is not a Product type !");
    }

    protected List<FieldValue> createFieldValue(final ProductModel product, final LanguageModel language, final IndexedProperty indexedProperty) {
        final List<FieldValue> fieldValues = new ArrayList<FieldValue>();
        // get Questions by product
        final Set<QuestionModel> questions = product.getQuestions();
        if (questions != null) {
            // add Question Count value to the fieldValues list
            addFieldValues(fieldValues, indexedProperty, language, questions.size());
        }
        return fieldValues;
    }

    protected void addFieldValues(final List<FieldValue> fieldValues, final IndexedProperty indexedProperty, final LanguageModel language, final Object value) {
        // generate all Solr fields based on different qualifiers
        final Collection<String> fieldNames = fieldNameProvider.getFieldNames(indexedProperty, language == null ? null : language.getIsocode());
        for (final String fieldName : fieldNames) {
            fieldValues.add(new FieldValue(fieldName, value));
        }
    }

    @Required
    public void setFieldNameProvider(final FieldNameProvider fieldNameProvider) {
        this.fieldNameProvider = fieldNameProvider;
    }
}