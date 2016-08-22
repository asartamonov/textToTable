package generator.formatter.fsettings;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "columns")
public class Columns {

    private List<Column> columnList;

    public List<Column> getColumnList() {
        return columnList;
    }

    @XmlElement(name = "column")
    public void setColumnList(List<Column> columnList) {
        this.columnList = columnList;
    }
}
