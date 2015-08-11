package rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import entity.Record;
import rest.data.RecordData;
import rest.data.RecordDataHelper;
import service.RecordService;

//Sets the path to base URL + /v1
@Path("/v1")
public class RestfulServer {

	private static RecordService instance = RecordService.getInstance();
	private static RecordDataHelper instanceHelper = RecordDataHelper.getInstance();

	@GET
	@Path("/records")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRecords() {
	//	return Response.status(200).entity(instance.getRecordsArray()).build();
		Record[] array = instance.getRecordsArray();
		//RecordData[] rd= instanceHelper.recordsToRecordsData(array);
		RecordDataHelper help = new RecordDataHelper();
		RecordData[] rd= help.recordsToRecordsData(array);
		return Response.status(200).entity(rd).build();
	}

	@GET
	@Path("/records/{device}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRecordsByDevice(@PathParam("device") String device) {
	//	return Response.status(200).entity(instance.getRecordsByDevice(device)).build();
		Record[] array = instance.getRecordsByDevice(device);
		RecordData[] rd= instanceHelper.recordsToRecordsData(array);
		return Response.status(200).entity(rd).build();
	}

	@POST
	@Path("/records")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postRecord(Record record) {
        if(instance.addRecord(record)){      
		 //  return Response.status(201).entity(record).build();
    		 RecordData rd= instanceHelper.recordToRecordData(record);
    		return Response.status(201).entity(rd).build();}
        return Response.status(404).build();//HTTP error code

	}
	
}
