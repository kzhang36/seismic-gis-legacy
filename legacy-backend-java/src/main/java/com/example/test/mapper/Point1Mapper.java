package com.example.test.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.test.entity.GPoint;
import com.example.test.entity.Point1;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface Point1Mapper extends BaseMapper<Point1> {

	@Select("select *,ST_X(geom)as lon,ST_Y(geom)as lat,geometrytype(geom)as type from point1")
	List<Point1> getList();

	@Select("select *,ST_X(geom)as lon,ST_Y(geom)as lat,geometrytype(geom)as type from point1 ORDER BY id DESC limit 1")
	Point1 getLastOne();

	@Select("select *,ST_X(geom)as lon,ST_Y(geom)as lat,geometrytype(geom)as type from point1 where location like #{k}")
	List<Point1> getByKeyWords(@Param("k") String k);


	@Select("select *,ST_X(geom)as lon,ST_Y(geom)as lat,geometrytype(geom)as type from point1 where id=#{id}")
	Point1 getByID(@Param("id") String id);

	/**
	 * 根据时间段查询
	 * @param s 时间起点
	 * @param e 时间终点
	 * @return 结果数据集
	 */
	@Select("select *,ST_X(geom)as lon,ST_Y(geom)as lat,geometrytype(geom)as type from point1 where to_date(time ,'yyyy-MM-dd') >= to_date(#{s},'yyyy-MM-dd') and to_date(time,'yyyy-MM-dd') <= to_date(#{e} ,'yyyy-MM-dd')")
	List<Point1> getByTimeBucket(@Param("s") String s, @Param("e") String e);

	@Select("SELECT *,ST_X(geom)as lon,ST_Y(geom)as lat,geometrytype(geom)as type from point1 WHERE ST_Intersects(geom, '${s}');")
	List<Point1> getByGeometry(@Param("s") String s);

	@Insert("INSERT INTO point1(id,SCALE,DEPTH,LOCATION,d_id,TIME,CITY,GEOM) VALUES(#{gPoint.id},#{gPoint.scale},#{gPoint.depth},#{gPoint.location},#{gPoint.dId},#{gPoint.time},#{gPoint.city},ST_GeomFromText(#{geoStr}, 4326))")
	void saveFn(@Param("gPoint") Point1 gPoint, @Param("geoStr") String geoStr);


	/**
	 * 根据经缓冲区查询
	 * @param lon 经度
	 * @param lat 纬度
	 * @param r 缓冲区半径
	 * @return 结果数据集
	 */
	@Select("select *,ST_X(geom)as lon,ST_Y(geom)as lat,geometrytype(geom)as type from point1 where" +
			" ST_Intersects(geom, ST_Transform(ST_Buffer(ST_Transform(st_pointfromtext('POINT(${lon} ${lat})',4326),3857),${r}, 'quad_segs=8'),4326))")
	List<Point1> getByBuffer(@Param("lon") Double lon, @Param("lat") Double lat,@Param("r") Integer r);


	/**
	 * 根据城市查询
	 * @return 结果数据集
	 */
	@Select("select * from by_city_view")
	List<Map<String, Object>> getByCity();
}
