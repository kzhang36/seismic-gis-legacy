package com.example.test.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.test.entity.GPoint;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GPointMapper extends BaseMapper<GPoint> {

	@Insert("INSERT INTO global_point(id,SCALE,DEPTH,LOCATION,d_id,TIME,GEOM) VALUES(#{gPoint.scale},#{gPoint.scale},#{gPoint.depth},#{gPoint.location},#{gPoint.dId},#{gPoint.time},ST_GeomFromText(#{geoStr}, 4326))")
	void saveFn(@Param("gPoint") GPoint gPoint, @Param("geoStr") String geoStr);
	//,LON,SCALE,DEPTH,LOCATION,
}
