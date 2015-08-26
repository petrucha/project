package data;
 
import java.util.Comparator;
import org.primefaces.model.SortOrder;
 
public class LazySorter implements Comparator<DeviceData> {
 
    private String sortField;
     
    private SortOrder sortOrder;
     
    public LazySorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }
 
    @SuppressWarnings("rawtypes")
	public int compare(DeviceData dd1, DeviceData dd2) {
        try {
            Object value1 = DeviceData.class.getField(this.sortField).get(dd1);
            Object value2 = DeviceData.class.getField(this.sortField).get(dd2);
 
            @SuppressWarnings("unchecked")
			int value = ((Comparable)value1).compareTo(value2);
             
            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        }
        catch(Exception e) {
            throw new RuntimeException();
        }
    }
}