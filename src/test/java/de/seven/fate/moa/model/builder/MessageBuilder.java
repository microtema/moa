package de.seven.fate.moa.model.builder;

import de.seven.fate.moa.model.Message;
import de.seven.fate.model.builder.AbstractModelBuilder;

/**
 * Created by Mario on 07.05.2016.
 */
public class MessageBuilder extends AbstractModelBuilder<Message> {

    @Override
    public Message min() {
        Message min = super.min();

        min.setId(null);
        min.setCreatedDate(null);
        min.setUpdateDate(null);
        min.setVersion(null);

        return min;
    }
}
