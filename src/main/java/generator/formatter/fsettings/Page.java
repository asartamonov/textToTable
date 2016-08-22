package generator.formatter.fsettings;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "page")
public class Page {
    private int width;
    private int height;

    @Override
    public String toString() {
        return "Page{" +
                "width=" + width +
                ", height=" + height +
                '}';
    }

    public int getWidth() {
        return width;
    }

    @XmlElement(name = "width")
    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    @XmlElement(name = "height")
    public void setHeight(int height) {
        this.height = height;
    }
}
