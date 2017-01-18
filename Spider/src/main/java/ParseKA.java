import java.util.LinkedList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;

import com.google.gson.Gson;


public class ParseKA {
	
	private static final int  MS_PER_HOUR = 3600000;
	private static final int  MS_PER_DAY = 86400000; 
	
	public static void parse(String message){
		Gson gson = new Gson();
		KA ka = gson.fromJson(message, KA.class);
		long[] events8 = decodeBase64(ka.getEvents8());
		long[] counts = decodeBase64(ka.getCounts8());
		long[] totals8 = decodeBase64(ka.getTotals8());
		System.out.println("events "+events8.length);
		System.out.println("counts "+counts.length);
		System.out.println("totals8 "+totals8.length);
		List<Kevent> list = parseKevent(events8);
		System.out.println("size "+list.size());
		for(Kevent event :list){
			System.out.println("$$$ event "+event.coords[2]+" ; "+event.coords[3]);
		}
	}
	
	private static List<Kevent> parseKevent(long[]  data){
		 long timeslot_duration = MS_PER_HOUR / 100;
	     int sp = 0;
	     
	     long now = System.currentTimeMillis();
	     List<Kevent> keventList = new LinkedList<Kevent>();
	     int hour = (int) Math.floor((now / MS_PER_HOUR) % 24);
	     int start_time = (int) (Math.floor(now / MS_PER_DAY)*MS_PER_DAY + hour*MS_PER_HOUR);
	     while (sp < data.length) {
	            long key = data[sp++];
	          //  System.out.println("##### 40");
	            // ensure system is ddos
	            long system_id = (key >> 16) & 0xff;
	            long target_key = (key >> 8) & 0xff;
	            long source_key = (key >> 0) & 0xff;
	           System.out.println("##### system_id # "+system_id);
	            //console.assert(system_id == 8);
	            if(sp>=data.length) break;
	            double[] target_coord = decodeCoord(data[sp++]);
	            if(sp>=data.length) break;
	            double[] source_coord = decodeCoord(data[sp++]); // might be useless

	        //    System.out.println("##### 52");
	           for(;;) {
	        	//   System.out.println("##### 54");
	            	if(sp>=data.length) break;
	                long d = data[sp++];
	                long timeslot_index = d & 0xffff;
	                long timeslot_count = (d >> 16);
	               // console.assert(0 <= timeslot_index && timeslot_index < 100);

	                if (timeslot_count == 0) {
	                    // end of run
	                //	 System.out.println("##### 63 " +timeslot_count);
	                    break;
	                }

	                long timeslot_time = start_time + timeslot_duration * timeslot_index;

	                long timeslot_count_factor = 30;
	                long timeslot_count_max = 500;

	                timeslot_count = Math.min(
	                    timeslot_count * timeslot_count_factor,
	                    timeslot_count_max);

	                // XXX refactor with above
	                Kevent ke = new Kevent();
	                ke.key = key;
	                ke.remaining = ke.count = timeslot_count;

	                ke.next_event_time = timeslot_time;
	                ke.end_time = timeslot_time + timeslot_duration;

	                ke.coords = new double[]{
	                    target_coord[0],
	                    target_coord[1],
	                    source_coord[0],
	                    source_coord[1]
	                };
	              //  System.out.println("##### 68");
	                keventList.add(ke);  
	            }
	        }
	     return keventList;
	     
	}
	
	private static long[] decodeBase64(String value){
		byte[] bytearray = Base64.decodeBase64(value);
		long[] longarray = new long[bytearray.length/4+1];
		int i=0;
		int index = 0;
		while(i<bytearray.length){
			
			long tem=0;
			for(int j=0;j<4;j++)
			{
				long a = 0xff&bytearray[i];
				//System.out.println("$$$$$$$ "+a);
				tem = tem*256 + a;
			//	System.out.println("%%%%%% "+tem);
				i++;
			}
			
			//System.out.println("%%%%%% "+tem);
				
			longarray[index++]=tem;
		}
		return longarray;
	}
	
	
	 private static double decodeU16(long u){
		 double x = u&0xffff;
		 if (x >= 0x8000) x = -(0x10000 - x);
		 return x/32768.0;
	 }
	 
	private static double[] decodeCoord(long coord){
		double lon = decodeU16(coord>>0);
		double lat = decodeU16(coord>>16);
		double[] coor = new double[2];
		coor[0] = 180.0*lon;
		coor[1] = 90.0*lat;
		return coor;
	}
	
}
