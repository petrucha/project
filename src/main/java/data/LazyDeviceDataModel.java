package data;
 
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
 
/**
 * Dummy implementation of LazyDataModel that uses a list to mimic a real datasource like a database.
 */
public class LazyDeviceDataModel extends LazyDataModel<DeviceData> {
     
	private static final long serialVersionUID = 1L;
	
	private List<DeviceData> datasource;
     
    public LazyDeviceDataModel(List<DeviceData> datasource) {
        this.datasource = datasource;
    }
     
    @Override
    public DeviceData getRowData(String rowKey) {
        for(DeviceData dd : datasource) {
            if(dd.getMac().equals(rowKey))
                return dd;
        }
 
        return null;
    }
 
    @Override
    public Object getRowKey(DeviceData dd) {
        return dd.getMac();
    }
 
    @Override
    public List<DeviceData> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
        List<DeviceData> data = new ArrayList<DeviceData>();
 
        //filter
        for(DeviceData dd : datasource) {
            boolean match = true;
 
            if (filters != null) {
                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
                    try {
                        String filterProperty = it.next();
                        Object filterValue = filters.get(filterProperty);
                        String fieldValue = String.valueOf(dd.getClass().getField(filterProperty).get(dd));
 
                        if(filterValue == null || fieldValue.startsWith(filterValue.toString())) {
                            match = true;
                    }
                    else {
                            match = false;
                            break;
                        }
                    } catch(Exception e) {
                        match = false;
                    }
                }
            }
 
            if(match) {
                data.add(dd);
            }
        }
 
        //sort
        if(sortField != null) {
            Collections.sort(data, new LazySorter(sortField, sortOrder));
        }
 
        //rowCount
        int dataSize = data.size();
        this.setRowCount(dataSize);
 
        //paginate
        if(dataSize > pageSize) {
            try {
                return data.subList(first, first + pageSize);
            }
            catch(IndexOutOfBoundsException e) {
                return data.subList(first, first + (dataSize % pageSize));
            }
        }
        else {
            return data;
        }
    }
}