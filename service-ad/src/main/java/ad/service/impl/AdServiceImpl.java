package ad.service.impl;

import ad.mapper.AdMapper;
import ad.po.AdPo;
import ad.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * AdServiceImpl
 *
 * @author 22510
 * @version 1.0
 * 2020/5/14 2:27
 **/
@Service
@Transactional
public class AdServiceImpl implements AdService {
    @Autowired
    AdMapper adMapper;
    @Override
    public int getAdTime() {
        return adMapper.getAdTime();
    }

    @Override
    public boolean isShowAd() {
        return adMapper.isShowAd();
    }

    @Override
    public AdPo getAd() {
        return adMapper.getAd();
    }
}
