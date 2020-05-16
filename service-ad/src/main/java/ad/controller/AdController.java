package ad.controller;

import ad.po.AdPo;
import ad.service.AdService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AdController
 *
 * @author 22510
 * @version 1.0
 * 2020/5/14 2:28
 **/
@RestController
public class AdController {
    @Autowired
    AdService adService;

    @RequestMapping(value = "/getAdTime")
    public int getAdTime(){
        return adService.getAdTime();
    }

    @RequestMapping(value = "/isShowAd")
    public boolean isShowAd(){
        return adService.isShowAd();
    }

    @RequestMapping(value = "/getAd")
    public JSONObject getAd(){
        return JSONObject.parseObject(JSON.toJSON(adService.getAd()).toString());
    }
}
