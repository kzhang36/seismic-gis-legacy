package com.example.test.common;

import com.example.test.entity.Point1;
import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.geotools.referencing.GeodeticCalculator;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.operation.buffer.BufferOp;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {

	// 按年分类

	public static List<Point1> byYear(int year, List<Point1> point1List) {
//		return point1List.stream().filter(point1 -> point1.getTime().getYear() == year).collect(Collectors.toList());
		return point1List.stream().filter(point1 -> {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime dateTime = LocalDateTime.parse(point1.getTime(), formatter);
			return dateTime.getYear() == year;
		}).collect(Collectors.toList());
	}

	public static List<Point1> byMonth(int month, List<Point1> point1List) {
//		return point1List.stream().filter(point1 -> point1.getTime().getMonth().getValue() == month).collect(Collectors.toList());
		return point1List.stream().filter(point1 -> {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime dateTime = LocalDateTime.parse(point1.getTime(), formatter);
			return dateTime.getMonth().getValue() == month;
		}).collect(Collectors.toList());
	}


	public static Point projectTransform(Point point, String source, String target) throws FactoryException, TransformException {

		MathTransform mathTransform = CRS.findMathTransform(CRS.decode(source), CRS.decode(target));

		return (Point) JTS.transform(point, mathTransform);
	}

	public static Polygon projectTransform(Polygon polygon, String source, String target) throws FactoryException, TransformException {

		MathTransform mathTransform = CRS.findMathTransform(CRS.decode(source), CRS.decode(target));

		return (Polygon) JTS.transform(polygon, mathTransform);
	}

	public static LineString projectTransform(LineString lineString, String source, String target) throws FactoryException, TransformException {

		MathTransform mathTransform = CRS.findMathTransform(CRS.decode(source), CRS.decode(target));
		return (LineString) JTS.transform(lineString, mathTransform);
	}

	/**
	 * 计算两点之间距离
	 * @param x1 第一点x
	 * @param y1 第一点y
	 * @param x2 第二点x
	 * @param y2 第二点y
	 * @return 距离
	 */
	public static Double getDistance(double x1, double y1, double x2, double y2) {

		// 84坐标系构造GeodeticCalculator
		GeodeticCalculator geodeticCalculator = new GeodeticCalculator(DefaultGeographicCRS.WGS84);

		// 起点经纬度
		geodeticCalculator.setStartingGeographicPoint(x1, y1);
		// 末点经纬度
		geodeticCalculator.setDestinationGeographicPoint(x2, y2);
		// 计算距离，单位：米
		return geodeticCalculator.getOrthodromicDistance();
	}

	/**
	 * 创建缓冲区
	 * @param p 中心点
	 * @param distance 缓冲区距离
	 * @return 结果几何体（面）
	 */
	public static Geometry getBuffer(Point p, Double distance){


		BufferOp bufferOp = new BufferOp(p);
		bufferOp.setEndCapStyle(1);

		// 米转换度
		double degree = distance / (2*Math.PI*6371004)*360;

		// 计算
		Geometry resultGeometry = bufferOp.getResultGeometry(degree);
		resultGeometry.setSRID(4326);
//		System.out.println(resultGeometry.toString());
//		System.out.println(resultGeometry.getSRID());

		return resultGeometry;
	}
}
