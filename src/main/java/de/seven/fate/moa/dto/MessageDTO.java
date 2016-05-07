package de.seven.fate.moa.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Mario on 07.05.2016.
 */
@XmlRootElement(name = "message")
@XmlAccessorType(XmlAccessType.FIELD)
public class MessageDTO implements Serializable {

    @XmlElement(required = true, nillable = false)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    private String description;

    @XmlElement(required = true, nillable = false)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    private String image;

    @XmlElement(required = false)
    private Date pubDate;

    @XmlElement(required = false)
    private String title;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
