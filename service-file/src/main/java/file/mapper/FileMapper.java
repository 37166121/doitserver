package file.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * FileMapper
 *
 * @author 22510
 * @version 1.0
 * 2020/5/10 4:52
 **/
@Mapper
public interface FileMapper {
    /**
     * 添加头像
     */
    @Update("UPDATE t_user set head = ")
    void setHead(String uri);
}
