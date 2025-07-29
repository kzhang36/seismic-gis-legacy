package com.example.test.controller;


import com.example.test.common.GeoJsonUtils;
import org.geotools.feature.FeatureCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/utils")
public class UtilsController {

	@Autowired
	ResourceLoader resourceLoader;

	/**
	 * 判断地震点是否我国范围内
	 * @param lat
	 * @param lon
	 * @return
	 */
	@GetMapping("/checkPoint")
	public Map<String, Object> checkPointInShape(@RequestParam double lat, @RequestParam double lon){

		Resource resource = resourceLoader.getResource("classpath:geojson\\China_1.json");
		String path;
		try {
			path = resource.getFile().getAbsolutePath();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return null;
		}

		FeatureCollection featureCollection = null;
		try {
			featureCollection = GeoJsonUtils.getFeatureCollection(path);
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			return null;
		}
		Map<String, Object> properties = GeoJsonUtils.properties(lat, lon, featureCollection);

		return properties;
	}
}
