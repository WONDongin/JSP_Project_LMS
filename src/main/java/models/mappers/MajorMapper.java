package models.mappers;

import org.apache.ibatis.annotations.Select;

import models.others.Major;

public interface MajorMapper {

	@Select("select * from majors where major_no = #{major_no}")
	Major selectOne(String major_no);

}
