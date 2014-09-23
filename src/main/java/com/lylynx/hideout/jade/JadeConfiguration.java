package com.lylynx.hideout.jade;

import com.lylynx.hideout.jade.model.FormModelVariable;
import de.neuland.jade4j.exceptions.JadeCompilerException;
import de.neuland.jade4j.template.JadeTemplate;

import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: kuba
 * Date: 05.07.14
 * Time: 21:55
 */
public class JadeConfiguration extends de.neuland.jade4j.JadeConfiguration {

    @Override
    public void renderTemplate(final JadeTemplate template, final Map<String, Object> model, final Writer writer) throws JadeCompilerException {

        Map<String, Object> modelDuplicate = addResultBindingsModelVariable(model);

        super.renderTemplate(template, modelDuplicate, writer);
    }

    private Map<String, Object> addResultBindingsModelVariable(final Map<String, Object> model) {
        if (null == model || model.isEmpty()) {
            Map<String, Object> newModel = new HashMap<>();
            newModel.put("form", new FormModelVariable(newModel));
            return newModel;
        }

        model.put("form", new FormModelVariable(model));

        return model;
    }
}
