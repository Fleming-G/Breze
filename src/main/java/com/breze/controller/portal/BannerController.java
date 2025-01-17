package com.breze.controller.portal;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.breze.entity.pojo.portal.Banner;
import com.qiniu.common.QiniuException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.breze.common.enums.ErrorEnum;
import com.breze.common.result.Result;
import com.breze.controller.core.BaseController;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 轮播图前端控制器
 * </p>
 *
 * @author tylt6688
 * @since 2022-04-15
 */
@RestController
@RequestMapping("/breze/portal/banner")
public class BannerController extends BaseController {


    //根据id获取轮播图信息
    @GetMapping("/info/{id}")
    public Result getBannerInfo(@PathVariable Long id) {
        Banner banner = bannerService.getById(id);
        return Result.createSuccessMessage("获取轮播图信息成功", banner);
    }

    @GetMapping("/select")
    public Result select() {
        List<Banner> banners = bannerService.listOrderByDesc();
        return Result.createSuccessMessage("查询轮播图成功", banners);
    }


    @Transactional
    @PostMapping("/insert")
    public Result insert(@Validated @RequestBody Banner banner) {
        try {
            bannerService.save(banner);
            return Result.createSuccessMessage("添加轮播图成功");
        } catch (Exception e) {
            return Result.createFailMessage(ErrorEnum.UnknowError, "添加轮播图失败");
        }

    }

    @Transactional
    @PostMapping("/update")
    public Result update(@Validated @RequestBody Banner banner) {
        bannerService.updateById(banner);
        return Result.createSuccessMessage(banner);
    }

    @Transactional
    @PostMapping("/upload")
    public Result upload(@RequestParam String alt, @RequestParam MultipartFile file) throws IOException {
        if (!Objects.requireNonNull(file.getOriginalFilename()).endsWith(".png") && !Objects.requireNonNull(file.getOriginalFilename()).endsWith(".jpg")) {
            return Result.createFailMessage(ErrorEnum.FindException, "文件必须为PNG或JPG格式");
        }
        String path = qiNiuService.uploadFile(file);
        Banner banner = new Banner();
        banner.setUrl(path).setAlt(alt);
        bannerService.save(banner);
        return Result.createSuccessMessage("上传Banner轮播图成功");
    }

    @Transactional
    @PostMapping("/delete")
    public Result delete(@RequestBody String url) throws QiniuException {
        if (!url.isEmpty() && url.substring(0, 24).equals(ossConfig.getUrl())) {

            try {
                qiNiuService.deleteFile(url);
                bannerService.remove(new QueryWrapper<Banner>().eq("url", url));
                return Result.createSuccessMessage("删除Banner轮播图成功");
            } catch (Exception e) {
                bannerService.remove(new QueryWrapper<Banner>().eq("url", url));
                return Result.createSuccessMessage("已删除失效Banner轮播图");
            }

        } else {
            bannerService.remove(new QueryWrapper<Banner>().eq("url", url));
            return Result.createSuccessMessage("已删除非法Banner轮播图");
        }

    }

}
