package unit;

import javax.xml.bind.annotation.XmlRootElement;

import org.json.JSONObject;

import spatialindex.spatialindex.Point;

@XmlRootElement
public class SpatialObject {

	public double _lat;
	public double _lng;
	public String _text;
	
	public SpatialObject()
	{
		
	}
	
	public SpatialObject(double lat, double lng, String text)
	{
		_lat = lat;
		_lng = lng;
		_text = text;
	}
	
	@Override
	public String toString()
	{
		return "[ " + _lng + " , " + _lat + " , " + _text + " ]";
	}
	
	public Point getPoint()
	{
		double[] coor = new double[2];
		coor[0] = _lng;
		coor[1] = _lat;
		
		return new Point(coor);
	}
	
	public String getText()
	{
		return this._text;
	}
	public String getJSON () {
		String jsonObject = "\t{\n";
		jsonObject += "\t\t\"latitude\":"+ _lat+",\n";
		jsonObject += "\t\t\"longitude\":"+ _lng+",\n";
		jsonObject += "\t\t\"result\":\""+_text+"\"\n";
		jsonObject += "\t}";
		
		return jsonObject.toString();
	}
}
