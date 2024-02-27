package Model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement
public class PerfumeList implements Serializable {

    private List<Perfume> perfumeList;

    public PerfumeList() {}

    public PerfumeList(List<Perfume> perfumeList) {
        this.perfumeList = perfumeList;
    }

    @XmlElementWrapper(name="perfumes")
    @XmlElement(name="perfume")
    public List<Perfume> getPerfumeList() {
        return perfumeList;
    }

    public void setPerfumeList(List<Perfume> perfumeList) {
        this.perfumeList = perfumeList;
    }
}