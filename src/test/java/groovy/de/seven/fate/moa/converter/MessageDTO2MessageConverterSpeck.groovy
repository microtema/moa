package groovy.de.seven.fate.moa.converter

import de.seven.fate.moa.converter.MessageDTO2MessageConverter
import de.seven.fate.moa.builder.MessageDTOBuilder
import spock.lang.Shared
import spock.lang.Specification

/**
 * Created by Mario on 08.05.2016.
 */
class MessageDTO2MessageConverterSpeck extends Specification {

    @Shared
    def sut = new MessageDTO2MessageConverter();

    @Shared
    def builder = new MessageDTOBuilder();

    def "convert null-dao to null-model"() {
        given: "null as dto"
        def dto = null

        when: "convert to model"
        def model = sut.convert(dto)

        then: "NullPointerException is not thrown but return null"
        notThrown(NullPointerException)
        model == null
    }

    def "convert dao to model"() {
        given: "random as dto"
        def dto = builder.random()

        when: "convert to model"
        def model = sut.convert(dto)

        then: "return model with same values properties"
        model != null
        model.title == dto.title
        model.description == dto.description
        model.image == dto.image
    }
}
