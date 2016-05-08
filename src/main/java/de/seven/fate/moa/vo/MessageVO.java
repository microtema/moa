package de.seven.fate.moa.vo;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by Mario on 08.05.2016.
 */
public class MessageVO implements Serializable {

    private Long id;
    private String title;
    private String description;
    private String image;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageVO messageVO = (MessageVO) o;
        return Objects.equals(id, messageVO.id) &&
                Objects.equals(title, messageVO.title) &&
                Objects.equals(description, messageVO.description) &&
                Objects.equals(image, messageVO.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, image);
    }
}
