package de.seven.fate.moa.model;

import de.seven.fate.moa.dao.BaseEntity;
import de.seven.fate.moa.dao.IdAble;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Created by Mario on 07.05.2016.
 */
@Entity
public class Message extends BaseEntity<Long> {

    @NotNull
    private String title;

    @Lob
    @NotNull
    @Column(length = 2048)
    private String description;

    @NotNull
    private String image;

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
        Message message = (Message) o;
        return Objects.equals(title, message.title) &&
                Objects.equals(description, message.description) &&
                Objects.equals(image, message.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, image);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("title", title)
                .append("description", description)
                .append("image", image)
                .toString();
    }
}
