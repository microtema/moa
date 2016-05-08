package groovy.de.seven.fate.moa.dto

import de.seven.fate.moa.dto.MessageDTO
import spock.lang.Shared
import spock.lang.Specification

import javax.xml.bind.annotation.XmlAccessType
import javax.xml.bind.annotation.XmlAccessorType
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

/**
 * Created by Mario on 08.05.2016.
 */
class MessageDTOTest extends Specification {

    @Shared
    def sut = new MessageDTO();

    def "should implement Serializable"() {
        expect:
        sut instanceof Serializable
    }

    def "should be annotated with XmlRootElement with name 'message'"() {
        expect:
        sut.class.getAnnotation(XmlRootElement)
        sut.class.getAnnotation(XmlRootElement).name() == "message"
    }

    def "should be annotated with XmlAccessorType with value XmlAccessType.FIELD"() {
        expect:
        sut.class.getAnnotation(XmlAccessorType)
        sut.class.getAnnotation(XmlAccessorType).value() == XmlAccessType.FIELD
    }

    def "is annotation present"() {
        expect:
        isAnnotationPresent == present

        where:
        isAnnotationPresent                                                       | present
        sut.class.getDeclaredField("title").isAnnotationPresent(XmlElement)       | true
        sut.class.getDeclaredField("description").isAnnotationPresent(XmlElement) | true
        sut.class.getDeclaredField("image").isAnnotationPresent(XmlElement)       | true
    }

    def "is required element"() {
        expect:
        annotation.required() == required

        where:
        annotation                                                          | required
        sut.class.getDeclaredField("title").getAnnotation(XmlElement)       | false
        sut.class.getDeclaredField("description").getAnnotation(XmlElement) | true
        sut.class.getDeclaredField("image").getAnnotation(XmlElement)       | true
    }

    def "is nillable element"() {
        expect:
        annotation.nillable() == nillable

        where:
        annotation                                                          | nillable
        sut.class.getDeclaredField("title").getAnnotation(XmlElement)       | false
        sut.class.getDeclaredField("description").getAnnotation(XmlElement) | false
        sut.class.getDeclaredField("image").getAnnotation(XmlElement)       | false
    }
}
