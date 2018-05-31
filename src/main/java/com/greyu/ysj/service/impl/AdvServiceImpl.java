package com.greyu.ysj.service.impl;

import com.greyu.ysj.config.Constants;
import com.greyu.ysj.config.ResultStatus;
import com.greyu.ysj.entity.AdvSwiper;
import com.greyu.ysj.entity.AdvSwiperExample;
import com.greyu.ysj.entity.CategorySecond;
import com.greyu.ysj.mapper.AdvSwiperMapper;
import com.greyu.ysj.mapper.CategorySecondMapper;
import com.greyu.ysj.model.ResultModel;
import com.greyu.ysj.service.AdvService;
import com.greyu.ysj.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.transform.Result;
import java.util.List;

/**
 * @Description:
 * @Author: gre_yu@163.com
 * @Date: Created in 10:58 2018/5/31.
 */
@Service
public class AdvServiceImpl implements AdvService {
    @Autowired
    private AdvSwiperMapper advSwiperMapper;

    @Autowired
    private CategorySecondMapper categorySecondMapper;

    @Override
    public ResultModel getAll() {
        AdvSwiperExample advSwiperExample = new AdvSwiperExample();
        AdvSwiperExample.Criteria criteria = advSwiperExample.createCriteria();

        List<AdvSwiper> advLists = this.advSwiperMapper.selectByExample(advSwiperExample);

        for (AdvSwiper adv: advLists) {
            adv.setImage(Constants.IMAGE_PREFIX_URL + adv.getImage());
        }

        return ResultModel.ok(advLists);
    }

    @Override
    public ResultModel getOne(Integer advSwiperId) {
        if (null == advSwiperId) {
            return ResultModel.error(ResultStatus.DATA_NOT_NULL);
        }

        AdvSwiper advSwiper = this.advSwiperMapper.selectByPrimaryKey(advSwiperId);

        if (null != advSwiper) {
            return ResultModel.ok(advSwiper);
        } else {
            return ResultModel.error(ResultStatus.ADV_NOT_FOUND);
        }
    }

    @Override
    public ResultModel create(String name, Integer categorySecondId, MultipartFile image) {
        if (null == name ||
                null == categorySecondId ||
                null == image) {
            return ResultModel.error(ResultStatus.DATA_NOT_NULL);
        }

        CategorySecond categorySecond = this.categorySecondMapper.selectByPrimaryKey(categorySecondId);

        if (null == categorySecond) {
            return ResultModel.error(ResultStatus.CATEGORY_NOT_FOUND);
        }

        AdvSwiper advSwiper = new AdvSwiper();

        String fileName = "";
        try {
            fileName = FileUtil.upload(image, Constants.IMAGE_SAVE_PATH);
        } catch (Exception e) {
            e.printStackTrace();;
        }
        advSwiper.setImage(fileName);
        advSwiper.setName(name);
        advSwiper.setCategorySecondId(categorySecondId);
        advSwiper.setCategoryName(categorySecond.getCategoryName());
        this.advSwiperMapper.insert(advSwiper);

        return ResultModel.ok();
    }

    @Override
    public ResultModel update(Integer advId, String name, Integer categorySecondId, MultipartFile image) {
        AdvSwiper oldAdv = this.advSwiperMapper.selectByPrimaryKey(advId);

        if (null != name) {
            oldAdv.setName(name);
        }
        if (null != categorySecondId) {
            CategorySecond categorySecond = this.categorySecondMapper.selectByPrimaryKey(categorySecondId);
            if (null != categorySecond) {
                oldAdv.setCategorySecondId(categorySecondId);
                oldAdv.setCategoryName(categorySecond.getCategoryName());
            } else {
                return ResultModel.error(ResultStatus.CATEGORY_NOT_FOUND);
            }
        }
        if (null != image) {
            String fileName = "";
            try {
                fileName = FileUtil.upload(image, Constants.IMAGE_SAVE_PATH);
            } catch (Exception e) {
                e.printStackTrace();
            }
            oldAdv.setImage(fileName);
        }

        this.advSwiperMapper.updateByPrimaryKey(oldAdv);

        return ResultModel.ok();
    }

    @Override
    public ResultModel delete(Integer advSwiperId) {
        AdvSwiper advSwiper = this.advSwiperMapper.selectByPrimaryKey(advSwiperId);
        if (null == advSwiper) {
            return ResultModel.error(ResultStatus.ADV_NOT_FOUND);
        } else {
            this.advSwiperMapper.deleteByPrimaryKey(advSwiperId);

            return ResultModel.ok();
        }
    }
}
