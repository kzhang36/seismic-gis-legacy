package com.example.test.controller;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.test.common.GeoJsonUtils;
import com.example.test.entity.GPoint;
import com.example.test.entity.Point1;
import com.example.test.mapper.GPointMapper;
import com.example.test.mapper.Point1Mapper;
import com.example.test.service.GPointServece;
import org.geotools.feature.FeatureCollection;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 全球地震点
 */
@RestController
@RequestMapping("/gp")
public class GPointController {

	@Autowired
	private GPointServece gPointServece;

	@Autowired
	private GPointMapper gPointMapper;

	@Autowired
	private Point1Mapper point1Mapper;

	@Autowired
	ResourceLoader resourceLoader;



	@GetMapping
	public List<GPoint> index() {
		List<GPoint> list;
		list = gPointServece.list();

		return list;
	}

	@PostMapping
	public Object save(@RequestBody GPoint gPoint) {
		gPoint.setId(IdUtil.randomUUID());
		System.out.println(gPoint);
		Resource resource = resourceLoader.getResource("classpath:geojson\\China_1.json");


		LambdaQueryWrapper<GPoint> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(GPoint::getDId, gPoint.getDId());

		GPoint one = gPointServece.getOne(queryWrapper);
		Map<String, Object> properties = new HashMap<>();
		properties.put("contains", false);
		if (one == null) {

			String geoString = GeoJsonUtils.geometryToString(gPoint.getLon(), gPoint.getLat());
			gPointMapper.saveFn(gPoint, geoString);
			String path;
			try {
				path = resource.getFile().getAbsolutePath();
			} catch (IOException e) {
				System.out.println(e.getMessage());
				return null;
			}

			FeatureCollection featureCollection ;
			try {
				featureCollection = GeoJsonUtils.getFeatureCollection(path);
			} catch (FileNotFoundException e) {
				System.out.println(e.getMessage());
				return null;
			}
			assert featureCollection != null;
			properties = GeoJsonUtils.properties(gPoint.getLon(), gPoint.getLat(), featureCollection);
			properties.put("points",null);
			if ((boolean)properties.get("contains")){
				Point1 point1 = new Point1();
				BeanUtils.copyProperties(gPoint, point1);
				point1.setCity(gPoint.getLocation().substring(0, 2));
				point1Mapper.saveFn(point1, geoString);
				properties.put("points",gPoint);
			}
		}
		return properties;
	}
}
