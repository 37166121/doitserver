package ad.service;

import ad.po.AdPo;
import org.apache.ibatis.annotations.Select;

/**
 * AdService
 *
 * @author 22510
 * @version 1.0
 * 2020/5/14 2:26
 **/
public interface AdService {
    /**
     * 广告持续时间
     * @return
     */
    int getAdTime();

    /**
     * 是否展示广告
     * @return
     */
    boolean isShowAd();

    /**
     * 获取广告
     * @return
     */
    AdPo getAd();
}
