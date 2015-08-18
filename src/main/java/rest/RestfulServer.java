package rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import entity.Device;
import entity.Record;
import rest.data.RecordData;
import rest.data.RecordDataHelper;
import service.DeviceService;
import service.RecordService;

//Sets the path to base URL + /v1
@Path("/v1")
public class RestfulServer {

	private static RecordService recordService = RecordService.getInstance();	
	private static DeviceService deviceService = DeviceService.getInstance();
	private static RecordDataHelper instanceHelper = RecordDataHelper.getInstance();

	@GET
	@Path("/records")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRecords() {
		List<Record> records = recordService.getRecords();
		RecordData[] rd = instanceHelper.recordsToRecordsData(records);
		return Response.status(200).entity(rd).build();
	}

	@GET
	@Path("/records/{device}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRecordsByDevice(@PathParam("device") String device) {
	//	return Response.status(200).entity(instance.getRecordsByDevice(device)).build();
		Device devWithRecords = deviceService.getDeviceByMac(device, true);
		List<Record> recordsList = new ArrayList<Record>(devWithRecords.getRecords());
		RecordData[] rd = instanceHelper.recordsToRecordsData(recordsList);
		return Response.status(200).entity(rd).build();
	}

	@POST
	@Path("/records")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postRecord(RecordData recordData) {
		Record record = instanceHelper.recordDataToRecord(recordData);
        if(recordService.addRecord(record)){      
    		RecordData rd= instanceHelper.recordToRecordData(record);
    		return Response.status(201).entity(rd).build();
    	}
        
        return Response.status(404).build();//HTTP error code
	}
	
}
