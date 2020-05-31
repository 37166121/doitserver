package ad.mapper;

import ad.po.AdPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * AdMapper
 *
 * @author 22510
 * @version 1.0
 * 2020/5/14 2:19
 **/
@Mapper
public interface AdMapper {

    /**
     * 广告持续时间
     * @return
     */
    @Select("SELECT adtime FROM doit_system_config")
    int getAdTime();

    /**
     * 是否展示广告
     * @return
     */
    @Select("SELECT showad_switch FROM doit_system_config")
    boolean isShowAd();

    /**
     * 获取广告
     * @return
     */
    @Select("SELECT * FROM doit_system_ad LIMIT 1")
    AdPo getAd();
}
