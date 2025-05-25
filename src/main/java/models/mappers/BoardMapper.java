package models.mappers;

import org.apache.ibatis.annotations.Select;

public interface BoardMapper {
	
	@Select("select board_name from boards where board_id = #{board_id}")
	String selectName(String board_id);

	
}
