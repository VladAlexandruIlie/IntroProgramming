import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class Row implements Iterable<String> {
    List<String> entries = new ArrayList<>();

    public Row(Iterable<String> vals) {
        vals.forEach(entries::add);
    }

    public Iterator<String> iterator() {
        return entries.iterator();
    }
}
