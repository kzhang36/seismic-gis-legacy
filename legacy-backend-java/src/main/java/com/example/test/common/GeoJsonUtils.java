package com.example.test.common;

import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.geotools.geojson.feature.FeatureJSON;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.locationtech.jts.geom.*;
//import org.locationtech.jts.geom.Coordinate;
//import org.locationtech.jts.geom.Point;
//import org.locationtech.jts.geom.Polygon;
import org.opengis.feature.Feature;
import org.opengis.feature.Property;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class GeoJsonUtils {
	/**
	 * 获取区域数据集合
	 *
	 * @param geoJsonFilePath
	 * @return
	 */
	public static FeatureCollection getFeatureCollection(String geoJsonFilePath) throws FileNotFoundException {
		// 读取 GeoJson 文件
		InputStream resourceAsStream = new FileInputStream(geoJsonFilePath);
//		InputStream resourceAsStream = new FileInputStream("E:\\Edge Download\\China_1.json");
		FeatureJSON featureJSON = new FeatureJSON();
		try {
			return featureJSON.readFeatureCollection(resourceAsStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 判断指定区域集合是否包含某个点
	 * @param longitude
	 * @param latitude
	 * @param featureCollection
	 * @return
	 */
	public static boolean contains(double longitude, double latitude, FeatureCollection featureCollection) {
		FeatureIterator features = featureCollection.features();
		try {
			while (features.hasNext()) {
				Feature next = features.next();
				if (isContains(longitude, latitude, next))
					return true;
			}
		} finally {
			features.close();
		}
		return false;
	}

	/**
	 * 判断指定区域集合是否包含某个点，如果包含，则返回所需属性
	 * @param longitude
	 * @param latitude
	 * @param featureCollection
	 * @return
	 */
	public static Map<String, Object> properties(double longitude, double latitude, FeatureCollection featureCollection) {
		FeatureIterator features = featureCollection.features();
		Map<String, Object> map = new HashMap<>();
		boolean contains;
		try {
			while (features.hasNext()) {
				Feature next = features.next();
				contains = isContains(longitude, latitude, next);
				// 如果点在面内则返回所需属性
				if (contains) {
					Collection<? extends Property> value = next.getValue();
					HashMap<String, Object> properties = new HashMap<>();
					properties.put("code", next.getProperty("code").getValue());
					properties.put("cityName", next.getProperty("fullname").getValue());
					properties.put("bbox", next.getProperty("bbox").getValue());
					map.put("contains",contains);
					map.put("properties", properties);
					return map;
				}
			}
		} finally {
			features.close();
		}
		map.put("contains", false);
		return map;
	}

	private static boolean isContains(double longitude, double latitude, Feature feature) {
		// 获取边界数据
		Property geometry = feature.getProperty("geometry");
		Object value = geometry.getValue();
		// 创建坐标的point
		org.locationtech.jts.geom.GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
		Point point = geometryFactory.createPoint(new Coordinate(longitude, latitude));
		boolean contains = false;
		// 判断是单面还是多面
		if (value instanceof MultiPolygon) {
			MultiPolygon multiPolygon = (MultiPolygon) value;
			contains = multiPolygon.contains(point);
		} else if (value instanceof Polygon) {
			Polygon polygon = (Polygon) value;
			contains = polygon.contains(point);
		}
		return contains;
	}

	public static String geometryToString(double lat, double lon){
		String geoStr = "POINT" + "(" + lat + " " + lon + ")";
		return geoStr;
	}
}
