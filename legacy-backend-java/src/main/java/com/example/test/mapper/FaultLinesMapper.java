package com.example.test.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.test.entity.FaultLines;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FaultLinesMapper extends BaseMapper<FaultLines> {

	@Select("select id, name, time, length, st_asewkt(geom)as wkt from fault_lines")
	List<FaultLines> getList();

	@Select("select id, name, time, length, st_asewkt(geom)as wkt from fault_lines where id=#{id}")
	FaultLines getFaultLinesById(@Param("id") Integer id);
}
