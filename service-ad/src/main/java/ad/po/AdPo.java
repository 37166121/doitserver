package ad.po;

import lombok.Data;

import java.util.Date;

/**
 * AdPo
 *
 * @author 22510
 * @version 1.0
 * 2020/5/14 2:17
 **/
@Data
public class AdPo {
    private int adid;
    private String adimage;
    private String adlink;
    private boolean status;
    private Date addtimeout;
}