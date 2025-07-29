package com.example.test.entity.vo;

import com.example.test.common.Utils;
import lombok.Data;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.operation.buffer.BufferOp;

import java.util.Arrays;
import java.util.List;

@Data
public class G {

	private String type;

	private double[][] coordinates;

	private String epsg;

//	private String s;

	public String getS() {

		StringBuilder s = new StringBuilder();

		if (this.getType().equals("CIRCLE")) {
			double distance = Utils.getDistance(this.getCoordinates()[0][0],
												this.getCoordinates()[0][1],
												this.getCoordinates()[1][0],
												this.getCoordinates()[1][1]);
			GeometryFactory geometryFactory = new GeometryFactory();
			Point point = geometryFactory.createPoint(new Coordinate(this.getCoordinates()[0][0], this.getCoordinates()[0][1]));
			Geometry geometry = Utils.getBuffer(point, distance);

			s.append("SRID=").append(this.getEpsg()).append(";");
			s.append(geometry.toString());

			return String.valueOf(s);
		}

		s.append("SRID=").append(this.getEpsg()).append(";").append(this.getType()).append("(");


		if (this.getType().equals("POLYGON")) s.append("(");

		long count = Arrays.stream(this.getCoordinates()).count();
		for (int i = 0; i < count; i++) {
			double[] item = this.getCoordinates()[i];

			if (i != 0) s.append(",");

			s.append(item[0]).append(" ").append(item[1]);
		}

		s.append(")");
		if (this.getType().equals("POLYGON")) s.append(")");


		System.out.println(s);

		return String.valueOf(s);
	}
}
