package com.breze.entity.pojo.portal;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * <p>
 * 主体内容表
 * </p>
 *
 * @author leochan
 * @since 2022-10-02
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("tb_maincontent")
@ApiModel(value = "Maincontent对象", description = "主体内容表")
public class Maincontent implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主页内容模块id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("导航栏Id")
    private Long navbarId;

    @ApiModelProperty("内容模块标题")
    private String titleName;

    @ApiModelProperty("内容模块简介")
    private String titleInfo;

    @ApiModelProperty("内容模块图片")
    private String imgUrl;

    @ApiModelProperty("指定路由跳转")
    private String routerPath;

    @ApiModelProperty("按钮信息")
    private String buttonInfo;

    @ApiModelProperty(value = "创建时间", name = "createTime", example = "2022-03-01 12:00:00", notes = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间", name = "updateTime", example = "2022-03-01 12:00:00", notes = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
