package com.example.test.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.example.test.common.Utils;
import com.example.test.entity.Point1;
import com.example.test.entity.vo.E;
import com.example.test.entity.vo.G;
import com.example.test.entity.vo.S;
import com.example.test.mapper.Point1Mapper;
import com.example.test.service.Point1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 我国地震点
 */
@RestController
@RequestMapping("/point")
public class Point1Controller {


	@Autowired
	private Point1Mapper point1Mapper;

	private final Point1Service point1Service;

	public Point1Controller(Point1Service point1Service) {
		this.point1Service = point1Service;
	}

	int startMonth = 1;
	int endMonth = 12;

//	@GetMapping
//	public List<Map<Integer, Object>> getAll() {
//		int startYear = 2009;
//		int endYear = 2023;
//
//
//		return this.getDataList(startYear, endYear);
//	}


	@GetMapping
	public List<Map<String, Object>> getAllV1() {


		return getDataList1(2009, 2023);
	}

	public List<Map<Integer, Object>> getDataList(int startYear, int endYear) {
		List<Point1> point1s;
		point1s = point1Mapper.getList();

		List<Map<Integer, Object>> mapList = new ArrayList<>();

		for (; startYear <= endYear; startYear++) {
			Map<Integer, Object> m = new HashMap<>();

			List<Point1> byYear = Utils.byYear(startYear, point1s);

			for (startMonth = 1; startMonth <= endMonth; startMonth++) {
				List<Point1> byMonth = Utils.byMonth(startMonth, byYear);

				m.put(startMonth, byMonth);
			}
//			map.put(startYear,m);
			Map<Integer, Object> a = new HashMap<>();
			a.put(startYear, m);
			mapList.add(a);

		}


		return mapList;
	}

	public List<Map<String, Object>> getDataList1(int startYear, int endYear) {
		List<Point1> point1s;
		point1s = point1Mapper.getList();

		List<Map<String, Object>> mapList = new ArrayList<>();

		for (; startYear <= endYear; startYear++) {
			Map<String, Object> m = new HashMap<>();
			List<Map<String, Object>> children = new ArrayList<>();

			List<Point1> byYear = Utils.byYear(startYear, point1s);

			m.put("year", startYear);
			m.put("length", (long) byYear.size());

			for (startMonth = 1; startMonth <= endMonth; startMonth++) {
				Map<String, Object> monthPoints = new HashMap<>();
				List<Point1> byMonth = Utils.byMonth(startMonth, byYear);

				monthPoints.put("month", startMonth);
				monthPoints.put("length", (long) byMonth.size());
				monthPoints.put("points", byMonth);

				children.add(monthPoints);
			}
			m.put("children", children);

			mapList.add(m);

		}


		return mapList;
	}


	@GetMapping("/xy")
	public List<Point1> getOne() {
		return point1Mapper.getList();
	}


	@GetMapping("/rank")
	public Object getByRank() {

		List<Point1> list = point1Service.list();


		return null;
	}

	@GetMapping("/lastOne")
	public Point1 getLastOne() {


		return point1Mapper.getLastOne();
	}


	@GetMapping("/getByCity")
	public List<Map<String, Object>> getByCity() {

		return point1Mapper.getByCity();
	}

	@PostMapping("/getByCityName")
	public List<Point1> byCityNameQuery(@RequestBody S s) {

		return point1Mapper.getByKeyWords("%" + s.getK() + "%");
	}


	@GetMapping("/getByTimeBucket")
	public List<Point1> byTimeBucket(@RequestParam String start, @RequestParam String end) {
		return point1Mapper.getByTimeBucket(start, end);
	}


	@PostMapping("/getByGeometry")
	public List<Point1> byGeometry(@RequestBody G g) {
		return point1Mapper.getByGeometry(g.getS());
	}


	@PostMapping("/excelExport")
	public void ExcelExport(HttpServletResponse response, @RequestBody E e) throws IOException {
//		String[] arr = new String[]{"ID", "类型", "X", "Y", "发生地", "发生时间", "震级", "深度", "d_id", "city"};

		extracted(response, e);
	}

	private void extracted(HttpServletResponse response, E e) throws IOException {
		String f = "points.xlsx";
		String fileName = new String(f.getBytes(), StandardCharsets.ISO_8859_1);
		response.setContentType("application/vnd.ms-excel");
		response.setCharacterEncoding("utf8");
		response.setHeader("Content-disposition", "attachment;filename=" + fileName);
		EasyExcel.write(response.getOutputStream())
				.head(Point1.class)
				.excelType(ExcelTypeEnum.XLSX)
				.sheet("sheet1")
				.doWrite(e.getPoints());
	}


	@GetMapping("/id/{id}")
	public Point1 getById(@PathVariable("id") String id) {
		return point1Mapper.getByID(id);
	}


	@GetMapping("/byBuffer")
	public List<Point1> getByBuffer(@RequestParam Double lon, @RequestParam Double lat, @RequestParam Integer distance) {

		return point1Mapper.getByBuffer(lon, lat, distance);
	}

}
